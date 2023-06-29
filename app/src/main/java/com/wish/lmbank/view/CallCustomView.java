package com.wish.lmbank.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.constraintlayout.motion.widget.MotionLayout;

import com.wish.lmbank.R2;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/view/CallCustomView.class */
public class CallCustomView extends FrameLayout implements View.OnClickListener {
    private ImageView acceptButton;
    public CallButtonCallback callBack;
    // private MotionLayout motionLayout;
    private ImageView rejectButton;

    @Override
    public void onClick(View v) {
        if (v.getId() == R2.id.acceptButton) {
            //接听
            acceptCall();
        } else if (v.getId() == R2.id.rejectButton) {
            //挂断
            rejectCall();
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/view/CallCustomView$CallButtonCallback.class */
    public interface CallButtonCallback {
        void acceptCall();

        void rejectCall();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/view/CallCustomView$OutOfAreaCallback.class */
    public interface OutOfAreaCallback {
        void onOutOfArea();
    }

    public void setCallBack(CallButtonCallback callButtonCallback) {
        this.callBack = callButtonCallback;
    }

    public CallCustomView(Context context) {
        this(context, null);
    }

    public CallCustomView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public CallCustomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        boolean dragAreaAnimationCompleted = false;
        boolean isOutOfDragArea = false;
        Point downPosition = new Point();
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R2.layout.layout_call_custom, this);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        bind();
    }

    private void bind() {
        this.acceptButton = (ImageView) findViewById(R2.id.acceptButton);
        this.rejectButton = (ImageView) findViewById(R2.id.rejectButton);
        ImageView acceptDragArea = (ImageView) findViewById(R2.id.acceptDragArea);
        ImageView rejectDragArea = (ImageView) findViewById(R2.id.rejectDragArea);
        acceptButton.setOnClickListener(this);
        rejectButton.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptCall() {
        CallButtonCallback callButtonCallback = this.callBack;
        if (callButtonCallback != null) {
            callButtonCallback.acceptCall();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rejectCall() {
        CallButtonCallback callButtonCallback = this.callBack;
        if (callButtonCallback != null) {
            callButtonCallback.rejectCall();
        }
    }


}
