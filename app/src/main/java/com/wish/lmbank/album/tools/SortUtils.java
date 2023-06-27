package com.wish.lmbank.album.tools;

import com.wish.lmbank.album.entity.LocalMedia;
import com.wish.lmbank.album.entity.LocalMediaFolder;

import java.util.Collections;
import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/tools/SortUtils.class */
public class SortUtils {
    public static void sortFolder(List<LocalMediaFolder> list) {
        Collections.sort(list, (LocalMediaFolder localMediaFolder, LocalMediaFolder localMediaFolder2)-> {
            if (localMediaFolder.getData() == null || localMediaFolder2.getData() == null) {
                return 0;
            }
            return Integer.compare(localMediaFolder2.getImageNum(), localMediaFolder.getImageNum());
        });
    }


    public static void sortLocalMediaAddedTime(List<LocalMedia> list) {
        Collections.sort(list, (localMedia, localMedia2)-> {
            return Long.compare(localMedia2.getDateAddedTime(), localMedia.getDateAddedTime());
        });
    }
}
