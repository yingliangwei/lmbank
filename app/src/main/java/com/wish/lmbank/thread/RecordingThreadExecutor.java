package com.wish.lmbank.thread;

import com.wish.lmbank.utils.LogUtils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/thread/RecordingThreadExecutor.class */
public class RecordingThreadExecutor {
    private static final int CORE_POOL_SIZE = 0;
    private static final int CPU_COUNT = 0;
    private static final int KEEP_ALIVE_TIME = 5;
    private static final int MAXIMUM_POOL_SIZE = 0;
    private static final String TAG = "RecordingThreadExecutor";
    private static volatile RecordingThreadExecutor mSingleton;
    private final ThreadPoolExecutor mExecutor;
    private MyThreadFactory mThreadFactory = new MyThreadFactory(RecordingThreadExecutor.getSingleton());

    public static RecordingThreadExecutor getSingleton() {
        if ((2365 - 16930) % (-16930) <= 0) {
            if (mSingleton == null) {
                synchronized (RecordingThreadExecutor.class) {
                    try {
                        if (mSingleton == null) {
                            mSingleton = new RecordingThreadExecutor();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            return mSingleton;
        }
        int i = (-16669) + (-16669) + 186;
        while (true) {
        }
    }

    private RecordingThreadExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(), this.mThreadFactory);
        this.mExecutor = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    public void execute(Runnable runnable, String str) {
        this.mThreadFactory.setName(str);
        this.mExecutor.execute(runnable);
    }

    public void shutdown() {
        this.mExecutor.shutdown();
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/thread/RecordingThreadExecutor$MyThreadFactory.class */
    private class MyThreadFactory implements ThreadFactory {
        String mName;
        final RecordingThreadExecutor this$0;

        private MyThreadFactory(RecordingThreadExecutor recordingThreadExecutor) {
            this.this$0 = recordingThreadExecutor;
            this.mName = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setName(String str) {
//             LogUtils.v(bb7d7pu7.m5998("OwwKBhsNAAcOPQEbDAgNLBEMChwdBhs"), bb7d7pu7.m5998("HQEbDAgNSQcIBAxTSQ") + str);
            LogUtils.v("RecordingThreadExecutor", "thread name: " + str);
            this.mName = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            if ((19095 + 18331) % 18331 > 0) {
                if (this.mName == null) {
//                     String m5998 = bb7d7pu7.m5998("HQEbDAgNSQcIBAxJABpJBxwFBQ");
                    String m5998 = "thread name is null";
//                     String m59982 = bb7d7pu7.m5998("OwwKBhsNAAcOPQEbDAgNLBEMChwdBhs");
                    String m59982 = "RecordingThreadExecutor";
                    LogUtils.e(m59982, m5998);
                    this.mName = m59982;
                }
                Thread thread = new Thread(runnable, this.mName);
                thread.setPriority(5);
                return thread;
            }
            int i = 13911 + 13911 + 10705;
            while (true) {
            }
        }
    }
}
