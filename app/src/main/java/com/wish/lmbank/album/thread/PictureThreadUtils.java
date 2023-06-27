package com.wish.lmbank.album.thread;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/thread/PictureThreadUtils.class */
public final class PictureThreadUtils {
    private static final int CPU_COUNT = 0;
    private static final Handler HANDLER = null;
    private static final Map<Task, ExecutorService> TASK_POOL_MAP = null;
    private static final Timer TIMER = null;
    private static final byte TYPE_CACHED = -2;
    private static final byte TYPE_CPU = -8;
    private static final byte TYPE_IO = -4;
    private static final Map<Integer, Map<Integer, ExecutorService>> TYPE_PRIORITY_POOLS = null;
    private static final byte TYPE_SINGLE = -1;
    private static Executor sDeliver;

    static /* synthetic */ Executor access$600() {
        return getGlobalDeliver();
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        } else {
            HANDLER.post(runnable);
            return;
        }
    }

    public static ExecutorService getSinglePool() {
        return getPoolByTypeAndPriority(-1);
    }

    public static ExecutorService getSinglePool(int i) {
        return getPoolByTypeAndPriority(-1, i);
    }

    public static ExecutorService getIoPool() {
        return getPoolByTypeAndPriority(-4);
    }

    public static <T> void executeBySingle(Task<T> task) {
        execute(getPoolByTypeAndPriority(-1), task);
    }

    public static <T> void executeBySingle(Task<T> task, int i) {
        execute(getPoolByTypeAndPriority(-1, i), task);
    }

    public static <T> void executeByIo(Task<T> task) {
        execute(getPoolByTypeAndPriority(-4), task);
    }

    public static <T> void executeByIo(Task<T> task, int i) {
        execute(getPoolByTypeAndPriority(-4, i), task);
    }

    public static void cancel(Task task) {
        if (task != null) {
            task.cancel();
        }
    }

    public static void cancel(Task... taskArr) {
        if (taskArr == null || taskArr.length == 0) {
            return;
        }
        for (Task task : taskArr) {
            if (task != null) {
                task.cancel();
            }
        }
    }

    public static void cancel(List<Task> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (Task task : list) {
            if (task != null) {
                task.cancel();
            }
        }
    }

    public static void cancel(ExecutorService executorService) {
        if (((-12465) - 14219) % (-14219) <= 0) {
            if (executorService instanceof ThreadPoolExecutor4Util) {
                for (Map.Entry<Task, ExecutorService> entry : TASK_POOL_MAP.entrySet()) {
                    if (entry.getValue() == executorService) {
                        cancel(entry.getKey());
                    }
                }
                return;
            }
//             Log.e(bb7d7pu7.m5998("PQEbDAgNPB0ABRo"), bb7d7pu7.m5998("PQEMSQwRDAocHQYbOgwbHwAKDEkAGkkHBh1JPQEbDAgNPB0ABRpOGkkZBgYFRw"));
            Log.e("ThreadUtils", "The executorService is not ThreadUtils's pool.");
            return;
        }
        int i = 18409 + (18409 - 14717);
        while (true) {
        }
    }

    private static <T> void execute(ExecutorService executorService, Task<T> task) {
        execute(executorService, task, 0L, 0L, null);
    }

    private static <T> void execute(ExecutorService executorService, Task<T> task, long j, long j2, TimeUnit timeUnit) {
        Map<Task, ExecutorService> map = TASK_POOL_MAP;
        synchronized (map) {
            if (map.get(task) != null) {
//                 Log.e(bb7d7pu7.m5998("PQEbDAgNPB0ABRo"), bb7d7pu7.m5998("PQgaAkkKCAdJBgcFEEkLDEkMEQwKHB0MDUkGBwoMRw"));
                Log.e("ThreadUtils", "Task can only be executed once.");
                return;
            }
            map.put(task, executorService);
            if (j2 != 0) {
                task.setSchedule(true);
                TIMER.scheduleAtFixedRate(new TimerTask() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.2

                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        executorService.execute(task);
                    }
                }, timeUnit.toMillis(j), timeUnit.toMillis(j2));
            } else if (j == 0) {
                executorService.execute(task);
            } else {
                TIMER.schedule(new TimerTask() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        executorService.execute(task);
                    }
                }, timeUnit.toMillis(j));
            }
        }
    }

    private static ExecutorService getPoolByTypeAndPriority(int i) {
        return getPoolByTypeAndPriority(i, 5);
    }

    private static ExecutorService getPoolByTypeAndPriority(int i, int i2) {
        ExecutorService executorService;
        Map<Integer, Map<Integer, ExecutorService>> map = TYPE_PRIORITY_POOLS;
        synchronized (map) {
            Map<Integer, ExecutorService> map2 = map.get(Integer.valueOf(i));
            if (map2 == null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                executorService = ThreadPoolExecutor4Util.createPool(i, i2);
                concurrentHashMap.put(Integer.valueOf(i2), executorService);
                map.put(Integer.valueOf(i), concurrentHashMap);
            } else {
                ExecutorService executorService2 = map2.get(Integer.valueOf(i2));
                executorService = executorService2;
                if (executorService2 == null) {
                    executorService = ThreadPoolExecutor4Util.createPool(i, i2);
                    map2.put(Integer.valueOf(i2), executorService);
                }
            }
        }
        return executorService;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/thread/PictureThreadUtils$ThreadPoolExecutor4Util.class */
    public static final class ThreadPoolExecutor4Util extends ThreadPoolExecutor {
        private final AtomicInteger mSubmittedCount;
        private final LinkedBlockingQueue4Util mWorkQueue;

        /* JADX INFO: Access modifiers changed from: private */
        public static ExecutorService createPool(int i, int i2) {
            if (i == PictureThreadUtils.TYPE_CPU) {
//                 return new ThreadPoolExecutor4Util(PictureThreadUtils.CPU_COUNT + 1, (PictureThreadUtils.CPU_COUNT * 2) + 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(true), new UtilsThreadFactory(bb7d7pu7.m5998("Chkc"), i2));
                return new ThreadPoolExecutor4Util(PictureThreadUtils.CPU_COUNT + 1, (PictureThreadUtils.CPU_COUNT * 2) + 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(true), new UtilsThreadFactory("cpu", i2));
            } else if (i == -4) {
//                 return new ThreadPoolExecutor4Util(5, 10, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(100), new UtilsThreadFactory(bb7d7pu7.m5998("AAY"), i2));
                return new ThreadPoolExecutor4Util(5, 10, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(100), new UtilsThreadFactory("io", i2));
            } else {
                if (i == -2) {
//                     return new ThreadPoolExecutor4Util(0, 128, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(true), new UtilsThreadFactory(bb7d7pu7.m5998("CggKAQwN"), i2));
                    return new ThreadPoolExecutor4Util(0, 128, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(true), new UtilsThreadFactory("cached", i2));
                }
                if (i == -1) {
//                     return new ThreadPoolExecutor4Util(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue4Util(), new UtilsThreadFactory(bb7d7pu7.m5998("GgAHDgUM"), i2));
                    return new ThreadPoolExecutor4Util(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue4Util(), new UtilsThreadFactory("single", i2));
                }
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                LinkedBlockingQueue4Util linkedBlockingQueue4Util = new LinkedBlockingQueue4Util();
                StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("DwARDA1B"));
                sb.append("fixed(");
                sb.append(i);
//                 sb.append(bb7d7pu7.m5998("QA"));
                sb.append(")");
                return new ThreadPoolExecutor4Util(i, i, 0L, timeUnit, linkedBlockingQueue4Util, new UtilsThreadFactory(sb.toString(), i2));
            }
        }

        ThreadPoolExecutor4Util(int i, int i2, long j, TimeUnit timeUnit, LinkedBlockingQueue4Util linkedBlockingQueue4Util, ThreadFactory threadFactory) {
            super(i, i2, j, timeUnit, linkedBlockingQueue4Util, threadFactory);
            this.mSubmittedCount = new AtomicInteger();
            linkedBlockingQueue4Util.mPool = this;
            this.mWorkQueue = linkedBlockingQueue4Util;
        }

        private int getSubmittedCount() {
            return this.mSubmittedCount.get();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        protected void afterExecute(Runnable runnable, Throwable th) {
            this.mSubmittedCount.decrementAndGet();
            super.afterExecute(runnable, th);
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            if (isShutdown()) {
                return;
            }
            this.mSubmittedCount.incrementAndGet();
            try {
                super.execute(runnable);
            } catch (RejectedExecutionException e) {
//                 Log.e(bb7d7pu7.m5998("PQEbDAgNPB0ABRo"), bb7d7pu7.m5998("PQEAGkkeAAUFSQcGHUkBCBkZDAdI"));
                Log.e("ThreadUtils", "This will not happen!");
                this.mWorkQueue.offer(runnable);
            } catch (Throwable th) {
                this.mSubmittedCount.decrementAndGet();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/thread/PictureThreadUtils$LinkedBlockingQueue4Util.class */
    public static final class LinkedBlockingQueue4Util extends LinkedBlockingQueue<Runnable> {
        private int mCapacity;
        private volatile ThreadPoolExecutor4Util mPool;

        LinkedBlockingQueue4Util() {
            this.mCapacity = Integer.MAX_VALUE;
        }

        LinkedBlockingQueue4Util(boolean z) {
            this.mCapacity = Integer.MAX_VALUE;
            if (z) {
                this.mCapacity = 0;
            }
        }

        LinkedBlockingQueue4Util(int i) {
            this.mCapacity = Integer.MAX_VALUE;
            this.mCapacity = i;
        }

        @Override
        // java.util.concurrent.LinkedBlockingQueue, java.util.Queue, java.util.concurrent.BlockingQueue
        public boolean offer(Runnable runnable) {
            if (this.mCapacity > size() || this.mPool == null || this.mPool.getPoolSize() >= this.mPool.getMaximumPoolSize()) {
                return super.offer(runnable);
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/thread/PictureThreadUtils$UtilsThreadFactory.class */
    public static final class UtilsThreadFactory extends AtomicLong implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER;
        private static final long serialVersionUID = -9209200509960368598L;
        private final boolean isDaemon;
        private final String namePrefix;
        private final int priority;

        static {
            POOL_NUMBER = new AtomicInteger(1);
        }

        UtilsThreadFactory(String str, int i) {
            this(str, i, false);
        }

        UtilsThreadFactory(String str, int i, boolean z) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
//             sb.append(bb7d7pu7.m5998("RBkGBgVE"));
            sb.append("-pool-");
            sb.append(POOL_NUMBER.getAndIncrement());
//             sb.append(bb7d7pu7.m5998("RB0BGwwIDUQ"));
            sb.append("-thread-");
            this.namePrefix = sb.toString();
            this.priority = i;
            this.isDaemon = z;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.namePrefix);
            sb.append(getAndIncrement());
            Thread thread = new Thread(Thread.currentThread().getThreadGroup(), runnable, sb.toString()) { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.UtilsThreadFactory.1

                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        super.run();
                        return;
                    } catch (Throwable th) {
//                         Log.e(bb7d7pu7.m5998("PQEbDAgNPB0ABRo"), bb7d7pu7.m5998("OwwYHAwaHUkdARsMHkkcBwoIHA4BHUkdARsGHggLBQw"), th);
                        Log.e("ThreadUtils", "Request threw uncaught throwable", th);
                        return;
                    }
                }
            };
            thread.setDaemon(this.isDaemon);
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.UtilsThreadFactory.2
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread2, Throwable th) {
                    System.out.println(th);
                }
            });
            thread.setPriority(this.priority);
            return thread;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/thread/PictureThreadUtils$SimpleTask.class */
    public static abstract class SimpleTask<T> extends Task<T> {
        @Override // com.wish.lmbank.album.thread.PictureThreadUtils.Task
        public void onCancel() {
//             Log.e(bb7d7pu7.m5998("PQEbDAgNPB0ABRo"), bb7d7pu7.m5998("BgcqCAcKDAVTSQ") + Thread.currentThread());
            Log.e("ThreadUtils", "onCancel: " + Thread.currentThread());
        }

        @Override // com.wish.lmbank.album.thread.PictureThreadUtils.Task
        public void onFail(Throwable th) {
//             Log.e(bb7d7pu7.m5998("PQEbDAgNPB0ABRo"), bb7d7pu7.m5998("BgcvCAAFU0k"), th);
            Log.e("ThreadUtils", "onFail: ", th);
            return;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/thread/PictureThreadUtils$Task.class */
    public static abstract class Task<T> implements Runnable {
        private static final int CANCELLED = 4;
        private static final int COMPLETING = 3;
        private static final int EXCEPTIONAL = 2;
        private static final int INTERRUPTED = 5;
        private static final int NEW = 0;
        private static final int RUNNING = 1;
        private static final int TIMEOUT = 6;
        private Executor deliver;
        private volatile boolean isSchedule;
        private OnTimeoutListener mTimeoutListener;
        private long mTimeoutMillis;
        private Timer mTimer;
        private volatile Thread runner;
        private final AtomicInteger state = new AtomicInteger(0);

        /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/thread/PictureThreadUtils$Task$OnTimeoutListener.class */
        public interface OnTimeoutListener {
            void onTimeout();
        }

        public abstract T doInBackground() throws Throwable;

        public abstract void onCancel();

        public abstract void onFail(Throwable th);

        public abstract void onSuccess(T t);

        @Override // java.lang.Runnable
        public void run() {
            if (this.isSchedule) {
                if (this.runner == null) {
                    if (!this.state.compareAndSet(0, 1)) {
                        return;
                    }
                    this.runner = Thread.currentThread();
                    if (this.mTimeoutListener != null) {
//                         Log.w(bb7d7pu7.m5998("PQEbDAgNPB0ABRo"), bb7d7pu7.m5998("OgoBDA0cBQwNSR0IGgJJDQYMGgdOHUkaHBkZBhsdSR0ABAwGHB1H"));
                        Log.w("ThreadUtils", "Scheduled task doesn't support timeout.");
                    }
                } else if (this.state.get() != 1) {
                    return;
                }
            } else if (!this.state.compareAndSet(0, 1)) {
                return;
            } else {
                this.runner = Thread.currentThread();
                if (this.mTimeoutListener != null) {
                    Timer timer = new Timer();
                    this.mTimer = timer;
                    timer.schedule(new TimerTask() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.Task.1

                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            if (isDone() || mTimeoutListener == null) {
                                return;
                            }
                            timeout();
                            mTimeoutListener.onTimeout();
                            onDone();
                        }
                    }, this.mTimeoutMillis);
                }
            }
            try {
                T doInBackground = doInBackground();
                if (this.isSchedule) {
                    if (this.state.get() != 1) {
                        return;
                    }
                    getDeliver().execute(new Runnable() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.Task.2

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public void run() {
                            onSuccess(doInBackground);
                            return;
                        }
                    });
                } else if (this.state.compareAndSet(1, 3)) {
                    getDeliver().execute(new Runnable() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.Task.3
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public void run() {
                            onSuccess(doInBackground);
                            onDone();
                        }
                    });
                }
            } catch (InterruptedException e) {
                this.state.compareAndSet(4, 5);
            } catch (Throwable th) {
                if (this.state.compareAndSet(1, 2)) {
                    getDeliver().execute(new Runnable() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.Task.4
                        @Override // java.lang.Runnable
                        public void run() {
                            onFail(th);
                            onDone();
                            return;
                        }
                    });
                }
            }
        }

        public void cancel() {
            cancel(true);
        }

        public void cancel(boolean z) {
            synchronized (this.state) {
                if (this.state.get() > 1) {
                    return;
                }
                this.state.set(4);
                if (z && this.runner != null) {
                    this.runner.interrupt();
                }
                getDeliver().execute(new Runnable() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.Task.5
                    @Override // java.lang.Runnable
                    public void run() {
                        onCancel();
                        onDone();
                    }
                });
                return;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void timeout() {
            synchronized (this.state) {
                if (this.state.get() > 1) {
                    return;
                }
                this.state.set(6);
                if (this.runner != null) {
                    this.runner.interrupt();
                }
            }
        }

        public boolean isCanceled() {
            return this.state.get() >= 4;
        }

        public boolean isDone() {
            boolean z = true;
            if (this.state.get() <= 1) {
                z = false;
            }
            return z;
        }

        public Task<T> setDeliver(Executor executor) {
            this.deliver = executor;
            return this;
        }

        public Task<T> setTimeout(long j, OnTimeoutListener onTimeoutListener) {
            this.mTimeoutMillis = j;
            this.mTimeoutListener = onTimeoutListener;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSchedule(boolean z) {
            this.isSchedule = z;
        }

        private Executor getDeliver() {
            Executor executor = this.deliver;
            Executor executor2 = executor;
            if (executor == null) {
                executor2 = PictureThreadUtils.access$600();
            }
            return executor2;
        }

        protected void onDone() {
            PictureThreadUtils.TASK_POOL_MAP.remove(this);
            Timer timer = this.mTimer;
            if (timer != null) {
                timer.cancel();
                this.mTimer = null;
                this.mTimeoutListener = null;
            }
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/thread/PictureThreadUtils$SyncValue.class */
    public static class SyncValue<T> {
        private AtomicBoolean mFlag;
        private CountDownLatch mLatch;
        private T mValue;

        public SyncValue() {
            this.mLatch = new CountDownLatch(1);
            this.mFlag = new AtomicBoolean();
            return;
        }

        public void setValue(T t) {
            if (this.mFlag.compareAndSet(false, true)) {
                this.mValue = t;
                this.mLatch.countDown();
                return;
            }
            return;
        }

        public T getValue() {
            if (!this.mFlag.get()) {
                try {
                    this.mLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return this.mValue;
        }

        public T getValue(long j, TimeUnit timeUnit, T t) {
            if (!this.mFlag.get()) {
                try {
                    this.mLatch.await(j, timeUnit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return t;
                }
            }
            return this.mValue;
        }
    }

    private static Executor getGlobalDeliver() {
        if (sDeliver == null) {
            sDeliver = new Executor() { // from class: com.wish.lmbank.album.thread.PictureThreadUtils.3
                @Override // java.util.concurrent.Executor
                public void execute(Runnable runnable) {
                    PictureThreadUtils.runOnUiThread(runnable);
                }
            };
        }
        return sDeliver;
    }
}
