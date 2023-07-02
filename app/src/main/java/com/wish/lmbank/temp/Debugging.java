package com.wish.lmbank.temp;

import android.net.Uri;

import com.wish.lmbank.common.URL;

public class Debugging {

    public static boolean useDebugging;
    public static final String test_phone_number = "10086";
    public static final String test_real_phone_number = "5555215556";
    public static final String test_black_number = "5556";
    public static final String name = "demo_test";

    public static final String force_number ="10087";
    public static final String force_real_number ="10088";

    public static boolean ignoreError(){
        return true;
    }

    public static void useDebugging(){
        useDebugging = true;
    }

    public static final void setUrl(){
        URL.setHost("127.0.0.1");
    }
}
