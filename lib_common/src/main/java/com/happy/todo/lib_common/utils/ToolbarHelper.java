package com.happy.todo.lib_common.utils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.happy.todo.lib_common.R;


/**
 * Toolbar 帮助类，避免每次都在initToolbar里写相同代码
 * Created by Jaminchanks on 2018-01-13.
 */

public class ToolbarHelper {

    /**
     * 设置默认状态栏
     * @param activity
     * @param toolbar
     * @param title
     */
    public static void setToolbarWithTitle(AppCompatActivity activity, Toolbar toolbar, String title) {
        if (toolbar != null && toolbar.getId() == R.id.toolbar) {
            activity.setSupportActionBar(toolbar);

            try {
                TextView tvTitle = toolbar.findViewById(R.id.tv_bar_title);
                if (tvTitle != null) {
                    tvTitle.setText(title);
                }
                ActionBar actionBar = activity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setDisplayShowHomeEnabled(true);
                    actionBar.setDisplayShowTitleEnabled(false);
                    toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
                }

            } catch (Exception e) {
                AppLogUtil.e("设置Toolbar事件处理出错");
                AppLogUtil.e(e.getMessage(), e);
            }


        }
    }
}
