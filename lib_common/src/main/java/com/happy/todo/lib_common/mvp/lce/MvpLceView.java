package com.happy.todo.lib_common.mvp.lce;

import androidx.annotation.UiThread;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * @作者: TwoSX
 * @时间: 2017/12/3 下午9:48
 * @描述:
 */
public interface MvpLceView<M> extends MvpView {

    /**
     * Display a loading view while loading data in background.
     * <b>The loading view must have the id = R.id.loadingView</b>
     *
     * @param pullToRefresh true, if pull-to-refresh has been invoked loading.
     */
    @UiThread
    void showLoading(boolean pullToRefresh);

    @UiThread
    void hideLoading(boolean pullToRefresh);

    /**
     * Show the content view.
     *
     */
    @UiThread
    void showContent();

    /**
     * Show the error view.
     *
     * @param e The Throwable that has caused this error
     * @param pullToRefresh true, if the exception was thrown during pull-to-refresh, otherwise
     * false.
     */
    @UiThread
    void showError(Throwable e, boolean pullToRefresh);

    @UiThread
    void hideError();

    void onErrorViewClicked();

    /**
     * 重新获取数据, pullToRefresh == 清除当前内容再加载, 或者显示下拉加载图标等行为
     * The data that should be displayed with {@link #showContent()}
     */
    @UiThread
    void setData(boolean pullToRefresh, M data);


    /**
     * 添加更多数据
     * @param data
     */
    @UiThread
    void addData(M data);

    /**
     * 处理事件，在该方法里调用presenter的初始化数据的方法
     * @param b 根据该参数处理是否显示下拉图标等事件
     */
    @UiThread
    void initDataEvent(boolean b);

}