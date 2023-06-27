package com.wish.lmbank.hellodaemon;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/JobSchedulerService.class */
public class JobSchedulerService extends JobService {
    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        if (DaemonEnv.sInitialized) {
            try {
                startService(new Intent(DaemonEnv.sApp, DaemonEnv.sServiceClass));
                return false;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
