package com.happy.todo.lib_common.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.happy.todo.lib_common.mvp.lce.MvpLceView;
import com.happy.todo.lib_common.utils.ViewUtil;

/**
 * @作者: TwoSX
 * @时间: 2017/12/3 下午10:09
 * @描述:
 */
public abstract class BaseMvpLceFragment<CV extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends BaseMvpFragment<V, P> implements MvpLceView<M> {

    protected CV contentView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contentView = createContentView();
    }

    /**
     * Create the content view. Default is {@code findViewById(R.id.contentView)}
     * @return the content view
     */
    @NonNull
    protected abstract CV createContentView();

    @Override
    public abstract void showLoading(boolean pullToRefresh);

    @Override
    public abstract void hideLoading();

    @Override
    public abstract void showError(Throwable e, boolean pullToRefresh);

    @Override
    public abstract void hideError();

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
    @Override
    public void onErrorViewClicked() {
        initDataEvent(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
    }}