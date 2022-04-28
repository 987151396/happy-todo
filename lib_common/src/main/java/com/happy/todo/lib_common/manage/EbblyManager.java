package com.happy.todo.lib_common.manage;

import android.content.Context;

import com.happy.todo.lib_common.ui.webview.WebActivity;
import com.happy.todo.lib_common.utils.language.LanguageUtil;

/**
 * author : twosx
 * time   : 2018/7/24 上午12:04
 * desc   : 管理APP的简单业务逻辑
 */
public class EbblyManager {



    // TODO: 2018/7/24 修改成正式服链接
    //private static final String sBaseUrl = "https://ebbly.ifreegroup.net/#/";
    private static final String sBaseUrl = "https://m.ebbly.com/index.html/";

    /**
     * 打开 隐私条款
     *
     * @param context
     */
    public static void openPrivacyPolicy(Context context) {
        String url = sBaseUrl + "privacy";
//        https://www.ebbly.com/privacy_policy
        // 拼接上当前语言
       url = url + "?l=" + LanguageUtil.newInstance().getLanguageCode().toLowerCase();
        WebActivity.start(context, url);
    }

}
