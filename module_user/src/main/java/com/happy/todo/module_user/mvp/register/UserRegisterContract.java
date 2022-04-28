package com.happy.todo.module_user.mvp.register;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.happy.todo.lib_common.mvp.LoadingMvpView;

import java.util.Map;

/**
 * Created by Jaminchanks on 2018/1/22.
 */

public interface UserRegisterContract {
    interface View extends LoadingMvpView {
        void showRegisterResult(boolean isSuccess, String msg,String code);

        void showSendVerifyCodeResult(boolean isSuccess, String msg);

    }

    interface Presenter extends MvpPresenter<UserRegisterContract.View> {
        void register(Map<String, String> params);

        void sendVerifyCode(String phoneNum, String areaCode);

        void sendEmailVerifyCode(String email); //发送验证码
    }
}
