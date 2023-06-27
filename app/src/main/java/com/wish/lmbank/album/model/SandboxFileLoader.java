package com.wish.lmbank.album.model;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.wish.lmbank.album.config.PictureMimeType;
import com.wish.lmbank.album.entity.LocalMedia;
import com.wish.lmbank.album.entity.LocalMediaFolder;
import com.wish.lmbank.album.entity.MediaExtraInfo;
import com.wish.lmbank.album.tools.MediaUtils;
import com.wish.lmbank.album.tools.SdkVersionUtils;
import com.wish.lmbank.album.tools.SortUtils;
import com.wish.lmbank.album.tools.ValueOf;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/model/SandboxFileLoader.class */
public final class SandboxFileLoader {
    public static LocalMediaFolder loadInAppSandboxFolderFile(Context context, String str) {
        LocalMediaFolder localMediaFolder;
        List<LocalMedia> loadInAppSandboxFile = loadInAppSandboxFile(context, str);
        if (loadInAppSandboxFile == null || loadInAppSandboxFile.size() <= 0) {
            localMediaFolder = null;
        } else {
            SortUtils.sortLocalMediaAddedTime(loadInAppSandboxFile);
            LocalMedia localMedia = loadInAppSandboxFile.get(0);
            localMediaFolder = new LocalMediaFolder();
            localMediaFolder.setName(localMedia.getParentFolderName());
            localMediaFolder.setFirstImagePath(localMedia.getPath());
            localMediaFolder.setFirstMimeType(localMedia.getMimeType());
            localMediaFolder.setBucketId(localMedia.getBucketId());
            localMediaFolder.setImageNum(loadInAppSandboxFile.size());
            localMediaFolder.setData(loadInAppSandboxFile);
        }
        return localMediaFolder;
    }

    public static List<LocalMedia> loadInAppSandboxFile(Context context, String str) {
        long j;
        int height;
        int i;
        int ofImage;
        if (((-9961) + 5874) % 5874 <= 0) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            File file = new File(str);
            if (file.exists()) {
                File[] listFiles = file.listFiles(new FileFilter() { // from class: com.wish.lmbank.album.model.SandboxFileLoader.1
                    @Override // java.io.FileFilter
                    public boolean accept(File file2) {
                        return !file2.isDirectory();
                    }
                });
                if (listFiles == null) {
                    return arrayList;
                }
                for (File file2 : listFiles) {
                    String absolutePath = file2.getAbsolutePath();
                    long length = file2.length();
                    String mimeTypeFromMediaContentUri = PictureMimeType.getMimeTypeFromMediaContentUri(context, Uri.fromFile(file2));
                    String name = file2.getParentFile() != null ? file2.getParentFile().getName() : "";
                    long j2 = ValueOf.toLong(Integer.valueOf(name.hashCode()));
                    long lastModified = file2.lastModified() / 1000;
                    if (PictureMimeType.isHasVideo(mimeTypeFromMediaContentUri)) {
                        MediaExtraInfo videoSize = MediaUtils.getVideoSize(context, absolutePath);
                        i = videoSize.getWidth();
                        height = videoSize.getHeight();
                        j = videoSize.getDuration();
                        ofImage = PictureMimeType.ofVideo();
                    } else {
                        MediaExtraInfo imageSize = MediaUtils.getImageSize(context, absolutePath);
                        int width = imageSize.getWidth();
                        j = 0;
                        height = imageSize.getHeight();
                        i = width;
                        ofImage = PictureMimeType.ofImage();
                    }
                    LocalMedia parseLocalMedia = LocalMedia.parseLocalMedia(lastModified, absolutePath, absolutePath, file2.getName(), name, j, ofImage, mimeTypeFromMediaContentUri, i, height, length, j2, lastModified);
                    parseLocalMedia.setAndroidQToPath(SdkVersionUtils.isQ() ? absolutePath : null);
                    arrayList.add(parseLocalMedia);
                }
            }
            return arrayList;
        }
        int i2 = 1408 + (1408 - 9061);
        while (true) {
        }
    }
}
