//
// Decompiled by Procyon - 758ms
//
package com.wish.lmbank.album.compress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;

import com.wish.lmbank.album.config.PictureMimeType;
import com.wish.lmbank.album.tools.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class Engine
{
    private static final int DEFAULT_QUALITY = 80;
    private int compressQuality;
    private final Context context;
    @Deprecated
    private final boolean focusAlpha;
    private final boolean isAutoRotating;
    private int srcHeight;
    private final InputStreamProvider srcImg;
    private int srcWidth;
    private final File tagImg;

    Engine(final Context context, final InputStreamProvider srcImg, final File tagImg, final boolean focusAlpha, final int n, final boolean isAutoRotating) throws IOException {
        this.tagImg = tagImg;
        this.srcImg = srcImg;
        this.context = context;
        this.focusAlpha = focusAlpha;
        this.isAutoRotating = isAutoRotating;
        int compressQuality = n;
        if (n <= 0) {
            compressQuality = 80;
        }
        this.compressQuality = compressQuality;
        if (srcImg.getMedia().getWidth() > 0 && srcImg.getMedia().getHeight() > 0) {
            this.srcWidth = srcImg.getMedia().getWidth();
            this.srcHeight = srcImg.getMedia().getHeight();
        }
        else {
            final Options bitmapFactory$Options = new Options();
            bitmapFactory$Options.inJustDecodeBounds = true;
            bitmapFactory$Options.inSampleSize = 1;
            BitmapFactory.decodeStream(srcImg.open(), (Rect)null, bitmapFactory$Options);
            this.srcWidth = bitmapFactory$Options.outWidth;
            this.srcHeight = bitmapFactory$Options.outHeight;
        }
    }

    private int computeSize() {
        int srcWidth;
        final int n = srcWidth = this.srcWidth;
        if (n % 2 == 1) {
            srcWidth = n + 1;
        }
        this.srcWidth = srcWidth;
        int srcHeight;
        final int n2 = srcHeight = this.srcHeight;
        if (n2 % 2 == 1) {
            srcHeight = n2 + 1;
        }
        this.srcHeight = srcHeight;
        final int max = Math.max(srcWidth, srcHeight);
        final float n3 = Math.min(this.srcWidth, this.srcHeight) / (float)max;
        int n4;
        if (n3 <= 1.0f && n3 > 0.5625) {
            if (max < 1664) {
                n4 = 1;
            }
            else if (max < 4990) {
                n4 = 2;
            }
            else if (max > 4990 && max < 10240) {
                n4 = 4;
            }
            else {
                n4 = max / 1280;
            }
        }
        else {
            final double n5 = n3;
            if (n5 <= 0.5625 && n5 > 0.5) {
                if ((n4 = max / 1280) == 0) {
                    n4 = 1;
                }
            }
            else {
                n4 = (int)Math.ceil(max / (1280.0 / n5));
            }
        }
        return n4;
    }

    File compress() throws IOException {
        final Options bitmapFactory$Options = new Options();
        bitmapFactory$Options.inSampleSize = this.computeSize();
        final Bitmap decodeStream = BitmapFactory.decodeStream(this.srcImg.open(), (Rect)null, bitmapFactory$Options);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap rotatingImage = decodeStream;
        if (this.isAutoRotating) {
            rotatingImage = decodeStream;
            if (Checker.isJPG(this.srcImg.getMedia().getMimeType())) {
                String s;
                if (this.srcImg.getMedia().isCut()) {
                    s = this.srcImg.getMedia().getCutPath();
                }
                else {
                    s = this.srcImg.getMedia().getPath();
                }
                int n;
                if (PictureMimeType.isContent(s)) {
                    n = BitmapUtils.readPictureDegree(this.srcImg.open());
                }
                else {
                    n = BitmapUtils.readPictureDegree(this.context, s);
                }
                rotatingImage = decodeStream;
                if (n > 0) {
                    rotatingImage = BitmapUtils.rotatingImage(decodeStream, n);
                }
            }
        }
        File tagImg;
        if (rotatingImage != null) {
            final int compressQuality = this.compressQuality;
            int compressQuality2;
            if (compressQuality <= 0 || (compressQuality2 = compressQuality) > 100) {
                compressQuality2 = 80;
            }
            this.compressQuality = compressQuality2;
            Bitmap.CompressFormat bitmap$CompressFormat;
            if (this.focusAlpha || rotatingImage.hasAlpha()) {
                bitmap$CompressFormat = Bitmap.CompressFormat.PNG;
            }
            else {
                bitmap$CompressFormat = Bitmap.CompressFormat.JPEG;
            }
            rotatingImage.compress(bitmap$CompressFormat, this.compressQuality, (OutputStream)byteArrayOutputStream);
            rotatingImage.recycle();
            final FileOutputStream fileOutputStream = new FileOutputStream(this.tagImg);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
            byteArrayOutputStream.close();
            tagImg = this.tagImg;
        }
        else {
            tagImg = null;
        }
        return tagImg;
    }
}

