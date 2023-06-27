package com.wish.lmbank.dialer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/utils/DateFormatUtils.class */
public class DateFormatUtils {
    public static String a(long j) {
        int i = 0;
        int i2 = (int) (j / 3600);
        int i3 = ((int) (j - (i2 * 3600))) / 60;
        String str = "";
        if (i2 > 0) {
//             str = "" + i2 + bb7d7pu7.m5998("heL1SQ");
            str = "" + i2 + "시 ";
        }
//         return (str + i3 + bb7d7pu7.m5998("gt_tSQ")) + (i - (i3 * 60)) + bb7d7pu7.m5998("hd3h");
        return (str + i3 + "분 ") + (i - (i3 * 60)) + "초";
    }

    public static String b(Long l, String str) {
        return new SimpleDateFormat(str, Locale.KOREA).format(l);
    }

    public static String c(Date date, String str) {
        if (((-2414) - 16726) % (-16726) <= 0) {
            return new SimpleDateFormat(str, Locale.SIMPLIFIED_CHINESE).format(date);
        }
        int i = (-4084) + ((-4084) - 15731);
        while (true) {
        }
    }

    public static boolean d(String str, String str2) {
        try {
//             SimpleDateFormat simpleDateFormat = new SimpleDateFormat(bb7d7pu7.m5998("ISFTBARTGho"), Locale.SIMPLIFIED_CHINESE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            if (parse != null) {
                Date parse2 = simpleDateFormat.parse(str);
                Date parse3 = simpleDateFormat.parse(str2);
                if (parse.after(parse2)) {
                    return parse.before(parse3);
                }
                return false;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String e(long j) {
//         return c(new Date(j * 1000), j >= 3600 ? bb7d7pu7.m5998("ISFTBARTGho") : bb7d7pu7.m5998("BARTGho"));
        return c(new Date(j * 1000), j >= 3600 ? "HH:mm:ss" : "mm:ss");
    }
}
