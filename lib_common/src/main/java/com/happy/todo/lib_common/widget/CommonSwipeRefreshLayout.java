package com.happy.todo.lib_common.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ResourceUtils;

/**
 * 通用的下拉刷新控件
 * @Deprecated 已经弃用的布局，改用SmartRefreshLayout
 * Created by Jaminchanks on 2018/6/12.
 */
@Deprecated
public class CommonSwipeRefreshLayout extends SwipeRefreshLayout {
    public CommonSwipeRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public CommonSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setColorSchemeColors(ResourceUtils.getColor(R.color.colorAccent));
    }
}
