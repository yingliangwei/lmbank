package com.wish.lmbank.album.compress;

import com.wish.lmbank.album.entity.LocalMedia;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/compress/InputStreamProvider.class */
public interface InputStreamProvider {
    void close();

    LocalMedia getMedia();

    String getPath();

    InputStream open() throws IOException;
}
