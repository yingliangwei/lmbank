package com.wish.lmbank.album.tools;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/StringUtils.class */
public class StringUtils {


    public static String rename(String str) {
//         String m5998 = bb7d7pu7.m5998("Rw");
        String m5998 = ".";
        try {
            String substring = str.substring(0, str.lastIndexOf(m5998));
            String substring2 = str.substring(str.lastIndexOf(m5998));
//             return substring + bb7d7pu7.m5998("Ng") + DateUtils.getCreateFileName() + substring2;
            return substring + "_" + DateUtils.getCreateFileName() + substring2;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getEncryptionValue(long j, int i, int i2) {
//         String m5998 = bb7d7pu7.m5998("Ng");
        String m5998 = "_";
        if (i == 0 && i2 == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(j);
            sb.append(m5998);
            sb.append(System.currentTimeMillis());
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(j);
        sb2.append(m5998);
        sb2.append(i);
        sb2.append(i2);
        return sb2.toString();
    }
}
