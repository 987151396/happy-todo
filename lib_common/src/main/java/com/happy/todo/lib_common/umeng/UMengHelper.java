/*
package com.ifreegroup.app.ebbly.lib_common.umeng;

import android.content.Context;

import com.ifreegroup.app.ebbly.lib_common.manage.LoginManage;
import com.ifreegroup.app.ebbly.lib_common.utils.AppLogUtil;
import com.ifreegroup.app.ebbly.lib_common.utils.AppUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
//import com.umeng.socialize.PlatformConfig;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareConfig;

*/
/**
 * @作者: TwoSX
 * @时间: 2017/11/29 上午10:02
 * @描述: 友盟推送的帮助类
 *//*

public class UMengHelper {
    private static final String TAG = "PushHelper";
    public static final String APP_KEY = "5a55892ff29d984987000072";
    public static final String APP_MESSAGE_SECRET = "cef046a2ee6154e7486673cf1bc5a5d2";

    private static UMengHelper sPushHelper;
    private static PushAgent sPushAgent;
    private static String sDeviceToken;

    public  PushAgent getsPushAgent() {
        return sPushAgent;
    }

    private UMengHelper() {
    }

    public static UMengHelper newInstance() {
        if (sPushHelper == null) {
            synchronized (UMengHelper.class) {
                if (sPushHelper == null) {
                    sPushHelper = new UMengHelper();
                }
            }
        }
        return sPushHelper;
    }

    */
/**
     * 初始化友盟，必须在初始化推送前进行
     *
     * @param context
     *//*

    public void initUment(Context context) {
        UMConfigure.setEncryptEnabled(true); // 设置日志加密
        UMConfigure.setLogEnabled(AppUtil.isDebug()); // 设置组件化的Log开关
        UMConfigure.init(context, APP_KEY, AppUtil.getChannel(), UMConfigure
                .DEVICE_TYPE_PHONE, APP_MESSAGE_SECRET);
        initPush(context);
//        initPlatform(context);
    }


    public void initPush(Context context) {
        AppLogUtil.d(TAG, "初始化友盟 " );
        if (sPushAgent == null) {
            sPushAgent = PushAgent.getInstance(context);
        }
        sPushAgent = PushAgent.getInstance(context);
        //因为改了applicationID ："com.ifreegroup.app.ebbly"
        sPushAgent.setResourcePackageName("com.ifreegroup.app.ebbly");
        //注册推送服务，每次调用register方法都会回调该接口
        sPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                AppLogUtil.d(TAG, "deviceToken: " + deviceToken);
                sDeviceToken = deviceToken;
                //注册成功会返回device token
                newInstance().uploadToken();//上传 Token
            }

            @Override
            public void onFailure(String s, String s1) {
                AppLogUtil.d(TAG, s + "---" + s1);
            }
        });
    }

    */
/**
     * 上传 token 到服务器
     *//*

    public void uploadToken() {
        if (LoginManage.isLogin()) {
            if (sPushAgent == null) {
                throw new ExceptionInInitializerError("先调用 initPush()");
            } else {
                // TODO: 2017/12/3 上传 token
            }
        }
    }

    public String getPushToken() {
        return sDeviceToken;
    }

}
*/
