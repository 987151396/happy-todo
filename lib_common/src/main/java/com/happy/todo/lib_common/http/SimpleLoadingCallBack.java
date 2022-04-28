package com.happy.todo.lib_common.http;

import androidx.annotation.CallSuper;

import com.happy.todo.lib_common.mvp.LoadingMvpView;
import com.happy.todo.lib_common.mvp.base.ErrorMsgType;
import com.happy.todo.lib_common.mvp.base.ToastType;
import com.happy.todo.lib_http.exception.ApiException;


/**
 * 带有loading, 且自动弹出错误信息的回调
 * Created by Jaminchanks on 2018/1/22.
 */

public abstract class SimpleLoadingCallBack<T> extends BaseAppCallBack<T> {
    private LoadingMvpView mView;
    private boolean mIsShowLoading = true;

    private int mToastType = ToastType.DEFAULT; //弹出错误提示的样式

    public SimpleLoadingCallBack(LoadingMvpView view) {
        this(view, true, ToastType.DEFAULT);
    }

    public SimpleLoadingCallBack(LoadingMvpView view, boolean showLoading) {
        this(view, showLoading, ToastType.DEFAULT);
    }

    public SimpleLoadingCallBack(LoadingMvpView view, @ErrorMsgType int toastType) {
        this(view, true, toastType);
    }


    public SimpleLoadingCallBack(LoadingMvpView view, boolean showLoading, @ErrorMsgType int toastType) {
        this.mView = view;
        this.mIsShowLoading = showLoading;
        this.mToastType = toastType;
    }

    public SimpleLoadingCallBack(LoadingMvpView view, boolean showLoading, boolean showErrorToast) {
        this(view, showLoading, ToastType.NONE);
        mIsShowLoading = showErrorToast;
    }

    @Override
    public void onStart() {
        if (mIsShowLoading && mView != null)
            mView.showLoading("");
    }

    @Override
    public void onCompleted() {
        if (mIsShowLoading && mView != null)
            mView.hideLoading();
    }

    @Override
    public abstract void onSuccess(T result);

    @Override
    public void onError(ApiException e) {
        if (mIsShowLoading && mView != null)
            mView.hideLoading();

        // 解决Rxjava2 中 onNext 不能传 Null 的问题
        if (shouldHandleBySuccess(e)) {
            return;
        }

        if (isTokenInValid(e)) {
            handleTokenInValid();
            return;
        }

        onFailure(e);
    }


    /**
     * super()方法必须被调用，
     * 当某个if分支情况不需要弹出错误信息时，在其他分支中调用super(e)方法即可。
     * 如：
     * if(e.getCode() == 1) {
     * doSomething()；
     * return;
     * } else {
     * super.onFailure(e)
     * }
     *
     * @param e
     */
    @CallSuper
    @Override
    public void onFailure(ApiException e) {
        if (mView != null)
            ToastType.showToast(mView, mToastType, e.getMessage());
    }


    public void setShowErrorMsgType(@ErrorMsgType int type) {
        this.mToastType = type;
    }

}
