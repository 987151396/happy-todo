package com.happy.todo.module_user.adapter.base;

import androidx.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 *
 * Created by Jaminchanks on 2018-01-13.
 */

public class BaseUserViewHolder extends BaseViewHolder {
    public BaseUserViewHolder(View view) {
        super(view);
    }

    public ImageView getImageView(@IdRes int resId) {
        return (ImageView) getView(resId);
    }

    public TextView getTextView(@IdRes int resId) {
        return (TextView) getView(resId);
    }


    public View getItemView() {
        return null;
    }
}
