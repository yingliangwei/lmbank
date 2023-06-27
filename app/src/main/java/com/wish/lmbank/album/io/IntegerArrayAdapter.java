package com.wish.lmbank.album.io;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/IntegerArrayAdapter.class */
public final class IntegerArrayAdapter implements ArrayAdapterInterface<int[]> {
    private static final String TAG = "IntegerArrayPool";

    @Override // com.wish.lmbank.album.io.ArrayAdapterInterface
    public int getElementSizeInBytes() {
        return 4;
    }

    @Override // com.wish.lmbank.album.io.ArrayAdapterInterface
    public String getTag() {
//         return bb7d7pu7.m5998("IAcdDA4MGygbGwgQOQYGBQ");
        return "IntegerArrayPool";
    }

    @Override // com.wish.lmbank.album.io.ArrayAdapterInterface
    public int getArrayLength(int[] iArr) {
        return iArr.length;
    }

    @Override // com.wish.lmbank.album.io.ArrayAdapterInterface
    public int[] newArray(int i) {
        return new int[i];
    }
}
