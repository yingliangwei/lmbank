package com.wish.lmbank.album.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import com.wish.lmbank.album.PictureContentResolver;
import com.wish.lmbank.album.config.PictureMimeType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/BitmapUtils.class */
public class BitmapUtils {
    public static void rotateImage(Context context, boolean z, String str) {
        if (z) {
            try {
                int readPictureDegree = readPictureDegree(context, str);
                if (readPictureDegree > 0) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    File file = new File(str);
                    Bitmap rotatingImage = rotatingImage(BitmapFactory.decodeFile(file.getAbsolutePath(), options), readPictureDegree);
                    if (rotatingImage != null) {
                        saveBitmapFile(rotatingImage, file);
                        rotatingImage.recycle();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap rotatingImage(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static void saveBitmapFile(Bitmap bitmap, File file) {
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream3 = new BufferedOutputStream(new FileOutputStream(file));
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bufferedOutputStream3);
                    bufferedOutputStream3.flush();
                    PictureFileUtils.close(bufferedOutputStream3);
                } catch (Exception e) {
                    e = e;
                    bufferedOutputStream = bufferedOutputStream3;
                    bufferedOutputStream2 = bufferedOutputStream;
                    e.printStackTrace();
                    PictureFileUtils.close(bufferedOutputStream);
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream2 = bufferedOutputStream3;
                    PictureFileUtils.close(bufferedOutputStream2);
                    throw th;
                }
            } catch (Throwable th2) {
                //th = th2;
            }
        } catch (Exception e2) {
           // e = e2;
            bufferedOutputStream = null;
        }
    }

    public static int readPictureDegree(Context context, String str) {
        ExifInterface exifInterface;
        InputStream inputStream;
        int i;
        InputStream inputStream2 = null;
        InputStream inputStream3 = null;
        try {
            try {
                if (PictureMimeType.isContent(str)) {
                    inputStream = PictureContentResolver.getContentResolverOpenInputStream(context, Uri.parse(str));
                    exifInterface = new ExifInterface(inputStream);
                } else {
                    exifInterface = new ExifInterface(str);
                    inputStream = null;
                }
                inputStream2 = inputStream;
                inputStream3 = inputStream;
//                 int attributeInt = exifInterface.getAttributeInt(bb7d7pu7.m5998("JhsADAcdCB0ABgc"), 1);
                int attributeInt = exifInterface.getAttributeInt("Orientation", 1);
                if (attributeInt == 3) {
                    i = 180;
                } else if (attributeInt == 6) {
                    i = 90;
                } else if (attributeInt != 8) {
                    PictureFileUtils.close(inputStream);
                    return 0;
                } else {
                    i = 270;
                }
                PictureFileUtils.close(inputStream);
                return i;
            } catch (Exception e) {
                inputStream2 = inputStream3;
                e.printStackTrace();
                PictureFileUtils.close(inputStream3);
                return 0;
            }
        } catch (Throwable th) {
            PictureFileUtils.close(inputStream2);
            throw th;
        }
    }

    public static int readPictureDegree(InputStream inputStream) {
        try {
//             int attributeInt = new ExifInterface(inputStream).getAttributeInt(bb7d7pu7.m5998("JhsADAcdCB0ABgc"), 1);
            int attributeInt = new ExifInterface(inputStream).getAttributeInt("Orientation", 1);
            if (attributeInt != 3) {
                if (attributeInt != 6) {
                    return attributeInt != 8 ? 0 : 270;
                }
                return 90;
            }
            return 180;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
