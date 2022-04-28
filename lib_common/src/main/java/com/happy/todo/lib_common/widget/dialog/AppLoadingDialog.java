package com.happy.todo.lib_common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.image.GlideApp;

/**
 * @作者: TwoSX
 * @时间: 2017/12/7 下午2:25
 * @描述:
 */
public class AppLoadingDialog extends Dialog {

    private Builder mBuilder;

    AppLoadingDialog(Builder builder) {
        super(builder.getContext(), R.style.common_dialog_style);
        mBuilder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_app_loading);

        TextView tv_load = findViewById(R.id.tv_load);
        ImageView iv_load = findViewById(R.id.iv_load);

        if (mBuilder != null) {
            if (TextUtils.isEmpty(mBuilder.getTxt_load())) {
                tv_load.setVisibility(View.GONE);
            } else {
                tv_load.setVisibility(View.VISIBLE);
                tv_load.setText(mBuilder.getTxt_load());
            }

            setCancelable(mBuilder.isCancel());
            setCanceledOnTouchOutside(mBuilder.isCancel());
        }

//        AppImageLoader.create(iv_load)
//                .with(getContext())
//                .load(R.mipmap-xxhdpi.ic_loading_gif)
//                .execute();

        GlideApp.with(getContext()).asGif().load(R.mipmap.gif_loading).into(iv_load);

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
        private String txt_load;
        private boolean is_cancel;

        public boolean isCancel() {
            return is_cancel;
        }

        public Builder setIsCancel(boolean is_cancel) {
            this.is_cancel = is_cancel;
            return this;
        }

        public Builder(Context context) {
            mContext = context;
        }

        public Context getContext() {
            return mContext;
        }

        public String getTxt_load() {
            return txt_load;
        }

        public Builder setTxt(String txt_load) {
            this.txt_load = txt_load;
            return this;
        }
        public Builder setTxt() {
            this.txt_load = mContext.getString(R.string.loading_text);
            return this;
        }

        public Builder setTxt(int txt_load) {
            this.txt_load = mContext.getString(txt_load);
            return this;
        }


        public AppLoadingDialog create() {
            return new AppLoadingDialog(this);
        }
    }

}
