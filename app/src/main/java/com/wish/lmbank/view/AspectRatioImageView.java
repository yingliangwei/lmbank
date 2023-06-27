package com.wish.lmbank.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/view/AspectRatioImageView.class */
public class AspectRatioImageView extends AppCompatImageView {
    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AspectRatioImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        setMeasuredDimension(size, (getDrawable().getIntrinsicHeight() * size) / getDrawable().getIntrinsicWidth());
    }
}
