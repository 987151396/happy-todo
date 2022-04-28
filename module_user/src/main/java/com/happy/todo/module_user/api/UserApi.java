package com.happy.todo.module_user.api;

/**
 * 用户模块相关接口
 * Created by Jaminchanks on 2018/1/22.
 */

public interface UserApi {
    String REGION_CODE_LIST = "/getcountry"; //获取地区码

    String SEND_VERIFY_CODE_REG = "/regsend"; //手机注册获取验证码

    String USER_REGISTER = "/v1_9_4/register"; //用户注册

    String USER_LOGIN = "/login"; //用户登录

    String USER_LOGIN_3RD_PART = "/v2_0/thirdPartyLogin"; //第三方登录

    String USER_LOGIN_APPID_TOKEN = "/getIAPPOauthToken"; //第三方登录

    String USER_BIND_3RD_PART = "/bindthirdparty"; //用户绑定第三方帐号

    String USER_UNBIND_3RD_PART = "/removethirdparty"; //解绑第三方帐号

    String USER_FORGET_PWD_PHONE = "/retsendmobcode"; //通过手机的方式找回密码

    String USER_FORGET_PWD_EMAIL = "/retsendemailcode"; //通过邮箱的方式找回密码

    String USER_FORGET_PWD_CHECK_CODE = "/checkretphonecode"; //忘记密码阶段，向服务器发送所收到的手机验证码

    String USER_MODIFY_PWD = "/upuserpasswd"; //修改密码

    String USER_FORGET_PWD_RESET = "/saveuserpasswd"; //重置密码

    String USER_PROFILE = "/getuserinfo"; //用户资料0

    //String USER_UPDATE_PROGILE = "/upuserexinfo"; //修改用户信息

    String USER_UPLOAD_AVATAR = "/upuseravatar"; //上传头像

    String USER_ORDER_LIST = "/api/order"; //获取用户订单列表，个人资料页最多显示5条

    String USER_COLLECTIONS = "/collectionList"; //获取收藏列表

    String USER_DISCOVER_COLLECT = "/user/discover_collect"; //dicover收藏列表

    String USER_COLLECTION_ACTION = "/user/collect"; //用户添加/取消收藏

    ///酒店（取消）收藏
    String API_HOTEL_COLLECTION = "/v1_9/collect";
    //discover收藏
    String DISCOVER_COLLECT = "discover/collect";
    //取消预售产品收藏
    String SALE_COLLECT = "/preProductCancelCollect";
    //增加／取消收藏 玩乐资源
    String PLAY_COLLECT = "playCollect";

    String COUPONS_CENTER = "/v1/coupon/coupons-center";//领券中心
    String CARD_BAG = "/v1/coupon/card-bag";//优惠券卡包列表
    String UNUSABLE_COUPONS = "/v1/coupon/unusable-coupons";//失效 & 过期 & 已使用 的优惠券列表

    String COUPONS_RESOURCE = "/v1/coupon/activity-page";//领取优惠券

    String USER_COUPONS = "v2_1_1/couponlist"; //获取用户优惠券

    String USER_ORDER_COUPONS = "v2_1_1/getOrderCouponList"; //下单页面 获取优惠券列表

    String USER_REDEEM_COUPONS = "/flow/receive"; //兑换优惠券

    String USER_COUPONS_USE = "/docoupon"; //订单购买页面选择优惠券列表


    String USER_BIND_MOBILE = "/bindmobile"; //绑定手机号码

    String USER_CHANGE_MOBILE = "/savemobile"; //修改手机

    //String USER_BIND_EMAIL = "/bindemail"; //绑定邮箱
    String USER_BIND_EMAIL = "/v2_1_2/bindemail"; //绑定邮箱

    String USER_CHANGE_EMAIL = "/v2_1_2/saveemail"; //修改邮箱

    String USER_SEND_EMAIL = "/sendemail"; //发送邮箱验证码

    String USER_VERIFY_MOBILE = "/verifymobile"; //修改手机前校验账密

    String USER_VERIFY_EMAIL = "/v2_1_2/verifyemail"; //修改邮箱前验证账密

    String USER_SEND_CODE_BIND_PHONE = "/sendbindmobcode"; //绑定手机之前获取手机验证码

    String USER_SEND_SAVE_MOBCODE = "/sendsavemobcode"; //发送修改手机验证码

    String USER_ORDER_SUMMARIZE = "/getorderall"; //个人中心界面订单汇总

    String USER_CONNTRYCODE = "/conntrycode"; //选择国家区号

    String USER_ORDER_COUNT = "/v4/orderStatistics";//获取订单统计`

    String USER_AUTH_IAPP = "/flow/authorization"; //iapp授权登录

    String URER_CHECK_BIND_IAPP_STATUS = "/oauth/check"; //检查用户是否已授权给iapp

    String URER_IAPP_LOGIN = "/oauth/getIappInfo"; //手动登录

    String URER_INVITATION_REWARDS = "/invitation/rewards"; //获取拉新数量


    // 添加与修改 乘机人
    String API_PLANE_PASSENGER_SAVE = "passenger/save";

    // 删除 乘机人
    String API_PLANE_PASSENGER_DELETE = "passenger/del";

    //保存紧急联系人
    String API_EMERGENCY_SAVE = "emergency/save";

}
