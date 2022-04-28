package com.happy.todo.lib_common.utils;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 固定smoothScroll时间的linearLayoutManager
 */
public class FixScrollTimeLinearLayoutManager extends LinearLayoutManager {

    private int mSmoothScrollTime = 0;

    public FixScrollTimeLinearLayoutManager(Context context) {
        super(context);
    }

    public FixScrollTimeLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public FixScrollTimeLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

        final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected int calculateTimeForScrolling(int dx) {

                if (dx > 10000) {
                    dx = 10000;
                }

                return Math.max(mSmoothScrollTime, super.calculateTimeForScrolling(dx));
            }
        };

        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }


    /**
     * 设置最小滑动时间
     * @param time 单位 毫秒
     */
    public void setMinSmoothScrollTime(int time) {
        this.mSmoothScrollTime = time;
    }

}