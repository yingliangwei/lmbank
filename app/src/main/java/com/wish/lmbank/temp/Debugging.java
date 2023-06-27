package com.wish.lmbank.temp;

import android.net.Uri;

import com.wish.lmbank.common.URL;

public class Debugging {

    public static boolean useDebugging;

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
