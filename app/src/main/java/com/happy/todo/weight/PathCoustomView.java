package com.happy.todo.weight;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

/**
 * Author by Ouyangle, Date on 2021/1/21.
 * PS: Not easy to write code, please indicate.
 */
public class PathCoustomView extends View {
    private Context context;
    private PathMeasure measure = new PathMeasure();
    private Paint paint = new Paint();
    //初始化Path并顺时针绘制一个矩形
    private Path sourcePath = new Path();
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private RectF mRectF;
    public PathCoustomView(Context context) {
        super(context);
        init(context);
    }

    public PathCoustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PathCoustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);

        mRectF = new RectF(300, 300, 600, 600);

        pos[0] = mRectF.left;
        pos[1] = mRectF.top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //初始化Paint
        sourcePath.reset();
        //sourcePath .addRect(mRectF, Path.Direction.CW);
        sourcePath.moveTo(mRectF.left,mRectF.top);
        sourcePath.cubicTo(mRectF.left,mRectF.top,+ mRectF.left + (mRectF.right - mRectF.left) / 2,mRectF.bottom,mRectF.right,mRectF.top);
        sourcePath.cubicTo(mRectF.right,mRectF.top,+ mRectF.left + (mRectF.right - mRectF.left) / 2,mRectF.bottom, mRectF.left,mRectF.top);

        measure.setPath(sourcePath , false);

        canvas.drawPath(sourcePath , paint);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(pos[0],pos[1],20,paint);

    }

    public void startMove(){
        ValueAnimator animator = ValueAnimator.ofFloat(0,measure.getLength());
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = (float) animation.getAnimatedValue();
                measure.getPosTan(distance,pos,tan);
                postInvalidate();
            }
        });
        animator.start();
    }
}
