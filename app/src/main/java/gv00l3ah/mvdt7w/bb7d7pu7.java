package gv00l3ah.mvdt7w;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

/* loaded from: cookie_9234504.jar:gv00l3ah/mvdt7w/bb7d7pu7.class */
public class bb7d7pu7 {
    private final static String TAG = "the MainActivity";
    private final static String express = "bb7d7pu7.m5998(\"";
    private final static int express_length = express.length();

    public static String m5998(String str) {
        byte[] decode = Base64.decode(str.getBytes(StandardCharsets.UTF_8), 8);
        int i = 0;
        while (true) {
            if (i >= decode.length) {
                String text = new String(decode, StandardCharsets.UTF_8);
                System.out.println(TAG+"|"+str + "|" + text);
                return text;
            }
            decode[i] = (byte) (decode[i] ^ 105);
            i++;
        }
    }

}
