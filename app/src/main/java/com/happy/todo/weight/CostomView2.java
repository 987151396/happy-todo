package com.happy.todo.weight;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.annotation.Nullable;

public class CostomView2 extends View implements View.OnClickListener {
    private Paint mPaint;
    private int wight;
    private int height;
    private PointF start, end, control;
    private Paint.Style mStyle = Paint.Style.STROKE;
    private float moveX, moveY = 0;
    private ValueAnimator v;

    public CostomView2(Context context) {
        super(context);
        init();
    }

    public CostomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CostomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        wight = w/2;
        height = h/2;
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(mStyle);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);

        start = new PointF(0, 0);
        control = new PointF(0, 0);
        end = new PointF(0, 0);

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 初始化数据点和控制点的位置
        start.x = wight - 200;
        start.y = height;
        end.x = wight + 300;
        end.y = height;
        control.x = wight;
        control.y = height - 450;

        Path mPath = new Path();
        mPath.moveTo(start.x, start.y);
        mPath.quadTo(control.x, control.y, end.x, end.y);

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.YELLOW);

        Log.d("ouyang", "start.x : " + start.x + " -- " + "start.y : " + start.y);
        if (moveX == 0 && moveY == 0) {
            canvas.drawCircle(start.x, start.y, 60, p);
        } else {
            canvas.drawCircle(moveX, moveX, 60, p);
        }


        canvas.drawPath(mPath, mPaint);
    }


    @Override
    public void onClick(View view) {
        Log.d("ouyang", "onClick");
        Log.d("ouyang", "start.x : " + start.x + " -- " + "start.y : " + start.y);
        v = ValueAnimator.ofObject(new BezierEvaluator(control), start, end);
        v.setInterpolator(new BounceInterpolator());
        v.setDuration(1000);
        v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                moveX = pointF.x;
                moveY = pointF.y;
                Log.d("ouyang2", "moveX : " + moveX + " -- " + "moveY : " + moveY);
                invalidate();
            }
        });

        v.start();
    }

    public class BezierEvaluator implements TypeEvaluator<PointF> {

        private PointF mControlPoint;

        public BezierEvaluator(PointF controlPoint) {
            this.mControlPoint = controlPoint;
        }

        @Override
        public PointF evaluate(float t, PointF startValue, PointF endValue) {
            Log.d("ouyang", "startValue.x : " + startValue.x + " -- " + "startValue.y : " + startValue.y);
            return BezierUtil.CalculateBezierPointForQuadratic(t, startValue, mControlPoint, endValue);
        }
    }
}
