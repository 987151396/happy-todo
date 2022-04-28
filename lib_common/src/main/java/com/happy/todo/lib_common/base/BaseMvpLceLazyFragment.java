package com.happy.todo.lib_common.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.happy.todo.lib_common.mvp.lce.MvpLceView;
import com.happy.todo.lib_common.utils.FragmentUserVisibleController;
import com.happy.todo.lib_common.utils.ViewUtil;

/**
 * @作者: TwoSX
 * @时间: 2017/12/4 上午12:16
 * @描述: lce 模式，并加上懒加载
 */
public abstract class BaseMvpLceLazyFragment<CV extends View, M, V extends MvpLceView<M>, P
        extends MvpPresenter<V>>
        extends BaseMvpFragment<V, P> implements MvpLceView<M>, FragmentUserVisibleController
        .UserVisibleCallback {

    protected CV contentView;

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

    public BaseMvpLceLazyFragment() {
        mFragmentUserVisibleController = new FragmentUserVisibleController(this, this);
    }


    @Override
    public void showLoading(boolean pullToRefresh){}

    @Override
    public void hideLoading(boolean pullToRefresh){}

    @Override
    public void showError(Throwable e, boolean pullToRefresh){}

    @Override
    public void hideError(){}

    @Override
    public void showContent() {
        animateContentViewIn();
    }

    /**
     * Called to animate from loading view to content view
     */

    protected void animateContentViewIn() {
        hideLoading();
        hideError();
        if (contentView.getVisibility() != View.VISIBLE) {
            ViewUtil.fadeIn(contentView, 200, null, true).start();
        }
    }


    /**
     * Called if the error view has been clicked. To disable clicking on the errorView use
     * <code>errorView.setClickable(false)</code>
     */
    public void onErrorViewClicked() {
        initDataEvent(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
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
        contentView = createContentView();
    }

    protected abstract CV createContentView();


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
        reloadData();
    }

    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    protected abstract int getLayoutId();


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
        //        Log.d("BaseLazyFragment", "invokeInResumeOrPause:"+ mPageName +"--"+
        // invokeInResumeOrPause);
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
    protected void reloadData() {
        initDataEvent(false);
    }


}