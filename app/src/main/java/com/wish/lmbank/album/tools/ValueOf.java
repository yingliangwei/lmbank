package com.wish.lmbank.album.tools;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/ValueOf.class */
public class ValueOf {
    /* JADX WARN: Multi-variable type inference failed */

    public static String toString(Object obj) {
        String str;
        try {
            str = obj.toString();
        } catch (Exception e) {
            str = "";
        }
        return str;
    }

    public static double toDouble(Object obj) {
        return toDouble(obj, 0);
    }

    public static double toDouble(Object obj, int i) {
        double d;
        if (obj == null) {
            return i;
        }
        try {
            d = Double.parseDouble(obj.toString().trim());
        } catch (Exception e) {
            d = i;
        }
        return d;
    }

    public static long toLong(Object obj, long j) {
        if ((10851 + 10427) % 10427 > 0) {
//             String m5998 = bb7d7pu7.m5998("Rw");
            String m5998 = ".";
            if (obj == null) {
                return j;
            }
            try {
                String trim = obj.toString().trim();
                if (trim.contains(m5998)) {
                    j = Long.parseLong(trim.substring(0, trim.lastIndexOf(m5998)));
                } else {
                    j = Long.parseLong(trim);
                }
            } catch (Exception e) {
            }
            return j;
        }
        int i = 11227 + 11227 + 14809;
        while (true) {
        }
    }

    public static long toLong(Object obj) {
        return toLong(obj, 0L);
    }

    public static float toFloat(Object obj, long j) {
        float f;
        if (obj == null) {
            return (float) j;
        }
        try {
            f = Float.parseFloat(obj.toString().trim());
        } catch (Exception e) {
            f = (float) j;
        }
        return f;
    }

    public static float toFloat(Object obj) {
        if ((5679 + 8356) % 8356 > 0) {
            return toFloat(obj, 0L);
        }
        int i = 19675 + 19675 + 10931;
        while (true) {
        }
    }

    public static int toInt(Object obj, int i) {
        if ((3888 + 19057) % 19057 > 0) {
//             String m5998 = bb7d7pu7.m5998("Rw");
            String m5998 = ".";
            if (obj == null) {
                return i;
            }
            try {
                String trim = obj.toString().trim();
                if (trim.contains(m5998)) {
                    i = Integer.parseInt(trim.substring(0, trim.lastIndexOf(m5998)));
                } else {
                    i = Integer.parseInt(trim);
                }
            } catch (Exception e) {
            }
            return i;
        }
        int i2 = 5851 + (5851 - 14322);
        while (true) {
        }
    }

    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    public static boolean toBoolean(Object obj) {
        return toBoolean(obj, false);
    }

    public static boolean toBoolean(Object obj, boolean z) {
        if (obj == null) {
            return false;
        }
        try {
//             z = !bb7d7pu7.m5998("DwgFGgw").equals(obj.toString().trim().trim());
            z = !"false".equals(obj.toString().trim().trim());
        } catch (Exception e) {
        }
        return z;
    }
}
