package com.happy.todo.lib_common.widget.input;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.SizeUtil;
import com.happy.todo.lib_common.utils.ViewUtil;

/**
 * 自动清除填写内容的EditText
 * Created by Jaminchanks on 2018/1/12.
 */

public class AutoClearContentEditText extends AppCompatEditText {
    private Drawable mDrawableRight;

    public AutoClearContentEditText(Context context) {
        super(context);
    }

    public AutoClearContentEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        ViewUtil.setDrawableRight(this, R.mipmap.icon_close_black);
        mDrawableRight = getCompoundDrawables()[2];
        mDrawableRight.setAlpha(0);

        //关闭按钮自动显示
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDrawableRight.setAlpha(TextUtils.isEmpty(s) ? 0 : 255);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //有文字时，获取焦点时显示x，否则不显示
        setOnFocusChangeListener((v, hasFocus) -> {
            if (!TextUtils.isEmpty(getText())) {
                mDrawableRight.setAlpha(hasFocus ? 255 : 0);
            }
        });

    }

    public void setHideDrawableRight(boolean value){
        mDrawableRight.setAlpha(value ? 255 : 0);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performClick();

            if (mDrawableRight == null) {
                return super.onTouchEvent(event);
            }
            final Rect bounds = mDrawableRight.getBounds();
            final int x = (int) event.getRawX(); // 点击的位置

            int rightCorner = getViewLocationInWindow()[0] + getMeasuredWidth();

            // Icon的位置
            int rightIcon = rightCorner - bounds.width();

            // 大于Icon的位置, 才能触发点击
            if (x >= rightIcon-SizeUtil.dp2px(12)) {
                setText(""); //清除内容
                event.setAction(MotionEvent.ACTION_CANCEL);
                return false;
            }
        }
        return super.onTouchEvent(event);
    }

//    // 获取上右角的距离
//    public PointF getTopRightCorner() {
//        float src[] = new float[8];
//        float[] dst = new float[]{0, 0, getWidth(), 0, 0, getHeight(), getWidth(), getHeight()};
//        getMatrix().mapPoints(src, dst);
//        return new PointF(getX() + src[2], getY() + src[3]);
//    }

    private int[] getViewLocationInWindow() {
        int[] location = new int[2] ;
//        getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        getLocationOnScreen(location);//获取该view在整个屏幕内的绝对坐标
        return location;
    }
}