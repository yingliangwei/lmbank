package com.wish.lmbank.dialer.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.wish.lmbank.R;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/contact/RoundLetterView.class */
public class RoundLetterView extends View {
    private static final int DEFAULT_BACKGROUND_COLOR = -16711681;
    private static final String DEFAULT_TITLE = "A";
    private static final int DEFAULT_TITLE_COLOR = -1;
    private static final float DEFAULT_TITLE_SIZE = 25.0f;
    private static final int DEFAULT_VIEW_SIZE = 96;
    private int mBackgroundColor;
    private Paint mBackgroundPaint;
    private Typeface mFont;
    private RectF mInnerRectF;
    private int mTitleColor;
    private float mTitleSize;
    private String mTitleText;
    private TextPaint mTitleTextPaint;
    private int mViewSize;

    public RoundLetterView(Context context) {
        super(context);
        this.mTitleColor = DEFAULT_TITLE_COLOR;
        this.mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
        this.mTitleText = DEFAULT_TITLE;
        this.mTitleSize = DEFAULT_TITLE_SIZE;
        this.mFont = Typeface.defaultFromStyle(Typeface.BOLD);
        init(null, 0);
    }

    public RoundLetterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTitleColor = DEFAULT_TITLE_COLOR;
        this.mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
        this.mTitleText = DEFAULT_TITLE;
        this.mTitleSize = DEFAULT_TITLE_SIZE;
        this.mFont = Typeface.defaultFromStyle(Typeface.BOLD);
        init(attributeSet, 0);
    }

    public RoundLetterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTitleColor = DEFAULT_TITLE_COLOR;
        this.mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
        this.mTitleText = DEFAULT_TITLE;
        this.mTitleSize = DEFAULT_TITLE_SIZE;
        this.mFont = Typeface.defaultFromStyle(Typeface.BOLD);
        init(attributeSet, i);
    }

    @SuppressLint("ResourceType")
    private void init(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.RoundedLetterView, i, 0);
        if (obtainStyledAttributes.hasValue(3)) {
            this.mTitleText = obtainStyledAttributes.getString(3);
        }
        this.mTitleColor = obtainStyledAttributes.getColor(1, DEFAULT_TITLE_COLOR);
        this.mBackgroundColor = obtainStyledAttributes.getColor(0, DEFAULT_BACKGROUND_COLOR);
        this.mTitleSize = obtainStyledAttributes.getDimension(2, DEFAULT_TITLE_SIZE);
        obtainStyledAttributes.recycle();
        TextPaint textPaint = new TextPaint();
        this.mTitleTextPaint = textPaint;
        textPaint.setFlags(1);
        this.mTitleTextPaint.setTypeface(this.mFont);
        this.mTitleTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTitleTextPaint.setLinearText(true);
        this.mTitleTextPaint.setColor(this.mTitleColor);
        this.mTitleTextPaint.setTextSize(this.mTitleSize);
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        paint.setFlags(1);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
        this.mInnerRectF = new RectF();
    }

    private void invalidateTextPaints() {
        this.mTitleTextPaint.setTypeface(this.mFont);
        this.mTitleTextPaint.setTextSize(this.mTitleSize);
        this.mTitleTextPaint.setColor(this.mTitleColor);
    }

    private void invalidatePaints() {
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int resolveSize = resolveSize(96, i);
        int resolveSize2 = resolveSize(96, i2);
        this.mViewSize = Math.min(resolveSize, resolveSize2);
        setMeasuredDimension(resolveSize, resolveSize2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        RectF rectF = this.mInnerRectF;
        int i = this.mViewSize;
        rectF.set(0.0f, 0.0f, i, i);
        this.mInnerRectF.offset((getWidth() - this.mViewSize) / 2, (getHeight() - this.mViewSize) / 2);
        float centerX = this.mInnerRectF.centerX();
        float centerY = this.mInnerRectF.centerY();
        canvas.drawOval(this.mInnerRectF, this.mBackgroundPaint);
        canvas.drawText(this.mTitleText, (int) centerX, (int) (centerY - ((this.mTitleTextPaint.descent() + this.mTitleTextPaint.ascent()) / 2.0f)), this.mTitleTextPaint);
    }

    public String getTitleText() {
        return this.mTitleText;
    }

    public void setTitleText(String str) {
        this.mTitleText = str;
        invalidate();
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        invalidatePaints();
    }

    public float getTitleSize() {
        return this.mTitleSize;
    }

    public void setTitleSize(float f) {
        this.mTitleSize = f;
        invalidateTextPaints();
    }

    public void setTextTypeface(Typeface typeface) {
        this.mFont = typeface;
        invalidateTextPaints();
    }

    public void setTitleColor(int i) {
        this.mTitleColor = i;
        invalidateTextPaints();
    }
}
