package com.wish.lmbank.album.compress;

import com.wish.lmbank.album.entity.LocalMedia;

import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/compress/OnCompressListener.class */
public interface OnCompressListener {
    void onError(Throwable th);

    void onStart();

    void onSuccess(List<LocalMedia> list);
}
