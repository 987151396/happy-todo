package com.happy.todo.module_user.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.happy.todo.module_user.R;

/**
 * @功能描述：
 * @创建日期： 2018/07/28
 * @作者： dengkewu
 */

public class RegisterServerDialog extends AlertDialog {
    private LayoutInflater mInflater;
    private View inflate;

    public RegisterServerDialog(@NonNull Context context) {
        super(context);
    }

    public RegisterServerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getContext());
        inflate = mInflater.inflate(R.layout.user_dialog_register_hint, null);
        setContentView(inflate);

        setCancelable(false);// 设置点击屏幕Dialog不消失

        initView();
    }

    private void initView() {
        TextView tv_content = inflate.findViewById(R.id.tv_content);
        Button btn_not_agree = inflate.findViewById(R.id.btn_not_agree);
        Button btn_agree = inflate.findViewById(R.id.btn_agree);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        btn_not_agree.setOnClickListener(v->{
            dismiss();
        });
        btn_agree.setOnClickListener(v -> {
            if (onClickEnterListener!=null){
                onClickEnterListener.onClick();
            }
            dismiss();
        });
    }

    public interface onClickEnterListener{
        void onClick();
    }
    private onClickEnterListener onClickEnterListener;

    public void setOnClickEnterListener(RegisterServerDialog.onClickEnterListener onClickEnterListener) {
        this.onClickEnterListener = onClickEnterListener;
    }
}
