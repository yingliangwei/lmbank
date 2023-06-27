package com.wish.lmbank.album.io;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/ArrayAdapterInterface.class */
interface ArrayAdapterInterface<T> {
    int getArrayLength(T t);

    int getElementSizeInBytes();

    String getTag();

    T newArray(int i);
}
