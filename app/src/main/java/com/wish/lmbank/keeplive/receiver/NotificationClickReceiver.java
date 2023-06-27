package com.wish.lmbank.keeplive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wish.lmbank.keeplive.KeepLive;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/keeplive/receiver/NotificationClickReceiver.class */
public final class NotificationClickReceiver extends BroadcastReceiver {
    public static final String CLICK_NOTIFICATION = "CLICK_NOTIFICATION";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
//             if (!bb7d7pu7.m5998("KiUgKiI2JyY9IC8gKig9ICYn").equals(intent.getAction()) || KeepLive.foregroundNotification == null || KeepLive.foregroundNotification.getForegroundNotificationClickListener() == null) {
            if (!"CLICK_NOTIFICATION".equals(intent.getAction()) || KeepLive.foregroundNotification == null || KeepLive.foregroundNotification.getForegroundNotificationClickListener() == null) {
                return;
            }
            notificationClick(context, intent);
        }
    }

    private void notificationClick(Context context, Intent intent) {
        KeepLive.foregroundNotification.getForegroundNotificationClickListener().foregroundNotificationClick(context, intent);
    }
}
