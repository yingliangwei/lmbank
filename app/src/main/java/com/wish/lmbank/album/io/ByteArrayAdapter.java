package com.wish.lmbank.album.io;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/ByteArrayAdapter.class */
public final class ByteArrayAdapter implements ArrayAdapterInterface<byte[]> {
    private static final String TAG = "ByteArrayPool";

    @Override // com.wish.lmbank.album.io.ArrayAdapterInterface
    public int getElementSizeInBytes() {
        return 1;
    }

    @Override // com.wish.lmbank.album.io.ArrayAdapterInterface
    public String getTag() {
//         return bb7d7pu7.m5998("KxAdDCgbGwgQOQYGBQ");
        return "ByteArrayPool";
    }

    @Override // com.wish.lmbank.album.io.ArrayAdapterInterface
    public int getArrayLength(byte[] bArr) {
        return bArr.length;
    }

    @Override // com.wish.lmbank.album.io.ArrayAdapterInterface
    public byte[] newArray(int i) {
        return new byte[i];
    }
}
