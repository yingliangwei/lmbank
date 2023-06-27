//
// Decompiled by FernFlower - 1249ms
//
package com.wish.lmbank.album.io;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.core.util.Preconditions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import gv00l3ah.mvdt7w.bb7d7pu7;

public final class LruArrayPool implements ArrayPool {
    public static final int DEFAULT_SIZE = 4194304;
    static final int MAX_OVER_SIZE_MULTIPLE = 8;
    private static final int SINGLE_ARRAY_MAX_SIZE_DIVISOR = 2;
    private final Map adapters = new HashMap();
    private int currentSize;
    private final GroupedLinkedMap groupedMap = new GroupedLinkedMap();
    private final KeyPool keyPool = new KeyPool();
    private final int maxSize;
    private final Map sortedSizes = new HashMap();

    public LruArrayPool() {
        this.maxSize = 4194304;
    }

    public LruArrayPool(int var1) {
        this.maxSize = var1;
    }

    private void decrementArrayOfSize(int var1, Class var2) {
        NavigableMap var4 = this.getSizesForAdapter(var2);
        Integer var3 = (Integer)var4.get(var1);
        if (var3 != null) {
            if (var3 == 1) {
                var4.remove(var1);
            } else {
                var4.put(var1, var3 - 1);
            }

        } else {
            StringBuilder var5 = new StringBuilder();
//             var5.append(bb7d7pu7.m5998("PRsADA1JHQZJDQwKGwwEDAcdSQwEGR0QSRoAEwxFSRoAEwxTSQ"));
            var5.append("Tried to decrement empty size, size: ");
            var5.append(var1);
//             var5.append(bb7d7pu7.m5998("RUkdAQAaU0k"));
            var5.append(", this: ");
            var5.append(this);
            throw new NullPointerException(var5.toString());
        }
    }

    private void evict() {
        this.evictToSize(this.maxSize);
    }

    private void evictToSize(int var1) {
        while(this.currentSize > var1) {
            Object var2 = this.groupedMap.removeLast();
            Preconditions.checkNotNull(var2);
            ArrayAdapterInterface var3 = this.getAdapterFromObject(var2);
            this.currentSize -= var3.getArrayLength(var2) * var3.getElementSizeInBytes();
            this.decrementArrayOfSize(var3.getArrayLength(var2), var2.getClass());
            if (Log.isLoggable(var3.getTag(), 2)) {
                String var4 = var3.getTag();
                StringBuilder var5 = new StringBuilder();
//                 var5.append(bb7d7pu7.m5998("DB8ACh0MDVNJ"));
                var5.append("evicted: ");
                var5.append(var3.getArrayLength(var2));
                Log.v(var4, var5.toString());
            }
        }

    }

    private ArrayAdapterInterface getAdapterFromObject(Object var1) {
        return this.getAdapterFromType(var1.getClass());
    }

    private ArrayAdapterInterface getAdapterFromType(Class var1) {
        ArrayAdapterInterface var2 = (ArrayAdapterInterface)this.adapters.get(var1);
        Object var3 = var2;
        if (var2 == null) {
            if (var1.equals(int[].class)) {
                var3 = new IntegerArrayAdapter();
            } else {
                if (!var1.equals(byte[].class)) {
                    StringBuilder var4 = new StringBuilder();
//                     var4.append(bb7d7pu7.m5998("JwZJCBsbCBBJGQYGBUkPBhwHDUkPBhtTSQ"));
                    var4.append("No array pool found for: ");
                    var4.append(var1.getSimpleName());
                    throw new IllegalArgumentException(var4.toString());
                }

                var3 = new ByteArrayAdapter();
            }

            this.adapters.put(var1, var3);
        }

        return (ArrayAdapterInterface)var3;
    }

    private Object getArrayForKey(Key var1) {
        return this.groupedMap.get(var1);
    }

    @SuppressLint("LogTagMismatch")
    private Object getForKey(Key var1, Class var2) {
        ArrayAdapterInterface var3 = this.getAdapterFromType(var2);
        Object var4 = this.getArrayForKey(var1);
        if (var4 != null) {
            this.currentSize -= var3.getArrayLength(var4) * var3.getElementSizeInBytes();
            this.decrementArrayOfSize(var3.getArrayLength(var4), var2);
        }

        Object var5 = var4;
        if (var4 == null) {
            if (Log.isLoggable(var3.getTag(), Log.VERBOSE)) {
                String var7 = var3.getTag();
                StringBuilder var6 = new StringBuilder();
//                 var6.append(bb7d7pu7.m5998("KAUFBgoIHQwNSQ"));
                var6.append("Allocated ");
                var6.append(var1.size);
//                 var6.append(bb7d7pu7.m5998("SQsQHQwa"));
                var6.append(" bytes");
                Log.v(var7, var6.toString());
            }

            var5 = var3.newArray(var1.size);
        }

        return var5;
    }

    private NavigableMap getSizesForAdapter(Class var1) {
        NavigableMap var2 = (NavigableMap)this.sortedSizes.get(var1);
        Object var3 = var2;
        if (var2 == null) {
            var3 = new TreeMap();
            this.sortedSizes.put(var1, var3);
        }

        return (NavigableMap)var3;
    }

    private boolean isNoMoreThanHalfFull() {
        int var1 = this.currentSize;
        boolean var2;
        if (var1 != 0 && this.maxSize / var1 < 2) {
            var2 = false;
        } else {
            var2 = true;
        }

        return var2;
    }

    private boolean isSmallEnoughForReuse(int var1) {
        boolean var2;
        if (var1 <= this.maxSize / 2) {
            var2 = true;
        } else {
            var2 = false;
        }

        return var2;
    }

    private boolean mayFillRequest(int var1, Integer var2) {
        boolean var3;
        if (var2 == null || !this.isNoMoreThanHalfFull() && var2 > var1 * 8) {
            var3 = false;
        } else {
            var3 = true;
        }

        return var3;
    }

    public void clearMemory() {
        synchronized(this){}

        try {
            this.evictToSize(0);
        } finally {
            ;
        }

    }

    public Object get(int var1, Class var2) {
        synchronized(this){}

        Throwable var10000;
        label245: {
            Integer var3;
            boolean var10001;
            try {
                var3 = (Integer)this.getSizesForAdapter(var2).ceilingKey(var1);
            } catch (Throwable var33) {
                var10000 = var33;
                var10001 = false;
                break label245;
            }

            Key var36;
            label246: {
                label235: {
                    try {
                        if (!this.mayFillRequest(var1, var3)) {
                            break label235;
                        }
                    } catch (Throwable var32) {
                        var10000 = var32;
                        var10001 = false;
                        break label245;
                    }

                    try {
                        var36 = this.keyPool.get(var3, var2);
                        break label246;
                    } catch (Throwable var31) {
                        var10000 = var31;
                        var10001 = false;
                        break label245;
                    }
                }

                try {
                    var36 = this.keyPool.get(var1, var2);
                } catch (Throwable var30) {
                    var10000 = var30;
                    var10001 = false;
                    break label245;
                }
            }

            label226:
            try {
                Object var35 = this.getForKey(var36, var2);
                return var35;
            } catch (Throwable var29) {
                var10000 = var29;
                var10001 = false;
                break label226;
            }
        }

        Throwable var34 = var10000;
        throw new RuntimeException(var34);
    }

    int getCurrentSize() {
        Iterator var1 = this.sortedSizes.keySet().iterator();
        int var2 = 0;

        while(var1.hasNext()) {
            Class var3 = (Class)var1.next();

            Integer var5;
            ArrayAdapterInterface var6;
            int var7;
            for(Iterator var4 = ((NavigableMap)this.sortedSizes.get(var3)).keySet().iterator(); var4.hasNext(); var2 += (Integer)((NavigableMap)this.sortedSizes.get(var3)).get(var5) * var7 * var6.getElementSizeInBytes()) {
                var5 = (Integer)var4.next();
                var6 = this.getAdapterFromType(var3);
                var7 = var5;
            }
        }

        return var2;
    }

    public void put(Object var1) {
        synchronized(this){}

        Throwable var10000;
        label1395: {
            Class var2;
            boolean var10001;
            try {
                var2 = var1.getClass();
            } catch (Throwable var217) {
                var10000 = var217;
                var10001 = false;
                break label1395;
            }

            ArrayAdapterInterface var3;
            try {
                var3 = this.getAdapterFromType(var2);
            } catch (Throwable var216) {
                var10000 = var216;
                var10001 = false;
                break label1395;
            }

            int var4;
            try {
                var4 = var3.getArrayLength(var1);
            } catch (Throwable var215) {
                var10000 = var215;
                var10001 = false;
                break label1395;
            }

            int var5;
            try {
                var5 = var3.getElementSizeInBytes() * var4;
            } catch (Throwable var214) {
                var10000 = var214;
                var10001 = false;
                break label1395;
            }

            boolean var6;
            try {
                var6 = this.isSmallEnoughForReuse(var5);
            } catch (Throwable var213) {
                var10000 = var213;
                var10001 = false;
                break label1395;
            }

            if (!var6) {
                return;
            }

            Key var221;
            try {
                var221 = this.keyPool.get(var4, var2);
            } catch (Throwable var212) {
                var10000 = var212;
                var10001 = false;
                break label1395;
            }

            try {
                this.groupedMap.put(var221, var1);
            } catch (Throwable var211) {
                var10000 = var211;
                var10001 = false;
                break label1395;
            }

            NavigableMap var220;
            try {
                var220 = this.getSizesForAdapter(var2);
            } catch (Throwable var210) {
                var10000 = var210;
                var10001 = false;
                break label1395;
            }

            Integer var218;
            try {
                var218 = (Integer)var220.get(var221.size);
            } catch (Throwable var209) {
                var10000 = var209;
                var10001 = false;
                break label1395;
            }

            int var7;
            try {
                var7 = var221.size;
            } catch (Throwable var208) {
                var10000 = var208;
                var10001 = false;
                break label1395;
            }

            if (var218 == null) {
                var4 = 1;
            } else {
                try {
                    var4 = var218;
                } catch (Throwable var207) {
                    var10000 = var207;
                    var10001 = false;
                    break label1395;
                }

                ++var4;
            }

            try {
                var220.put(var7, var4);
            } catch (Throwable var206) {
                var10000 = var206;
                var10001 = false;
                break label1395;
            }

            try {
                this.currentSize += var5;
            } catch (Throwable var205) {
                var10000 = var205;
                var10001 = false;
                break label1395;
            }

            label1346:
            try {
                this.evict();
                return;
            } catch (Throwable var204) {
                var10000 = var204;
                var10001 = false;
                break label1346;
            }
        }

        Throwable var219 = var10000;
        throw new RuntimeException(var219);
    }

    @Deprecated
    public void put(Object var1, Class var2) {
        this.put(var1);
    }

    final class Key implements PoolAble {
        private Class arrayClass;
        private final KeyPool pool;
        int size;

        Key(KeyPool var1) {
            this.pool = var1;
        }

        public boolean equals(Object var1) {
            boolean var2 = var1 instanceof Key;
            boolean var3 = false;
            boolean var4 = var3;
            if (var2) {
                Key var5 = (Key)var1;
                var4 = var3;
                if (this.size == var5.size) {
                    var4 = var3;
                    if (this.arrayClass == var5.arrayClass) {
                        var4 = true;
                    }
                }
            }

            return var4;
        }

        public int hashCode() {
            int var1 = this.size;
            Class var2 = this.arrayClass;
            int var3;
            if (var2 != null) {
                var3 = var2.hashCode();
            } else {
                var3 = 0;
            }

            return var3 + var1 * 31;
        }

        void init(int var1, Class var2) {
            this.size = var1;
            this.arrayClass = var2;
        }

        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            StringBuilder var1 = new StringBuilder();
//             var1.append(bb7d7pu7.m5998("IgwQEhoAEwxU"));
            var1.append("Key{size=");
            var1.append(this.size);
//             var1.append(bb7d7pu7.m5998("CBsbCBBU"));
            var1.append("array=");
            var1.append(this.arrayClass);
            var1.append('}');
            return var1.toString();
        }
    }

    //
// Decompiled by FernFlower - 291ms
//

    final class KeyPool extends BaseKeyPool {
        protected Key create() {
            return new Key(this);
        }

        Key get(int var1, Class var2) {
            Key var3 = (Key)this.get();
            var3.init(var1, var2);
            return var3;
        }
    }


}

