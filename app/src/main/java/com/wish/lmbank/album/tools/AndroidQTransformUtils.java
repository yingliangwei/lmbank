package com.wish.lmbank.album.tools;

import android.content.Context;
import android.net.Uri;

import com.wish.lmbank.album.PictureContentResolver;

import java.io.File;
import java.io.FileOutputStream;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/AndroidQTransformUtils.class */
public class AndroidQTransformUtils {
    public static String copyPathToAndroidQ(Context context, long j, String str, int i, int i2, String str2, String str3) {
        try {
            String createFilePath = PictureFileUtils.createFilePath(context, StringUtils.getEncryptionValue(j, i, i2), str2, str3);
            File file = new File(createFilePath);
            if (file.exists()) {
                return createFilePath;
            }
            return PictureFileUtils.writeFileFromIS(PictureContentResolver.getContentResolverOpenInputStream(context, Uri.parse(str)), new FileOutputStream(file)) ? createFilePath : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
