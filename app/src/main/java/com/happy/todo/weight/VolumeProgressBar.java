package com.happy.todo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Author by Ouyangle, Date on 2022/4/11.
 * PS: Not easy to write code, please indicate.
 */
public class VolumeProgressBar extends View implements GestureDetector.OnGestureListener{
    private int step = 3;
    private String[] bgTxet = new String[]{"静音","低","中","高"};
    private Paint pgPaint_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint pgPaint_fg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint line_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float wight;
    private float height;
    private float progress = 1;
    private Context context;
    private RectF TotalBgRect;
    private RectF barRect = new RectF();
    // 定义手势检测器实例
    private GestureDetector detector;

    public VolumeProgressBar(Context context) {
        this(context,null);
    }

    public VolumeProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VolumeProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        pgPaint_bg.setColor(Color.parseColor("#F1F4FC"));
        pgPaint_fg.setColor(Color.parseColor("#2DA3F6"));
        barPaint.setColor(Color.WHITE);
        barPaint.setShadowLayer(2,0,0, Color.DKGRAY);

        textPaint.setColor(Color.parseColor("#94A5BE"));
        textPaint.setTextSize(sp2px(12));

        line_bg.setColor(Color.parseColor("#94A5BE"));

        line_bg.setStrokeWidth(1);

        setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速

        // 创建手势检测器
        detector = new GestureDetector(context, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        wight = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int)wight,(int)(height + dp2px(20)));
        TotalBgRect = new RectF(0,dp2px(5),wight,height - dp2px(5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        drawFg(canvas);
        drawBgText(canvas);
    }

    private void drawBgText(Canvas canvas) {
        //划等份
        float getWight = (TotalBgRect.right - TotalBgRect.left) / step;

        for(int index = 0; index <= step;  index++){
            if(Math.round(progress) == index){
                textPaint.setColor(Color.parseColor("#2DA3F6"));
            }else {
                textPaint.setColor(Color.parseColor("#94A5BE"));
            }
            //画底部步进文字
            if(index == step){
                canvas.drawText(bgTxet[index], TotalBgRect.right - getTextWidth(bgTxet[index],textPaint), TotalBgRect.bottom + dp2px(20), textPaint);
            }else {
                float w = index > 0 ? getTextWidth(bgTxet[index],textPaint) / 2f : 0f;
                canvas.drawText(bgTxet[index], getWight * index - w, TotalBgRect.bottom + dp2px(20), textPaint);
            }



        }
    }

    private void drawFg(Canvas canvas) {
        //划等份
        float getWight = (TotalBgRect.right - TotalBgRect.left) / step;
        if(progress > 0){
            RectF fgRect = new RectF(0,TotalBgRect.top,getWight * progress,TotalBgRect.bottom);
            canvas.drawRoundRect(fgRect,dp2px(10),dp2px(10),pgPaint_fg);
        }

        float barWight = dp2px(20);
        float left = progress == 0 ? TotalBgRect.left : (getWight * progress - barWight / 2);
        left = progress == 3 ? getWight * progress - barWight : left;

        float right = progress == 0 ? barWight : (getWight * progress + barWight / 2);
        right = progress == 3 ? getWight * progress : right;

        //sp2px(1)是为了给画阴影留的1dp空间
        barRect = new RectF(left,dp2px(1),right,height);

        canvas.drawRoundRect(barRect,dp2px(5),dp2px(5),barPaint);
    }

    private void drawBg(Canvas canvas) {
        canvas.drawRoundRect(TotalBgRect,dp2px(10),dp2px(10),pgPaint_bg);

        //划等份
        float getWight = (TotalBgRect.right - TotalBgRect.left) / step;
        for(int index = 0; index <= step;  index++){
            if(index > 0 && index < step){
                canvas.drawLine(getWight *  index,TotalBgRect.top, getWight *  index, TotalBgRect.bottom, line_bg);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float down_X = event.getX();
                float down_Y = event.getY();
                float down_RawX = event.getRawX();
                float down_RawY= event.getRawY();
                Log.d("ouyang","down_X : " + down_X + " ----------- down_Y : " + down_Y +
                        " -------- down_RawX : " + down_RawX + "------- down_RawY : " + down_RawY );
                Log.d("ouyang","barRect.left : " + barRect.left + " ----------- barRect.right : " + barRect.right);
                Log.d("ouyang","barRect.top : " + barRect.top + " ----------- barRect.bottom : " + barRect.bottom);
                if(down_X >= barRect.left && down_X <= barRect.right && down_Y >= barRect.top && down_Y <= barRect.bottom){
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float move_x = event.getX();
                float move_x2 = event.getRawX();
                Log.d("ouyang","----------- ACTION_MOVE -----------");
                //detector.onTouchEvent(event);
                moveDataProgressBar(move_x);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float up_x = event.getX();
                upDataProgressBar(up_x);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void moveDataProgressBar(float value) {
        float rate = value/wight;
        rate = rate * step;

        if (rate < 0){
            rate = 0;
        }else if (rate > step){
            rate = step;
        }
        setProgress(rate);
    }

    private void upDataProgressBar(float value) {
        float rate = value/wight;
        rate = rate * step;

        if (rate < 0){
            rate = 0;
        }else if (rate > step){
            rate = step;
        }
        int progress = Math.round(rate);
        setProgress(progress);
        if(volumeChangeListener != null){
            volumeChangeListener.volumeChangeListener(progress,bgTxet[progress]);
        }
    }


    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
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

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("ouyang","----------- onFling -----------");
        float minMove = 0; // 最小滑动距离
        float minVelocity = 0; // 最小滑动速度
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();

        if (beginX - endX > minMove && Math.abs(velocityX) > minVelocity) { // 左滑
           progress--;
           if(progress < 0)
               progress = 0;
           setProgress(progress);
        } else if (endX - beginX > minMove && Math.abs(velocityX) > minVelocity) { // 右滑
           progress++;
           if(progress > step)
               progress = 3;
           setProgress(progress);
        } else if (beginY - endY > minMove && Math.abs(velocityY) > minVelocity) { // 上滑

        } else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity) { // 下滑

        }
        return true;
    }

    private OnVolumeChangeListener volumeChangeListener;
    public void setVolumeChangeListener(OnVolumeChangeListener volumeChangeListener) {
        this.volumeChangeListener = volumeChangeListener;
    }
    public interface OnVolumeChangeListener{
        void volumeChangeListener(int progress, String values);
    }
}
