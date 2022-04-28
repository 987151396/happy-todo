package com.happy.todo.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.happy.todo.R;

/**
 * Author by Ouyangle, Date on 5/30/21.
 * PS: Not easy to write code, please indicate.
 */
public class StatisticsView extends View {
    private Paint paintText1;
    private Paint paintText2;
    private Paint paintStatistics;

    private float wight;
    private float height;

    private float paddingTop = 8f;
    private float paddingBottom = 8f;

    private float tv2Margin = 8f;
    private float tv1Margin = 15f;


    private float wightTV1;
    private float heightTV1;

    private float wightTV2;
    private float heightTV2;

    private int tvStr1 = 20;
    private int tvStr2 = 10;

    private int disPlayMode = 0;
    private int rect_color;
    private int tv_color1;
    private int tv_color2;

    private int textSize = 14;

    private Context context;

    private long maxProgress = 100;

    public StatisticsView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttributes(context,attrs);
        init();
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttributes(context,attrs);
        init();
    }

    private void init(){
        paintText1 = new Paint();
        paintText1.setStyle(Paint.Style.FILL);
        //paintText1.setStrokeWidth(12);
        paintText1.setTextSize(sp2px(textSize));
        paintText1.setColor(tv_color1);

        paintText2 = new Paint();
        paintText2.setStyle(Paint.Style.FILL);
        paintText2.setTextSize(sp2px(textSize));
        paintText2.setColor(tv_color2);

        paintStatistics = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStatistics.setStyle(Paint.Style.FILL);
        paintStatistics.setColor(rect_color);
        paintStatistics.setAlpha(225/2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        wight = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        getTextViewSize();
        //整体的一个矩形画布范围
        RectF totalRect = new RectF(0, 0, wight, height);

        if(disPlayMode == 0) { // 右边
            float marginLeft = totalRect.right / maxProgress * tvStr2;
            Log.d("dadasdsasad","右边 ： totalRect.right : " + totalRect.right);
            Log.d("dadasdsasad","右边 ： marginLeft : " + marginLeft);
            Log.d("dadasdsasad","右边 ： tvStr2 : " + tvStr2);
            Log.d("dadasdsasad","右边 ： maxProgress : " + maxProgress);
            marginLeft = totalRect.right - marginLeft;
            //统计图
            RectF statisticsRect = new RectF(totalRect.left + marginLeft, totalRect.top + dp2px(paddingTop), totalRect.right, totalRect.bottom - dp2px(paddingBottom));
            //画统计条
            canvas.drawRect(statisticsRect, paintStatistics);

            //tv1
            RectF tv1Rect = new RectF(totalRect.left + dp2px(tv1Margin), totalRect.top + dp2px(paddingTop), totalRect.right, totalRect.bottom - dp2px(paddingBottom));
            canvas.drawText(tvStr1+"", tv1Rect.left, (tv1Rect.bottom + dp2px(paddingTop) + heightTV1) / 2, paintText1);

            //tv2
            RectF tv2Rect = new RectF(totalRect.right - (wightTV2 + dp2px(tv2Margin)), totalRect.top + dp2px(paddingTop), totalRect.right - dp2px(tv2Margin), totalRect.bottom - dp2px(paddingBottom));
            canvas.drawText(tvStr2+"", tv2Rect.left, (tv2Rect.bottom + dp2px(paddingTop) + heightTV2) / 2, paintText2);
        }else {
            float marginRight = totalRect.right / maxProgress * tvStr2;
            marginRight = totalRect.right - marginRight;
            Log.d("dadasdsasad","左边 ： totalRect.right : " + totalRect.right);
            Log.d("dadasdsasad","左边 ： marginRight : " + marginRight);
            //统计图
            RectF statisticsRect = new RectF(totalRect.left, totalRect.top + dp2px(paddingTop), totalRect.right - marginRight, totalRect.bottom - dp2px(paddingBottom));
            //画统计条
            canvas.drawRect(statisticsRect, paintStatistics);

            //tv1
            RectF tv1Rect = new RectF(totalRect.right - wightTV1 - dp2px(tv1Margin), statisticsRect.top, totalRect.right , statisticsRect.bottom);
            canvas.drawText(tvStr1+"", tv1Rect.left, (tv1Rect.bottom + dp2px(paddingTop) + heightTV1) / 2, paintText1);

            //tv2
            RectF tv2Rect = new RectF(totalRect.left + dp2px(tv2Margin), statisticsRect.top, totalRect.right, statisticsRect.bottom);

            canvas.drawText(tvStr2+"", tv2Rect.left, (tv2Rect.bottom + dp2px(paddingTop) + heightTV2) / 2 , paintText2);
        }

    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StatisticsView);
        disPlayMode = typedArray.getInt(R.styleable.StatisticsView_sv_display_mode,0);
        rect_color = typedArray.getColor(R.styleable.StatisticsView_rect_color,0);
        tv_color1 = typedArray.getColor(R.styleable.StatisticsView_tv_color1,0);
        tv_color2 = typedArray.getColor(R.styleable.StatisticsView_tv_color2,0);

        tvStr1 = typedArray.getInt(R.styleable.StatisticsView_tv_text1,0);
        tvStr2 = typedArray.getInt(R.styleable.StatisticsView_tv_text2,0);

        textSize = typedArray.getInt(R.styleable.StatisticsView_tv_text_size,14);

        typedArray.recycle();

    }

    private void getTextViewSize() {
        wightTV1 = getTextWidth(tvStr1+"",paintText1);
        heightTV1 = getTextHeight(tvStr1+"",paintText1);

        wightTV2 = getTextWidth(tvStr2+"",paintText2);
        heightTV2 = getTextHeight(tvStr2+"",paintText2);
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

    /**
     * px 转 sp
     *
     * @param pxValue px 值
     * @return sp 值
     */
    public int px2sp(final float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
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

    public void random(){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                int x=100; // 上界。

                java.util.Random random=new java.util.Random();
                // 返回0 to x的一个随机数但不会取到x，即返回[0,x)闭开区间的值。
                int rn=random.nextInt(x);

                tvStr2 = rn;

                postInvalidate();

                random();
            }
        },1000);



    }
}
