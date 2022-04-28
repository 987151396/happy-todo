package com.happy.todo.lib_common.http.api;

/**
 * Description:
 * Author: 贰师兄
 * Date: 2017/10/12 下午2:54
 */

public class AppApi {
    public static final String KEY_API_HEADER_USER_TOKEN = "user-token";
    public static final String KEY_API_HEADER_USER_ID = "user-id";
    public static final String KEY_API_HEADER_CLIENT = "client";
    public static final String KEY_API_HEADER_CLIENT_LANG = "client-lang";

    public static final String API_SIGN_KEY = "f560c6aa6cb2b03120bc8a3d58778425";

    // 测试
    //public static final String API_BASE_URL = "http://apiebbly.ifreegroup.net:8000/";

    //正式
    //public static final String API_BASE_URL = "https://v1.ebbly-api.cn/";

    //拉取汇率的接口，多个地方用到
    public static final String API_CURRENCY = "/v2/rate";

    //上传友盟推送设备码
    public static String API_UPLOAD_TOKEN = "/updatedevice";

    //上传语言
    public static String API_UPLOAD_LANG = "/record/lang";

    //用户MOGO S 管理地址
    public static String URER_MOGOS = "v1_9/flowurl";

    //获取乘机人列表
    public static String API_PLANE_PASSENGERLIST = "passenger/list";

    //获取session（第一步获取，后面参数需要用到）
    public static String API_PLANE_GETCREATEL = "getcreate";

    //酒店列表
    public static String API_HOTEL_ZONEHOTELLIST="/v2_3_1/hotelList";
    //获取城市的区域和热门地标等
    public static String API_HOTEL_SCREEN="/v2_3/screen";
    //获取价格区间列表
    public static String API_HOTEL_PRICERANGE="/v1_9/priceRange";
    //酒店列表价格
    public static String API_HOTEL_ZONEHOTELLIST_PRICE="/v2_2/hotelPrice";
    //接送车搜索
    public static String API_CAR_ZONEHOTELLIST="v2_0/transfer/search";
    //修改用户信息
    public static String USER_UPDATE_PROGILE = "/upuserexinfo";
    //保存app版本信息
    public static String APP_CLIENT_VERSION = "/clientVersion";
    //获取用户资源收藏状态
    public static String API_PLAY_COLLECTSTATUS = "/v1/play/collectStatus";
    //增加／取消收藏 玩乐资源
    public static String API_PLAY_COLLECT = "/playCollect";
    /**
     * web链接管理
     */

    public final static String WEB_URL_INIT = "https://m.ebbly.com/index.html/init";//正式服修改密码
    //public final static String WEB_URL_INIT = "http://webapp.ebbly.ifreegroup.net:8000/init";//测试服修改密码

    public final static String WEB_URL_LOGIN = "https://m.ebbly.com/signup/invite"; // 邀新注册正式
//    public final static String WEB_URL_LOGIN = "http://webapp.ebbly.ifreegroup.net:8000/signup/invite"; // 邀新注册


    public final static String WEB_URL_FAQ = "https://webapp.ebbly.com/help"; // 正式服faq
    //public final static String WEB_URL_FAQ = "http://webapp.ebbly.ifreegroup.net:8000/help"; // 测试服faq

    public final static String WEB_URL_HOTEL_DETAIL = "https://m.ebbly.com/hotel/detail/"; // 酒店详情
    //public final static String WEB_URL_HOTEL_DETAIL = "http://webapp.ebbly.ifreegroup.net:8000"; // 酒店详情测试

    //获取外网IP地址
    public final static String GET_INTERNET_IP_ADDRESS = "http://ip.taobao.com";
    //public final static String GET_INTERNET_IP_ADDRESS = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";

}
