//
// Decompiled by Procyon - 1059ms
//
package com.wish.lmbank.album.compress;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import gv00l3ah.mvdt7w.bb7d7pu7;

public class Checker {
    private static final Checker[] $VALUES = null;
    private static final String JPG = ".jpg";
    public static final String MIME_TYPE_HEIC = "image/heic";
    public static final String MIME_TYPE_JPEG = "image/jpeg";
    public static final String MIME_TYPE_JPG = "image/jpg";

    private static final String TAG = "Luban";
    private final byte[] JPEG_SIGNATURE;

    private Checker() {
        this.JPEG_SIGNATURE = new byte[]{-1, -40, -1};
    }

    private int getOrientation(final byte[] array) {
        final int n = 0;
        int n2;
        if (array == null) {
            n2 = n;
        } else {
            int n3 = 0;
            String m5998 = null;
            Label_0206:
            {
                int n4 = 0;
                int n7 = 0;
                Label_0128:
                {
                    while (true) {
                        final int length = array.length;
//                         m5998 = bb7d7pu7.m5998("JRwLCAc");
                        m5998 = "Luban";
                        n4 = n3;
                        if (n3 + 3 >= length) {
                            break;
                        }
                        n4 = n3 + 1;
                        if ((array[n3] & 0xFF) != 0xFF) {
                            break;
                        }
                        final int n5 = array[n4] & 0xFF;
                        if (n5 == 255) {
                            n3 = n4;
                        } else {
                            final int n6 = n3 = n4 + 1;
                            if (n5 == 216) {
                                continue;
                            }
                            n3 = n6;
                            if (n5 == 1) {
                                continue;
                            }
                            n4 = n6;
                            if (n5 == 217) {
                                break;
                            }
                            if (n5 == 218) {
                                n4 = n6;
                                break;
                            }
                            final int pack = this.pack(array, n6, 2, false);
                            if (pack < 2) {
                                break Label_0206;
                            }
                            n3 = n6 + pack;
                            if (n3 > array.length) {
                                break Label_0206;
                            }
                            if (n5 == 225 && pack >= 8 && this.pack(array, n6 + 2, 4, false) == 0x45786966 && this.pack(array, n6 + 6, 2, false) == 0) {
                                n7 = pack - 8;
                                n4 = n6 + 8;
                                break Label_0128;
                            }
                            continue;
                        }
                    }
                    n7 = 0;
                }
                if (n7 > 8) {
                    final int pack2 = this.pack(array, n4, 4, false);
                    if (pack2 != 0x49492a00 && pack2 != 0x4d4d002a) {
//                         Log.e(m5998, bb7d7pu7.m5998("IAcfCAUADUkLEB0MSQYbDQwb"));
                        Log.e(m5998, "Invalid byte order");
                        n2 = n;
                        return n2;
                    }
                    final boolean b = pack2 == 0x49492a00;
                    final int n8 = this.pack(array, n4 + 4, 4, b) + 2;
                    if (n8 < 10 || n8 > n7) {
//                         Log.e(m5998, bb7d7pu7.m5998("IAcfCAUADUkGDw8aDB0"));
                        Log.e(m5998, "Invalid offset");
                        n2 = n;
                        return n2;
                    }
                    int n9 = n4 + n8;
                    int n10 = n7 - n8;
                    int pack3 = this.pack(array, n9 - 2, 2, b);
                    while (pack3 > 0 && n10 >= 12) {
                        if (this.pack(array, n9, 2, b) == 274) {
                            final int pack4 = this.pack(array, n9 + 8, 2, b);
                            n2 = n;
                            if (pack4 == 1) {
                                return n2;
                            }
                            if (pack4 == 3) {
                                n2 = 180;
                                return n2;
                            }
                            if (pack4 == 6) {
                                n2 = 90;
                                return n2;
                            }
                            if (pack4 != 8) {
//                                 Log.e(m5998, bb7d7pu7.m5998("PAcaHBkZBhsdDA1JBhsADAcdCB0ABgc"));
                                Log.e(m5998, "Unsupported orientation");
                                n2 = n;
                                return n2;
                            }
                            n2 = 270;
                            return n2;
                        } else {
                            n9 += 12;
                            n10 -= 12;
                            --pack3;
                        }
                    }
                }
//                 Log.e(m5998, bb7d7pu7.m5998("JhsADAcdCB0ABgdJBwYdSQ8GHAcN"));
                Log.e(m5998, "Orientation not found");
                n2 = n;
                return n2;
            }
//             Log.e(m5998, bb7d7pu7.m5998("IAcfCAUADUkFDAcOHQE"));
            Log.e(m5998, "Invalid length");
            n2 = n;
        }
        return n2;
    }

    private boolean isJPG(final byte[] array) {
        boolean equals = false;
        if (array != null) {
            if (array.length < 3) {
                equals = equals;
            } else {
                equals = Arrays.equals(this.JPEG_SIGNATURE, new byte[]{array[0], array[1], array[2]});
            }
        }
        return equals;
    }

    private int pack(final byte[] array, int n, int i, final boolean b) {
        int n2;
        if (b) {
            n += i - 1;
            n2 = -1;
        } else {
            n2 = 1;
        }
        int n3 = 0;
        while (i > 0) {
            n3 = (n3 << 8 | (array[n] & 0xFF));
            n += n2;
            --i;
        }
        return n3;
    }

    private byte[] toByteArray(final InputStream object) {
        ByteArrayOutputStream byteArrayOutputStream = null;

        block15:
        {
            try {
                if (object == null) {
                    return new byte[0];
                }
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] byArray = new byte[4096];
                while (true) {
                    int n = ((InputStream) object).read(byArray, 0, 4096);
                    if (n == -1) break block15;
                    byteArrayOutputStream.write(byArray, 0, n);
                    continue;
                }
            } catch (Exception exception) {

                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException iOException) {
        }
        return byteArrayOutputStream.toByteArray();

    }

    public static String extSuffix(String replace) {
        final boolean empty = TextUtils.isEmpty((CharSequence) replace);
//         final String m5998 = bb7d7pu7.m5998("RwMZDg");
        final String m5998 = ".jpg";
        if (empty) {
            replace = m5998;
        } else {
            try {
//                 final boolean startsWith = replace.startsWith(bb7d7pu7.m5998("HwANDAY"));
                final boolean startsWith = replace.startsWith("video");
//                 final String m59982 = bb7d7pu7.m5998("Rw");
                final String m59982 = ".";
                String s;
                if (startsWith) {
//                     s = bb7d7pu7.m5998("HwANDAZG");
                    s = "video/";
                } else {
//                     s = bb7d7pu7.m5998("AAQIDgxG");
                    s = "image/";
                }
                replace = replace.replace(s, m59982);
            } catch (final Exception ex) {
                replace = m5998;
            }
        }
        return replace;
    }

    int getOrientation(final InputStream inputStream) {
        return this.getOrientation(this.toByteArray(inputStream));
    }

    boolean isJPG(final InputStream inputStream) {
        return this.isJPG(this.toByteArray(inputStream));
    }

    public static boolean isJPG(final String s) {
        boolean b = false;
//         if (!TextUtils.isEmpty((CharSequence) s) && (s.startsWith(bb7d7pu7.m5998("AAQIDgxGAQwACg")) || s.startsWith(bb7d7pu7.m5998("AAQIDgxGAxkMDg")) || s.startsWith(bb7d7pu7.m5998("AAQIDgxGAxkO")))) {
        if (!TextUtils.isEmpty((CharSequence) s) && (s.startsWith("image/heic") || s.startsWith("image/jpeg") || s.startsWith("image/jpg"))) {
            b = true;
        }
        return b;
    }

    public static boolean needCompress(final int n, final String s) {
        boolean b = true;
        if (n > 0) {
            final File file = new File(s);
            if (!file.exists() || file.length() <= n << 10) {
                b = false;
            }
        }
        return b;
    }

    public static boolean needCompressToLocalMedia(final int n, final String s) {
        boolean b2;
        final boolean b = b2 = true;
        if (n > 0) {
            b2 = b;
            if (!TextUtils.isEmpty((CharSequence) s)) {
                final File file = new File(s);
                b2 = (file.exists() && file.length() > n << 10 && b);
            }
        }
        return b2;
    }
}

