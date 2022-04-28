package com.happy.todo.lib_common.mvp;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * 需要处理异步任务的mvp
 * Created by Jaminchanks on 2018/1/18.
 */

public interface LoadingMvpView extends MvpView{
    void showLoading(String msg);

    void hideLoading();

    void showToast(String str);

    /**
     * 自定义的错误提示
     * @param msg 文字提示
     */
    default void showError(String msg){
        throw new RuntimeException("自定义的错误显示需要重写该方法:showError(String)");
    }
}
