package com.happy.todo.module_main.ui.version;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.happy.todo.module_main.R;

/**
 * Created by Jaminchanks on 2018-09-01.
 */
public class UpdateDialog extends AlertDialog {
    private LayoutInflater mInflater;
    private View inflate;
    private CharSequence mUpdateText;

    private OnUpdateActionListener mListener;

    public UpdateDialog(@NonNull Context context, CharSequence content) {
        super(context);
        this.mUpdateText = content;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getContext());
        inflate = mInflater.inflate(R.layout.main_dialog_update, null);
        setContentView(inflate);
        
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        initView();
    }

    private void initView() {
        TextView tvContent = inflate.findViewById(R.id.tv_update_content);
        tvContent.setText(mUpdateText);

        Button btnIgnore = inflate.findViewById(R.id.btn_ignore);
        Button btnUpdate = inflate.findViewById(R.id.btn_update);

        btnIgnore.setOnClickListener(v->{
            dismiss();
            if (mListener!=null){
                mListener.onIgnore();
            }
        });
        btnUpdate.setOnClickListener(v -> {
            if (mListener!=null){
                mListener.onUpdate();
            }
            dismiss();
        });
    }


    public void setOnUpdateActionListener(OnUpdateActionListener listener) {
        this.mListener = listener;
    }


    public interface OnUpdateActionListener {
        void onIgnore();
        void onUpdate();
    }



}
