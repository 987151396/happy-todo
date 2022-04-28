package com.happy.todo.module_user.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.happy.todo.module_user.R;

/**
 * Author by Ouyangle, Date on 2021/1/15.
 * PS: Not easy to write code, please indicate.
 */
public class CouponsView extends View {
    private Paint mPaint;
    private int wight;
    private int height;
    private Paint.Style mStyle = Paint.Style.FILL;
    private Context context;

    public CouponsView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CouponsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CouponsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(mStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        wight = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float leftWight = wight/2;

        Path path = new Path();

        RectF rect = new RectF(0, 0, leftWight, height);

        float rectWight = height * 0.7f;

        path.moveTo(rect.left + rectWight, rect.top);
        path.lineTo(rect.right, rect.top);
        path.lineTo(rect.right, rect.bottom);

        RectF rectLeft = new RectF(0, 0, rectWight, rect.bottom);
        path.lineTo(rect.left + rectWight , rectLeft.bottom);

        path.arcTo(rectLeft,90,-180);

        Path path2 = new Path();
        RectF rect2 = new RectF(rect.right, 0, wight, height);
        path2.moveTo(rect2.right,rect2.top);
        path2.lineTo(rect2.left , rect2.top);
        path2.lineTo(rect2.left, rect2.bottom);

        RectF rectRight = new RectF(rect2.right -rectWight, rect2.top, rect2.right, rect2.bottom);
        path2.arcTo(rectRight,90,180);

        path.addPath(path2);

        canvas.drawPath(path, mPaint);

        Paint mPaintDrawLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintDrawLine.setColor(getResources().getColor(R.color.gray_e6e6e6));
        mPaintDrawLine.setStrokeWidth(3);
        mPaintDrawLine.setPathEffect(new DashPathEffect(new float[] {5, 5}, 0));
        int centerY = getHeight() / 2;
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        canvas.drawLine(rectLeft.right + 8, centerY, rectRight.left - 8, centerY, mPaintDrawLine);
    }

}