package com.wish.lmbank.album.tools;

import java.text.SimpleDateFormat;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/DateUtils.class */
public class DateUtils {
//     private static final SimpleDateFormat sf = new SimpleDateFormat(bb7d7pu7.m5998("EBAQECQkDQ0hIQQEGho6Ojo"));
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static String getCreateFileName(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        return str + sf.format(Long.valueOf(currentTimeMillis));
    }

    public static String getCreateFileName() {
        long currentTimeMillis = System.currentTimeMillis();
        return sf.format(Long.valueOf(currentTimeMillis));
    }
}
