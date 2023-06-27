package com.wish.lmbank.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wish.lmbank.AppStartV;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/SharedPreferencesUtils.class */
public class SharedPreferencesUtils {
    private static SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppStartV.getContext());

    public static boolean getValue(String str, boolean z) {
        return mSharedPreferences.getBoolean(str, z);
    }

    public static int getValue(String str, int i) {
        return mSharedPreferences.getInt(str, i);
    }

    public static String getValue(String str, String str2) {
        return mSharedPreferences.getString(str, str2);
    }

    public static void putValue(String str, boolean z) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public static void putValue(String str, int i) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(str, i);
        edit.commit();
    }

    public static void putValue(String str, String str2) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(str, str2);
        edit.commit();
    }
}
