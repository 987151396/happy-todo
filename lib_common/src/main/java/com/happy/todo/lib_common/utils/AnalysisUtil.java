/*
package com.ifreegroup.app.ebbly.lib_common.utils;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.ifreegroup.app.ebbly.lib_common.base.BaseApplication;
import com.tendcloud.tenddata.TCAgent;

*/
/**
 * @Describe：
 * @Date： 2018/11/30
 * @Author： dengkewu
 * @Contact：
 *//*

public class AnalysisUtil {

    private static AnalysisUtil mAnalysisUtil = null;
    public static Tracker mTracker;
    private static BaseApplication mApplication;

    public static final String DATA_ANALYSIS_COMMON_LABEL = "%s点击";
    //   首页数据统计
    public static final String DATA_ANALYSIS_HOME_LOCATION = "我的附近按钮";
    public static final String DATA_ANALYSIS_HOME_LOCATION_EDIT = "我的附近搜索框";
    public static final String DATA_ANALYSIS_HOME_HOTEL = "酒店的按钮点击";
    public static final String DATA_ANALYSIS_HOME_PLANE = "机票的按钮点击";
    public static final String DATA_ANALYSIS_HOME_PLANE_LOCATION = "目的地的点击";
    public static final String DATA_ANALYSIS_HOTEL_NAME = "酒店名称 : ";
    public static final String DATA_ANALYSIS_HOTEL_ID = "酒店ID : ";

    public static final String DATA_ANALYSIS_HOTEL_SORTING_CONDITION = "酒店搜索列表的排序条件";
    public static final String DATA_ANALYSIS_HOTEL_LOCATION_CONDITION = "酒店搜索列表的位置条件";
    public static final String DATA_ANALYSIS_BANNER_EVENT = "banner名称 : ";
    public static final String DATA_ANALYSIS_PROMOTION = "促销活动的名称 : ";


    public static final String USER_ID  = "用户ID";

    //首页机票模块统计
    public static final String DATA_ANALYSIS_PLANE_SORTING = "机票搜索结果-排序条件";
    public static final String DATA_ANALYSIS_PLANE_NUMBER_OF_TRANSFERS = "机票搜索结果-筛选条件-转机";
    public static final String DATA_ANALYSIS_PLANE_AIRLINE_NAME = "机票搜索结果-筛选条件-航司";

    public static final String DATA_ANALYSIS_PLANE_PLANE_SIGLE = "单程";
    public static final String DATA_ANALYSIS_PLANE_PLANE_RETRUN = "往返";
    public static final String DATA_ANALYSIS_PLANE_PLANE_BEGIN_CITY = "出发城市 : ";
    public static final String DATA_ANALYSIS_PLANE_PLANE_END_CITY = "返回城市 : ";

    //搜索页面统计
    public static final String DATA_ANALYSIS_SRATCH_LOCATION = "当前定位的点击";

    //酒店列表页面统计
    public static final String DATA_ANALYSIS_HOTEL_LIST_CURRENCY = "货币切换的选择";
    public static final String DATA_ANALYSIS_HOTEL_LIST_CHOSE = "单个酒店的点击";
    public static final String DATA_ANALYSIS_HOTEL_LIST_SORT = "排序按钮";
    public static final String DATA_ANALYSIS_HOTEL_LIST_FILTER = "过滤按钮";
    public static final String DATA_ANALYSIS_HOTEL_LIST_RETRUNY = "返回的点击";
    public static final String DATA_ANALYSIS_HOTEL_LIST_DATE = "房间数目更换的点击";

    //酒店详情页面统计
    public static final String DATA_ANALYSIS_HOTEL_DETAIL_DATE = "切换日期的点击";
    public static final String DATA_ANALYSIS_HOTEL_DETAIL_ROOM = "房间数目更换的点击";
    public static final String DATA_ANALYSIS_HOTEL_DETAIL_COLLECTION = "收藏酒店";

    //登陆注册页面统计
    public static final String DATA_ANALYSIS_USER_CREATE = "注册账户";
    public static final String DATA_ANALYSIS_USER_LOGIN = "登陆按钮";
    public static final String DATA_ANALYSIS_USER_OTHER_LOGIN = "第三方登录";

    //支付订单页面统计
    public static final String DATA_ANALYSIS_PAY_HOTEL = "酒店支付页面";
    public static final String DATA_ANALYSIS_PAY_ENTER = "确认支付的按钮点击";
    public static final String DATA_ANALYSIS_PAY_SUCCESS = "支付成功";
    public static final String DATA_ANALYSIS_PAY_ERROR = "支付失败";

    //“更多”页面统计
    public static final String DATA_ANALYSIS_MORE_EDIT = "个人信息编辑的点击";
    public static final String DATA_ANALYSIS_MORE_ALL_ORDER = "全部订单的点击";
    public static final String DATA_ANALYSIS_MORE_ORDER = "订单各项按钮的点击";
    public static final String LABEL_PLANE_MORE_ORDER1= "待确认";
    public static final String LABEL_PLANE_MORE_ORDER2= "已完成";
    public static final String LABEL_PLANE_MORE_ORDER3= "已取消";
    public static final String DATA_ANALYSIS_MORE_COUPON= "优惠券点击";
    public static final String DATA_ANALYSIS_MORE_COLLECTION= "收藏的点击";
    public static final String DATA_ANALYSIS_MORE_SETTING= "设置的点击";
    public static final String DATA_ANALYSIS_MORE_FEEDBACK= "反馈的点击";
    public static final String DATA_ANALYSIS_MORE_SURPPORT= "支持的点击";
    public static final String DATA_ANALYSIS_MORE_MOOGS= "MOGO S 的点击";

    // 机票列表页面统计
    public static final String TYPE_PLANE_DATE_TRANSFER = "机票列表日期切换";
    public static final String LABEL_PLANE_DATE_TRANSFER = "%s点击";        // 单程或往返
    public static final String TYPE_PLANE_CURRENCY_TRANSFER = "机票列表货币切换";
    public static final String LABEL_PLANE_CURRENCY_TRANSFER = "%s点击";
    public static final String TYPE_PLANE_BOOK_WAIT_TIME = "点击预订按钮跳转到机票详情页面的等待时间";
    public static final String LABEL_PLANE_BOOK_WAIT_TIME = "%s点击,时间戳：%s";

    // 机票订单页面统计
    public static final String TYPE_PLANE_FARE_INFO_COUPON = "机票优惠券的点击";
    public static final String LABEL_PLANE_FARE_INFO_COUPON = "%s点击";
    public static final String TYPE_PLANE_ADD_PASSENGER = "默认保存为乘机人的选择点击";
    public static final String LABEL_PLANE_ADD_PASSENGER = "%s点击";
    public static final String TYPE_PLANE_PAY = "支付按钮的点击";
    public static final String LABEL_PLANE_PAY = "%s点击";

    //发现频道
    public static final String DATA_ANALYSIS_DISCOVER_SWITCH_CITY = "切换城市";
    public static final String DATA_ANALYSIS_DISCOVER_CLICK_TOPIC = "点击主题";
    public static final String DATA_ANALYSIS_DISCOVER_CLICK_BUSINESS = "点击商家";
    public static final String DATA_ANALYSIS_DISCOVER_RECOMMENDED = "发现页面的推荐酒店";

    //流量优惠券领取页面
    public static final String DATA_ANALYSIS_COUPON_COLLECTION = "流量优惠券领取页面";

    public static final String TYPE_HOTEL = "酒店预定";
    public static final String TYPE_PLANE = "机票预定";
    public static final String TYPE_CAR = "接送车预定";
    public static final String TYPE_DISCOVER = "发现频道";
    public static final String TYPE_TRAFFIC = "领取流量";
    public static final String TYPE_ACCOUNT = "注册&登录&邀请好友流程";

    public static AnalysisUtil getInstance(BaseApplication baseApplication) {
        if (mAnalysisUtil == null) {
            synchronized (AnalysisUtil.class) {
                if (mAnalysisUtil == null) {
                    mApplication = baseApplication;
                    mAnalysisUtil = new AnalysisUtil(baseApplication);
                }
            }
        }
        return mAnalysisUtil;
    }


    private AnalysisUtil(BaseApplication application) {
        mTracker = application.getDefaultTracker();
    }

    private AnalysisUtil() {
    }

    public static void setAnalysis(String type, String label) {
        if (AppUtil.isDebug()) {
            return;
        }
        if (DateUtil.getCurrentTimeZone().toUpperCase().equals("Asia/Shanghai".toUpperCase())) {
            TCAgent.onEvent(mApplication.getApplicationContext(), type, label);
        } else {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(type)
                    .setAction(label)
                    .build());
        }
        AppLogUtil.e(String.format("[数据埋点] [%s] [%s]", type, label));
    }

    public static void setAnalysis(String type, String action, String label) {
        if (AppUtil.isDebug()) {
            return;
        }
        if (DateUtil.getCurrentTimeZone().toUpperCase().equals("Asia/Shanghai".toUpperCase())) {
            TCAgent.onEvent(mApplication.getApplicationContext(), type, label);
        } else {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(type)
                    .setAction(action)
                    .setLabel(label)
                    .build());
        }
        AppLogUtil.e(String.format("[数据埋点] [%s] [%s] [%s]", type, action,label));
    }

    */
/*首页酒店-酒店搜索*//*

    public static void setAnalysis_HotelSearch(String check_in, String check_out, String hotelName, String hotelID, String peopleNum){
        String str = "入住日期 : "+ check_in + "\t" + "退房日期 : " + check_out + "\t" + "酒店名称 : " + hotelName + "\t" + "酒店ID : " + hotelID + "\t" + "入住人数 : " + peopleNum;
        setAnalysis(TYPE_HOTEL,"首页酒店-酒店搜索",str);
    }
    */
/*首页酒店-城市搜索*//*

    public static void setAnalysis_CitySearch(String check_in, String check_out, String hotelName, String hotelID, String peopleNum){
        String str = "入住日期 : "+ check_in + "\t" + "退房日期 : " + check_out + "\t" + "酒店名称 : " + hotelName + "\t" + "酒店ID : " + hotelID + "\t" + "入住人数 : " + peopleNum;
        setAnalysis(TYPE_HOTEL,"首页酒店-城市搜索",str);
    }
    */
/*首页特价精选列表*//*

    public static void setAnalysis_HotelSpecialList(String city, String hotelName, String hotelID){
        String str = "酒店所属城市 : "+ city + "\t" + "酒店名称 : " + hotelName + "\t" + "酒店ID : " + hotelID;
        setAnalysis(TYPE_HOTEL,"首页特价精选列表",str);
    }
    */
/*预订房型*//*

    public static void setAnalysis_BookRoom(String room, String hotelName, String hotelID, String roomType){
        String str = "选择房型 : "+ room + "\t" + "酒店名称 : " + hotelName + "\t" + "酒店ID : " + hotelID + "\t" + "房型类型 : " + roomType;
        setAnalysis(TYPE_HOTEL,"预订房型",str);
    }
    */
/*分享酒店*//*

    public static void setAnalysis_ShareHotels(String hotelName, String hotelID, String sharing){
        String str = "酒店名称 : " + hotelName + "\t" + "酒店ID : " + hotelID + "\t" + "分享平台 : " + sharing;
        setAnalysis(TYPE_HOTEL,"分享酒店",str);
    }
    */
/*酒店订单详情填写*//*

    public static void setAnalysis_HotelOrders(String hotelName, String hotelID, String orderCode, String userID, String userInfo, String special, String phone, String eml){
        String str = "酒店名称 : " + hotelName + "\t" + "酒店ID : " + hotelID + "\t" + "订单号 : " + orderCode + "\t" + "用户ID : " + userID
                + "\t" + "入住人信息 : " + userInfo + "\t" + "特殊要求 : " + special + "\t" + "联系人电话 : " + phone + "\t" + "联系人邮箱 : " + eml;
        setAnalysis(TYPE_HOTEL,"酒店订单详情填写",str);
    }
    */
/*支付*//*

    public static void setAnalysis_Pay(String payType, String hotelName, String hotelID, String orderState, String orderCode, String userID, String payMoney){
        String str = "支付方式 : " + payType + "\t" + "酒店名称 : " + hotelName + "\t" + "酒店ID : " + hotelID + "\t" + "订单状态 : " + orderState + "\t" +
                "订单号 : " + orderCode + "\t" + "用户ID : " + userID + "\t" + "支付金额 : " + payMoney;
        setAnalysis(TYPE_HOTEL,"酒店支付",str);
    }

    */
/*机票*//*

    */
/*首页机票搜索*//*

    public static void setAnalysis_PlaneSearch(String departure_city, String reach_city, String way, String goDate, String peopleNum, String selectCabin){
        String str = "出发城市 : "+ departure_city + "\t" + "到达城市 : " + reach_city + "\t" + "单程或往返 : " + way + "\t" +
                "出行日期 : " + goDate + "\t" + "乘机人数 : " + peopleNum + "\t" + "选择舱位 : " + selectCabin;
        setAnalysis(TYPE_PLANE,"首页机票搜索",str);
    }
    */
/*机票订单详情信息*//*

    public static void setAnalysis_PlaneOrders(String way, String flight_number, String departure_city, String reach_city, String orderCode, String userID, String userName, String phone, String eml){
        String str = "单程或往返 : " + way + "\t" + "航班号 : " + flight_number + "\t" + "出发城市 : " + departure_city + "\t" + "到达城市 : " + reach_city + "\t" +
                "用户ID : " + userID + "\t" + "订单号 : " + orderCode + "\t" + "乘机人姓名 : " + userName + "\t" +  "联系人电话 : " + phone + "\t" + "联系人邮箱 : " + eml;
        setAnalysis(TYPE_PLANE,"机票订单详情信息",str);
    }
    */
/*机票支付*//*

    public static void setAnalysis_PlanePay(String pay, String flight_number, String departure_city, String reach_city,String orderState, String orderCode, String userID, String payMoney){
        String str = "支付方式 : " + pay + "\t" + "航班号 : " + flight_number + "\t" + "出发城市 : " + departure_city + "\t" + "到达城市 : " + reach_city + "\t" +
                "订单状态 ：" + orderState + "\t" + "订单号 : " + orderCode + "\t" + "用户ID : " + userID + "\t" + "支付金额 : " + payMoney;
        setAnalysis(TYPE_PLANE,"机票支付",str);
    }

    //接送车
    */
/*接送车搜索*//*

    public static void setAnalysis_CarSearch(String pick_up, String destination, String boardingTime, String people){
        String str = "上车地点 : " + pick_up + "\t" + "目的地 : " + destination + "\t" + "上车时间 : " + boardingTime + "\t" + "乘车人数 ：" + people;
        setAnalysis(TYPE_CAR,"接送车搜索",str);
    }
    */
/*预订接送车*//*

    public static void setAnalysis_BookingCar(String pick_up, String destination, String carName, String MaxPeople){
        String str = "上车地点 : " + pick_up + "\t" + "目的地 : " + destination + "\t" + "车辆名称 ：" + carName + "\t" + "最多乘车人数 ：" + MaxPeople;
        setAnalysis(TYPE_CAR,"预订接送车",str);
    }
    */
/*接送车订单详情填写*//*

    public static void setAnalysis_BookingCarOrder(String pick_up, String destination, String userInfo, String eml, String phone, String sex, String flightNumber_shipNumber, String orderCode, String userID){
        String str = "上车地点 : " + pick_up + "\t" + "目的地 : " + destination + "\t" + "乘车人信息 ：" + userInfo + "\t" + "联系人邮箱 ：" + eml + "\t" + "联系人手机 : " + phone + "\t" +
                "男or女 : " + sex + "\t" + "航班号/船号 : " + flightNumber_shipNumber + "\t" + "订单号 : " + orderCode + "\t" + "用户ID : " + userID;
        setAnalysis(TYPE_CAR,"接送车订单详情填写",str);
    }
    */
/*接送车支付*//*

    public static void setAnalysis_CarPay(String payType, String pick_up, String destination, String orderState, String orderCode, String userID, String payMoney){
        String str = "支付方式 : " + payType + "\t" + "上车地点 : " + pick_up + "\t" + "目的地 : " + destination + "\t" + "订单状态 : " + orderState + "\t" +
                "订单号 : " + orderCode + "\t" + "用户ID : " + userID + "\t" + "支付金额 : " + payMoney;
        setAnalysis(TYPE_CAR,"接送车支付",str);
    }

    */
/*DisCover*//*

    */
/*分享商家*//*

    public static void setAnalysis_ShareMerchan(String merchantName, String merchantID, String SharingPlatform){
        String str = "商家名称 : " + merchantName + "\t" + "商家ID : " + merchantID + "\t" + "分享平台 : " + SharingPlatform;
        setAnalysis(TYPE_DISCOVER,"分享商家",str);
    }
    */
/*兑换流量券*//*

    public static void setAnalysis_FlowCoupons(String flow, String date, String userID, String iccID){
        String str = "流量券种类 : " + flow + "\t" + "兑换日期 : " + date + "\t" + "用户ID : " + userID + "\t" + "MOGOS卡的ICCID : " + iccID;
        setAnalysis(TYPE_TRAFFIC,"兑换流量券",str);
    }

    */
/*注册*//*

    public static void setAnalysis_register(String name, String eml_phone, String userID){
        String str = "姓名 : " + name + "\t" + "邮箱/手机 : " + eml_phone + "\t" + "用户ID : " + userID;
        setAnalysis(TYPE_ACCOUNT,"注册",str);
    }
    */
/*账号登录*//*

    public static void setAnalysis_accountLogin(String name, String eml_phone, String userID){
        String str = "姓名 : " + name + "\t" + "邮箱/手机 : " + eml_phone + "\t" + "用户ID : " + userID;
        setAnalysis(TYPE_ACCOUNT,"账号登录",str);
    }

}
*/
