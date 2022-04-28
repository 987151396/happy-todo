package com.happy.todo.lib_common.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;


import com.happy.todo.lib_common.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 退订政策
 *
 */
public class BottomPolicyDialog extends BasePopupWindow {

    private TextView tvContent;

    public BottomPolicyDialog(Context context) {
        super(context);
        setOnBeforeShowCallback((popupRootView, anchorView, hasShowAnima) -> {
            setBlurBackgroundEnable(true);
            return true;
        });

        initView();
    }

    private void initView() {
        findViewById(R.id.btn_clear).setOnClickListener(view -> dismiss());
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    public void setContent(String content){
        tvContent.setText(content);
    }
    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 200);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 200);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.hotel_dialog_bottom_policy);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_content);
    }

}
