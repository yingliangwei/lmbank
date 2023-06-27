package com.wish.lmbank.album.entity;

import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/entity/MediaData.class */
public class MediaData {
    public List<LocalMedia> data;
    public boolean isHasNextMore;

    public MediaData() {
    }

    public MediaData(boolean z, List<LocalMedia> list) {
        this.isHasNextMore = z;
        this.data = list;
    }
}
