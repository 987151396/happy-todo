package com.happy.todo.module_user.mvp.login;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.happy.todo.lib_common.mvp.LoadingMvpView;

/**
 * 手机登录，邮箱登录，第三方登录 通用
 * Created by Jaminchanks on 2018/1/18.
 */

public interface UserLoginContract {

    interface View extends LoadingMvpView {
        /**
         * 第三方登录或者帐号登录的放回结果
         * @param isSuccess 是否成功
         * @param msg 附带消息说明
         */
        default void showManualLoginResult(boolean isSuccess, String msg,String code) {}

        default void getIPLocationResult(String ipLocation){}
    }

    interface Presenter extends MvpPresenter<View> {
        /**
         * 手动登录
         * @param account 帐号
         * @param password 密码
         * @param areaCode 国家/地区编码
         */
        void handleManualLogin(String account, String password, String areaCode);

        /**
         * 第三方登录
         * @param typeId 第三方帐号平台类型，具体参数见上方
         */
        void handle3rdPartLogin(String typeId);

        /**
         * IFree的登录情况稍微特殊...,需要跳转到本地安装的iapp应用，
         * 然后获取到对应的接口请求参数，再将这些请求参数向接口请求
         * @param openid openId
         * @param avatar 头像
         * @param nickName 昵称
         */
        void realHandleIFreeLogin(String openid, String avatar, String nickName,String email);

        void IAppLogin(String email,String password);

        /*获取 APPID 和 Token*/
        void getAppIDAndToken();

        void getIPLocation();
    }


}
