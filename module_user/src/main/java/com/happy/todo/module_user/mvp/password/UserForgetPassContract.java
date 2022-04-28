package com.happy.todo.module_user.mvp.password;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.happy.todo.lib_common.mvp.LoadingMvpView;

/**
 * Created by Jaminchanks on 2018/2/8.
 */

public interface UserForgetPassContract {

    interface View extends LoadingMvpView {

        /**
         * 获取验证码结果
         * @param usePhone
         * @param msg
         */
        void showSendVerifyCodeSuccess(boolean usePhone, String msg);

        void showSendVerifyCodeFaild(String msg,String code);

        void startSendPhoneVerifyCode(long remainTime);

        void endSendPhoneVerifyCode(String msg,String code);

        /**
         * 验证手机验证码结果
         * @param account 手机帐号
         * @param encryptionCode 加密编码
         * @param token 加密token
         */
        void showCheckMobileVerifyCodeSuccess(String account, String encryptionCode, String token);

        void showCheckMobileVerifyCodeError(String msg,String code);
        /**
         * 自动修改地区码
         * @param areaCode
         */
        void setAreaCode(String areaCode);
    }

    interface Presenter extends MvpPresenter<View>{

        /**
         * 自动获取区号并发送验证码
         * @param phoneNum
         */
        void autoSendVerifyCode(String phoneNum);

        /**
         * 发送手机验证码
         * @param isUsePhone
         * @param account
         */
        void sendVerifyCode(boolean isUsePhone, String account, String areaCode);

        /**
         * 将获取到的手机验证码发送至服务器以获取加密token等信息，用于下一步重置密码用
         * @param areaCode
         * @param phoneNumber
         * @param verifyCode
         */
        void checkMobileVerifyCode(String areaCode, String phoneNumber, String verifyCode);
    }

}
