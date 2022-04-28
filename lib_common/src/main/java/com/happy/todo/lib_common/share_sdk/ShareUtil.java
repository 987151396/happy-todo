/*
package com.ifreegroup.app.ebbly.lib_common.share_sdk;

import android.content.Context;
import android.widget.Toast;


import com.ifreegroup.app.ebbly.lib_common.R;
import com.ifreegroup.app.ebbly.lib_common.manage.LoginManage;
import com.ifreegroup.app.ebbly.lib_common.utils.AppLogUtil;
import com.ifreegroup.app.ebbly.lib_common.utils.ResourceUtils;
import com.ifreegroup.app.ebbly.lib_common.utils.ToastUtil;

import java.util.HashMap;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.line.Line;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.whatsapp.WhatsApp;


*/
/**
 * @author: twosx
 * @description: 分享工具类
 * @projectName: com.ifreegroup.iapp.utils
 * @date: 2017/3/14
 * @time: 上午10:20
 *//*


public class ShareUtil {
    private PlatformActionListener listener;

    public final static String default_title = ResourceUtils.getString(R.string.user_share_title); // 默认内容
    public final static String default_content = ResourceUtils.getString(R.string.user_share_content); // 默认内容
    public final static String default_url = "https://www.ebbly.com/download.html"; // 默认链接
    private static String url = "https://ifreegroup.com";//点击跳转的地址

    private static ShareUtil sShareUtil;

    public ShareUtil() {
        listener = new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                ToastUtil.showShort(ResourceUtils.getString(R.string.share_dialog_success));
                PlatformDb db = platform.getDb();

                AppLogUtil.OY_D("分享成功===》" + db.getUserName());

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
//                Toast.makeText(context, context.getResources().getString(R.string.share_dialog_failed), Toast.LENGTH_SHORT).show();
                AppLogUtil.OY_D("分享失败===》" + throwable.getMessage());
                ToastUtil.showShort(throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                AppLogUtil.OY_D("分享取消===》" + platform.getName());

//                Toast.makeText(context, context.getResources().getString(R.string.share_dialog_dismiss), Toast.LENGTH_SHORT).show();
            }
        };


    }

    public static ShareUtil newInstance() {
        if (sShareUtil == null) {
            synchronized (ShareUtil.class) {
                if (sShareUtil == null) {
                    sShareUtil = new ShareUtil();
                }
            }
        }
        if (LoginManage.isLogin()) {
//            if (UserUtil.newInstance().getUserInfoBean() != null) {
//                url = TextUtils.isEmpty(UserUtil.newInstance().getUserInfoBean().getShare_url()) ? "http://ifreegroup.com" : UserUtil.newInstance().getUserInfoBean().getShare_url();
//            }
        }

        return sShareUtil;
    }


    */
/**
     * 分享到QQ
     *//*

    public void share2QQ(String default_title,String default_content,String url,String logoUrl) {
        if (url != null) {
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setTitleUrl(url);//需要跳转的链接
            sp.setTitle(default_title);
            sp.setText(default_content);
            sp.setImageUrl(logoUrl);
            try {
                Platform platform = ShareSDK.getPlatform(QQ.NAME);
                platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 分享到facebook
     *//*

    public void share2FaceBook(String default_title,String default_content,String url,String logoUrl) {
        if (url != null) {
            url +="&utm_source=1";
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setText(default_content);
            sp.setUrl(url);
            sp.setImageUrl(logoUrl);
            try {
                Platform platform = ShareSDK.getPlatform(Facebook.NAME);
                platform.SSOSetting(false);
                //        platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 分享到新浪微博
     *//*

    public void share2WeiBo(String default_title,String default_content,String url,String logoUrl) {
        if (url != null) {
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setText(default_content + " - " + url);
            sp.setUrl(url);
            sp.setImageUrl(logoUrl);
            try {
                Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                platform.SSOSetting(false);
                platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("url", "https://t3.ftcdn.net/jpg/02/01/25/00/240_F_201250053_xMFe9Hax6w01gOiinRLEPX0Wt1zGCzYz.jpg");
//            jsonObject.put("width", 120);
//            jsonObject.put("height", 120);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
//        Platform.ShareParams sp = new Platform.ShareParams();
//        sp.setText("MobSDK 只为最优质的服务");
//        sp.setLcCreateAt("2019-01-24");
//        sp.setLcDisplayName("Mob-全球领先的第三方服务商");
//        sp.setLcImage(jsonObject);
//        sp.setLcSummary("不止是SDK");
//        sp.setLcUrl(url);
//        sp.setLcObjectType("webpage");
//        platform.setPlatformActionListener(listener);
//        platform.share(sp);
    }

    */
/**
     * 分享到Email
     *//*

*/
/*    public void share2Email() {
        if (url != null) {
            initShareText();

            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setTitle(title);
            sp.setText(content + ": " + url);
            try {
                Platform platform = ShareSDK.getPlatform(Email.NAME);
                platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {

            }

        }
    }*//*


    */
/**
     * 分享到Line
     *//*

    public void share2Line(String default_title,String default_content,String url) {
        if (url != null) {
            url +="&utm_source=0";
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setText(default_content + " - " + url);

            try {
                Platform platform = ShareSDK.getPlatform(Line.NAME);
                platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 分享到微信
     *//*

    public void share2WeChat(String default_title,String default_content,String url,String logoUrl) {
        if (url != null) {
            url +="&utm_source=3";
            AppLogUtil.OY_D("url : " + url);
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle(default_title);
            sp.setText(default_content);
            sp.setUrl(url);//需要跳转的链接
                    sp.setImageUrl(logoUrl);//图片链接，可以换成我们的logo

            try {
                Platform platform = ShareSDK.getPlatform(Wechat.NAME);
                platform.SSOSetting(false);
                platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 分享到微信朋友圈
     *//*

    public void share2WechatMoments(String default_title,String default_content,String url,String logoUrl) {
        if (url != null) {
            url +="&utm_source=3";
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle(default_title);
            sp.setText(default_content + " - " + url);
            sp.setUrl(url);//需要跳转的链接
            sp.setImageUrl(logoUrl);
//            sp.setImagePath(ResourceUtils.getDrawable(R.drawable.ssdk_logo));//图片链接，可以换成我们的logo

            try {
                Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
                platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 分享到WhatApps
     *//*

    public void share2WhatAPPS(String default_title,String default_content,String url,String logoUrl) {
        if (url != null) {
            url +="&utm_source=0";
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setTitle(default_title);
            sp.setText(default_content + " - " + url);
            sp.setImageUrl(logoUrl);
            //        sp.setImageUrl("https://testapi.ifreegroup.net//Uploads//countries//America_cover.png");//图片链接，可以换成我们的logo

            try {
                Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
                platform.SSOSetting(false);
                platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 分享到推特
     *//*

    public void share2Twitter(String default_title,String default_content,String url,String logoUrl) {
        if (url != null) {
            url +="&utm_source=0";
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setText(default_content + " - " + url);
            sp.setImageUrl(logoUrl);
            //        sp.setImageUrl("https://testapi.ifreegroup.net//Uploads//countries//America_cover.png");//图片链接，可以换成我们的logo

            try {
                Platform platform = ShareSDK.getPlatform(Twitter.NAME);
                platform.SSOSetting(false);
                platform.setPlatformActionListener(listener);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 分享到短信
     *//*

    public void share2SMS(String phone) {
        if (url != null) {

            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setText(default_content + " - " + url);
            sp.setAddress(phone);
            //        sp.setImageUrl("https://testapi.ifreegroup.net//Uploads//countries//America_cover.png");//图片链接，可以换成我们的logo

            try {
                Platform platform = ShareSDK.getPlatform(ShortMessage.NAME);
                platform.setPlatformActionListener(listener);
                platform.SSOSetting(false);
                platform.share(sp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
*/
