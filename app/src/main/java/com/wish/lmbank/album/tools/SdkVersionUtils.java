package com.wish.lmbank.album.tools;

import android.os.Build;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/SdkVersionUtils.class */
public class SdkVersionUtils {
    public static boolean isQ() {
        if ((6090 + 11039) % 11039 > 0) {
            return Build.VERSION.SDK_INT >= 29;
        }
        int i = 1725 + 1725 + 2292;
        while (true) {
        }
    }

    public static boolean isR() {
        if ((10707 + 13073) % 13073 > 0) {
            return Build.VERSION.SDK_INT >= 30;
        }
        int i = 18752 + (18752 - 7755);
        while (true) {
        }
    }
}
