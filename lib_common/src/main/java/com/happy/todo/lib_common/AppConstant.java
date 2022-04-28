package com.happy.todo.lib_common;

import android.os.Environment;

import com.happy.todo.lib_common.utils.AppUtil;

import java.io.File;

/**
 * Description:
 * Author: 贰师兄
 * Date: 2017/10/12 下午3:01
 */
public class AppConstant {

    //默认外部存储位置的路径，统一管理
    public static final String APP_ROOT_STORAGE_PATH = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "mexico_oppo" + File.separator + AppUtil.getAppName();

    //跳转到iapp的scheme
    //public static final String IFREE_AUTH_URI = "ifreegroup://oauth_login_activity?appid=iapp_c9abb8bb1d392d7b78e7b884821c6552";


    //酒店、机票,跨模块值传递key
    public static final String EXTRA_PAY_DATA = "EXTRA_PAY_DATA"; //订单数据
    public static final String EXTRA_PAY_PRICE = "EXTRA_PAY_PRICE"; //订单价格
    public static final String EXTRA_HOTEL_ID = "EXTRA_HOTEL_ID"; //hotelID
    public static final String EXTRA_PAY_CURRENCY = "EXTRA_PAY_CURRENCY"; //订单价格类型
    public static final String EXTRA_PAY_EMAIL = "EXTRA_PAY_EMAIL"; //联系人email
    public static final String EXTRA_IS_PENDING = "EXTRA_IS_PENDING"; //是否代预定
    public static final String EXTRA_FORM_WEB = "EXTRA_FROM_WEB"; //是否从web跳转过来的
    public static final String EXTRA_FORM_ORDER_LIST = "EXTRA_FORM_ORDER_LIST"; //是否从订单列表跳转过来的
    public static final String EXTRA_FAILD_MSG = "EXTRA_FAILD_MSG"; //支付失败信息
    public static final String EXTRA_ORDER_SN = "EXTRA_ORDER_SN"; //订单sn
    public static final String EXTRA_ORDER_STATUS = "EXTRA_ORDER_STATUS"; //订单支付状态
    public static final String EXTRA_ORDER_TYPE = "EXTRA_ORDER_TYPE"; //1.酒店 2.飞机票
    public static final String RESULT_COUPON_MONEY = "RESULT_COUPON_MONEY"; //优惠券金额
    public static final String RESULT_COUPON_SN = "RESULT_COUPON_SN"; //优惠券sn
    public static final String EXTRA_ACCOUNT_TYPE = "EXTRA_ACCOUNT_TYPE"; //未登录下单的账户类型
    public static final String EXTRA_RATES_DATA = "extra_rates_data"; //房间数据

    public static final String EXTRA_SELECT_TAB = "SELECT_TAB"; //跳转到首页
    public static final String EXTRA_SELECT_TAB_DISCOVER = "UPTATE_DISCOVER"; //刷新discover
    public static final String EXTRA_SELECT_TAB_DISCOVER_CITY = "UPTATE_DISCOVER_CITY"; //刷新discover

    // 与通知模块传递的 key
    public static final String EXTRA_NOTIFICATION_NEW_SCHEDULE = "EXTRA_NOTIFICATION_NEW_SCHEDULE";         // 行程通知消息的红点
    public static final String EXTRA_NOTIFICATION_PROMOTION_MAX_ID = "EXTRA_NOTIFICATION_PROMOTION_MAX_ID"; // 活动广播最大活动id

//    权限
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 0x00051;

    //全局使用的变量
    public static boolean IS_REFRESH_SICOVER = false; //刷新discover
    public static String REFRESH_SICOVER_CITY_CODE = "HKG"; //刷新discover的城市

    //日期时间格式
    public static final String TIME_FORMAT = "yyy.MM.dd";

    //支付类型
    public static final int REQUEST_PAY_VISA = 0xf11;
    public static final int REQUEST_PAY_PAYPAL = 0xf12;

    public static final int NOTIFYCATION_YOUMENG = 0x0a;
    public static final int NOTIFYCATION_UDESK = 0x0b;

    //订单类型
    //类型1表示酒店，2表示机票,3是接送车，4是预售商品, 5补差支付
    public static final int ORDER_HOTEL = 0x01;
    public static final int ORDER_PLANE = 0x02;
    public static final int ORDER_CAR = 0x03;
    public static final int ORDER_SALE = 0x04;
    public static final int ORDER_PLAY = 0x05;
}
