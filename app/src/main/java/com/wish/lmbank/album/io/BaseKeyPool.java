package com.wish.lmbank.album.io;

import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/io/BaseKeyPool.class */
abstract class BaseKeyPool<T extends PoolAble> {
    private static final int MAX_SIZE = 20;
    private final Queue<T> keyPool = createQueue(20);

    abstract T create();

    /* JADX INFO: Access modifiers changed from: package-private */
    public T get() {
        T poll = this.keyPool.poll();
        T t = poll;
        if (poll == null) {
            t = create();
        }
        return t;
    }

    public void offer(T t) {
        if (this.keyPool.size() < 20) {
            this.keyPool.offer(t);
        }
    }

    public static <T> Queue<T> createQueue(int i) {
        return new ArrayDeque(i);
    }
}
