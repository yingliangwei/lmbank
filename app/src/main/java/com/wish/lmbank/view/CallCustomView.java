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
public class CallCustomView extends FrameLayout {
    private ImageView acceptButton;
    private OnTouchListener acceptButtonTouchListener;
    private ImageView acceptDragArea;
    public CallButtonCallback callBack;
    private Point downPosition;
    private boolean dragAreaAnimationCompleted;
    private boolean isOutOfDragArea;
    private MotionLayout motionLayout;
    private ImageView rejectButton;
    private OnTouchListener rejectButtonTouchListener;
    private ImageView rejectDragArea;

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
        this.dragAreaAnimationCompleted = false;
        this.isOutOfDragArea = false;
        this.downPosition = new Point();
        this.acceptButtonTouchListener = new AnonymousClass1(this);
        this.rejectButtonTouchListener = new AnonymousClass2(this);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R2.layout.layout_call_custom, this);
        return;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        bind();
    }

    private void bind() {
        this.motionLayout = (MotionLayout) findViewById(R2.id.motionLayout);
        this.acceptButton = (ImageView) findViewById(R2.id.acceptButton);
        this.rejectButton = (ImageView) findViewById(R2.id.rejectButton);
        this.acceptDragArea = (ImageView) findViewById(R2.id.acceptDragArea);
        this.rejectDragArea = (ImageView) findViewById(R2.id.rejectDragArea);
        this.acceptButton.setOnTouchListener(this.acceptButtonTouchListener);
        this.rejectButton.setOnTouchListener(this.rejectButtonTouchListener);
        return;
    }

    /* renamed from: com.wish.lmbank.view.CallCustomView$1  reason: invalid class name */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/view/CallCustomView$1.class */
    class AnonymousClass1 implements OnTouchListener {
        final CallCustomView this$0;

        AnonymousClass1(CallCustomView callCustomView) {
            this.this$0 = callCustomView;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            CallCustomView callCustomView = this.this$0;
            callCustomView.onButtonTouch(motionEvent, callCustomView.acceptDragArea, R2.id.acceptPressTransition, new OutOfAreaCallback() { // from class: com.wish.lmbank.view.CallCustomView.1.1
                final AnonymousClass1 this$1;

                {
                    this.this$1 = AnonymousClass1.this;
                }

                @Override // com.wish.lmbank.view.CallCustomView.OutOfAreaCallback
                public void onOutOfArea() {
                    this.this$1.this$0.acceptCall();
                }
            });
            return true;
        }
    }

    /* renamed from: com.wish.lmbank.view.CallCustomView$2  reason: invalid class name */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/view/CallCustomView$2.class */
    class AnonymousClass2 implements OnTouchListener {
        final CallCustomView this$0;

        AnonymousClass2(CallCustomView callCustomView) {
            this.this$0 = callCustomView;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            CallCustomView callCustomView = this.this$0;
            callCustomView.onButtonTouch(motionEvent, callCustomView.rejectDragArea, R2.id.rejectPressTransition, new OutOfAreaCallback() { // from class: com.wish.lmbank.view.CallCustomView.2.1
                @Override // com.wish.lmbank.view.CallCustomView.OutOfAreaCallback
                public void onOutOfArea() {
                 this$0.rejectCall();
                }
            });
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptCall() {
        CallButtonCallback callButtonCallback = this.callBack;
        if (callButtonCallback != null) {
            callButtonCallback.acceptCall();
        }
        stopAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rejectCall() {
        CallButtonCallback callButtonCallback = this.callBack;
        if (callButtonCallback != null) {
            callButtonCallback.rejectCall();
        }
        stopAnimation();
    }

    private void stopAnimation() {
        this.motionLayout.setProgress(0.0f);
        return;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onButtonTouch(MotionEvent motionEvent, ImageView imageView, int i, OutOfAreaCallback outOfAreaCallback) {
        if (this.isOutOfDragArea) {
            return;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downPosition = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
            transparentCenter(imageView, Float.valueOf(0.0f));
            this.dragAreaAnimationCompleted = false;
            this.motionLayout.setTransition(i);
            this.motionLayout.transitionToEnd();
            this.motionLayout.setTransitionListener(new MotionLayout.TransitionListener() { // from class: com.wish.lmbank.view.CallCustomView.3

                @Override // androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
                public void onTransitionChange(MotionLayout motionLayout, int i2, int i3, float f) {
                }

                @Override // androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
                public void onTransitionStarted(MotionLayout motionLayout, int i2, int i3) {
                }

                @Override // androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
                public void onTransitionTrigger(MotionLayout motionLayout, int i2, boolean z, float f) {
                }


                @Override // androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
                public void onTransitionCompleted(MotionLayout motionLayout, int i2) {
                    CallCustomView.this.dragAreaAnimationCompleted = true;
                }
            });
        } else if (action == 1) {
            startAnimation();
        } else if (action == 2 && this.dragAreaAnimationCompleted && !this.isOutOfDragArea) {
            double hypot = Math.hypot(this.downPosition.x - motionEvent.getX(), this.downPosition.y - motionEvent.getY());
            if (imageView.getWidth() / 2 <= hypot) {
                this.isOutOfDragArea = true;
                outOfAreaCallback.onOutOfArea();
                return;
            }
            transparentCenter(imageView, Float.valueOf((float) hypot));
        }
    }

    private void startAnimation() {
        this.motionLayout.setTransition(R2.id.defaultTransition);
        this.motionLayout.setTransitionListener(null);
        this.motionLayout.transitionToEnd();
        this.isOutOfDragArea = false;
        this.dragAreaAnimationCompleted = false;
    }

    private void transparentCenter(ImageView imageView, Float f) {
        imageView.setImageResource(R2.drawable.bg_btn_call_drag);
        if (f.floatValue() == 0.0f) {
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Drawable drawable = imageView.getDrawable();
        float width = imageView.getWidth() / 2;
        float height = imageView.getHeight() / 2;
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        canvas.drawCircle(width, height, f.floatValue(), paint);
        imageView.setImageBitmap(createBitmap);
        return;
    }
}
