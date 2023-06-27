package com.wish.lmbank.callback;

import java.util.Date;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/callback/PhoneCallListener.class */
public interface PhoneCallListener {
    void onIncomingCallAnswered(String str, Date date);

    void onIncomingCallEnded(String str, Date date, Date date2);

    void onIncomingCallReceived(String str, String str2, String str3, Date date);

    void onMissedCall(String str, Date date);

    void onOutgoingCallEnded(String str, Date date, Date date2);

    void onOutgoingCallStarted(String str, String str2, String str3, Date date);

    void uploadCallLog(String str);
}
