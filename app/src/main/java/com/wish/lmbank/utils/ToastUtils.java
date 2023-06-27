package com.wish.lmbank.utils;

import android.content.Context;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.view.InputDeviceCompat;

import com.wish.lmbank.AppStartV;

import java.lang.ref.WeakReference;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/ToastUtils.class */
public final class ToastUtils {
    private static final int DEFAULT_COLOR = 301989888;
    private static int backgroundColor = 0;
    private static int bgResource = 0;
    private static int gravity = 81;
    private static int messageColor;
    private static Handler sHandler;
    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int xOffset;
    private static int yOffset;


    private ToastUtils() {
//         throw new UnsupportedOperationException(bb7d7pu7.m5998("HEkKCAdOHUkABxodCAcdAAgdDEkEDEdHRw"));
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setGravity(int i, int i2, int i3) {
        gravity = i;
        xOffset = i2;
        yOffset = i3;
    }

    public static void setView(int i) {
//         sViewWeakReference = new WeakReference<>(((LayoutInflater) AppStartV.getContext().getSystemService(bb7d7pu7.m5998("BQgQBhwdNgAHDwUIHQwb"))).inflate(i, (ViewGroup) null));
        sViewWeakReference = new WeakReference<>(((LayoutInflater) AppStartV.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(i, (ViewGroup) null));
    }

    public static void setView(View view) {
        sViewWeakReference = view == null ? null : new WeakReference<>(view);
    }

    public static View getView() {
        View view;
        WeakReference<View> weakReference = sViewWeakReference;
        if (weakReference == null || (view = weakReference.get()) == null) {
            Toast toast = sToast;
            if (toast != null) {
                return toast.getView();
            }
            return null;
        }
        return view;
    }

    public static void setBackgroundColor(int i) {
        backgroundColor = i;
    }

    public static void setBgResource(int i) {
        bgResource = i;
    }

    public static void setMessageColor(int i) {
        messageColor = i;
    }

    public static void showShortSafe(CharSequence charSequence) {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.1

            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show(charSequence, 0);
            }
        });
    }

    public static void showShortSafe(int i) {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.2

            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show(i, 0);
            }
        });
    }

    public static void showShortSafe(int i, Object... objArr) {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.3

            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show(i, 0, objArr);
            }
        });
    }

    public static void showShortSafe(String str, Object... objArr) {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.4
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show(str, 0, objArr);
            }
        });
    }

    public static void showLongSafe(CharSequence charSequence) {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.5

            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show(charSequence, 1);
            }
        });
    }

    public static void showLongSafe(int i) {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.6

            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show(i, 1);
            }
        });
    }

    public static void showLongSafe(int i, Object... objArr) {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.7

            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show(i, 1, objArr);
            }
        });
        return;
    }

    public static void showLongSafe(String str, Object... objArr) {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.8

            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show(str, 1, objArr);
            }
        });
    }

    public static void showShort(CharSequence charSequence) {
        show(charSequence, 0);
    }

    public static void showShort(int i) {
        show(i, 0);
    }

    public static void showShort(int i, Object... objArr) {
        show(i, 0, objArr);
    }

    public static void showShort(String str, Object... objArr) {
        show(str, 0, objArr);
    }

    public static void showLong(CharSequence charSequence) {
        show(charSequence, 1);
    }

    public static void showLong(int i) {
        show(i, 1);
    }

    public static void showLong(int i, Object... objArr) {
        show(i, 1, objArr);
    }

    public static void showLong(String str, Object... objArr) {
        show(str, 1, objArr);
    }

    public static void showCustomShortSafe() {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.9
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show("", 0);
                return;
            }
        });
        return;
    }

    public static void showCustomLongSafe() {
        sHandler.post(new Runnable() { // from class: com.wish.lmbank.utils.ToastUtils.10
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.show("", 1);
                return;
            }
        });
    }

    public static void showCustomShort() {
        show("", 0);
    }

    public static void showCustomLong() {
        show("", 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void show(int i, int i2) {
        show(AppStartV.getContext().getResources().getText(i).toString(), i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void show(int i, int i2, Object... objArr) {
        show(String.format(AppStartV.getContext().getResources().getString(i), objArr), i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void show(String str, int i, Object... objArr) {
        show(String.format(str, objArr), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void show(CharSequence charSequence, int i) {
        boolean z;
        View view;
        cancel();
        WeakReference<View> weakReference = sViewWeakReference;
        if (weakReference == null || (view = weakReference.get()) == null) {
            z = false;
        } else {
            Toast toast = new Toast(AppStartV.getContext());
            sToast = toast;
            toast.setView(view);
            sToast.setDuration(i);
            z = true;
        }
        if (!z) {
            if (messageColor != DEFAULT_COLOR) {
                SpannableString spannableString = new SpannableString(charSequence);
                spannableString.setSpan(new ForegroundColorSpan(messageColor), 0, spannableString.length(), 33);
                sToast = Toast.makeText(AppStartV.getContext(), spannableString, i);
            } else {
                sToast = Toast.makeText(AppStartV.getContext(), charSequence, i);
            }
        }
        View view2 = sToast.getView();
        int i2 = bgResource;
        if (i2 != -1) {
            view2.setBackgroundResource(i2);
        } else {
            int i3 = backgroundColor;
            if (i3 != DEFAULT_COLOR) {
                view2.setBackgroundColor(i3);
            }
        }
        sToast.setGravity(gravity, xOffset, yOffset);
        sToast.show();
    }

    public static void cancel() {
        Toast toast = sToast;
        if (toast != null) {
            toast.cancel();
            sToast = null;
        }
    }
}
