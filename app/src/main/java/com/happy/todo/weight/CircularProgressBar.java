package com.happy.todo.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.happy.todo.R;

/**
 * Author by Ouyangle, Date on 2021/11/16.
 * PS: Not easy to write code, please indicate.
 */
public class CircularProgressBar extends View {
    private Paint paint1;
    private Paint paint2;

    private Paint paintText1;
    private Paint paintText2;

    private Paint paintText3;

    private float wight;
    private float height;

    private int circular_color1;
    private int circular_color2;
    private int text_color;

    private Context context;

    private int tvStateSize, tvSize;

    private float wightTV1;
    private float heightTV1;

    private float wightTV2;
    private float heightTV2;

    private float wightTV3;
    private float heightTV3;

    private String textState1;
    private String textState2;
    private String textStr;

    private float progress1 = 0;
    private float progress2 = 0;

    private float speed = 10;
    private float needWight;

    public CircularProgressBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttributes(context,attrs);
        init();
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttributes(context,attrs);
        init();
    }

    private void init() {

        paintText1 = new Paint();
        paintText1.setStyle(Paint.Style.FILL);
        paintText1.setTextSize(sp2px(tvStateSize));
        paintText1.setColor(circular_color1);

        paintText2 = new Paint();
        paintText2.setStyle(Paint.Style.FILL);
        paintText2.setTextSize(sp2px(tvStateSize));
        paintText2.setColor(circular_color2);

        paintText3 = new Paint();
        paintText3.setStyle(Paint.Style.FILL);
        paintText3.setTextSize(sp2px(tvSize));
        paintText3.setColor(text_color);

        paint1 = new Paint();

        paint1.setAntiAlias(true);//取消锯齿
        paint1.setStyle(Paint.Style.STROKE);//设置画圆弧的画笔的属性为描边
        //paint1.setStrokeCap(Paint.Cap.ROUND);
        paint1.setStrokeWidth(sp2px(20));
        paint1.setColor(circular_color1);

        paint2 = new Paint();
        paint2.setAntiAlias(true);//取消锯齿
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(sp2px(20));
        paint2.setColor(circular_color2);

    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar);
        circular_color1 = typedArray.getColor(R.styleable.CircularProgressBar_circular_color1, Color.BLUE);
        circular_color2 = typedArray.getColor(R.styleable.CircularProgressBar_circular_color2,Color.YELLOW);
        text_color = typedArray.getColor(R.styleable.CircularProgressBar_text_color,Color.BLACK);

        textState1 = typedArray.getString(R.styleable.CircularProgressBar_text_state1);
        textState2 = typedArray.getString(R.styleable.CircularProgressBar_text_state2);
        textStr = typedArray.getString(R.styleable.CircularProgressBar_text_str);

        tvSize = typedArray.getInt(R.styleable.CircularProgressBar_tv_state_size,12);
        tvStateSize = typedArray.getInt(R.styleable.CircularProgressBar_tv_size,12);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        getTextViewSize();

        if(wightTV1 > wightTV2){
            needWight = wightTV1;
        }else {
            needWight = wightTV2;
        }

        wight = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        wight = sp2px(130) + (needWight * 2);
        height = sp2px(130) + (needWight * 2);

        Log.d("onMeasure","wight : " + wight);
        Log.d("onMeasure","height : " + height);
        Log.d("onMeasure","needWight : " + needWight);

        setMeasuredDimension((int)wight,(int)height);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //getTextViewSize();
        //整体的一个矩形画布范围
        RectF totalRect = new RectF( 0, 0, wight,  height);
        RectF circleRect = new RectF(totalRect.left + (needWight + needWight / 2), totalRect.top +  (needWight + needWight / 2) , wight -  (needWight + needWight / 2), height -  (needWight + needWight / 2));

        Paint p = new Paint();
        p.setAntiAlias(true);//取消锯齿
        p.setStyle(Paint.Style.STROKE);//设置画圆弧的画笔的属性为描边
        //paint1.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeWidth(sp2px(2));
        p.setColor(circular_color1);

        canvas.drawRect(totalRect,p);

        Log.d("progress","progress 1 : " + progress1 + "progress 2 : " + progress2);

        if(progress1 == 0 && progress2 == 0){
            Path pathArc1 = new Path();
            pathArc1.addArc(circleRect,0,360);
            canvas.drawPath(pathArc1,paint1);

            canvas.drawText(textStr,totalRect.left + totalRect.right / 2 - wightTV3 / 2,totalRect.bottom / 2,paintText3);

        }else {
            if(progress1 >=1){
                //画弧线1
                Path pathArc1 = new Path();
                pathArc1.addArc(circleRect,0,(360 / speed * (speed * progress1)));
                canvas.drawPath(pathArc1,paint1);

                drawText1(canvas,totalRect,circleRect,pathArc1);
            }else if(progress2 >=1){
                //画弧线2
                Path pathArc2 = new Path();
                pathArc2.addArc(circleRect,0,- (360 / speed * (speed * progress2)));
                canvas.drawPath(pathArc2,paint2);

                drawText2(canvas,totalRect,circleRect,pathArc2);
            }else {
                //画弧线1
                Path pathArc1 = new Path();
                pathArc1.addArc(circleRect,0,(360 / speed * (speed * progress1)) - 4);
                canvas.drawPath(pathArc1,paint1);

                //画弧线2
                Path pathArc2 = new Path();
                pathArc2.addArc(circleRect,-2,- (360 / speed * (speed * progress2)));
                canvas.drawPath(pathArc2,paint2);

                drawText1(canvas,totalRect,circleRect,pathArc1);
                drawText2(canvas,totalRect,circleRect,pathArc2);
            }
        }

    }

    private void drawText1(Canvas canvas, RectF totalRect, RectF circleRect, Path pathArc1) {
        int divisor = 2;
        float[] coords = new float[]{0f, 0f};
        PathMeasure pm = new PathMeasure (pathArc1,false);
        pm.getPosTan(pm.getLength()/ divisor,coords,null);

        float x = coords[0];
        float y = coords[1];

        Path path = new Path();
        Paint path_paint = new Paint();

        path_paint.setColor(circular_color1);
        path_paint.setStyle(Paint.Style.STROKE);
        path_paint.setStrokeWidth(sp2px(1f));

        if(x > pm.getLength() / 2){//向右
            path.moveTo(x ,y);
            path.lineTo(circleRect.right +  needWight, y);
            path.lineTo(circleRect.right + needWight + sp2px(5), y + sp2px(10));

            RectF rvRectF = new RectF(circleRect.right + wightTV1 / 2, y + sp2px(10),
                    circleRect.right + wightTV1,  y + sp2px(10) + heightTV1);

            canvas.drawText(textState1,rvRectF.left,rvRectF.bottom,paintText1);

        }else {//向左
            path.moveTo(x ,y);
            path.lineTo(circleRect.left - needWight, y);
            path.lineTo(circleRect.left - needWight - sp2px(5), y + sp2px(10));

            RectF rvRectF = new RectF(totalRect.left, y + sp2px(10),
                    circleRect.left,  y + sp2px(10) + heightTV1);

            canvas.drawText(textState1,rvRectF.left,rvRectF.bottom,paintText1);

        }
        canvas.drawPath(path,path_paint);
    }

    private void drawText2(Canvas canvas, RectF totalRect, RectF circleRect, Path pathArc2) {
        //利用PathMeasure分别测量出各个点的坐标值coords
        int divisor = 2;
        float[] coords = new float[]{0f, 0f};
        PathMeasure pm2 = new PathMeasure (pathArc2,false);
        pm2.getPosTan(pm2.getLength()/ divisor,coords,null);

        Path path = new Path();
        Paint path_paint = new Paint();

        path_paint.setColor(circular_color2);
        path_paint.setStyle(Paint.Style.STROKE);
        path_paint.setStrokeWidth(sp2px(1f));

        float x = coords[0];
        float y = coords[1];

        if(x > pm2.getLength() / 2){//向右
            path.moveTo(x ,y);
            path.lineTo(circleRect.right +  + needWight, y);
            path.lineTo(circleRect.right + needWight + + sp2px(5), y + sp2px(10));

            RectF rvRectF = new RectF(circleRect.right + wightTV2 / 2, y + sp2px(10),
                    circleRect.right + wightTV2,  y + sp2px(10) + heightTV2);

            canvas.drawText(textState2,rvRectF.left,rvRectF.bottom,paintText2);

        }else {//向左
            path.moveTo(x ,y);
            path.lineTo(circleRect.left - needWight, y);
            path.lineTo(circleRect.left - needWight - sp2px(5), y + sp2px(10));

            RectF rvRectF = new RectF(totalRect.left, y + sp2px(10),
                    circleRect.left,  y + sp2px(10) + heightTV2);

            canvas.drawText(textState2,rvRectF.left,rvRectF.bottom,paintText2);

        }


        /*Log.d("abcd"," x : " + x);
        Log.d("abcd"," y : " + y);
        Log.d("abcd"," pm2.getLength() : " + pm2.getLength());*/
        canvas.drawPath(path,path_paint);
    }

    public void setProgress(float progress1, float progress2) {
        this.progress1 = progress1;
        this.progress2 = progress2;
        postInvalidate();
    }

    private void getTextViewSize() {
        wightTV1 = getTextWidth(textState1,paintText1);
        heightTV1 = getTextHeight(textState1,paintText1);

        wightTV2 = getTextWidth(textState2,paintText2);
        heightTV2 = getTextHeight(textState2,paintText2);

        wightTV3 = getTextWidth(textStr,paintText3);
        heightTV3 = getTextHeight(textStr,paintText3);

        /*Log.d("abc"," wightTV1 : " + wightTV1);
        Log.d("abc"," heightTV2 : " + heightTV2);
        Log.d("abc"," wight : " + wight);
        Log.d("abc"," height : " + height);*/
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
