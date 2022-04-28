package com.happy.todo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Author by Ouyangle, Date on 2021/1/14.
 * PS: Not easy to write code, please indicate.
 */
public class BubbleTextView extends View {
    private Paint mPaint;
    private Paint mStrokePaint;
    private Paint textPaint;
    private float wight;
    private float height;

    private int strokes = 3;
    private int radians = 20;

    private Paint.Style mStyle = Paint.Style.FILL;
    private Context context;

    String text = "我是泡泡框，出来冒个泡泡";

    public BubbleTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(mStyle);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(strokes);
        mStrokePaint.setColor(Color.BLUE);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(12);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLACK);

        wight = getTextWidth(text,textPaint) + dp2px(100);
        height = getTextHeight(text,textPaint) + dp2px(50);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged((int)wight, (int)height, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)wight, (int)height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先画一个总体的矩形
        Path path = new Path();
        //整体的一个矩形画布范围
        RectF rect = new RectF(strokes, strokes, wight - strokes, height);

        //尖尖的宽度
        float AWight = wight/5;
        float AHeight = height/5;

        //画泡泡框
        path.moveTo(rect.left, rect.top);
        path.lineTo(rect.right - radians, rect.top);

        if(radians > 0) {
           /* //右上角圆角
            RectF rectRT = new RectF(rect.right - stroke, 0, rect.right, rect.top + stroke);
            //画弧形
            path.arcTo(rectRT, -90, 90);*/
           //也可以用上面的画圆弧角
           //画右上角弧形
            // 三次贝塞尔曲线
            path.cubicTo(rect.right - radians, rect.top, rect.right, rect.top, rect.right,rect.top + radians);
        }


        path.lineTo(rect.right, rect.bottom - AHeight - radians);

        if(radians >0) {//画右下角弧形
            //三次贝塞尔曲线
            path.cubicTo(rect.right, rect.bottom - AHeight - radians, rect.right, rect.bottom - AHeight, rect.right - radians, rect.bottom - AHeight);
        }

        float left = (rect.right)/2 - AWight/2 + strokes;
        float top = rect.bottom - AHeight;
        float right = (rect.right)/2 + AWight/2 - strokes;
        float bottom = rect.bottom - strokes;

        //画泡泡下面的尖角
        RectF rectA = new RectF(left, top, right, bottom);
        path.lineTo(rectA.right,rectA.top);

        path.lineTo(rect.right/2,rectA.bottom);
        path.lineTo(rectA.left,rectA.top);

        path.lineTo(rect.left + radians,rect.bottom - AHeight);

        if(radians >0) {//画左下角弧形
            //三次贝塞尔曲线
            path.cubicTo(rect.left + radians, rect.bottom - AHeight, rect.left, rect.bottom - AHeight, rect.left, rect.bottom - AHeight - radians);
        }

        path.lineTo(rect.left,rect.top + radians);

        if(radians >0) {//画左下角弧形
            //三次贝塞尔曲线
            path.cubicTo(rect.left,rect.top + radians, rect.left,rect.top, rect.left + radians,rect.top);
        }

        if(strokes > 0){
            canvas.drawPath(drawStroke(canvas), mStrokePaint);
        }
        canvas.drawPath(path, mPaint);

        //画一个大于泡泡框宽的圆角矩形
        //RectF circularRect = new RectF(0, 0, wight, top);
        //canvas.drawRoundRect(circularRect,circular,circular,mPaint);

        //画文字
        canvas.drawText(text,rect.right/2 - (float) getTextWidth(text,textPaint)/2,top/2 + (float)getTextHeight(text,textPaint)/2,textPaint);

    }

    private Path drawStroke(Canvas canvas) {
        int radians = this.radians + strokes;
        //用来描边
        Path strokePath = new Path();
        //整体的一个矩形画布范围
        RectF rect = new RectF(0, 0, wight, height);

        //尖尖的宽度
        float AWight = wight/5;
        float AHeight = height/5;

        //画泡泡框
        strokePath.moveTo(rect.left + radians, rect.top);
        strokePath.lineTo(rect.right - radians, rect.top);

        if(radians > 0) {
           /* //右上角圆角
            RectF rectRT = new RectF(rect.right - stroke, 0, rect.right, rect.top + stroke);
            //画弧形
            path.arcTo(rectRT, -90, 90);*/
            //也可以用上面的画圆弧角
            //画右上角弧形
            // 三次贝塞尔曲线
            strokePath.cubicTo(rect.right - radians, rect.top, rect.right, rect.top, rect.right,rect.top + radians);
        }


        strokePath.lineTo(rect.right, rect.bottom - AHeight - radians);

        if(radians >0) {//画右下角弧形
            //三次贝塞尔曲线
            strokePath.cubicTo(rect.right, rect.bottom - AHeight - radians, rect.right, rect.bottom - AHeight, rect.right - radians, rect.bottom - AHeight);
        }

        float left = (rect.right)/2 - AWight/2 ;
        float top = rect.bottom - AHeight;
        float right = (rect.right)/2 + AWight/2 ;
        float bottom = rect.bottom;

        //画泡泡下面的尖角
        RectF rectA = new RectF(left, top, right, bottom);
        strokePath.lineTo(rectA.right,rectA.top);

        strokePath.lineTo(rect.right/2,rectA.bottom);
        strokePath.lineTo(rectA.left,rectA.top);

        strokePath.lineTo(rect.left + radians,rect.bottom - AHeight);

        if(radians >0) {//画左下角弧形
            //三次贝塞尔曲线
            strokePath.cubicTo(rect.left + radians, rect.bottom - AHeight, rect.left, rect.bottom - AHeight, rect.left, rect.bottom - AHeight - radians);
        }

        strokePath.lineTo(rect.left,rect.top + radians);

        if(radians >0) {//画左下上角弧形
            //三次贝塞尔曲线
            strokePath.cubicTo(rect.left,rect.top + radians, rect.left,rect.top, rect.left + radians,rect.top);
        }

        return strokePath;

    }



    public int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.left + bounds.width();
    }

    public int getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.bottom + bounds.height();
    }

    public int dp2px(final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param pxValue px 值
     * @return dp 值
     */
    public int px2dp(final float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
