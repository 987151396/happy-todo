package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.SizeUtil;

public class OrderBgView extends View {

    private Paint mPaint;
    private float rectPercent = 0.9f;
    private int fromColor;
    private int toColor;
    private float rect = 20f;
    public OrderBgView(Context context) {
        this(context, null, 0);
    }

    public OrderBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.OrderBgView);
        rectPercent = array.getFloat(R.styleable.OrderBgView_percentRect,rectPercent);
        rect = array.getFloat(R.styleable.OrderBgView_bottomRect,rect);
        fromColor = array.getColor(R.styleable.OrderBgView_fromColor, ContextCompat.getColor(context, R.color.theme_color));
        toColor = array.getColor(R.styleable.OrderBgView_toColor,  ContextCompat.getColor(context, R.color.theme_color1));
        array.recycle();
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LinearGradient backGradient = new LinearGradient(0, 0, 0, getMeasuredHeight(),
                fromColor, toColor, Shader.TileMode.CLAMP);
        mPaint.setShader(backGradient);
        // 计算矩形区域的高度
        int rectBottom = (int) (getMeasuredHeight() - SizeUtil.dp2px(rect)); //设置为规定弧度
        Rect rect = new Rect(0, 0, getMeasuredWidth(), rectBottom);
        canvas.drawRect(rect, mPaint);

        // 计算半个椭圆的高度 = View的高度 - 矩形的高度
        int ovalRectFHeight = getMeasuredHeight() - rectBottom;
        Path path = new Path();
        path.moveTo(0, rectBottom);
        path.quadTo(getMeasuredWidth()/2, getMeasuredHeight() + ovalRectFHeight, getMeasuredWidth(), rectBottom);
        canvas.drawPath(path, mPaint);
    }
}
