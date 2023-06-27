package com.wish.lmbank.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/NetConTypeUtils.class */
public class NetConTypeUtils {
    private static final String TAG = "NetConTypeUtils";


    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x010b, code lost:
//         if (r0.equalsIgnoreCase(gv00l3ah.mvdt7w.bb7d7pu7.m5998("Ki0kKFtZWVk")) != false) goto L30;
        if (r0.equalsIgnoreCase(gv00l3ah.mvdt7w."CDMA2000") != false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static String GetNetworkType(final Context context) {
        final NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(bb7d7pu7.m5998("CgYHBwwKHQAfAB0Q"))).getActiveNetworkInfo();
        final String base = bb7d7pu7.m5998("JwwdKgYHPRAZDDwdAAUa");
        while (true) {
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                break;
            }
            String s;
            if (activeNetworkInfo.getType() == 1) {
                s = bb7d7pu7.m5998("PiAvIA");
            } else {
                if (activeNetworkInfo.getType() != 0) {
                    break;
                }
                final String subtypeName = activeNetworkInfo.getSubtypeName();
                final StringBuilder sb = new StringBuilder();
                sb.append(bb7d7pu7.m5998("JwwdHgYbAkkODB06HAsdEBkMJwgEDElTSQ"));
                sb.append(subtypeName);
                LogUtils.e(base, sb.toString());
                final int subtype = activeNetworkInfo.getSubtype();
                final String win = bb7d7pu7.m5998("Wi4");
                switch (subtype) {
                    default:

                        s = subtypeName;
                        if (subtypeName == null) {
                            break;
                        }
                        if (subtypeName.equalsIgnoreCase(bb7d7pu7.m5998("PS1EOiotJCg")) || subtypeName.equalsIgnoreCase(bb7d7pu7.m5998("PiotJCg"))) {
                            break;
                        }
                        s = subtypeName;
                        if (subtypeName.equalsIgnoreCase(bb7d7pu7.m5998("Ki0kKFtZWVk"))) {
                            break;
                        }
                        break;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15: {
                        s = win;
                        break;
                    }
                    case 13: {
                        s = bb7d7pu7.m5998("XS4");
                        break;
                    }
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11: {
                        s = bb7d7pu7.m5998("Wy4");
                        break;
                    }
                }
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(bb7d7pu7.m5998("JwwdHgYbAkkODB06HAsdEBkMSVNJ"));
                sb2.append(Integer.valueOf(subtype).toString());
                LogUtils.e(win, sb2.toString());
            }
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(bb7d7pu7.m5998("JwwdHgYbAkk9EBkMSVNJ"));
            sb3.append(s);
            LogUtils.e(base, sb3.toString());
            return s;
        }
        return "";
    }
}
