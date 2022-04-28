package com.happy.todo.lib_common.mvp.base;

import android.app.Activity;

import com.happy.todo.lib_common.base.BaseActivity;
import com.happy.todo.lib_common.base.BaseFragment;
import com.happy.todo.lib_common.mvp.LoadingMvpView;
import com.happy.todo.lib_common.widget.TopAlerter;

/**
 * 错误消息提示
 * Created by Jaminchanks on 2018/2/2.
 */

public final class ToastType {
    public static final int NONE = 1 << 30; //无
    public static final int DEFAULT = 1 << 29; //普通toast
    public static final int TOP_ALTER = 1 << 28; //从顶部弹下的提醒
    public static final int CUSTOMER = 1 << 27; //自定义的方式

    public static void showToast(LoadingMvpView loadingView, @ErrorMsgType int toastType, String msg) {
        switch (toastType) {
            case NONE: //不处理
                break;

            case DEFAULT: //默认
                loadingView.showToast(msg);
                break;

            case TOP_ALTER: //顶部弹出
                Activity activity = null;
                if (loadingView instanceof BaseActivity) {
                    activity = (BaseActivity)loadingView;
                }
                if (loadingView instanceof BaseFragment) {
                    activity = ((BaseFragment) loadingView).getActivity();
                }
                TopAlerter.showError(activity, msg);
                break;

            case CUSTOMER://其他的自定义类型, 需要View有该方法的具体实现
                loadingView.showError(msg);
                break;

            default:
                break;
        }
    }
}
