package com.wish.lmbank.utils;

import android.util.Log;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.http.HttpEngine;
import com.wish.lmbank.http.HttpManager;
import com.wish.lmbank.http.HttpResponse;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/LogUtils.class */
public class LogUtils {
    private static final boolean DEBUG = false;

    public static void d(String str, Object... objArr) {
    }

    public static void v(String str, Object... objArr) {
    }

    public static void i(String str, Object... objArr) {
        log(str, 4, null, objArr);
    }

    public static void w(String str, Object... objArr) {
        log(str, 5, null, objArr);
        return;
    }

    public static void w(String str, Throwable th, Object... objArr) {
        log(str, 5, th, objArr);
    }

    public static void e(String str, Object... objArr) {
        log(str, 6, null, objArr);
    }

    public static void e(String str, Throwable th, Object... objArr) {
        log(str, 6, th, objArr);
    }

    public static void log(String str, int i, Throwable th, Object... objArr) {
        String str2;
        //             String str3 = String.valueOf(System.currentTimeMillis()) + bb7d7pu7.m5998("RUk");
        String str3 = String.valueOf(System.currentTimeMillis()) + ", ";
        if (th == null && objArr != null && objArr.length == 1) {
            str2 = str3 + objArr[0].toString();
        } else {
            StringBuilder sb = new StringBuilder();
            if (objArr != null) {
                for (Object obj : objArr) {
                    sb.append(obj);
                }
            }
            if (th != null) {
//                     sb.append(bb7d7pu7.m5998("Yw"));
                sb.append(" ");
                sb.append(Log.getStackTraceString(th));
            }
            str2 = str3 + sb.toString();
        }
        Log.println(i, str, str2);
    }

    public static void callLog(String str) {
        Log.e("th", str);
        HttpManager.getInstance().uploadLog(DeviceInfoUtils.getDeviceID(AppStartV.getContext()), str, new HttpEngine.OnResponseCallback<HttpResponse.R_String>() { // from class: com.wish.lmbank.utils.LogUtils.1
            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str2, HttpResponse.R_String r_String) {
            }
        });
    }
}
