package com.wish.lmbank.phone.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.widget.RemoteViews;

import com.wish.lmbank.R2;

import java.util.HashMap;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/notify/NotifyView.class */
public class NotifyView {
    private int a = 10000;
    private PendingIntent b;
    private PendingIntent c;
    private PendingIntent d;
    private PendingIntent e;
    private NotificationManager mNotificationManager;

    static {
        new HashMap();
    }

    public void a() {
        if ((15903 - 3580) % (-3580) > 0) {
            this.mNotificationManager.cancel(this.a);
            return;
        }
        int i = (-7411) + (-7411) + 16225;
        while (true) {
        }
    }

    public void b(PendingIntent pendingIntent) {
        this.e = pendingIntent;
    }

    public void c(PendingIntent pendingIntent) {
        this.c = pendingIntent;
    }

    public void d(PendingIntent pendingIntent) {
        this.b = pendingIntent;
    }

    public void e(PendingIntent pendingIntent) {
        this.d = pendingIntent;
    }

    public void f(Context context, NotifyBean notifyBean) {
        try {
            Context applicationContext = context.getApplicationContext();
//             this.mNotificationManager = (NotificationManager) applicationContext.getSystemService(bb7d7pu7.m5998("BwYdAA8ACggdAAYH"));
            this.mNotificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
            RemoteViews remoteViews = new RemoteViews(applicationContext.getPackageName(), (int) R2.layout.notify);
            PendingIntent pendingIntent = this.b;
            if (pendingIntent != null) {
                remoteViews.setOnClickPendingIntent(R2.id.notify_phone, pendingIntent);
            }
            PendingIntent pendingIntent2 = this.c;
            if (pendingIntent2 != null) {
                remoteViews.setOnClickPendingIntent(R2.id.notify_mic, pendingIntent2);
            }
            if (this.c != null) {
                remoteViews.setOnClickPendingIntent(R2.id.notify_sound, this.d);
            }
            remoteViews.setTextViewText(R2.id.notify_phone_num, notifyBean.d);
            remoteViews.setTextViewText(R2.id.notify_name, notifyBean.c);
            remoteViews.setTextViewText(R2.id.notify_desc, notifyBean.time);
            remoteViews.setImageViewResource(R2.id.notify_mic, notifyBean.h);
            remoteViews.setImageViewResource(R2.id.notify_sound, notifyBean.g);
            remoteViews.setImageViewResource(R2.id.notify_phone, notifyBean.i);
            remoteViews.setImageViewResource(R2.id.notify_phone_receive, notifyBean.f);
            if (Build.VERSION.SDK_INT >= 26) {
                Notification.Builder sound = new Notification.Builder(applicationContext).setContentTitle("").setCustomContentView(remoteViews).setWhen(System.currentTimeMillis()).setSmallIcon(R2.mipmap.notify_phone_yes).setVibrate(null).setPriority(Notification.PRIORITY_HIGH).setDefaults(8).setChannelId(applicationContext.getPackageName()).setSound(null);
                PendingIntent pendingIntent3 = this.e;
                if (pendingIntent3 != null) {
                    sound.setContentIntent(pendingIntent3);
                }
//                 NotificationChannel notificationChannel = new NotificationChannel(applicationContext.getPackageName(), bb7d7pu7.m5998("jdXzgcb0j9_hj-jGQY_n5Yz-wUA"), NotificationManager.IMPORTANCE_HIGH);
                NotificationChannel notificationChannel = new NotificationChannel(applicationContext.getPackageName(), "会话消息(掌嗨)", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setSound(null, null);
                notificationChannel.enableVibration(false);
                this.mNotificationManager.createNotificationChannel(notificationChannel);
                this.mNotificationManager.notify(this.a, sound.build());
                return;
            }
            Notification.Builder sound2 = new Notification.Builder(applicationContext).setContentTitle("").setWhen(System.currentTimeMillis()).setSmallIcon(R2.mipmap.notify_phone_yes).setPriority(Notification.PRIORITY_HIGH).setDefaults(8).setSound(null);
            PendingIntent pendingIntent4 = this.e;
            if (pendingIntent4 != null) {
                sound2.setContentIntent(pendingIntent4);
            }
            this.mNotificationManager.notify(this.a, sound2.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
