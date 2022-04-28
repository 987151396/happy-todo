package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.SizeUtil;

/**
 * 方块进度条
 * Created by Jaminchanks on 2018-01-13.
 */

public class DiamondsProgressBar extends View{
    private Paint mPaintHighLight; //高亮
    private Paint mPaintDim; //黯淡
    private float mDiamondWidth = 100; //方块宽度
    private float mDiamondHeight = 50; //方块高度
    private int mDiamondCount = 8; //方块个数
    private float mDiamondSpace = 20; //方块间距
    private int mHighlightCount = 0;

    private float mRoundCornerSize = 10; //圆角大小

    RectF[] mRectF = new RectF[mDiamondCount];

    private Context mContext;

    public DiamondsProgressBar(Context context) {
        super(context);
    }

    public DiamondsProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    public DiamondsProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        mPaintHighLight = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintHighLight.setDither(true);
        mPaintHighLight.setColor(mContext.getResources().getColor(R.color.theme_color));
        mPaintHighLight.setStyle(Paint.Style.FILL);
        mPaintHighLight.setStrokeWidth(mDiamondWidth);

        mPaintDim = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintDim.setDither(true);
        mPaintDim.setColor(mContext.getResources().getColor(R.color.gray_eeeeee));
        mPaintDim.setStyle(Paint.Style.FILL);
        mPaintDim.setStrokeWidth(mDiamondWidth);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resultWidth = MeasureSpec.getSize(widthMeasureSpec);
        int resultHeight = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            resultWidth = (int) (mDiamondWidth * mDiamondCount + mDiamondSpace * (mDiamondCount - 1));
        }

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            resultHeight = (int) mDiamondHeight;
        }

        setMeasuredDimension(resultWidth, resultHeight);
    }


    /**
     * onMeasure后执行onSizeChanged
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDiamondSpace = SizeUtil.dp2px(5);
        mRoundCornerSize = SizeUtil.dp2px(2);
        mDiamondWidth = ( w - mDiamondSpace * mDiamondCount) / mDiamondCount;
    }

    /**
     * 设置百分比
     * @param progress
     */
    public void setProgress(int progress) {
        mHighlightCount = Math.round((progress / 100.0f) * mDiamondCount); //四舍五入
        postInvalidate();
    }

    public void setDiamondCount(int diamondCount) {
        this.mDiamondCount = diamondCount;
        postInvalidate();
    }

    public void setHighLightCount(int count) {
        this.mHighlightCount = count;
        postInvalidate();
    }


    /**
     * 初始化所有方块
     */
    private void initRects() {
        for (int i = 0; i < mDiamondCount; i++) {
            mRectF[i] = new RectF((mDiamondSpace + mDiamondWidth) * i,
                    0,
                    mDiamondWidth * (i + 1) + mDiamondSpace * i,
                    getHeight());
        }
    }

    /**
     * 方块为整个整个显示。如果需求更改再说了。。。
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        initRects();

        //画高亮部分
        for (int i = 0; i < mHighlightCount; i++) {
            canvas.drawRoundRect(mRectF[i], mRoundCornerSize, mRoundCornerSize, mPaintHighLight);
        }

        //画剩余部分
        for (int i = mHighlightCount; i < mDiamondCount; i++){
            canvas.drawRoundRect(mRectF[i], mRoundCornerSize, mRoundCornerSize, mPaintDim);
        }
    }
}
