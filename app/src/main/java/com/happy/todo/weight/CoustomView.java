package com.happy.todo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.happy.todo.R;

public class CoustomView extends View {
    private Paint mPaint;
    private int wight;
    private int height;
    private PointF start, end, control;
    private Paint.Style mStyle = Paint.Style.FILL;
    private Context context;

    public CoustomView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CoustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CoustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(mStyle);
        //mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeWidth(10);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        wight = w;
        height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //onDraw1(canvas);
        //onDraw2(canvas);
        onDraw3(canvas);//画卡券
    }


    private void onDraw3(Canvas canvas) {
        float leftWight = wight/2;

        Path path = new Path();

        RectF rect = new RectF(0, 0, leftWight, height);

        float rectWight = height;

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
        mPaintDrawLine.setColor(getResources().getColor(R.color.blue_2D91F7));
        mPaintDrawLine.setStrokeWidth(3);
        mPaintDrawLine.setPathEffect(new DashPathEffect(new float[] {5, 5}, 0));
        int centerY = getHeight() / 2;
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        canvas.drawLine(rectLeft.right + 8, centerY, rectRight.left - 8, centerY, mPaintDrawLine);
    }

    private void onDraw2(Canvas canvas) {

        Path path = new Path();
        RectF rect = new RectF(0, 0, wight, height);
        float mArrowWidth = 200;
        float mArrowHeight = 100;
        float mAngle = 20;
        float mArrowPosition = (rect.right - rect.left) / 2 - mArrowWidth / 2;
        path.moveTo(rect.left + mAngle, rect.top);
        path.lineTo(rect.width() - mAngle, rect.top);
        path.arcTo(new RectF(rect.right - mAngle,
                rect.top, rect.right, mAngle + rect.top), 270, 90);

        path.lineTo(rect.right, rect.bottom - mArrowHeight - mAngle);
        path.arcTo(new RectF(rect.right - mAngle, rect.bottom - mAngle - mArrowHeight,
                rect.right, rect.bottom - mArrowHeight), 0, 90);

        //path.moveTo(rect.left + mArrowWidth + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mArrowWidth + mArrowPosition, rect.bottom - mArrowHeight);
        //path.lineTo(rect.left + mArrowPosition + mArrowWidth / 2, rect.bottom);
        //path.lineTo(rect.left + mArrowPosition, rect.bottom - mArrowHeight);

        RectF rect2 = new RectF(rect.left + mArrowPosition, rect.bottom - mArrowHeight -mArrowHeight,
                rect.left + mArrowWidth + mArrowPosition, rect.bottom );
        path.arcTo(rect2,0,-180);
        //path.lineTo(rect.left + mArrowWidth + mArrowPosition,rect2.top);

        path.lineTo(rect.left + mArrowPosition , rect.bottom - mArrowHeight);
        path.lineTo(rect.left + Math.min(mAngle, mArrowPosition), rect.bottom - mArrowHeight);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle - mArrowHeight,
                mAngle + rect.left, rect.bottom - mArrowHeight), 90, 90);
        path.lineTo(rect.left, rect.top + mAngle);
        path.arcTo(new RectF(rect.left, rect.top, mAngle
                + rect.left, mAngle + rect.top), 180, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }


    private void onDraw1(Canvas canvas) {

        Path mPath = new Path();
        RectF rectF = new RectF(0, 0, wight, height);

        mPath.moveTo(rectF.left, rectF.top);
        //右边
        mPath.lineTo(rectF.width(), rectF.top);
        mPath.arcTo(new RectF(rectF.right, rectF.top, rectF.right,
                +rectF.top), 270, 90);
        //右下
        mPath.lineTo(rectF.right, rectF.bottom);
        mPath.arcTo(new RectF(rectF.right, rectF.bottom,
                rectF.right, rectF.bottom), 0, 90);
        //左下
        mPath.lineTo(rectF.left, rectF.bottom);

        //左上
        mPath.moveTo(rectF.left, rectF.top);

        // 初始化数据点和控制点的位置
        start.x = rectF.left;
        start.y = rectF.top;
        end.x = rectF.left;
        end.y = rectF.bottom;
        control.x = rectF.width() / 2;
        control.y = (rectF.bottom - rectF.top) / 2;
        // 绘制贝塞尔曲线
        mPath.moveTo(start.x, start.y);
        mPath.quadTo(control.x, control.y, end.x, end.y);

        /*mPath.arcTo(new RectF(rectF.left, rectF.top,
                rectF.right/2, rectF.bottom), 220, 270);*/


        mPath.close();

        canvas.drawPath(mPath, mPaint);

    }
}
