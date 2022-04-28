package com.happy.todo.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.happy.todo.R;

import java.math.BigDecimal;

/**
 * Author by Ouyangle, Date on 2021/11/26.
 * PS: Not easy to write code, please indicate.
 */
public class LevelCircularProgressBar extends View {
    private Paint tvPaint1;
    private Paint tvPaint2;
    private Paint tvPaint3;

    private Paint cp_Paint1;
    private Paint cp_Paint2;
    private Paint cp_Paint3;

    private Context context;

    private float wight;
    private float height;

    private float maxProgress = 5;
    private float MAX;
    private float progress = 3;
    private int balance = 0;
    private float tvWight;
    private float tvHeight;
    private String text_hint;

    private final int AT_LEFT = 0;
    private final int AT_MIDDLE = 1;
    private final int AT_RIGHT = 2;

    private float DoTRadius;

    public LevelCircularProgressBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LevelCircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttributes(context, attrs);
        init();
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LevelCircularProgressBar);
        text_hint = typedArray.getString(R.styleable.LevelCircularProgressBar_text_hint);

        typedArray.recycle();
    }

    public LevelCircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttributes(context, attrs);
        init();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        if(progress > maxProgress || progress < 0)
            return;
        this.progress = progress;
        invalidate();
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        invalidate();
    }

    private void init() {
        MAX = maxProgress - 1;

        DoTRadius = sp2px(5);

        tvPaint1 = new Paint();
        tvPaint1.setStyle(Paint.Style.FILL);
        tvPaint1.setTextSize(sp2px(12));

        tvPaint2 = new Paint();
        tvPaint2.setStyle(Paint.Style.FILL);
        tvPaint2.setTextSize(sp2px(32));
        tvPaint2.setColor(Color.parseColor("#FFFFFF"));

        tvPaint3 = new Paint();
        tvPaint3.setStyle(Paint.Style.FILL);
        tvPaint3.setTextSize(sp2px(13));
        tvPaint3.setColor(Color.parseColor("#FFFFFF"));


        cp_Paint1 = new Paint();
        cp_Paint1.setStyle(Paint.Style.STROKE);
        cp_Paint1.setStrokeWidth(sp2px(3));
        cp_Paint1.setAntiAlias(true);
        cp_Paint1.setColor(Color.parseColor("#F8DDA8"));

        cp_Paint2 = new Paint();
        cp_Paint2.setStyle(Paint.Style.STROKE);
        cp_Paint2.setStrokeWidth(sp2px(3));
        cp_Paint2.setAntiAlias(true);
        cp_Paint2.setColor(Color.parseColor("#CACCD0"));

        cp_Paint3 = new Paint();
        cp_Paint3.setStyle(Paint.Style.STROKE);
        cp_Paint3.setStrokeWidth(sp2px(3));
        cp_Paint3.setAntiAlias(true);
        cp_Paint3.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        getTextViewSize();

        wight = MeasureSpec.getSize(widthMeasureSpec);
        //height = MeasureSpec.getSize(heightMeasureSpec);
        height = wight;
        setMeasuredDimension((int) wight, (int) height);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        RectF totalRect = new RectF(0, 0, wight, height);

        float variable = tvWight + DoTRadius;
        RectF circleRect = new RectF(totalRect.left + variable, totalRect.top + variable , wight - variable, height - variable);
        //画文字
        String text = balance + "";
        canvas.drawText(text, totalRect.left + totalRect.right / 2 - getTextWidth(text, tvPaint2) / 2, totalRect.bottom / 2, tvPaint2);
        canvas.drawText(text_hint, totalRect.left + totalRect.right / 2 - getTextWidth(text_hint, tvPaint3) / 2, totalRect.bottom / 2 + getTextHeight(text, tvPaint2), tvPaint3);

        Path pathArc1 = new Path();
        pathArc1.addArc(circleRect, 0, -180);
        //pathArc1.addArc(totalRect, 0, -180);
        canvas.drawPath(pathArc1, cp_Paint3);
        //圆点
        PointF yd_pointF = new PointF(((totalRect.right - tvWight) - (totalRect.left + tvWight)) / 2f + tvWight, ((totalRect.bottom - tvWight) - (totalRect.top - tvWight)) / 2f);

        for (float a = 0; a <= MAX; a++) {
            float ao = (-180 - (-180 * (a / MAX)));
            Log.d("dadasdad", "ao : " + ao);
            float x = getArcX(yd_pointF.x, (circleRect.right - circleRect.left) / 2, ao);
            float y = getArcY(yd_pointF.y, (circleRect.right - circleRect.left) / 2, ao);

            Log.d("yd_pointF","圆点 : " + yd_pointF.x + " x :" + x + " a ： " + a + " getDirection : " + getDirection(yd_pointF.x, x));

            //背景
            Path p = new Path();
            p.addCircle(x, y, DoTRadius, Path.Direction.CW);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            canvas.drawPath(p, paint);

            RectF rectF = getTextRectF(x, y, yd_pointF.x, x);

            tvPaint1.setColor(Color.parseColor("#CACCD0"));

            if (progress == 0) {
                drawText("LV" + (int) (a + 1), rectF, canvas,tvPaint1);
            } else {
                if (a > progress) {
                    drawText("LV" + (int) (a + 1), rectF, canvas,tvPaint1);
                }
            }
        }

        if (progress > 0) {
            progress = progress - 1;
            for (float a = 0; a <= progress + 1; a++) {

                float ao = (-180 - (-180 * (a / MAX)));

                float x = getArcX(yd_pointF.x, (circleRect.right - circleRect.left) / 2, ao);
                float y = getArcY(yd_pointF.y, (circleRect.right - circleRect.left) / 2, ao);


                if(progress < MAX) {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);
                    //第二进度
                    float ao2 = (-180 - (-180 * ((MAX - (progress + 1)) / MAX)));
                    Path path2 = new Path();
                    path2.addArc(circleRect, 180, -ao2);
                    canvas.drawPath(path2, cp_Paint2);

                    Path p2 = new Path();
                    p2.addCircle(x, y, DoTRadius, Path.Direction.CW);
                    paint.setColor(Color.parseColor("#CACCD0"));
                    canvas.drawPath(p2, paint);

                    //画文字
                    if (a == progress + 1) {
                        RectF rectF = getTextRectF(x, y, yd_pointF.x, x);

                        Paint p = new Paint();
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(sp2px(1));
                        p.setAntiAlias(true);
                        p.setColor(Color.parseColor("#F5DEA6"));

                        Path path = new Path();
                        path.addRoundRect(rectF, sp2px(10), sp2px(10), Path.Direction.CW);
                        canvas.drawPath(path, p);

                        tvPaint1.setColor(Color.parseColor("#FFFFFF"));
                        drawText("LV" + (int) (a + 1), rectF, canvas,tvPaint1);
                    }
                }
            }

            for (float a = 0; a <= progress; a++) {
                float ao = (-180 - (-180 * (a / MAX)));
                float x = getArcX(yd_pointF.x, (circleRect.right - circleRect.left) / 2, ao);
                float y = getArcY(yd_pointF.y, (circleRect.right - circleRect.left) / 2, ao);
                //背景
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                //第一进度
                float ao1 = (-180 - (-180 * ((MAX - progress) / MAX)));
                Path path1 = new Path();
                path1.addArc(circleRect, 180, -ao1);
                canvas.drawPath(path1, cp_Paint1);

                Path p1 = new Path();
                p1.addCircle(x, y, DoTRadius, Path.Direction.CW);
                paint.setColor(Color.parseColor("#F8DDA8"));
                canvas.drawPath(p1, paint);

                RectF rectF = getTextRectF(x, y, yd_pointF.x, x);

                //画文字
                if (a == progress) {
                    Paint p = new Paint();
                    p.setStyle(Paint.Style.STROKE);
                    p.setStrokeWidth(sp2px(1));
                    p.setAntiAlias(true);
                    p.setColor(Color.parseColor("#F8DDA8"));

                    Path path = new Path();
                    path.addRoundRect(rectF, sp2px(10), sp2px(10), Path.Direction.CW);
                    canvas.drawPath(path, p);
                    tvPaint1.setColor(Color.parseColor("#FFFFFF"));
                    drawText("LV" + (int) (a + 1), rectF, canvas,tvPaint1);
                } else {
                    tvPaint1.setColor(Color.parseColor("#F8DDA8"));
                    drawText("LV" + (int) (a + 1), rectF, canvas,tvPaint1);
                }
            }
        }
    }

    private void drawText(String text, RectF rectF, Canvas canvas, Paint tvPaint1) {
        canvas.drawText(text, rectF.left + ((rectF.right - rectF.left) / 2 - tvWight / 2) + DoTRadius, rectF.top + (rectF.bottom - rectF.top) / 2 + tvHeight / 2, tvPaint1);
    }

    private RectF getTextRectF(float arcX, float arcY, float midpointX, float directionX){
        switch (getDirection(midpointX, directionX)){
            case AT_LEFT://左
                return new RectF(arcX - tvWight - DoTRadius , arcY - tvHeight - DoTRadius - tvHeight , arcX - DoTRadius, arcY - DoTRadius);
            case AT_MIDDLE://中
                return new RectF(arcX - tvWight / 2, arcY - tvHeight - DoTRadius - tvHeight , arcX + tvWight / 2 , arcY - DoTRadius);
            case AT_RIGHT://右
                return new RectF(arcX + DoTRadius, arcY - tvHeight - DoTRadius - tvHeight , arcX + tvWight + DoTRadius, arcY - DoTRadius);
        }
        return new RectF(arcX - tvWight / 2, arcY - tvHeight - DoTRadius - tvHeight , arcX + tvWight / 2, arcY - DoTRadius);
    }

    private int getDirection(float midpointX, float directionX){
       if(Math.floor(midpointX) > Math.floor(directionX)){
           return AT_LEFT;
       }
        if(Math.floor(midpointX) < Math.floor(directionX)){
            return AT_RIGHT;
        }
        return AT_MIDDLE;
    }

    private float getArcX(float x, float r, float ao) {
        return (float) (x + r * Math.cos(ao * 3.14 / 180));
    }

    private float getArcY(float y, float r, float ao) {
        return (float) (y + r * Math.sin(ao * 3.14 / 180));
    }

    private void getTextViewSize() {
        tvWight = getTextWidth("LV" + 8, tvPaint1);
        tvHeight = getTextHeight("LV" + 8, tvPaint1);

        tvWight += tvWight / 2;
    }

    public float getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.left + bounds.width();
    }

    public float getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.bottom + bounds.height();
    }

    /**
     * sp 转 px
     *
     * @param spValue sp 值
     * @return px 值
     */
    public int sp2px(final float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
