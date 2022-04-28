package com.happy.todo.module_user.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.happy.todo.lib_common.utils.fun.Action0;
import com.happy.todo.module_user.R;

/**
 * 提示内容dialog
 * Created by Jaminchanks on 2018/8/14.
 */
public class TipContentDialog extends AlertDialog {
    private View mView;

    private Action0 mAction0;

    private String mTitle;

    private String mMessage;

    public TipContentDialog(@NonNull Context context, String title, String message) {
        super(context);
        this.mTitle = title;
        this.mMessage = message;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater mInflater = LayoutInflater.from(getContext());
        mView = mInflater.inflate(R.layout.user_dialog_profile_input, null);
        setContentView(mView);
        setCancelable(true);// 设置点击屏幕Dialog消失
        initView();
    }

    private void initView() {
        TextView titleView = mView.findViewById(R.id.tv_title);
        titleView.setText(mTitle);

        AppCompatTextView contentTextView = mView.findViewById(R.id.et_input);
        contentTextView.setText(mMessage);

        Button btn_not_agree = mView.findViewById(R.id.btn_cancel);
        Button btn_agree = mView.findViewById(R.id.btn_confirm);
        btn_not_agree.setOnClickListener(v-> dismiss());

        btn_agree.setOnClickListener(v -> {
            if (mAction0 != null){
                mAction0.invoke();
            }
            dismiss();
        });
    }


    @Override
    public void show() {
        super.show();
    }

    public void setOnConfirmListener(Action0 action0) {
        this.mAction0 = action0;
    }

}
