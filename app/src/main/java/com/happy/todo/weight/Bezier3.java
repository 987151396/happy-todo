package com.happy.todo.weight;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Author by Ouyangle, Date on 2021/11/24.
 * PS: Not easy to write code, please indicate.
 */
public class Bezier3 extends View {
    private Paint mPaint;
    private Paint.Style mStyle = Paint.Style.FILL;
    private int wight;
    private int height;
    private Context context;

    private PointF point1 = new PointF();
    private PointF point2 = new PointF();

    private float  circularRadius1 = 37f;
    private float  circularRadius2 = 37f;

    private float maxRadius = circularRadius2;
    private float minRadius = 10f;

    public Bezier3(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public Bezier3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public Bezier3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(mStyle);
        mPaint.setColor(Color.BLUE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        wight = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        point1.set(wight / 2, height / 2);
        point2.set(wight / 2,height / 2);

       setMeasuredDimension((int)wight,(int)height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //圆1
        Path path1 = new Path();
        path1.addCircle(point1.x, point1.y,circularRadius1,Path.Direction.CW);

        //圆2
        Path path2 = new Path();
        path2.moveTo(point1.x, point1.y);
        path2.lineTo(point1.x + sp2px(20), point1.y);

        path1.addCircle(point2.x, point2.y,circularRadius2,Path.Direction.CW);
        canvas.drawPath(path1, mPaint);



       /* Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);
        p.setStrokeWidth(sp2px(2));*/

        //canvas.drawPath(path2, p);

        /*double atan2 = Math.atan2(point1.y - point2.y, point1.x - point1.x);
        p = new Paint();
        p.setTextSize(sp2px(12));
        p.setColor(Color.BLACK);
        canvas.drawText(atan2+"", point2.x + sp2px(30), point2.y,p);
        canvas.drawText(trace(atan2)+"", point2.x + sp2px(30), point2.y + sp2px(10),p);*/


        //画一条两圆中心点之间的连线
        Path path4 = new Path();
        Paint p4 = new Paint();
        p4.setColor(Color.BLACK);
        p4.setStyle(Paint.Style.STROKE);
        p4.setStrokeWidth(sp2px(2));

        //path4.moveTo(point1.x,point1.y);
        //path4.lineTo(point2.x,point2.y);


        //求两圆中心点之间的连线，线的中心点坐标
        float x = (point1.x + point2.x) / 2;
        float y = (point1.y + point2.y) / 2;

        PointF point3 = new PointF(x,y);
        //path4.addCircle(point3.x, point3.y,10,Path.Direction.CW);

        //canvas.drawPath(path4,p4);


        //求圆1的两个切点
        PointF U1 = new PointF();
        PointF Q1 = new PointF();
        PointF Q2 = new PointF();
        getPoint(circularRadius1, point3, point1,U1, Q1, Q2);
        //画切点坐标的圆
        /*Path path5 = new Path();
        path5.addCircle(Q1.x, Q1.y,10,Path.Direction.CW);
        path5.addCircle(Q2.x, Q2.y,10,Path.Direction.CW);
        canvas.drawPath(path5,p4);*/


        //求圆2的两个切点
        PointF U2 = new PointF();
        PointF Q3 = new PointF();
        PointF Q4 = new PointF();
        getPoint(circularRadius2, point3, point2, U2, Q3, Q4);
        //画切点坐标的圆
        /*Path path6 = new Path();
        path6.addCircle(Q3.x, Q3.y,10,Path.Direction.CW);
        path6.addCircle(Q4.x, Q4.y,10,Path.Direction.CW);
        canvas.drawPath(path6,p4);*/




        // 绘制两圆连接闭合
        Path path = new Path();
        Paint mPaintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRed.setColor(Color.BLUE);

        path.moveTo(Q1.x, Q1.y);
        path.quadTo(point3.x, point3.y,
                Q4.x, Q4.y);
        path.lineTo(Q3.x, Q3.y);
        path.quadTo(point3.x, point3.y,
                Q2.x, Q2.y);
        canvas.drawPath(path, mPaintRed);





        path4.moveTo(point1.x,point1.y);
        path4.lineTo(point2.x,point2.y);
        //canvas.drawPath(path4,p4);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("onTouchEvent",event.getAction()+"");
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                point2.set(event.getX(),event.getY());

                float x = Math.abs(event.getX() - point1.x);
                float y = Math.abs(event.getY() - point1.y);

                float move_values = (float) (wight / 2f);
                float distance = Math.min(getDistanceBetween2Points(point1, point2), move_values);

                float fraction = (0.8f * distance) / move_values;
                circularRadius1 = Math.abs(1 - fraction) * maxRadius;
                Log.d("ACTION_MOVE","fraction :" +  fraction + "  circularRadius1 : " + circularRadius1);

                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                //point2.set(wight / 2,height / 2);
                //circularRadius1 = maxRadius;

                Path path = new Path();
                path.moveTo(point1.x, point1.y);
                path.lineTo(point2.x, point2.y);

                float[] coords = new float[]{0f, 0f};
                PathMeasure pathMeasure = new PathMeasure(path,false);

                ValueAnimator mAnim = ValueAnimator.ofFloat(1.0f);
                mAnim.setDuration(300);
                mAnim.addUpdateListener(animation -> {
                    float f = animation.getAnimatedFraction();
                    int step = (int) (f * 10);
                    if(step <= 0)
                        step = 1;
                    Log.d("ValueAnimator","step : " + step);

                    pathMeasure.getPosTan(pathMeasure.getLength()/ step,coords,null);
                    float getX = coords[0];
                    float getY = coords[1];
                    Log.d("pathMeasure","getX : " + getX  + " getY : " + getY + "\n");
                    Log.d("pathMeasure","point1.x : " + point1.x  + " point1.y : " + point1.y + "\n");
                    point2.set(getX,getY);

                    postInvalidate();
                });
                mAnim.start();


                break;
        }

        return true;
    }

    private void getPoint(float radius, PointF P, PointF C, PointF U, PointF Q1, PointF Q2){
    // C是圆心的坐标
        // P是点的坐标
        // Q1,Q2是切点坐标
        // U是点到圆心的单位向量坐标
        double r = radius; // 圆的半径
        double distance = 0; // 圆心r 到p 点的距离
        double length = 0; // 点p 到切点的距离
        double angle = 0; // 切线与点心连线的夹角


        // 求出点到圆心的距离
        distance = Math.sqrt((P.x-C.x)*(P.x-C.x)+ (P.y-C.y)*(P.y-C.y));
        // 判断是否符合要求 distance<=r 不符合则返回 否则进行运算
        if(distance<=r){

            //printf("您输入的数值不在范围内!\n");
            //return 0;
        }

        // 点p 到切点的距离
        length = Math.sqrt(distance*distance-r*r);

        // 点到圆心的单位向量
        U.x= (float) ((C.x-P.x)/distance);
        U.y= (float) ((C.y-P.y)/distance);

        // 计算切线与点心连线的夹角
        angle = Math.asin(r/distance);

        // 向正反两个方向旋转单位向量
        Q1.x = (float) (U.x * Math.cos(angle)  -  U.y * Math.sin(angle));
        Q1.y = (float) (U.x * Math.sin(angle)  +  U.y * Math.cos(angle));
        Q2.x = (float) (U.x * Math.cos(-angle) -  U.y * Math.sin(-angle));
        Q2.y = (float) (U.x * Math.sin(-angle) +  U.y * Math.cos(-angle));
        // 得到新座标
        Q1.x = (float) (Q1.x * length+ P.x);
        Q1.y = (float) (Q1.y * length+ P.y);
        Q2.x = (float) (Q2.x * length+ P.x);
        Q2.y = (float) (Q2.y * length+ P.y);
        // 输出坐标
        //printf("Q1的坐标为：(%.1f,%.1f),Q2的坐标为：(%.1f,%.1f) \n",Q1.x,Q1.y,Q2.x,Q2.y);

        Log.d("getPoint", "Q1.x : " + Q1.x);
        Log.d("getPoint", "Q1.y : " + Q1.y);

        Log.d("getPoint", "Q2.x : " + Q2.x);
        Log.d("getPoint", "Q2.y : " + Q2.y);
    }

    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
        Log.d("getDistance","distance : " + distance);
        return distance;
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

    //输入弧度值，return 角度值
    public double trace(double x){
        //弧度=角度*Math.PI/180
        return 180 * x / Math.PI;
    }
}
