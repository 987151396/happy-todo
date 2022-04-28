package com.happy.todo.module_user.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.happy.todo.lib_common.utils.KeyboardUtil;
import com.happy.todo.lib_common.utils.fun.Action1;
import com.happy.todo.module_user.R;

/**
 * 用户信息输入弹出窗口
 * Created by Jaminchanks on 2018/8/13.
 */
public class InputContentDialog extends AlertDialog {
    private View mView;

    private Action1<String> mAction1;

    private AppCompatEditText mEditText;

    private String mTitle;

    private String mDefaultText;

    public InputContentDialog(@NonNull Context context, String title) {
        super(context);
        this.mTitle = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弹出软键盘
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        LayoutInflater mInflater = LayoutInflater.from(getContext());
        mView = mInflater.inflate(R.layout.user_dialog_profile_input, null);
        setContentView(mView);
        setCancelable(true);// 设置点击屏幕Dialog消失
        initView();
    }

    private void initView() {
        mEditText = mView.findViewById(R.id.et_input);

        TextView titleView = mView.findViewById(R.id.tv_title);
        titleView.setText(mTitle);

        Button btn_not_agree = mView.findViewById(R.id.btn_cancel);
        Button btn_agree = mView.findViewById(R.id.btn_confirm);
        btn_not_agree.setOnClickListener(v-> dismiss());

        btn_agree.setOnClickListener(v -> {
            if (mAction1 != null){
                Editable inputContent = mEditText.getText();
                if (!TextUtils.isEmpty(inputContent)) {
                    mAction1.invoke(inputContent.toString());
                }
            }
            dismiss();
        });

        mEditText.setText(mDefaultText);
    }


    public void setInputText(String text) {
        mDefaultText = text;

        if (mEditText != null) {
            mEditText.setText(mDefaultText);
        }
    }

    @Override
    public void show() {
        super.show();
        KeyboardUtil.showKeyboard(mEditText, true);
    }


    public void setOnConfirmListener(Action1<String> action1) {
        this.mAction1 = action1;
    }

}
