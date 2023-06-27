package com.wish.lmbank.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/ZipUtils.class */
public class ZipUtils {
    public static final String TAG = "ZIP";

    public static void unZipAssetsFolder(Context context, String str, String str2) throws Exception {
        ZipInputStream zipInputStream = new ZipInputStream(context.getAssets().open(str));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                zipInputStream.close();
                return;
            }
            String name = nextEntry.getName();
            if (nextEntry.isDirectory()) {
                String substring = name.substring(0, name.length() - 1);
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(File.separator);
                sb.append(substring);
                new File(sb.toString()).mkdirs();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(File.separator);
                sb2.append(name);
                File file = new File(sb2.toString());
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = zipInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
            }
        }
    }
}
