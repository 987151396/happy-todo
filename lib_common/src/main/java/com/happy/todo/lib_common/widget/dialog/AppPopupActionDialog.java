package com.happy.todo.lib_common.widget.dialog;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.image.GlideApp;

import razerdp.basepopup.BasePopupWindow;

/**
 * @作者: TwoSX
 * @时间: 2018/1/12 下午5:47
 * @描述:
 */
public class AppPopupActionDialog extends BasePopupWindow {


    public AppPopupActionDialog(Context context) {
        super(context);
        setOnBeforeShowCallback((popupRootView, anchorView, hasShowAnima) -> {
            setBlurBackgroundEnable(true);
            return true;
        });

        setDismissWhenTouchOutside(false);
        findViewById(R.id.btn_dialog_ok).setOnClickListener(view -> {
            dismiss();
        });
    }

    public AppPopupActionDialog setContentTxt(CharSequence content) {
        TextView textView = (TextView) findViewById(R.id.tv_dialog_content);
        textView.setVisibility(View.VISIBLE);
        textView.setText(content);
        return this;
    }


    public AppPopupActionDialog setBtnOk(CharSequence txt, OnOkClickListener onOkClickListener) {
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

    public AppPopupActionDialog setActionImg(@DrawableRes int img) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_action);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(img);
        return this;
    }

    public AppPopupActionDialog setActionGif(@DrawableRes int img) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_action);
        imageView.setVisibility(View.VISIBLE);
        GlideApp.with(getContext()).asGif().load(img).into(new SimpleTarget<GifDrawable>() {
            @Override
            public void onResourceReady(@NonNull GifDrawable drawable, @Nullable Transition<? super GifDrawable> transition) {
                GifDrawable gifDrawable = drawable;
                gifDrawable.setLoopCount(1);
                imageView.setImageDrawable(drawable);
                gifDrawable.start();
            }
        });
        return this;
    }

    public TextView getContentView() {
        return (TextView) findViewById(R.id.tv_dialog_content);
    }

    public ImageView getActionView() {
        return (ImageView) findViewById(R.id.iv_action);
    }

    public Button getBtnOkView() {
        return (Button) findViewById(R.id.btn_dialog_ok);
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
        Animation alphaAnimation = new AlphaAnimation(in ? 0 : 1, in ? 1 : 0);
        alphaAnimation.setDuration(200);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        return alphaAnimation;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.dialog_app_popup_action);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_content);
    }

    public interface OnOkClickListener {
        void onClick(AppPopupActionDialog dialog);
    }

    public interface OnCancelClickListener {
        void onClick(AppPopupActionDialog dialog);
    }
}
