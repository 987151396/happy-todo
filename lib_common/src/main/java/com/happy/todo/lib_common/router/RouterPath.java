package com.happy.todo.lib_common.router;

/**
 * Note:
 * ARouter允许一个module中存在多个分组，
 * 但是不允许多个module中存在相同的分组，否则会导致映射文件冲突
 * 路径如 /path1/path2 和 /path1/path3 视为同个分组:path1
 *
 * @作者: TwoSX
 * @时间: 2017/12/4 上午11:19
 * @描述: 路由的路径
 */
public interface RouterPath {
    int NEED_LOGIN = 1; //该界面是否需要登录

    String SERIALIZATION = "/service/json"; //序列化相关

    //引导页
    String GUIDE_PAGE = "/guide/guide_page"; //引导页


    // main
    String MAIN = "/main/main_page";

    String COMMON_WEB = "/common/web";

    // 酒店
    String HOTEL_MAIN = "/hotel/main"; // fragment

    String HOTEL_LIST = "/hotel/list";

    String HOTEL_ORDER_PAY = "/hotel/order/pay"; //下单支付
    String HOTEL_DETAIL_BIG_IMAGE = "/hotel/big/image"; //图片详情



    // 订单

    String ORDER_HOTEL_PRICE_DETAIL = "/order/hotel_detail"; //酒店价格详情

    // 用户
    String USER_PROFILE = "/user/profile"; // 个人中心, fragment


    //用户中心
    String USER_SETTING = "/user/setting"; //设置

    String USER_LOGIN = "/user/login";
    String USER_FORGET_PASS = "/user/forget/pass";
}
