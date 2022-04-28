package com.happy.todo.lib_common.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.happy.todo.lib_common.R;


/**
 * @功能描述：超时提示对话框
 * @创建日期： 2018/07/28
 * @作者： dengkewu
 */

public class TimeoutHintDialog extends AlertDialog {
    private LayoutInflater mInflater;
    private View inflate;
    private TextView tvContent;
    private Button mBtCancel;

    public TimeoutHintDialog(@NonNull Context context) {
        super(context);
    }

    public TimeoutHintDialog(@NonNull Context context, int themeResId) {
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
        inflate = mInflater.inflate(R.layout.plane_dialog_timeout_hint, null);
        setContentView(inflate);
        tvContent = inflate.findViewById(R.id.tv_content);
        inflate.findViewById(R.id.btn_enter).setOnClickListener(v -> {
            if (onClickEnterListener!=null){
                onClickEnterListener.onClick();
            }
            dismiss();
        });
        mBtCancel =  inflate.findViewById(R.id.btn_cancel);
        mBtCancel.setOnClickListener(v -> {
            if (onClickEnterListener!=null){
                onClickEnterListener.onCancel();
            }
            dismiss();
        });
        setCancelable(false);// 设置点击屏幕Dialog不消失

    }

    public interface onClickEnterListener{
        void onClick();
        default void onCancel(){}
    }
    private onClickEnterListener onClickEnterListener;

    public void setOnClickEnterListener(onClickEnterListener onClickEnterListener) {
        this.onClickEnterListener = onClickEnterListener;
    }

    public TimeoutHintDialog setContent(String content){
        tvContent.setText(content);
        return this;
    }

    public void setmBtCancelVisible(int isVisible){
        mBtCancel.setVisibility(isVisible);
    }
}
