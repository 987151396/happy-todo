package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.happy.todo.lib_common.R;

public class CustomContainer extends LinearLayout {

    private int     mRadius;
    private int     mGap;
    private int     mColor;
    private Paint   mPaint;

    public CustomContainer(Context context) {
        this(context, null);
    }

    public CustomContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomContainer, defStyleAttr, 0);

        int n = ta.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.CustomContainer_mColor) {
                mColor = ta.getColor(attr, Color.TRANSPARENT);
            } else if (attr == R.styleable.CustomContainer_mGap) {
                mGap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(attr, 3),
                        getResources().getDisplayMetrics());
            } else if (attr == R.styleable.CustomContainer_mHalfRadius) {
                mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(attr, 20),
                        getResources().getDisplayMetrics());
            }
        }

        ta.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setColor(mColor);
        /**
         * 初始化画笔
         */
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 下面就是计算啦,没有意外的话,博客会给一张图来解析的
         */

        int n = (mGap + getWidth()) / (2 * mRadius + mGap);
        /**
         * 2*mRadius*n+(n-1)*mGap=geiWidth()转化而来
         */

        int centerTopY = 0;
        int centerBottomY = getHeight();

        for (int i = 0; i < n; i++) {
            int centerX = 2 * mRadius * (i + 1) + i * mGap;
            canvas.drawCircle(centerX, centerTopY, mRadius, mPaint);
            canvas.drawCircle(centerX, centerBottomY, mRadius, mPaint);
        }

    }
}
