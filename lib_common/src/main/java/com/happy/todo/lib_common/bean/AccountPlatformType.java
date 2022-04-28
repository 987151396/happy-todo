package com.happy.todo.lib_common.bean;

/**
 * 帐号平台类型
 * Created by Jaminchanks on 2018/2/6.
 */

public interface AccountPlatformType {
    /**
     * 第三方平台类型,1为用户名，2为微信，3为facebook ,默认就是1（用户名），
     * 此参数和openid一起使用，主要作用是用户在使用第三方平台登录时，
     * 如果用户的第三方平台id未绑定系统用户ID时，跳转到登录页面时使用
     */
    String TYPE_ACCOUNT = "1"; //用户名登录
    String TYPE_WECHAT = "2"; //微信登录
    String TYPE_FACEBOOK = "3"; //facebook登录
    String TYPE_IFREE = "4"; //ifree登录

}
