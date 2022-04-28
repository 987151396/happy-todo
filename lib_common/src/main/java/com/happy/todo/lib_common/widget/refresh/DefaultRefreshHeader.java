package com.happy.todo.lib_common.widget.refresh;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.happy.todo.lib_common.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.internal.InternalClassics;

/**
 * 默认下拉刷新头
 * Created by Jaminchanks on 2018/8/29.
 */
public class DefaultRefreshHeader extends InternalClassics<ClassicsHeader> implements RefreshHeader {

    private LottieAnimationView mIvDrag;
    private LottieAnimationView mIvRelease;

    private boolean mIsAfterRelease = false;

    public DefaultRefreshHeader(Context context) {
        this(context, null, 0);
    }

    public DefaultRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header, this);
        mIvDrag = rootView.findViewById(R.id.iv_refresh);
        mIvRelease = rootView.findViewById(R.id.iv_release);
        setSpinnerStyle(SpinnerStyle.Scale);
    }



    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight);
        if (isDragging && !mIsAfterRelease) {
            mIvRelease.setVisibility(GONE);
            mIvDrag.setVisibility(VISIBLE);
        } else {
            if (!isDragging && mIsAfterRelease) {
                mRefreshKernel.getRefreshLayout().getLayout().setEnabled(false);
            }
        }

        mIvDrag.setProgress(Math.min(percent, 1));
    }


    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onReleased(refreshLayout, height, maxDragHeight);
        mIvDrag.pauseAnimation();
        mIvDrag.setVisibility(GONE);
        mIvRelease.setVisibility(VISIBLE);
        mIvRelease.setRepeatCount(LottieDrawable.INFINITE);
        mIvRelease.playAnimation();

        mIsAfterRelease = true;
    }


    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mIvDrag.pauseAnimation();
        mIvRelease.pauseAnimation();

//        mIvDrag.setVisibility(GONE);
//        mIvRelease.setVisibility(GONE);

        mRefreshKernel.getRefreshLayout().getLayout().setEnabled(true);

        mIsAfterRelease = false;
        return super.onFinish(refreshLayout, success);
    }


}

