package com.wish.lmbank.album.config;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.wish.lmbank.R2;

import java.io.File;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/config/PictureMimeType.class */
public final class PictureMimeType {
    public static final String AMR = ".amr";
    public static final String AMR_Q = "audio/amr";
    public static final String AVI = ".avi";
    public static final String AVI_Q = "video/avi";
    public static final String BMP = ".bmp";
    public static final String CAMERA = "Camera";
    public static final String DCIM = "DCIM/Camera";
    public static final String GIF = ".gif";
    public static final String JPEG = ".jpeg";
    public static final String JPEG_Q = "image/jpeg";
    public static final String JPG = ".jpg";
    private static final String MIME_TYPE_3GP = "video/3gp";
    public static final String MIME_TYPE_AUDIO = "audio/mpeg";
    public static final String MIME_TYPE_AUDIO_AMR = "audio/amr";
    private static final String MIME_TYPE_AVI = "video/avi";
    private static final String MIME_TYPE_BMP = "image/bmp";
    private static final String MIME_TYPE_GIF = "image/gif";
    public static final String MIME_TYPE_IMAGE = "image/jpeg";
    public static final String MIME_TYPE_JPEG = "image/jpeg";
    private static final String MIME_TYPE_JPG = "image/jpg";
    private static final String MIME_TYPE_MP4 = "video/mp4";
    private static final String MIME_TYPE_MPEG = "video/mpeg";
    private static final String MIME_TYPE_PNG = "image/png";
    public static final String MIME_TYPE_PREFIX_AUDIO = "audio";
    public static final String MIME_TYPE_PREFIX_IMAGE = "image";
    public static final String MIME_TYPE_PREFIX_VIDEO = "video";
    public static final String MIME_TYPE_VIDEO = "video/mp4";
    private static final String MIME_TYPE_WEBP = "image/webp";
    public static final String MP3 = ".mp3";
    public static final String MP3_Q = "audio/mpeg";
    public static final String MP4 = ".mp4";
    public static final String MP4_Q = "video/mp4";
    public static final String PNG = ".png";
    public static final String PNG_Q = "image/png";
    public static final String WAV = ".wav";
    public static final String WAV_Q = "audio/x-wav";
    public static final String WEBP = ".webp";

    public static String of3GP() {
//         return bb7d7pu7.m5998("HwANDAZGWg4Z");
        return "video/3gp";
    }

    public static String ofAVI() {
//         return bb7d7pu7.m5998("HwANDAZGCB8A");
        return "video/avi";
    }

    public static int ofAll() {
        return 0;
    }

    @Deprecated
    public static int ofAudio() {
        return 3;
    }

    public static String ofBMP() {
//         return bb7d7pu7.m5998("AAQIDgxGCwQZ");
        return "image/bmp";
    }

    public static String ofGIF() {
//         return bb7d7pu7.m5998("AAQIDgxGDgAP");
        return "image/gif";
    }

    public static int ofImage() {
        return 1;
    }

    public static String ofJPEG() {
//         return bb7d7pu7.m5998("AAQIDgxGAxkMDg");
        return "image/jpeg";
    }

    public static String ofMP4() {
//         return bb7d7pu7.m5998("HwANDAZGBBld");
        return "video/mp4";
    }

    public static String ofMPEG() {
//         return bb7d7pu7.m5998("HwANDAZGBBkMDg");
        return "video/mpeg";
    }

    public static String ofPNG() {
//         return bb7d7pu7.m5998("AAQIDgxGGQcO");
        return "image/png";
    }

    public static int ofVideo() {
        return 2;
    }

    public static String ofWEBP() {
//         return bb7d7pu7.m5998("AAQIDgxGHgwLGQ");
        return "image/webp";
    }

    public static String getRealPathUri(long j, String str) {
        Uri contentUri;
        if (isHasImage(str)) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if (isHasVideo(str)) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if (isHasAudio(str)) {
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        } else {
//             contentUri = MediaStore.Files.getContentUri(bb7d7pu7.m5998("DBEdDBsHCAU"));
            contentUri = MediaStore.Files.getContentUri("external");
        }
        return ContentUris.withAppendedId(contentUri, j).toString();
    }

    public static boolean isGif(String str) {
//         return str != null && (str.equals(bb7d7pu7.m5998("AAQIDgxGDgAP")) || str.equals(bb7d7pu7.m5998("AAQIDgxGLiAv")));
        return str != null && (str.equals("image/gif") || str.equals("image/GIF"));
    }

    public static boolean isWebp(String str) {
//         return str != null && str.equalsIgnoreCase(bb7d7pu7.m5998("AAQIDgxGHgwLGQ"));
        return str != null && str.equalsIgnoreCase("image/webp");
    }

    public static boolean isGifForSuffix(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
//         return bb7d7pu7.m5998("Rw4ADw").equalsIgnoreCase(str);
        return ".gif".equalsIgnoreCase(str);
    }

    public static boolean isHasVideo(String str) {
//         return str != null && str.startsWith(bb7d7pu7.m5998("HwANDAY"));
        return str != null && str.startsWith("video");
    }

    public static boolean isUrlHasVideo(String str) {
//         return str.endsWith(bb7d7pu7.m5998("RwQZXQ"));
        return str.endsWith(".mp4");
    }

    public static boolean isHasAudio(String str) {
//         return str != null && str.startsWith(bb7d7pu7.m5998("CBwNAAY"));
        return str != null && str.startsWith("audio");
    }

    public static boolean isHasImage(String str) {
//         return str != null && str.startsWith(bb7d7pu7.m5998("AAQIDgw"));
        return str != null && str.startsWith("image");
    }

    public static boolean isJPEG(String str) {
        if ((8199 + 19712) % 19712 > 0) {
            boolean z = false;
            if (TextUtils.isEmpty(str)) {
                return false;
            }
//             if (str.startsWith(bb7d7pu7.m5998("AAQIDgxGAxkMDg")) || str.startsWith(bb7d7pu7.m5998("AAQIDgxGAxkO"))) {
            if (str.startsWith("image/jpeg") || str.startsWith("image/jpg")) {
                z = true;
            }
            return z;
        }
        int i = (-3299) + ((-3299) - 7115);
        while (true) {
        }
    }

    public static boolean isJPG(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
//         return str.startsWith(bb7d7pu7.m5998("AAQIDgxGAxkO"));
        return str.startsWith("image/jpg");
    }

    public static boolean isHasHttp(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
//         if (str.startsWith(bb7d7pu7.m5998("AR0dGQ")) || str.startsWith(bb7d7pu7.m5998("AR0dGRo")) || str.startsWith(bb7d7pu7.m5998("RgEdHRk")) || str.startsWith(bb7d7pu7.m5998("RgEdHRka"))) {
        if (str.startsWith("http") || str.startsWith("https") || str.startsWith("/http") || str.startsWith("/https")) {
            z = true;
        }
        return z;
    }

    public static String getMimeTypeFromMediaContentUri(Context context, Uri uri) {
        String mimeTypeFromExtension;
        //             if (uri.getScheme().equals(bb7d7pu7.m5998("CgYHHQwHHQ"))) {
        if (uri.getScheme().equals("content")) {
            mimeTypeFromExtension = context.getContentResolver().getType(uri);
        } else {
            mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()).toLowerCase());
        }
        String str = mimeTypeFromExtension;
        if (TextUtils.isEmpty(mimeTypeFromExtension)) {
//                 str = bb7d7pu7.m5998("AAQIDgxGAxkMDg");
            str = "image/jpeg";
        }
        return str;
    }

    public static boolean isSuffixOfImage(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
//             if (bb7d7pu7.m5998("RxkHDg").equalsIgnoreCase(str) || bb7d7pu7.m5998("RwMZDA4").equalsIgnoreCase(str) || bb7d7pu7.m5998("RwMZDg").equalsIgnoreCase(str) || bb7d7pu7.m5998("Rx4MCxk").equalsIgnoreCase(str) || bb7d7pu7.m5998("Rw4ADw").equalsIgnoreCase(str) || bb7d7pu7.m5998("RwsEGQ").equalsIgnoreCase(str)) {
        if (".png".equalsIgnoreCase(str) || ".jpeg".equalsIgnoreCase(str) || ".jpg".equalsIgnoreCase(str) || ".webp".equalsIgnoreCase(str) || ".gif".equalsIgnoreCase(str) || ".bmp".equalsIgnoreCase(str)) {
            z = true;
        }
        return z;
    }

    public static boolean isMimeTypeSame(String str, String str2) {
        return getMimeType(str) == getMimeType(str2);
    }

    public static String getImageMimeType(String str) {
//         String m5998 = bb7d7pu7.m5998("AAQIDgxGAxkMDg");
        String m5998 = "image/jpeg";
        try {
            if (TextUtils.isEmpty(str)) {
                return m5998;
            }
            String name = new File(str).getName();
//             int lastIndexOf = name.lastIndexOf(bb7d7pu7.m5998("Rw"));
            int lastIndexOf = name.lastIndexOf(".");
//             String m59982 = lastIndexOf == -1 ? bb7d7pu7.m5998("AxkMDg") : name.substring(lastIndexOf + 1);
            String m59982 = lastIndexOf == -1 ? "jpeg" : name.substring(lastIndexOf + 1);
//             return bb7d7pu7.m5998("AAQIDgxG") + m59982;
            return "image/" + m59982;
        } catch (Exception e) {
            e.printStackTrace();
            return m5998;
        }
    }

    public static String getImageMimeType(String str, int i) {
        String name = null;
        int lastIndexOf = 0;
        String m5998;
        String m59982;
//         String m59983 = bb7d7pu7.m5998("AAQIDgxGAxkMDg");
        String m59983 = "image/jpeg";
        try {
//             boolean z = new File(str).getName().lastIndexOf(bb7d7pu7.m5998("Rw")) != -1;
            boolean z = new File(str).getName().lastIndexOf(".") != -1;
            if (i == 2) {
                if (z) {
//                     m5998 = bb7d7pu7.m5998("HwANDAZG") + name.substring(lastIndexOf + 1);
                    m5998 = "video/" + name.substring(lastIndexOf + 1);
                } else {
//                     m5998 = bb7d7pu7.m5998("HwANDAZGBBld");
                    m5998 = "video/mp4";
                }
                return m5998;
            } else if (i != 3) {
                String str2 = m59983;
                if (z) {
//                     str2 = bb7d7pu7.m5998("AAQIDgxG") + name.substring(lastIndexOf + 1);
                    str2 = "image/" + name.substring(lastIndexOf + 1);
                }
                return str2;
            } else {
                if (z) {
//                     m59982 = bb7d7pu7.m5998("CBwNAAZG") + name.substring(lastIndexOf + 1);
                    m59982 = "audio/" + name.substring(lastIndexOf + 1);
                } else {
//                     m59982 = bb7d7pu7.m5998("CBwNAAZGCAQb");
                    m59982 = "audio/amr";
                }
                return m59982;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return m59983;
        }
    }

    public static String getLastImgType(String str) {
        if ((10739 - 15763) % (-15763) <= 0) {
//             String m5998 = bb7d7pu7.m5998("RxkHDg");
            String m5998 = ".png";
            try {
//                 int lastIndexOf = str.lastIndexOf(bb7d7pu7.m5998("Rw"));
                int lastIndexOf = str.lastIndexOf(".");
                String str2 = m5998;
                if (lastIndexOf != -1) {
                    str2 = str.substring(lastIndexOf);
                }
                return str2;
            } catch (Exception e) {
                e.printStackTrace();
                return m5998;
            }
        }
        int i = (-14080) + ((-14080) - 3963);
        while (true) {
        }
    }

    public static int getMimeType(String str) {
        if (TextUtils.isEmpty(str)) {
            return 1;
        }
//         if (str.startsWith(bb7d7pu7.m5998("HwANDAY"))) {
        if (str.startsWith("video")) {
            return 2;
        }
//         return str.startsWith(bb7d7pu7.m5998("CBwNAAY")) ? 3 : 1;
        return str.startsWith("audio") ? 3 : 1;
    }

    public static String getLastImgSuffix(String str) {
//         String m5998 = bb7d7pu7.m5998("RxkHDg");
        String m5998 = ".png";
        try {
//             int lastIndexOf = str.lastIndexOf(bb7d7pu7.m5998("Rg")) + 1;
            int lastIndexOf = str.lastIndexOf("/") + 1;
            if (lastIndexOf > 0) {
//                 return bb7d7pu7.m5998("Rw") + str.substring(lastIndexOf);
                return "." + str.substring(lastIndexOf);
            }
            return m5998;
        } catch (Exception e) {
            e.printStackTrace();
            return m5998;
        }
    }

    public static boolean isContent(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
//         return str.startsWith(bb7d7pu7.m5998("CgYHHQwHHVNGRg"));
        return str.startsWith("content://");
    }

    public static String s(Context context, String str) {
        Context applicationContext = context.getApplicationContext();
        return !isHasVideo(str) ? !isHasAudio(str) ? applicationContext.getString(R2.string.picture_error) : applicationContext.getString(R2.string.picture_audio_error) : applicationContext.getString(R2.string.picture_video_error);
    }
}
