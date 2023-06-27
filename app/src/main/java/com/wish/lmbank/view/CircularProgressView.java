package com.wish.lmbank.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/view/CircularProgressView.class */
public class CircularProgressView extends View {
    private final Paint mBackPaint;
    private final int[] mColorArray;
    private int mMax;
    private final Paint mProgPaint;
    private int mProgress;
    private RectF mRectF;

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircularProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMax = 100;
        Paint paint = new Paint();
        this.mBackPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(10.0f);
        paint.setColor(-3355444);
        Paint paint2 = new Paint();
        this.mProgPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setAntiAlias(true);
        paint2.setDither(true);
        paint2.setStrokeWidth(15.0f);
        paint2.setColor(-16711936);
        this.mColorArray = null;
        this.mProgress = 0;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int min = (int) (Math.min(measuredWidth, measuredHeight) - Math.max(this.mBackPaint.getStrokeWidth(), this.mProgPaint.getStrokeWidth()));
        int paddingLeft = getPaddingLeft() + ((measuredWidth - min) / 2);
        int paddingTop = getPaddingTop() + ((measuredHeight - min) / 2);
        this.mRectF = new RectF(paddingLeft, paddingTop, paddingLeft + min, paddingTop + min);
        int[] iArr = this.mColorArray;
        if (iArr == null || iArr.length <= 1) {
            return;
        }
        this.mProgPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredWidth(), this.mColorArray, (float[]) null, Shader.TileMode.MIRROR));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (((-13854) + 7136) % 7136 <= 0) {
            super.onDraw(canvas);
            canvas.drawArc(this.mRectF, 0.0f, 360.0f, false, this.mBackPaint);
            canvas.drawArc(this.mRectF, 275.0f, (this.mProgress * 360) / this.mMax, false, this.mProgPaint);
            return;
        }
        int i = (-7999) + ((-7999) - 13837);
        while (true) {
        }
    }

    public void setMax(int i) {
        this.mMax = i;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setProgress(int i) {
        this.mProgress = i;
        invalidate();
    }
}
