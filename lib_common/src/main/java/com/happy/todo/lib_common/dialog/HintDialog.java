package com.happy.todo.lib_common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ScreenUtil;
import com.happy.todo.lib_common.utils.SizeUtil;


/**
 * @功能描述：普通提示对话框(两个按钮)
 * @创建日期： 2018/10/25
 * @作者： dengkewu
 */

public class HintDialog extends Dialog {
    private LayoutInflater mInflater;
    private View inflate;
    private TextView tvContent;
    private TextView tvTitle;
    private Button btnNotAgree;
    private Button btnAgree;
    private LinearLayout ll_dialog_parent;

    public HintDialog(@NonNull Context context) {
        super(context);
    }

    public HintDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        // 去除四角黑色背景
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置周围的暗色系数
        params.dimAmount = 0.5f;
        window.setAttributes(params);


        mInflater = LayoutInflater.from(getContext());
        inflate = mInflater.inflate(R.layout.dialog_hint, null);
        setContentView(inflate);
        tvContent = inflate.findViewById(R.id.tv_content);
        tvTitle = inflate.findViewById(R.id.tv_title);
        btnNotAgree = inflate.findViewById(R.id.btn_not_agree);
        btnAgree = inflate.findViewById(R.id.btn_agree);
        ll_dialog_parent = inflate.findViewById(R.id.ll_dialog_parent);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_dialog_parent.getLayoutParams();
        layoutParams.width=ScreenUtil.getScreenWidth()-SizeUtil.dp2px(40);
        ll_dialog_parent.setLayoutParams(layoutParams);
        btnAgree.setOnClickListener(v -> {
            alertDialogExitAnim();

            if (onClickEnterListener != null) {
                onClickEnterListener.onRight();
            }
        });
        btnNotAgree.setOnClickListener(v ->{
            alertDialogExitAnim();
            if (onClickEnterListener != null) {
                onClickEnterListener.onLeft();
            }
        });
        //进入动画是伪代码
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(200);
        ll_dialog_parent.startAnimation(scaleAnimation);

//        setCancelable(false);// 设置点击屏幕Dialog不消失
    }

    public HintDialog setContent(String title, String content) {
        if (tvTitle == null) {
            return this;
        }
        tvTitle.setText(title);
        tvContent.setText(content);
        return this;
    }
    public HintDialog setGoneTitle(){
        tvTitle.setVisibility(View.GONE);
        return this;
    }

    /**
     * 第一个参数：同意按钮
     * @return
     */
    public HintDialog setBtnContent(String right,String left) {
        if (btnAgree != null) {
//            if (agress.length == 0) {
//                btnAgree.setVisibility(View.VISIBLE);
//                btnNotAgree.setVisibility(View.GONE);
//                btnAgree.setText(content[0]);
//            }else{
                btnAgree.setVisibility(View.VISIBLE);
                btnNotAgree.setVisibility(View.VISIBLE);
                btnAgree.setText(right);
                btnNotAgree.setText(left);
//            }
        }
        return this;
    }

    public interface onClickEnterListener {
        void onRight();
        void onLeft();
    }

    private onClickEnterListener onClickEnterListener;

    public void setOnClickListener(onClickEnterListener onClickEnterListener) {
        this.onClickEnterListener = onClickEnterListener;
    }


    private void alertDialogExitAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f,  0.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);

        scaleAnimation.setDuration(200);
        ll_dialog_parent.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                HintDialog.this.dismiss();
            }
        });
    }

}
