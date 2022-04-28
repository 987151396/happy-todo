package com.happy.todo.lib_common.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;

import razerdp.basepopup.BasePopupWindow;

public abstract class BaseAppPopWindow extends BasePopupWindow {
    protected Activity activity;
    public BaseAppPopWindow(Context context) {
        super(context);
        this.activity = (Activity) context;
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    protected void backgroundAlpha(Activity context, float bgAlpha)
    {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        return null;
    }

    @Override
    public View initAnimaView() {
        return null;
    }


}
