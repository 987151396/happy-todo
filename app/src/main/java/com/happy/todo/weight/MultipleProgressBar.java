package com.happy.todo.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author by Ouyangle, Date on 6/27/21.
 * PS: Not easy to write code, please indicate.
 */
public class MultipleProgressBar extends View {
    private Paint pgPaint_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint pgPaint_fg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint line_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint line_fg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context context;

    private float wight;
    private float height;

    private int maxProgress = 125;
    private int step = 25;

    private boolean isDrawBg = false;

    private float progress = 1;

    private RectF topRect;

    public MultipleProgressBar(Context context) {
        super(context);
        init(context);
    }

    public MultipleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultipleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;

        pgPaint_bg.setColor(Color.GRAY);
        pgPaint_fg.setColor(Color.BLUE);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(sp2px(12));

        line_bg.setColor(Color.GRAY);
        line_fg.setColor(Color.BLUE);

        line_bg.setStrokeWidth(5);
        line_fg.setStrokeWidth(5);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        wight = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int)wight,(int)(height+dp2px(20)));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {


        if(!isDrawBg) {
            //drawBg(canvas);
            topRect = new RectF(0, 0, wight, dp2px(12));
            isDrawBg = true;
        }

        drawBg(canvas);
        drawFg(canvas);

    }

    private void drawFg(Canvas canvas) {


        RectF r_Record = new RectF();

        int num = maxProgress / step;

        int stepRect_wight = dp2px(5);

        int star_left = 0;

        float fg_values = progress / step;

        float fg_step = Float.parseFloat(getInteger(fg_values+""));

        if(fg_step >= 1){

            for (int a = 1; a <= fg_step; a++){
                float rw = topRect.right / num * a;
                if(a == 1){

                    RectF r_start = new RectF();
                    RectF r_end = new RectF();

                    r_start.left = star_left;
                    r_start.top = topRect.top;
                    r_start.right = star_left + stepRect_wight;
                    r_start.bottom = topRect.bottom;

                    r_end.left = rw - stepRect_wight;
                    r_end.top = topRect.top;
                    r_end.right = rw;
                    r_end.bottom = topRect.bottom;

                    canvas.drawRect(r_start,pgPaint_fg);
                    canvas.drawLine(r_start.right,r_start.bottom/2, r_end.left,r_end.bottom /2, line_fg);
                    canvas.drawRect(r_end,pgPaint_fg);

                    r_Record = new RectF(r_end.left,r_end.top,r_end.right,r_end.bottom);

                    star_left += r_end.right;
                }else {
                    RectF r_end = new RectF();

                    r_end.left = rw - stepRect_wight;
                    r_end.top = topRect.top;
                    r_end.right = rw;
                    r_end.bottom = topRect.bottom;

                    canvas.drawLine(r_Record.right,r_Record.bottom/2, r_end.left,r_end.bottom /2, line_fg);
                    canvas.drawRect(r_end,pgPaint_fg);

                    r_Record = new RectF(r_end.left,r_end.top,r_end.right,r_end.bottom);

                    star_left += r_end.right;
                }
            }

            float fg_step2 = fg_values - fg_step;
            if(fg_step2 > 0){
                float fg_step2_wight = topRect.right / num * fg_step2;

                RectF r_end = new RectF();

                r_end.left = r_Record.left + fg_step2_wight;
                r_end.top = topRect.top;
                r_end.right = r_Record.left + fg_step2_wight + stepRect_wight;
                r_end.bottom = topRect.bottom;

                if(fg_step2_wight > stepRect_wight) {
                    canvas.drawLine(r_Record.right, r_Record.bottom / 2, r_end.left, r_end.bottom / 2, line_fg);
                    canvas.drawRect(r_end, pgPaint_fg);
                }
            }

        }else {
            float fg_step2 = fg_values - fg_step;
            if(fg_step2 > 0){
                float fg_step2_wight = topRect.right / num * fg_step2;

                RectF r_start = new RectF();
                RectF r_end = new RectF();

                r_start.left = 0;
                r_start.top = topRect.top;
                r_start.right = stepRect_wight;
                r_start.bottom = topRect.bottom;

                r_end.left = fg_step2_wight - stepRect_wight;
                r_end.top = topRect.top;
                r_end.right = fg_step2_wight;
                r_end.bottom = topRect.bottom;

                canvas.drawRect(r_start,pgPaint_fg);

                if (fg_step2_wight > stepRect_wight) {
                    canvas.drawLine(r_start.right,r_start.bottom/2, r_end.left,r_end.bottom /2, line_fg);
                    canvas.drawRect(r_end,pgPaint_fg);
                }

            }
        }

    }

    private void drawBg(Canvas canvas) {

        RectF bottomRect = new RectF(0, topRect.bottom, wight, topRect.bottom + dp2px(20));

        RectF r_Record = new RectF();

        int num = maxProgress / step;

        int stepRect_wight = dp2px(5);

        int star_left = 0;

        for (int a = 1; a <= num + 1; a++){
            float rw = topRect.right / num * a;
            if(a == 1){

                RectF r_start = new RectF();
                RectF r_end = new RectF();

                r_start.left = star_left;
                r_start.top = topRect.top;
                r_start.right = star_left + stepRect_wight;
                r_start.bottom = topRect.bottom;

                r_end.left = rw - stepRect_wight;
                r_end.top = topRect.top;
                r_end.right = rw;
                r_end.bottom = topRect.bottom;

                canvas.drawRect(r_start,pgPaint_bg);
                canvas.drawLine(r_start.right,r_start.bottom/2, r_end.left,r_end.bottom /2, line_bg);
                canvas.drawRect(r_end,pgPaint_bg);

                //画底部步进文字
                canvas.drawText(a+"X", bottomRect.left, bottomRect.bottom, textPaint);

                r_Record = new RectF(r_end.left,r_end.top,r_end.right,r_end.bottom);

                star_left += r_end.right;
            }else {
                RectF r_end = new RectF();

                r_end.left = rw - stepRect_wight;
                r_end.top = topRect.top;
                r_end.right = rw;
                r_end.bottom = topRect.bottom;

                canvas.drawLine(r_Record.right,r_Record.bottom/2, r_end.left,r_end.bottom /2, line_bg);
                canvas.drawRect(r_end,pgPaint_bg);

                //画底部步进文字
                String text = step * (a - 1) + "X";
                float textWight = getTextWidth(text,textPaint);

                if(a < num + 1) {
                    canvas.drawText(text, r_Record.left - textWight / 2, bottomRect.bottom, textPaint);
                }else {
                    canvas.drawText(text, r_Record.right - textWight, bottomRect.bottom, textPaint);
                }


                r_Record = new RectF(r_end.left,r_end.top,r_end.right,r_end.bottom);

                star_left += r_end.right;
            }



        }


    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float down_x = event.getX();
                float down_x2 = event.getRawX();
                Log.d("ouyang","getX : " + down_x + " ----------- getRawX : " + down_x2 + " -------- wight : " + wight);
                Log.d("ouyang","topRect.left : " + topRect.left + " ----------- topRect.right : " + topRect.right);
                upDataProgressBar(down_x);
                break;
            case MotionEvent.ACTION_MOVE:
                float move_x = event.getX();
                float move_x2 = event.getRawX();
                Log.d("ouyang","getX : " + move_x + " ----------- getRawX : " + move_x2);
                upDataProgressBar(move_x);
               break;
        }
        return true;
    }

    private void upDataProgressBar(float value){
        if(value < 0){
            return;
        }else if (value == 0){
            setProgress(1);
            return;
        }

        float rate = value/wight;
        rate = rate * maxProgress;

        if (rate < 1){
            rate = 1;
        }else if (rate > maxProgress){
            rate = maxProgress;
        }
        setProgress(rate);
        Log.d("ouyang","rate : " + rate);
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

    public int dp2px(final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.left + bounds.width();
    }

    //获取整数
    private String getInteger(String content){
        Pattern pattern = Pattern.compile("\\d+");
                 Matcher matcher = pattern.matcher(content);
                 if (matcher.find()) {
                     return matcher.group(0);
                 }
                 return "0";
    }

    /*//获取小数
    private String getDecimal(String content){

    }*/

}
