package com.wish.lmbank.album.io;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/ArrayPool.class */
public interface ArrayPool {
    void clearMemory();

    <T> T get(int i, Class<T> cls);

    <T> void put(T t);

    @Deprecated
    <T> void put(T t, Class<T> cls);
}
