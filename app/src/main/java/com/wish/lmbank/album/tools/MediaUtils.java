package com.wish.lmbank.album.tools;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.album.PictureContentResolver;
import com.wish.lmbank.album.config.PictureMimeType;
import com.wish.lmbank.album.entity.LocalMedia;
import com.wish.lmbank.album.entity.MediaExtraInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/MediaUtils.class */
public class MediaUtils {
    public static boolean isLongImg(int i, int i2) {
        boolean z = false;
        if (i > 0) {
            if (i2 <= 0) {
                z = false;
            } else {
                z = false;
                if (i2 > i * 3) {
                    z = true;
                }
            }
        }
        return z;
    }

    public static boolean isLongImg(LocalMedia localMedia) {
        int width = localMedia.getWidth();
        int height = localMedia.getHeight();
        boolean z = false;
        if (width > 0) {
            if (height <= 0) {
                z = false;
            } else {
                z = false;
                if (height > width * 3) {
                    z = true;
                }
            }
        }
        return z;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static MediaExtraInfo getImageSize(Context context, String str) {
        ExifInterface exifInterface;
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        try {
            if (PictureMimeType.isContent(str)) {
                exifInterface = new ExifInterface(PictureContentResolver.getContentResolverOpenInputStream(context, Uri.parse(str)));
            } else {
                exifInterface = new ExifInterface(str);
            }
//             mediaExtraInfo.setWidth(exifInterface.getAttributeInt(bb7d7pu7.m5998("IAQIDgw-AA0dAQ"), 1));
            mediaExtraInfo.setWidth(exifInterface.getAttributeInt("ImageWidth", 1));
//             mediaExtraInfo.setHeight(exifInterface.getAttributeInt(bb7d7pu7.m5998("IAQIDgwlDAcOHQE"), 1));
            mediaExtraInfo.setHeight(exifInterface.getAttributeInt("ImageLength", 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaExtraInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17, types: [java.io.InputStream] */
    public static MediaExtraInfo getImageSize(String str) {
        InputStream fileInputStream;
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            if (PictureMimeType.isContent(str)) {
                fileInputStream = PictureContentResolver.getContentResolverOpenInputStream(AppStartV.getContext(), Uri.parse(str));
            } else {
                fileInputStream = new FileInputStream(str);
            }
            BitmapFactory.decodeStream(fileInputStream, null, options);
            mediaExtraInfo.setWidth(options.outWidth);
            mediaExtraInfo.setHeight(options.outHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaExtraInfo;
    }

    public static MediaExtraInfo getVideoSize(Context context, String str) {
        String extractMetadata = null;
        int i;
        int i2;
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                if (PictureMimeType.isContent(str)) {
                    mediaMetadataRetriever.setDataSource(context, Uri.parse(str));
                } else {
                    mediaMetadataRetriever.setDataSource(str);
                }
                extractMetadata = mediaMetadataRetriever.extractMetadata(24);
            } catch (Exception e) {
                e.printStackTrace();
            }
//             if (!TextUtils.equals(bb7d7pu7.m5998("UFk"), extractMetadata) && !TextUtils.equals(bb7d7pu7.m5998("W15Z"), extractMetadata)) {
            if (!TextUtils.equals("90", extractMetadata) && !TextUtils.equals("270", extractMetadata)) {
                i2 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(18));
                i = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(19));
                mediaExtraInfo.setWidth(i2);
                mediaExtraInfo.setHeight(i);
                mediaExtraInfo.setDuration(ValueOf.toLong(mediaMetadataRetriever.extractMetadata(9)));
                return mediaExtraInfo;
            }
            i = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(18));
            i2 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(19));
            mediaExtraInfo.setWidth(i2);
            mediaExtraInfo.setHeight(i);
            mediaExtraInfo.setDuration(ValueOf.toLong(mediaMetadataRetriever.extractMetadata(9)));
            return mediaExtraInfo;
        } finally {
            try {
                mediaMetadataRetriever.release();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static MediaExtraInfo getAudioSize(Context context, String str) {
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                if (PictureMimeType.isContent(str)) {
                    mediaMetadataRetriever.setDataSource(context, Uri.parse(str));
                } else {
                    mediaMetadataRetriever.setDataSource(str);
                }
                mediaExtraInfo.setDuration(ValueOf.toLong(mediaMetadataRetriever.extractMetadata(9)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mediaExtraInfo;
        } finally {
            try {
                mediaMetadataRetriever.release();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void removeMedia(Context context, int i) {
        try {
//             context.getApplicationContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, bb7d7pu7.m5998("NgANVFY"), new String[]{Long.toString(i)});
            context.getApplicationContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{Long.toString(i)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bundle createQueryArgsBundle(String str, String[] strArr, int i, int i2) {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= 26) {
//             bundle.putString(bb7d7pu7.m5998("CAcNGwYADVMYHAwbEEQIGw5EGhgFRBoMBQwKHQAGBw"), str);
            bundle.putString("android:query-arg-sql-selection", str);
//             bundle.putStringArray(bb7d7pu7.m5998("CAcNGwYADVMYHAwbEEQIGw5EGhgFRBoMBQwKHQAGB0QIGw4a"), strArr);
            bundle.putStringArray("android:query-arg-sql-selection-args", strArr);
//             bundle.putString(bb7d7pu7.m5998("CAcNGwYADVMYHAwbEEQIGw5EGhgFRBoGGx1EBhsNDBs"), bb7d7pu7.m5998("NgANSS0sOio"));
            bundle.putString("android:query-arg-sql-sort-order", "_id DESC");
            if (Build.VERSION.SDK_INT >= 30) {
//                 bundle.putString(bb7d7pu7.m5998("CAcNGwYADVMYHAwbEEQIGw5EGhgFRAUABAAd"), i + bb7d7pu7.m5998("SQYPDxoMHUk") + i2);
                bundle.putString("android:query-arg-sql-limit", i + " offset " + i2);
            }
        }
        return bundle;
    }

    public static void deleteCamera(Context context, String str) {
        try {
            if (PictureMimeType.isContent(str)) {
                context.getContentResolver().delete(Uri.parse(str), null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUri(Context context, Uri uri) {
        if (uri != null) {
            try {
                context.getContentResolver().delete(uri, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
