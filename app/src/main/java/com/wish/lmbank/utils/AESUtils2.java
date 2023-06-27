package com.wish.lmbank.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/AESUtils2.class */
public class AESUtils2 {
    private static final String KEY_ALGORITHM = "AES";

    public static String encodeByECB(String str, String str2) {
//         return encodeByECB(str, str2, bb7d7pu7.m5998("KCw6RiwqK0Y5Iio6XDkIDQ0ABw4"));
        return encodeByECB(str, str2, "AES/ECB/PKCS5Padding");
    }

    public static String encodeByECB(String str, String str2, String str3) {
        checkContentAndKey(str, str2);
        return Base64.encodeToString(encryptByECB(str.getBytes(Charset.defaultCharset()), str2.getBytes(Charset.defaultCharset()), str3), 2);
    }

    public static byte[] encryptByECB(byte[] bArr, byte[] bArr2, String str) {
        try {
            return doFinalECB(bArr, bArr2, 1, str);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
//             throw new RuntimeException(bb7d7pu7.m5998("KAwaSQwHChsQGR1JDBsbBhs"));
            throw new RuntimeException("Aes encrypt error");
        }
    }

    public static String decodeByECB(String str, String str2) {
        checkContentAndKey(str, str2);
        return new String(decryptByECB(Base64.decode(str, 2), str2.getBytes(Charset.defaultCharset())), Charset.defaultCharset());
    }

    public static byte[] decryptByECB(byte[] bArr, byte[] bArr2) {
//         return decryptByECB(bArr, bArr2, bb7d7pu7.m5998("KCw6RiwqK0Y5Iio6XDkIDQ0ABw4"));
        return decryptByECB(bArr, bArr2, "AES/ECB/PKCS5Padding");
    }

    public static byte[] decryptByECB(byte[] bArr, byte[] bArr2, String str) {
        try {
            return doFinalECB(bArr, bArr2, 2, str);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
//             throw new RuntimeException(bb7d7pu7.m5998("KAwaSQ0MChsQGR1JDBsbBhs"));
            throw new RuntimeException("Aes decrypt error");
        }
    }

    private static byte[] doFinalECB(byte[] bArr, byte[] bArr2, int i, String str) throws GeneralSecurityException {
//         SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, bb7d7pu7.m5998("KCw6"));
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        Cipher cipher = Cipher.getInstance(str);
        cipher.init(i, secretKeySpec);
        return cipher.doFinal(bArr);
    }

    private static void checkContentAndKey(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
//             throw new NullPointerException(bb7d7pu7.m5998("PQEMSRodGwAHDkkdBkkLDEkMBwobEBkdDA1JCggHBwYdSQsMSQccBQVH"));
            throw new NullPointerException("The string to be encrypted cannot be null.");
        }
        if (TextUtils.isEmpty(str2)) {
//             throw new NullPointerException(bb7d7pu7.m5998("PQEMSQIMEEkKCAdOHUkLDEkHHAUFSQYbSQwEGR0QRw"));
            throw new NullPointerException("The key can't be null or empty.");
        }
        if (str2.length() != 16) {
//             throw new RuntimeException(bb7d7pu7.m5998("PQEMSQUMBw4dAUkGD0kCDBBJBBwaHUkLDElYX0keAQAFDEkcGgxJKCw6SSorKkkEBg0MRw"));
            throw new RuntimeException("The length of key must be 16 while use AES CBC mode.");
        }
    }
}
