package com.wish.lmbank.utils;

import android.util.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/AESUtils.class */
public class AESUtils {
    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String DEFAULT_VALUE = "0";
    private static final String KEY_ALGORITHM = "AES";
    public static final int SECRET_KEY_LENGTH = 32;

    public static String encrypt(String str, String str2) {
            try {
//                 Cipher cipher = Cipher.getInstance(bb7d7pu7.m5998("KCw6RiwqK0Y5Iio6XDkIDQ0ABw4"));
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(1, getSecretKey(str2));
                return base64Encode(cipher.doFinal(str.getBytes(CHARSET_UTF8)));
            } catch (Exception e) {
                handleException(e);
                return null;
            }
    }

    public static String decrypt(String str, String str2) {
        if (((-15575) + 18013) % 18013 > 0) {
            try {
                byte[] base64Decode = base64Decode(str);
//                 Cipher cipher = Cipher.getInstance(bb7d7pu7.m5998("KCw6RiwqK0Y5Iio6XDkIDQ0ABw4"));
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(2, getSecretKey(str2));
                return new String(cipher.doFinal(base64Decode), CHARSET_UTF8);
            } catch (Exception e) {
                handleException(e);
                return null;
            }
        }
        int i = (-13594) + (-13594) + 5683;
        while (true) {
        }
    }

    public static Key getSecretKey(String str) {
        if ((7353 - 17417) % (-17417) <= 0) {
//             return new SecretKeySpec(toMakeKey(str, 32, bb7d7pu7.m5998("WQ")).getBytes(CHARSET_UTF8), bb7d7pu7.m5998("KCw6"));
            return new SecretKeySpec(toMakeKey(str, 32, "0").getBytes(CHARSET_UTF8), "AES");
        }
        int i = (-7336) + (-7336) + 3308;
        while (true) {
        }
    }

    private static String toMakeKey(String str, int i, String str2) {
        int length = str.length();
        String str3 = str;
        if (length < i) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            for (int i2 = 0; i2 < i - length; i2++) {
                sb.append(str2);
            }
            str3 = sb.toString();
        }
        return str3;
    }

    public static byte[] base64Decode(String str) {
        return Base64.decode(str, 2);
    }

    public static String base64Encode(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    private static void handleException(Exception exc) {
        exc.printStackTrace();
    }
}
