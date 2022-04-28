package com.happy.todo.module_user.mvp.password;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.happy.todo.lib_common.mvp.LoadingMvpView;

/**
 * Created by Jaminchanks on 2018/1/23.
 */

public interface UserResetPasswordContract {
    interface View extends LoadingMvpView {

        void showResetPasswordResult(boolean isSuccess, String msg);
    }

    interface Presenter extends MvpPresenter<View> {
        void resetPassword(String account, String newPassword, String encryptionCode, String token, String areaCode);
    }
}
