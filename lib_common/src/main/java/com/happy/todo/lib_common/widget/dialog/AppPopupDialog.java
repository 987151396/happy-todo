package com.happy.todo.lib_common.widget.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.base.BaseAppPopWindow;

/**
 * @作者: TwoSX
 * @时间: 2018/1/12 下午5:47
 * @描述: APP 背景虚化弹窗
 */
public class AppPopupDialog extends BaseAppPopWindow {


    public AppPopupDialog(Context context) {
        super(context);
        setOnBeforeShowCallback((popupRootView, anchorView, hasShowAnima) -> {
            backgroundAlpha(activity,0.7f);
            return true;
        });
        findViewById(R.id.btn_dialog_ok).setOnClickListener(view -> {
            dismiss();
        });
    }

    public AppPopupDialog setContentTxt(CharSequence content) {
        TextView textView = (TextView) findViewById(R.id.tv_dialog_content);
        textView.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        textView.setText(content);
        return this;
    }

    @SuppressLint("NewApi")
    public AppPopupDialog setContentTxtStyle(int style) {
        TextView textView = (TextView) findViewById(R.id.tv_dialog_content);
        textView.setTextAppearance(style);
        return this;
    }

    public AppPopupDialog setTitleTxt(CharSequence title) {
        TextView textView = (TextView) findViewById(R.id.tv_dialog_title);
        textView.setVisibility(View.VISIBLE);
        textView.setText(title);
        return this;
    }

    public AppPopupDialog setBtnOk(CharSequence txt, OnOkClickListener onOkClickListener) {
        Button btn_dialog_ok = (Button) findViewById(R.id.btn_dialog_ok);
        if (txt != null) {
            btn_dialog_ok.setText(txt);
        }
        btn_dialog_ok.setVisibility(View.VISIBLE);
        if (onOkClickListener != null) {
            btn_dialog_ok.setOnClickListener(view -> {
                onOkClickListener.onClick(this);
                dismiss();
            });
        }
        return this;
    }

    public AppPopupDialog setBtnCancel(CharSequence txt, OnCancelClickListener
            onCancelClickListener) {
        Button btn_dialog_cancel = (Button) findViewById(R.id.btn_dialog_cancel);
        if (txt != null) {
            btn_dialog_cancel.setText(txt);
        }
        btn_dialog_cancel.setVisibility(View.VISIBLE);
        if (onCancelClickListener != null) {
            btn_dialog_cancel.setOnClickListener(view -> {
                onCancelClickListener.onClick(this);
                dismiss();
            });
        }
        return this;
    }

    public TextView getContentView() {
        return (TextView) findViewById(R.id.tv_dialog_content);
    }

    public TextView getTitleView() {
        return (TextView) findViewById(R.id.tv_dialog_title);
    }

    public Button getBtnOkView() {
        return (Button) findViewById(R.id.btn_dialog_ok);
    }

    public Button getBtnCancelView() {
        return (Button) findViewById(R.id.btn_dialog_cancel);
    }

    @Override
    protected Animation initShowAnimation() {
        return getAlphaAnimation(true);
    }

    @Override
    protected Animation initExitAnimation() {
        return getAlphaAnimation(false);
    }


    private Animation getAlphaAnimation(boolean in) {
//        Animation alphaAnimation = new AlphaAnimation(in ? 0 : 1, in ? 1 : 0);
//        alphaAnimation.setDuration(200);
//        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        ScaleAnimation scaleAnimation;
        if (in){
            scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        }else{
            scaleAnimation  = new ScaleAnimation(1.0f, 1.0f, 1.0f,  0.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        }
        scaleAnimation.setDuration(200);

        return scaleAnimation;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        backgroundAlpha(activity,1f);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.dialog_app_popup);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_content);
    }

    public interface OnOkClickListener {
        void onClick(AppPopupDialog dialog);
    }

    public interface OnCancelClickListener {
        void onClick(AppPopupDialog dialog);
    }
}
