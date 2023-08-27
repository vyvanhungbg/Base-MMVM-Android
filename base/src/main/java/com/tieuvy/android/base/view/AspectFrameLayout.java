package com.tieuvy.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.tieuvy.android.base.R;


public class AspectFrameLayout extends FrameLayout {

    private static final int DEFAULT_XRATIO = 1;

    private static final int DEFAULT_YRATIO = 1;

    private int xRatio = DEFAULT_XRATIO;

    private int yRatio = DEFAULT_YRATIO;

    public int getXRatio() {
        return xRatio;
    }

    public void setXRatio(int xRatio) {
        this.xRatio = xRatio;
    }

    public int getYRatio() {
        return yRatio;
    }

    public void setYRatio(int yRatio) {
        this.yRatio = yRatio;
    }

    public AspectFrameLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AspectFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public AspectFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AspectFrameLayout, 0, 0);
        try {
            xRatio = a.getInt(R.styleable.AspectFrameLayout_xRatio, DEFAULT_XRATIO);
            yRatio = a.getInt(R.styleable.AspectFrameLayout_yRatio, DEFAULT_YRATIO);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY && (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED)) {
            setMeasuredDimension(widthSize, (int)((double)widthSize / xRatio * yRatio));
        } else if(heightMode == MeasureSpec.EXACTLY && (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED)) {
            setMeasuredDimension((int)((double)heightSize / yRatio * xRatio), heightSize);
        } else {
            super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }
    }
}