package com.wish.lmbank.album.tools;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.wish.lmbank.album.config.PictureMimeType;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Locale;
import java.util.Objects;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/PictureFileUtils.class */
public class PictureFileUtils {
    private static final int BYTE_SIZE = 1024;
    public static final int GB = 1073741824;
    public static final int KB = 1024;
    public static final int MB = 1048576;
    public static final String POSTFIX = ".jpeg";
    public static final String POST_AUDIO = ".amr";
    public static final String POST_VIDEO = ".mp4";
    static final String TAG = "PictureFileUtils";

    public static File createCameraFile(Context context, int i, String str, String str2, String str3) {
        return createMediaFile(context, i, str, str2, str3);
    }

    private static File createMediaFile(Context context, int i, String str, String str2, String str3) {
        return createOutFile(context, i, str, str2, str3);
    }

    private static File createOutFile(Context context, int i, String str, String str2, String str3) {
        File file;
        File rootDirFile;
        File file2;
        Context applicationContext = context.getApplicationContext();
        if (TextUtils.isEmpty(str3)) {
//             if (TextUtils.equals(bb7d7pu7.m5998("BAYcBx0MDQ"), Environment.getExternalStorageState())) {
            if (TextUtils.equals("mounted", Environment.getExternalStorageState())) {
                rootDirFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//                 file2 = new File(rootDirFile.getAbsolutePath() + File.separator + bb7d7pu7.m5998("KggEDBsI") + File.separator);
                file2 = new File(rootDirFile.getAbsolutePath() + File.separator + "Camera" + File.separator);
            } else {
                rootDirFile = getRootDirFile(applicationContext, i);
                file2 = new File(rootDirFile.getAbsolutePath() + File.separator);
            }
            file = file2;
            if (!rootDirFile.exists()) {
                rootDirFile.mkdirs();
                file = file2;
            }
        } else {
            file = new File(str3);
            File parentFile = file.getParentFile();
            Objects.requireNonNull(parentFile);
            if (!parentFile.exists()) {
                file.getParentFile().mkdirs();
            }
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        boolean isEmpty = TextUtils.isEmpty(str);
        if (i == 2) {
            if (isEmpty) {
//                 str = DateUtils.getCreateFileName(bb7d7pu7.m5998("PyAtNg")) + bb7d7pu7.m5998("RwQZXQ");
                str = DateUtils.getCreateFileName("VID_") + ".mp4";
            }
            return new File(file, str);
        } else if (i == 3) {
            if (isEmpty) {
//                 str = DateUtils.getCreateFileName(bb7d7pu7.m5998("KDwtNg")) + bb7d7pu7.m5998("RwgEGw");
                str = DateUtils.getCreateFileName("AUD_") + ".amr";
            }
            return new File(file, str);
        } else {
            String str4 = str2;
            if (TextUtils.isEmpty(str2)) {
//                 str4 = bb7d7pu7.m5998("RwMZDA4");
                str4 = ".jpeg";
            }
            if (isEmpty) {
//                 str = DateUtils.getCreateFileName(bb7d7pu7.m5998("ICQuNg")) + str4;
                str = DateUtils.getCreateFileName("IMG_") + str4;
            }
            return new File(file, str);
        }
    }

    private static File getRootDirFile(Context context, int i) {
        if (i != 2) {
            if (i == 3) {
                return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            }
            return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
    }

    private PictureFileUtils() {
    }

    public static boolean isExternalStorageDocument(Uri uri) {
//         return bb7d7pu7.m5998("CgYERwgHDRsGAA1HDBEdDBsHCAUaHQYbCA4MRw0GChwEDAcdGg").equals(uri.getAuthority());
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
//         return bb7d7pu7.m5998("CgYERwgHDRsGAA1HGRsGHwANDBsaRw0GHgcFBggNGkcNBgocBAwHHRo").equals(uri.getAuthority());
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
//         return bb7d7pu7.m5998("CgYERwgHDRsGAA1HGRsGHwANDBsaRwQMDQAIRw0GChwEDAcdGg").equals(uri.getAuthority());
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
//         return bb7d7pu7.m5998("CgYERw4GBg4FDEcIBw0bBgANRwgZGRpHGQEGHQYaRwoGBx0MBx0").equals(uri.getAuthority());
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor;
        if (((-4534) + 1279) % 1279 <= 0) {
//             String m5998 = bb7d7pu7.m5998("Ng0IHQg");
            String m5998 = "_data";
            Cursor cursor2 = null;
            Cursor cursor3 = null;
            try {
                try {
                    cursor = context.getContentResolver().query(uri, new String[]{m5998}, str, strArr, null);
                } catch (IllegalArgumentException e) {
                    cursor3 = cursor2;
//                     Log.i(bb7d7pu7.m5998("OQAKHRwbDC8ABQw8HQAFGg"), String.format(Locale.getDefault(), bb7d7pu7.m5998("DgwdLQgdCCoGBRwEB1NJNg0IHQhJREkyTBo0"), e.getMessage()));
                    Log.i("PictureFileUtils", String.format(Locale.getDefault(), "getDataColumn: _data - [%s]", e.getMessage()));
                    if (cursor2 == null) {
                        return "";
                    }
                    cursor = cursor2;
                }
                if (cursor == null || !cursor.moveToFirst()) {
                    if (cursor == null) {
                        return "";
                    }
                    cursor.close();
                    return "";
                }
                cursor3 = cursor;
                cursor2 = cursor;
                String string = cursor.getString(cursor.getColumnIndexOrThrow(m5998));
                if (cursor != null) {
                    cursor.close();
                }
                return string;
            } catch (Throwable th) {
                if (cursor3 != null) {
                    cursor3.close();
                }
                throw th;
            }
        }
        int i = 7148 + 7148 + 8993;
        while (true) {
        }
    }

    public static String getPath(Context context, Uri uri) {
        String[] split = null;
            Context applicationContext = context.getApplicationContext();
            Uri uri2 = null;
            if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(applicationContext, uri)) {
//                 if (!bb7d7pu7.m5998("CgYHHQwHHQ").equalsIgnoreCase(uri.getScheme())) {
                if (!"content".equalsIgnoreCase(uri.getScheme())) {
//                     return bb7d7pu7.m5998("DwAFDA").equalsIgnoreCase(uri.getScheme()) ? uri.getPath() : "";
                    return "file".equalsIgnoreCase(uri.getScheme()) ? uri.getPath() : "";
                } else if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                } else {
                    return getDataColumn(applicationContext, uri, null, null);
                }
            }
            boolean isExternalStorageDocument = isExternalStorageDocument(uri);
//             String m5998 = bb7d7pu7.m5998("Uw");
            String m5998 = ":";
            if (isExternalStorageDocument) {
//                 if (bb7d7pu7.m5998("GRsABAgbEA").equalsIgnoreCase(DocumentsContract.getDocumentId(uri).split(m5998)[0])) {
                if ("primary".equalsIgnoreCase(DocumentsContract.getDocumentId(uri).split(m5998)[0])) {
                    boolean isQ = SdkVersionUtils.isQ();
//                     String m59982 = bb7d7pu7.m5998("Rg");
                    String m59982 = "/";
                    if (isQ) {
                        return applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + m59982 + split[1];
                    }
                    return Environment.getExternalStorageDirectory() + m59982 + split[1];
                }
                return "";
            } else if (isDownloadsDocument(uri)) {
//                 return getDataColumn(applicationContext, ContentUris.withAppendedId(Uri.parse(bb7d7pu7.m5998("CgYHHQwHHVNGRg0GHgcFBggNGkYZHAsFAAo2DQYeBwUGCA0a")), ValueOf.toLong(DocumentsContract.getDocumentId(uri))), null, null);
                return getDataColumn(applicationContext, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), ValueOf.toLong(DocumentsContract.getDocumentId(uri))), null, null);
            } else {
                if (isMediaDocument(uri)) {
                    String[] split2 = DocumentsContract.getDocumentId(uri).split(m5998);
                    String str = split2[0];
//                     if (bb7d7pu7.m5998("AAQIDgw").equals(str)) {
                    if ("image".equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                     } else if (bb7d7pu7.m5998("HwANDAY").equals(str)) {
                    } else if ("video".equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                     } else if (bb7d7pu7.m5998("CBwNAAY").equals(str)) {
                    } else if ("audio".equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
//                     return getDataColumn(applicationContext, uri2, bb7d7pu7.m5998("NgANVFY"), new String[]{split2[1]});
                    return getDataColumn(applicationContext, uri2, "_id=?", new String[]{split2[1]});
                }
                return "";
            }
    }

    public static void copyFile(String str, String str2) {
        FileChannel fileChannel;
        FileChannel fileChannel2;
        FileChannel channel = null;
        FileChannel fileChannel3 = null;
        FileChannel fileChannel4 = null;
        if (str.equalsIgnoreCase(str2)) {
            return;
        }
        try {
            channel = new FileInputStream(str).getChannel();
            fileChannel3 = null;
            fileChannel4 = null;
        } catch (Exception e) {
            e = e;
            fileChannel = null;
            fileChannel2 = null;
        } catch (Throwable th) {
            th = th;
            fileChannel = null;
            fileChannel2 = null;
        }
        try {
            FileChannel channel2 = new FileOutputStream(str2).getChannel();
            fileChannel3 = channel2;
            fileChannel4 = channel2;
            channel.transferTo(0L, channel.size(), channel2);
            close(channel);
            close(channel2);
        } catch (Exception e2) {
            fileChannel2 = channel;
            fileChannel = fileChannel4;
            try {
                e2.printStackTrace();
                close(fileChannel2);
                close(fileChannel);
            } catch (Throwable th2) {
                close(fileChannel2);
                close(fileChannel);
                throw th2;
            }
        } catch (Throwable th3) {
            close(fileChannel3);
            close(channel);
            throw new RuntimeException(th3);
        }
    }

    public static boolean writeFileFromIS(InputStream inputStream, OutputStream outputStream) {
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2;
        if (((-11165) - 6737) % (-6737) > 0) {
            int i = (-10565) + ((-10565) - 1229);
            while (true) {
            }
        } else {
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
                try {
                    bufferedOutputStream = new BufferedOutputStream(outputStream);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = bufferedInputStream2.read(bArr);
                            if (read != -1) {
                                outputStream.write(bArr, 0, read);
                            } else {
                                outputStream.flush();
                                close(bufferedInputStream2);
                                close(bufferedOutputStream);
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        bufferedOutputStream2 = bufferedOutputStream;
                        e = e;
                        bufferedInputStream = bufferedInputStream2;
                        try {
                            e.printStackTrace();
                            close(bufferedInputStream);
                            close(bufferedOutputStream2);
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            bufferedOutputStream = bufferedOutputStream2;
                            close(bufferedInputStream);
                            close(bufferedOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        bufferedInputStream = bufferedInputStream2;
                        close(bufferedInputStream);
                        close(bufferedOutputStream);
                        throw th2;
                    }
                } catch (Exception e2) {
                    bufferedOutputStream2 = null;
                    throw e2;
                } catch (Throwable th3) {
                    bufferedOutputStream = null;
                    throw th3;
                }
            } catch (Throwable th4) {
                bufferedInputStream = null;
                bufferedOutputStream = null;
                throw new RuntimeException(th4);
            }
        }
    }

    public static String rename(String str) {
        if ((8179 + 6036) % 6036 > 0) {
//             String m5998 = bb7d7pu7.m5998("Rw");
            String m5998 = ".";
            String substring = str.substring(0, str.lastIndexOf(m5998));
            String substring2 = str.substring(str.lastIndexOf(m5998));
            StringBuilder sb = new StringBuilder();
            sb.append(substring);
//             sb.append(bb7d7pu7.m5998("Ng"));
            sb.append("_");
            sb.append(DateUtils.getCreateFileName());
            sb.append(substring2);
            return sb.toString();
        }
        int i = 8281 + (8281 - 1929);
        while (true) {
        }
    }

    public static String getDCIMCameraPath() {
        if ((16493 + 3303) % 3303 > 0) {
            try {
                StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("TA"));
                sb.append("%");
                sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
//                 sb.append(bb7d7pu7.m5998("RioIBAwbCA"));
                sb.append("/Camera");
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        int i = 4468 + 4468 + 11226;
        while (true) {
        }
    }

    @Deprecated
    public static void deleteCacheDirFile(Context context, int i) {
        File[] listFiles;
        File externalFilesDir = context.getExternalFilesDir(i == PictureMimeType.ofImage() ? Environment.DIRECTORY_PICTURES : Environment.DIRECTORY_MOVIES);
        if (externalFilesDir == null || (listFiles = externalFilesDir.listFiles()) == null) {
            return;
        }
        for (File file : listFiles) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    @Deprecated
    public static void deleteAllCacheDirFile(Context context) {
        File[] listFiles;
        File[] listFiles2;
        File[] listFiles3;
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir != null && (listFiles3 = externalFilesDir.listFiles()) != null) {
            for (File file : listFiles3) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
        File externalFilesDir2 = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (externalFilesDir2 != null && (listFiles2 = externalFilesDir2.listFiles()) != null) {
            for (File file2 : listFiles2) {
                if (file2.isFile()) {
                    file2.delete();
                }
            }
        }
        File externalFilesDir3 = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        if (externalFilesDir3 == null || (listFiles = externalFilesDir3.listFiles()) == null) {
            return;
        }
        for (File file3 : listFiles) {
            if (file3.isFile()) {
                file3.delete();
            }
        }
    }

    public static String getDiskCacheDir(Context context) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return externalFilesDir == null ? "" : externalFilesDir.getPath();
    }

    public static String getVideoDiskCacheDir(Context context) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        return externalFilesDir == null ? "" : externalFilesDir.getPath();
    }

    public static String getAudioDiskCacheDir(Context context) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        return externalFilesDir == null ? "" : externalFilesDir.getPath();
    }

    public static Uri parUri(Context context, File file) {
        Uri fromFile;
//         String str = context.getPackageName() + bb7d7pu7.m5998("RwUcCgI5GwYfAA0MGw");
        String str = context.getPackageName() + ".luckProvider";
        if (Build.VERSION.SDK_INT > 23) {
            fromFile = FileProvider.getUriForFile(context, str, file);
        } else {
            fromFile = Uri.fromFile(file);
        }
        return fromFile;
    }

    public static String createFilePath(Context context, String str, String str2, String str3) {
        String lastImgSuffix = PictureMimeType.getLastImgSuffix(str2);
        if (PictureMimeType.isHasVideo(str2)) {
            String str4 = getVideoDiskCacheDir(context) + File.separator;
            boolean isEmpty = TextUtils.isEmpty(str);
//             String m5998 = bb7d7pu7.m5998("PyAtNg");
            String m5998 = "VID_";
            if (isEmpty) {
                String str5 = str3;
                if (TextUtils.isEmpty(str3)) {
                    str5 = DateUtils.getCreateFileName(m5998) + lastImgSuffix;
                }
                return str4 + str5;
            }
            String str6 = str3;
            if (TextUtils.isEmpty(str3)) {
                str6 = m5998 + str.toUpperCase() + lastImgSuffix;
            }
            return str4 + str6;
        } else if (PictureMimeType.isHasAudio(str2)) {
            String str7 = getAudioDiskCacheDir(context) + File.separator;
            boolean isEmpty2 = TextUtils.isEmpty(str);
//             String m59982 = bb7d7pu7.m5998("KDwtNg");
            String m59982 = "AUD_";
            if (isEmpty2) {
                String str8 = str3;
                if (TextUtils.isEmpty(str3)) {
                    str8 = DateUtils.getCreateFileName(m59982) + lastImgSuffix;
                }
                return str7 + str8;
            }
            String str9 = str3;
            if (TextUtils.isEmpty(str3)) {
                str9 = m59982 + str.toUpperCase() + lastImgSuffix;
            }
            return str7 + str9;
        } else {
            String str10 = getDiskCacheDir(context) + File.separator;
            boolean isEmpty3 = TextUtils.isEmpty(str);
//             String m59983 = bb7d7pu7.m5998("ICQuNg");
            String m59983 = "IMG_";
            if (isEmpty3) {
                String str11 = str3;
                if (TextUtils.isEmpty(str3)) {
                    str11 = DateUtils.getCreateFileName(m59983) + lastImgSuffix;
                }
                return str10 + str11;
            }
            String str12 = str3;
            if (TextUtils.isEmpty(str3)) {
                str12 = m59983 + str.toUpperCase() + lastImgSuffix;
            }
            return str10 + str12;
        }
    }

    public static boolean isFileExists(String str) {
        return TextUtils.isEmpty(str) || new File(str).exists();
    }

    public static String formatFileSize(long j, int i) {
        if (i >= 0) {
            if (j >= 0) {
//                 String m5998 = bb7d7pu7.m5998("TEc");
                String m5998 = "%.";
                if (j < PlaybackState.ACTION_PLAY_FROM_MEDIA_ID) {
//                     return String.format(m5998 + i + bb7d7pu7.m5998("Dys"), Double.valueOf(j));
                    return String.format(m5998 + i + "fB", Double.valueOf(j));
                } else if (j < 1048576) {
//                     return String.format(m5998 + i + bb7d7pu7.m5998("DyIr"), Double.valueOf(j / 1024.0d));
                    return String.format(m5998 + i + "fKB", Double.valueOf(j / 1024.0d));
                } else if (j < 1073741824) {
//                     return String.format(m5998 + i + bb7d7pu7.m5998("DyQr"), Double.valueOf(j / 1048576.0d));
                    return String.format(m5998 + i + "fMB", Double.valueOf(j / 1048576.0d));
                } else {
//                     return String.format(m5998 + i + bb7d7pu7.m5998("Dy4r"), Double.valueOf(j / 1.073741824E9d));
                    return String.format(m5998 + i + "fGB", Double.valueOf(j / 1.073741824E9d));
                }
            }
//             throw new IllegalArgumentException(bb7d7pu7.m5998("CxAdDDoAEwxJGgEGHAUNB04dSQsMSQUMGhpJHQEIB0kTDBsGSA"));
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        }
//         throw new IllegalArgumentException(bb7d7pu7.m5998("GRsMCgAaAAYHSRoBBhwFDQdOHUkLDEkFDBoaSR0BCAdJEwwbBkg"));
        throw new IllegalArgumentException("precision shouldn't be less than zero!");
    }

    public static void close(Closeable closeable) {
        if (closeable instanceof Closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }
}
