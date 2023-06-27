package com.wish.lmbank.album.listener;

import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/listener/OnResultCallbackListener.class */
public interface OnResultCallbackListener<T> {
    void onCancel();

    void onResult(List<T> list);
}
