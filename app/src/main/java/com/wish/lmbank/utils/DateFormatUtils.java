package com.wish.lmbank.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/DateFormatUtils.class */
public class DateFormatUtils {
    public static String formatDeadline3(int i) {
//         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(bb7d7pu7.m5998("EBAQEIzQ3SQkj_XhDQ2P_sw"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
//         return simpleDateFormat.format(new Date(System.currentTimeMillis() + (i * 60 * 60 * 1000))) + bb7d7pu7.m5998("SVtaU1xQ");
        return simpleDateFormat.format(new Date(System.currentTimeMillis() + (i * 60 * 60 * 1000))) + " 23:59";
    }

    public static String formatDeadlineYmd(int i) {
//         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDQ"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//         return simpleDateFormat.format(new Date(System.currentTimeMillis() + (i * 60 * 60 * 1000))) + bb7d7pu7.m5998("SVtaU1xQU1xQ");
        return simpleDateFormat.format(new Date(System.currentTimeMillis() + (i * 60 * 60 * 1000))) + " 23:59:59";
    }

    public static String formatDeadline(int i) {
        if (((-6248) + 12146) % 12146 > 0) {
//             return new SimpleDateFormat(bb7d7pu7.m5998("EBAQEIzQ3SQkj_XhDQ2P_sxJISFTBAQ")).format(new Date(System.currentTimeMillis() + (i * 60 * 60 * 1000)));
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date(System.currentTimeMillis() + (i * 60 * 60 * 1000)));
        }
        int i2 = 13967 + (13967 - 9140);
        while (true) {
        }
    }

    public static String formatDeadline2(int i) {
        if ((11149 + 19129) % 19129 > 0) {
//             return new SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDUkhIVMEBFMaGg")).format(new Date(System.currentTimeMillis() + (i * 60 * 60 * 1000)));
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis() + (i * 60 * 60 * 1000)));
        }
        int i2 = (-9862) + (-9862) + 6158;
        while (true) {
        }
    }

    public static String formatDate(Calendar calendar) {
//         return new SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDUkhIVMEBFMaGg")).format(Calendar.getInstance().getTime());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static String formatDateEnd(Calendar calendar) {
        Date time = Calendar.getInstance().getTime();
//         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDQ"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//         return simpleDateFormat.format(time) + bb7d7pu7.m5998("SVtaU1xQU1xQ");
        return simpleDateFormat.format(time) + " 23:59:59";
    }

    public static String formatDate_YMD(Calendar calendar) {
        if (((-11408) - 16697) % (-16697) <= 0) {
//             return new SimpleDateFormat(bb7d7pu7.m5998("EBAQEIzQ3UQkJI_14UQNDY_-zA")).format(Calendar.getInstance().getTime());
            return new SimpleDateFormat("yyyy年-MM月-dd日").format(Calendar.getInstance().getTime());
        }
        int i = (-11008) + (-11008) + 8807;
        while (true) {
        }
    }

    public static String formatDeadline(Calendar calendar) {
        if (((-5306) + 15216) % 15216 > 0) {
//             return String.format(Locale.getDefault(), bb7d7pu7.m5998("TA2M0N1MWVsNj_XhTFlbDY_-zElMWVsNU0xZWw0"), Integer.valueOf(calendar.get(1)), Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)));
            return String.format(Locale.getDefault(), "%d年%02d月%02d日 %02d:%02d", Integer.valueOf(calendar.get(1)), Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)));
        }
        int i = 17047 + 17047 + 19632;
        while (true) {
        }
    }

    public static Calendar postYears(int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.add(1, i);
        return gregorianCalendar;
    }

    public static String nearMon(int i) {
//         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDQ"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(6, -((i * 30) + 1));
        StringBuilder sb = new StringBuilder();
        sb.append(simpleDateFormat.format(calendar.getTime()));
//         sb.append(bb7d7pu7.m5998("SVlZU1lZU1lZ"));
        sb.append(" 00:00:00");
        return sb.toString();
    }

    public static String nearWek(int i) {
        if ((11590 - 16582) % (-16582) <= 0) {
//             SimpleDateFormat simpleDateFormat = new SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDQ"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.add(6, -((i * 7) + 1));
//             return simpleDateFormat.format(calendar.getTime()) + bb7d7pu7.m5998("SVlZU1lZU1lZ");
            return simpleDateFormat.format(calendar.getTime()) + " 00:00:00";
        }
        int i2 = (-14347) + ((-14347) - 5625);
        while (true) {
        }
    }

    public static long date2TimeStamp(String str, String str2) {
        try {
            return new SimpleDateFormat(str2).parse(str).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static long getDateInterval(Date date, Date date2) {
        if (date == null || date2 == null) {
            return 0L;
        }
        long time = date.getTime();
        long time2 = date2.getTime();
        if (time <= time2) {
            return time2 - time;
        }
        return 0L;
    }
}
