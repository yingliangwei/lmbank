package com.wish.lmbank.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wish.lmbank.R2;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/view/dialog/LoadingDialog.class */
public class LoadingDialog {
    private static Dialog mLoadingDialog;

    public static Dialog showLoading(Activity activity, String str, boolean z) {
        disDialog();
        View inflate = LayoutInflater.from(activity).inflate(R2.layout.dialog_loading, (ViewGroup) null);
        ((TextView) inflate.findViewById(R2.id.id_tv_loading_dialog_text)).setText(str);
        Dialog dialog = new Dialog(activity, R2.style.CustomProgressDialog);
        mLoadingDialog = dialog;
        dialog.setCancelable(z);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(inflate, new LinearLayout.LayoutParams(-1, -1));
        mLoadingDialog.show();
        return mLoadingDialog;
    }

    public static Dialog showLoading(Activity activity) {
        disDialog();
        View inflate = LayoutInflater.from(activity).inflate(R2.layout.dialog_loading, (ViewGroup) null);
//         ((TextView) inflate.findViewById(R2.id.id_tv_loading_dialog_text)).setText(bb7d7pu7.m5998("jOPJgdTUjdHER0dH"));
        ((TextView) inflate.findViewById(R2.id.id_tv_loading_dialog_text)).setText("加载中...");
        Dialog dialog = new Dialog(activity, R2.style.CustomProgressDialog);
        mLoadingDialog = dialog;
        dialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(inflate, new LinearLayout.LayoutParams(-1, -1));
        mLoadingDialog.show();
        return mLoadingDialog;
    }

    public static void disDialog() {
        Dialog dialog = mLoadingDialog;
        if (dialog != null) {
            dialog.cancel();
            mLoadingDialog = null;
        }
    }
}
