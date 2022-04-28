package com.happy.todo.lib_common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.happy.todo.lib_common.R;


/**
 * @作者: TwoSX
 * @时间: 2017/12/7 下午2:25
 * @描述:
 */
public class AppAlertDialog extends Dialog {

    private Builder mBuilder;

    AppAlertDialog(Builder builder) {
        super(builder.getContext(), R.style.common_dialog_style);
        mBuilder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_app_alert);

        TextView tv_title = findViewById(R.id.tv_dialog_title);
        TextView tv_content = findViewById(R.id.tv_dialog_content);
        Button btn_cancel = findViewById(R.id.btn_dialog_cancel);
        Button btn_ok = findViewById(R.id.btn_dialog_ok);
        if (mBuilder != null) {
            if (!TextUtils.isEmpty(mBuilder.getTitle())) {
                tv_title.setText(mBuilder.getTitle());
                tv_title.setVisibility(View.VISIBLE);
            } else {
                tv_title.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mBuilder.getContent())) {
                tv_content.setText(mBuilder.getContent());
            }
            if (TextUtils.isEmpty(mBuilder.getTxt_cancel()) && mBuilder.getOnCancelClickListener
                    () == null) {
                btn_cancel.setVisibility(View.GONE);
            } else {
                btn_cancel.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(mBuilder.getTxt_cancel())) {
                    btn_cancel.setText(mBuilder.getTxt_cancel());
                }

                if (mBuilder.bg_cancel != 0) {
                    btn_cancel.setBackgroundResource(mBuilder.bg_cancel);
                }

                if (mBuilder.color_cancel != 0) {
                    btn_cancel.setTextColor(mBuilder.color_cancel);
                }

                btn_cancel.setOnClickListener(v -> {
                    if (mBuilder.getOnCancelClickListener() != null) {
                        mBuilder.getOnCancelClickListener().onCancel(AppAlertDialog.this);
                    }
                    cancel();
                });
            }

            if (TextUtils.isEmpty(mBuilder.getTxt_ok()) && mBuilder.getOnOkClickListener() ==
                    null) {
                btn_ok.setVisibility(View.GONE);
            } else {
                btn_cancel.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(mBuilder.getTxt_ok())) {
                    btn_ok.setText(mBuilder.getTxt_ok());
                }

                if (mBuilder.bg_ok != 0) {
                    btn_ok.setBackgroundResource(mBuilder.bg_ok);
                }

                if (mBuilder.color_ok != 0) {
                    btn_ok.setTextColor(mBuilder.color_ok);
                }

                btn_ok.setOnClickListener(v -> {
                    if (mBuilder.getOnOkClickListener() != null) {
                        mBuilder.getOnOkClickListener().onOk(AppAlertDialog.this);
                    }
                    cancel();
                });
            }

            setCancelable(mBuilder.isCancel());
            setCanceledOnTouchOutside(mBuilder.isCancel());
        }


        Window window = getWindow();
        WindowManager.LayoutParams lp = null;
        if (window != null) {
            lp = window.getAttributes();
            lp.gravity = Gravity.CENTER; // 居中位置
            window.setAttributes(lp);
        }
    }


    public static class Builder {
        private Context mContext;
        private String title;
        private String content;
        private String txt_cancel;
        private String txt_ok;
        @DrawableRes
        private int bg_ok;
        @DrawableRes
        private int bg_cancel;
        @ColorInt
        private int color_ok;
        @ColorInt
        private int color_cancel;

        public int getColor_ok() {
            return color_ok;
        }

        public Builder setColor_ok(int color_ok) {
            this.color_ok = color_ok;
            return this;
        }

        public int getColor_cancel() {
            return color_cancel;
        }

        public Builder setColor_cancel(int color_cancel) {
            this.color_cancel = color_cancel;
            return this;
        }

        private boolean is_cancel = true;
        private OnCancelClickListener mOnCancelClickListener;
        private OnOkClickListener mOnOkClickListener;

        public boolean isCancel() {
            return is_cancel;
        }

        public Builder setIs_cancel(boolean is_cancel) {
            this.is_cancel = is_cancel;
            return this;
        }

        public Builder(Context context) {
            mContext = context;
        }

        public Context getContext() {
            return mContext;
        }

        public Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public String getTxt_cancel() {
            return txt_cancel;
        }

        public Builder setTxt_cancel(String txt_cancel) {
            this.txt_cancel = txt_cancel;
            return this;
        }

        public String getTxt_ok() {
            return txt_ok;
        }

        public Builder setTxt_ok(String txt_ok) {
            this.txt_ok = txt_ok;
            return this;
        }

        public OnCancelClickListener getOnCancelClickListener() {
            return mOnCancelClickListener;
        }

        public Builder setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
            mOnCancelClickListener = onCancelClickListener;
            return this;
        }

        public OnOkClickListener getOnOkClickListener() {
            return mOnOkClickListener;
        }

        public Builder setOnOkClickListener(OnOkClickListener onOkClickListener) {
            mOnOkClickListener = onOkClickListener;
            return this;
        }

        public AppAlertDialog create() {
            return new AppAlertDialog(this);
        }
    }


    public static interface OnCancelClickListener {
        void onCancel(AppAlertDialog dialog);
    }


    public static interface OnOkClickListener {
        void onOk(AppAlertDialog dialog);
    }

}
