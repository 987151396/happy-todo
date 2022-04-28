package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * @author vegen
 * @date 2018/9/28
 * @description 带删除的输入框, 使用时请设置 drawableRight,如不显示 x 图标，请检查 drawableRight 属性是否设置
 */
public class CleanEditText extends androidx.appcompat.widget.AppCompatEditText {

    private Drawable dRight;
    private Rect rBound;

    public CleanEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initEditText();
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText();
    }

    public CleanEditText(Context context) {
        super(context);
        initEditText();
    }

    private void initEditText() {
        setEditTextDrawable(false);
    }

    public void setEditTextDrawable(boolean focused) {
        if (!focused) {
            setCompoundDrawables(null, null, null, null);
        } else {
            setCompoundDrawables(null, null, this.dRight, null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dRight = null;
        this.rBound = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ((this.dRight != null) && (event.getAction() == 1)) {
            this.rBound = this.dRight.getBounds();
            int i = (int) event.getRawX();
            if (i > getRight() - 3 * this.rBound.width()) {
                // 点击的位置聚焦
                requestFocus();
                setText("");
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        CleanEditText.this.setEditTextDrawable(focused);
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {
        if (right != null) {
            this.dRight = right;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

}
