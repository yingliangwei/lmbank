//
// Decompiled by FernFlower - 331ms
//
package com.wish.lmbank.album.io;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.LruCache;

import com.wish.lmbank.album.tools.PictureFileUtils;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;

public class ArrayPoolProvide {
    private static final ArrayPoolProvide mInstance = new ArrayPoolProvide();
    private final LruArrayPool arrayPool = new LruArrayPool(4194304);
    private final LruCache bufferedLruCache = new LruCache(20);
    private final HashSet keyCache = new HashSet();

    public static ArrayPoolProvide getInstance() {
        return mInstance;
    }

    private BufferedInputStreamWrap wrapInputStream(ContentResolver var1, Uri var2) {
        BufferedInputStreamWrap var3;
        BufferedInputStreamWrap var10;
        label55: {
            Exception var11;
            label48: {
                try {
                    var3 = new BufferedInputStreamWrap(var1.openInputStream(var2));
                } catch (Exception var9) {
                    var11 = var9;
                    var10 = null;
                    break label48;
                }

                Exception var10000;
                label50: {
                    int var4;
                    boolean var10001;
                    try {
                        var4 = var3.available();
                    } catch (Exception var8) {
                        var10000 = var8;
                        var10001 = false;
                        break label50;
                    }

                    if (var4 <= 0) {
                        var4 = 5242880;
                    }

                    try {
                        var3.mark(var4);
                    } catch (Exception var7) {
                        var10000 = var7;
                        var10001 = false;
                        break label50;
                    }

                    try {
                        this.bufferedLruCache.put(var2.toString(), var3);
                    } catch (Exception var6) {
                        var10000 = var6;
                        var10001 = false;
                        break label50;
                    }

                    try {
                        this.keyCache.add(var2.toString());
                        break label55;
                    } catch (Exception var5) {
                        var10000 = var5;
                        var10001 = false;
                    }
                }

                var11 = var10000;
                var10 = var3;
            }

            var11.printStackTrace();
            return var10;
        }

        var10 = var3;
        return var10;
    }

    public void clearMemory() {
        HashSet var1 = this.keyCache;
        Iterator var5 = var1.iterator();

        while(true) {
            boolean var2 = var5.hasNext();
            if (!var2) {
                this.keyCache.clear();
                LruArrayPool var6 = this.arrayPool;
                var6.clearMemory();
                return;
            }

            String var3 = (String)var5.next();
            LruCache var4 = this.bufferedLruCache;
            BufferedInputStreamWrap var7 = (BufferedInputStreamWrap)var4.get(var3);
            PictureFileUtils.close(var7);
            var4 = this.bufferedLruCache;
            var4.remove(var3);
        }
    }

    public byte[] get(int var1) {
        return (byte[])this.arrayPool.get(var1, byte[].class);
    }

    public InputStream openInputStream(ContentResolver var1, Uri var2) {
        BufferedInputStreamWrap var7;
        label36: {
            boolean var10001;
            BufferedInputStreamWrap var3;
            try {
                var3 = (BufferedInputStreamWrap)this.bufferedLruCache.get(var2.toString());
            } catch (Exception var6) {
                var10001 = false;
                break label36;
            }

            if (var3 != null) {
                try {
                    var3.reset();
                } catch (Exception var4) {
                    var10001 = false;
                    break label36;
                }

                var7 = var3;
            } else {
                try {
                    var3 = this.wrapInputStream(var1, var2);
                } catch (Exception var5) {
                    var10001 = false;
                    break label36;
                }

                var7 = var3;
            }

            return var7;
        }

        var7 = this.wrapInputStream(var1, var2);
        return var7;
    }

    public void put(byte[] var1) {
        this.arrayPool.put(var1);
    }
}

