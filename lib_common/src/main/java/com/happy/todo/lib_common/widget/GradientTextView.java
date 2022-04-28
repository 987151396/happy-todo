package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ResourceUtils;

/**
 * @Describe：渐变颜色TextView
 * @Date： 2018/12/11
 * @Author： dengkewu
 * @Contact：
 */
public class GradientTextView extends androidx.appcompat.widget.AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private Rect mTextBound = new Rect();
    private boolean mIsVertical;
    private int mFromColor;
    private int mToColor;

    public GradientTextView(Context context) {
        this(context, null);
    }

    public GradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTv);
        mToColor = typedArray.getColor(R.styleable.GradientTv_gtv_toColor, ResourceUtils.getColor(R.color.green_FFFEF7));
        mFromColor = typedArray.getColor(R.styleable.GradientTv_gtv_fromColor, ResourceUtils.getColor(R.color.white_FFFEF7));
        mIsVertical = typedArray.getBoolean(R.styleable.GradientTv_gtv_is_vertical, true);
        typedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsVertical) {
            mViewHeight = getMeasuredHeight();
        } else {
            mViewWidth = getMeasuredWidth();
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            mViewHeight = (int) (Math.ceil(fm.descent - fm.ascent) + getLineSpacingExtra());
//        } else {
//            mViewHeight = (int) (Math.ceil(fm.descent - fm.ascent) + mLineSpace);
//        }


        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        //前面4个参数分别表示渐变的开始x轴,开始y轴,结束的x轴,结束的y轴,mcolorList表示渐变的颜色数组
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, mViewHeight, new int[]{mFromColor, mToColor}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
        //画出文字
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }
}
