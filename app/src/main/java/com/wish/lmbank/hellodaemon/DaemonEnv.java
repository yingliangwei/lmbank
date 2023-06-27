package com.wish.lmbank.hellodaemon;

import android.content.Context;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/DaemonEnv.class */
public final class DaemonEnv {
    public static final int DEFAULT_WAKE_UP_INTERVAL = 360000;
    private static final int MINIMAL_WAKE_UP_INTERVAL = 180000;
    static Context sApp;
    static boolean sInitialized = false;
    static Class<? extends AbsWorkService> sServiceClass;
    private static int sWakeUpInterval = 360000;

    private DaemonEnv() {
    }

    public static void initialize(Context context, Class<? extends AbsWorkService> cls, Integer num) {
        sApp = context;
        sServiceClass = cls;
        if (num != null) {
            sWakeUpInterval = num.intValue();
        }
        sInitialized = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getWakeUpInterval() {
        return Math.max(sWakeUpInterval, (int) MINIMAL_WAKE_UP_INTERVAL);
    }
}
