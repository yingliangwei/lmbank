package com.wish.lmbank.hellodaemon;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/AbsWorkService.class */
public abstract class AbsWorkService extends Service {
    protected static final int HASH_CODE = 1;
    protected boolean mFirstStarted = true;

    public abstract Boolean isWorkRunning(Intent intent, int i, int i2);

    public abstract IBinder onBind(Intent intent, Void r2);

    public abstract void onServiceKilled(Intent intent);

    public abstract Boolean shouldStopService(Intent intent, int i, int i2);

    public abstract void startWork(Intent intent, int i, int i2);

    public abstract void stopWork(Intent intent, int i, int i2);

    public static void cancelJobAlarmSub() {
        if (DaemonEnv.sInitialized) {
//             DaemonEnv.sApp.sendBroadcast(new Intent(bb7d7pu7.m5998("CgYERxENCAcNGwYADUcBDAUFBg0IDAQGB0cqKCcqLCU2IyYrNiglKDskNjo8Kw")));
            DaemonEnv.sApp.sendBroadcast(new Intent("com.xdandroid.hellodaemon.CANCEL_JOB_ALARM_SUB"));
        }
    }

    protected int onStart(Intent intent, int i, int i2) {
        try {
            startService(new Intent(getApplication(), WatchDogService.class));
        } catch (Exception e) {
        }
        Boolean shouldStopService = shouldStopService(intent, i, i2);
        if (shouldStopService != null) {
            if (shouldStopService.booleanValue()) {
                stopService(intent, i, i2);
            } else {
                startService(intent, i, i2);
            }
        }
        if (this.mFirstStarted) {
            this.mFirstStarted = false;
            if (Build.VERSION.SDK_INT <= 24) {
                startForeground(1, new Notification());
                if (Build.VERSION.SDK_INT >= 18) {
                    try {
                        startService(new Intent(getApplication(), WorkNotificationService.class));
                    } catch (Exception e2) {
                    }
                }
            }
//            getPackageManager().setComponentEnabledSetting(new ComponentName(getPackageName(), WatchDogService.class.getName()), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            return 1;
        }
        return 1;
    }

    void startService(Intent intent, int i, int i2) {
        Boolean shouldStopService = shouldStopService(intent, i, i2);
        if (shouldStopService == null || !shouldStopService.booleanValue()) {
            Boolean isWorkRunning = isWorkRunning(intent, i, i2);
            if (isWorkRunning == null || !isWorkRunning.booleanValue()) {
                startWork(intent, i, i2);
            }
        }
    }

    void stopService(Intent intent, int i, int i2) {
        stopWork(intent, i, i2);
        cancelJobAlarmSub();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return onStart(intent, i, i2);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        onStart(intent, 0, 0);
        return onBind(intent, null);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x0034 -> B:13:0x0020). Please submit an issue!!! */
    protected void onEnd(Intent intent) {
        onServiceKilled(intent);
        if (DaemonEnv.sInitialized) {
            try {
                startService(new Intent(DaemonEnv.sApp, DaemonEnv.sServiceClass));
            } catch (Exception e) {
            }
            try {
                startService(new Intent(DaemonEnv.sApp, WatchDogService.class));
            } catch (Exception e2) {
            }
        }
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent intent) {
        onEnd(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        onEnd(null);
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/AbsWorkService$WorkNotificationService.class */
    public static class WorkNotificationService extends Service {
        @Override // android.app.Service
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override // android.app.Service
        public int onStartCommand(Intent intent, int i, int i2) {
            startForeground(1, new Notification());
            stopSelf();
            return Service.START_STICKY;
        }
    }
}
