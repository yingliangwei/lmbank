package com.wish.lmbank.utils;

import android.os.Handler;
import android.os.Looper;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/HandlerUtils.class */
public class HandlerUtils {
    private static Handler mHandler;

    static {
            mHandler = new Handler(Looper.getMainLooper());
    }

    public static Handler getMainThreadHandler() {
        return mHandler;
    }
}
