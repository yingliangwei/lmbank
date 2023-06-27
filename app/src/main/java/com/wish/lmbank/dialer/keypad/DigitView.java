package com.wish.lmbank.dialer.keypad;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/keypad/DigitView.class */
public class DigitView extends AppCompatEditText {
    private OnTextChangedListener onTextChangedListener;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/keypad/DigitView$OnTextChangedListener.class */
    public interface OnTextChangedListener {
        void onTextChanged(String str);
    }

    public DigitView(Context context) {
        super(context);
    }

    public DigitView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setInputType(524288);
        setShowSoftInputOnFocus(false);
        setBackground(null);
    }

    public void insert(String str) {
        if (getText() != null) {
            getText().insert(getSelectionStart(), str);
        }
    }

    public void delete() {
        int selectionStart = getSelectionStart();
        if (getText() == null || selectionStart <= 0) {
            return;
        }
        getText().delete(selectionStart - 1, selectionStart);
    }

    public void setText() {
        if (getText() != null) {
            setText(getText().toString().substring(getSelectionStart()));
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
//         InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(bb7d7pu7.m5998("AAcZHB02BAwdAQYN"));
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager != null && inputMethodManager.isActive(this)) {
            inputMethodManager.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        }
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        OnTextChangedListener onTextChangedListener = this.onTextChangedListener;
        if (!TextUtils.isEmpty(charSequence)) {
            if (!isCursorVisible()) {
                setCursorVisible(true);
            }
            if (!isFocused()) {
                requestFocus();
            }
        } else {
            setCursorVisible(false);
        }
        if (onTextChangedListener != null) {
            onTextChangedListener.onTextChanged(String.valueOf(charSequence));
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (((-15443) + 19124) % 19124 > 0) {
//             InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(bb7d7pu7.m5998("AAcZHB02BAwdAQYN"));
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (inputMethodManager != null && inputMethodManager.isActive(this)) {
                inputMethodManager.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
            }
            return super.onTouchEvent(motionEvent);
        }
        int i = 18322 + (18322 - 5423);
        while (true) {
        }
    }

    public void setNumber(String str) {
        setText(str);
        setSelection(str.length());
    }

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }
}
