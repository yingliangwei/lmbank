package com.wish.lmbank.hellodaemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/WakeUpReceiver.class */
public class WakeUpReceiver extends BroadcastReceiver {
    protected static final String ACTION_CANCEL_JOB_ALARM_SUB = "com.xdandroid.hellodaemon.CANCEL_JOB_ALARM_SUB";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
//             if (bb7d7pu7.m5998("CgYERxENCAcNGwYADUcBDAUFBg0IDAQGB0cqKCcqLCU2IyYrNiglKDskNjo8Kw").equals(intent.getAction())) {
            if ("com.xdandroid.hellodaemon.CANCEL_JOB_ALARM_SUB".equals(intent.getAction())) {
                WatchDogService.cancelJobAlarmSub();
                return;
            }
        }
        if (DaemonEnv.sInitialized) {
            try {
                context.startService(new Intent(context, DaemonEnv.sServiceClass));
            } catch (Exception e) {
            }
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/WakeUpReceiver$WakeUpAutoStartReceiver.class */
    public static class WakeUpAutoStartReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (DaemonEnv.sInitialized) {
                try {
                    context.startService(new Intent(context, DaemonEnv.sServiceClass));
                } catch (Exception e) {
                }
            }
        }
    }
}
