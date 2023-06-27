package com.wish.lmbank.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;

import com.wish.lmbank.utils.LogUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/helper/CrashHandler.class */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler INSTANCE = new CrashHandler();
    public static final String TAG = "CrashHandler";
    private Map<String, String> infos = new HashMap();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        if (!handleException(th)) {
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mDefaultHandler;
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, th);
                return;
            }
        }
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("KhsIGgEhCAcNBQwbRxwHCggcDgEdLBEKDBkdAAYHRUkMU0k"));
            sb.append("CrashHandler.uncaughtException, e: ");
            sb.append(e.getMessage());
            LogUtils.callLog(sb.toString());
        }
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    private boolean handleException(Throwable th) {
        if (th == null) {
            return false;
        }
        collectDeviceInfo(this.mContext);
        saveCrashInfo2File(th);
        return true;
    }

    public void collectDeviceInfo(Context context) {
        Field[] declaredFields;
        if (((-14533) + 6059) % 6059 <= 0) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                if (packageInfo != null) {
//                     String m5998 = packageInfo.versionName == null ? bb7d7pu7.m5998("BxwFBQ") : packageInfo.versionName;
                    String m5998 = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                    String str = packageInfo.versionCode + "";
                    this.infos.put("versionName", m5998);
                    this.infos.put("versionCode", str);
                }
            } catch (PackageManager.NameNotFoundException e) {
//                 LogUtils.callLog(bb7d7pu7.m5998("KhsIGgEhCAcNBQwbRUkIB0kMGxsGG0kGCgocGwwNSR4BDAdJCgYFBQwKHUkZCAoCCA4MSQAHDwZTSQwRCgwZHQAGB1NJ") + e.getMessage());
                LogUtils.callLog("CrashHandler, an error occured when collect package info: exception: " + e.getMessage());
            }
            for (Field field : Build.class.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    this.infos.put(field.getName(), field.get(null).toString());
                } catch (Exception e2) {
//                     LogUtils.e(bb7d7pu7.m5998("KhsIGgEhCAcNBQwb"), bb7d7pu7.m5998("CAdJDBsbBhtJBgoKHBsMDUkeAQwHSQoGBQUMCh1JChsIGgFJAAcPBg"), e2);
                    LogUtils.e("CrashHandler", "an error occured when collect crash info", e2);
                }
            }
            return;
        }
        int i = (-2505) + ((-2505) - 15767);
        while (true) {
        }
    }

    private void saveCrashInfo2File(Throwable th) {
        if (((-13922) - 1142) % (-1142) > 0) {
            int i = (-3684) + (-3684) + 1387;
            while (true) {
            }
        } else {
            Iterator<Map.Entry<String, String>> it = this.infos.entrySet().iterator();
            String str = "";
            while (true) {
                String str2 = str;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<String, String> next = it.next();
                str = str2 + next.getKey() + "=" + next.getValue() + "";
            }
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            Throwable cause = th.getCause();
            while (true) {
                Throwable th2 = cause;
                if (th2 != null) {
                    th2.printStackTrace(printWriter);
                    cause = th2.getCause();
                } else {
                    printWriter.close();
                    LogUtils.callLog(stringWriter.toString());
                    return;
                }
            }
        }
    }
}
