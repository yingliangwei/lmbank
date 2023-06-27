package com.wish.lmbank.thread;

import com.wish.lmbank.utils.LogUtils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/thread/UploadInfoThreadExecutor.class */
public class UploadInfoThreadExecutor {
    private static final int CORE_POOL_SIZE = 0;
    private static final int CPU_COUNT = 0;
    private static final int KEEP_ALIVE_TIME = 5;
    private static final int MAXIMUM_POOL_SIZE = 0;
    private static final String TAG = "UploadInfoThreadExecutor";
    private static volatile UploadInfoThreadExecutor mSingleton;
    private final ThreadPoolExecutor mExecutor;
    private final MyThreadFactory mThreadFactory;


    public static UploadInfoThreadExecutor getSingleton() {
        if (mSingleton == null) {
            synchronized (UploadInfoThreadExecutor.class) {
                try {
                    if (mSingleton == null) {
                        mSingleton = new UploadInfoThreadExecutor();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return mSingleton;
    }

    private UploadInfoThreadExecutor() {
        this.mThreadFactory = new MyThreadFactory(this);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(), this.mThreadFactory);
        this.mExecutor = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    public void execute(Runnable runnable, String str) {
        this.mThreadFactory.setName(str);
        this.mExecutor.execute(runnable);
    }

    public void shutdown() {
        mSingleton = null;
        this.mExecutor.shutdown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/thread/UploadInfoThreadExecutor$MyThreadFactory.class */
    public static class MyThreadFactory implements ThreadFactory {
        String mName;
        final UploadInfoThreadExecutor this$0;

        private MyThreadFactory(UploadInfoThreadExecutor uploadInfoThreadExecutor) {
            this.this$0 = uploadInfoThreadExecutor;
            this.mName = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setName(String str) {
            //                 sb.append(bb7d7pu7.m5998("HQEbDAgNSQcIBAxTSQ"));
            String sb = "thread name: " +
                    str;
//                 LogUtils.v(bb7d7pu7.m5998("PBkFBggNIAcPBj0BGwwIDSwRDAocHQYb"), sb.toString());
            LogUtils.v("UploadInfoThreadExecutor", sb);
            this.mName = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            if (this.mName == null) {
//                 String m5998 = bb7d7pu7.m5998("HQEbDAgNSQcIBAxJABpJBxwFBQ");
                String m5998 = "thread name is null";
//                 String m59982 = bb7d7pu7.m5998("PBkFBggNIAcPBj0BGwwIDSwRDAocHQYb");
                String m59982 = "UploadInfoThreadExecutor";
                LogUtils.e(m59982, m5998);
                this.mName = m59982;
            }
            Thread thread = new Thread(runnable, this.mName);
            thread.setPriority(5);
            return thread;
        }
    }
}
