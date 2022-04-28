package com.happy.todo.lib_common.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.happy.todo.lib_common.utils.FragmentUserVisibleController;

/**
 * @作者: TwoSX
 * @时间: 2017/12/3 下午9:04
 * @描述: 懒加载，适应多嵌套
 */

public abstract class BaseMvpLazyFragment<V extends MvpView, P extends MvpPresenter<V>> extends BaseMvpFragment<V,P>
        implements FragmentUserVisibleController
        .UserVisibleCallback {
    /**
     * The Is visible.
     */
    protected boolean isVisible;
    public boolean isPrepared;
    /**
     * The Is first.
     */
    public boolean isFirst = true;

    private FragmentUserVisibleController mFragmentUserVisibleController;

    public BaseMvpLazyFragment() {
        mFragmentUserVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mFragmentUserVisibleController.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mFragmentUserVisibleController.activityCreated();
        isPrepared = true;
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        mFragmentUserVisibleController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentUserVisibleController.pause();
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        isFirst = false;
        initData();
    }


    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        mFragmentUserVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isWaitingShowToUser() {
        return mFragmentUserVisibleController.isWaitingShowToUser();
    }

    /**
     * 当前 Fragment 是否对用户可见
     *
     * @return
     */
    @Override
    public boolean isVisibleToUser() {
        return mFragmentUserVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        //        Log.d("BaseLazyFragment", "isVisibleToUser:" + mPageName +"--"+ isVisibleToUser);
        //        Log.d("BaseLazyFragment", "invokeInResumeOrPause:"+ mPageName +"--"+ invokeInResumeOrPause);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }


    /**
     * 初始化数据
     */
    protected abstract void initData();

}
