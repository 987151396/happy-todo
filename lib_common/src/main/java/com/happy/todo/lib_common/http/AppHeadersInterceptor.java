package com.happy.todo.lib_common.http;

import com.happy.todo.lib_common.manage.LoginManage;
import com.happy.todo.lib_common.utils.AppUtil;
import com.happy.todo.lib_common.utils.language.LanguageUtil;
import com.happy.todo.lib_http.model.HttpHeaders;
import com.happy.todo.lib_http.utils.HttpLog;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.happy.todo.lib_common.http.api.AppApi.KEY_API_HEADER_CLIENT;
import static com.happy.todo.lib_common.http.api.AppApi.KEY_API_HEADER_CLIENT_LANG;
import static com.happy.todo.lib_common.http.api.AppApi.KEY_API_HEADER_USER_ID;
import static com.happy.todo.lib_common.http.api.AppApi.KEY_API_HEADER_USER_TOKEN;

/**
 * @作者: TwoSX
 * @时间: 2018/1/26 下午4:27
 * @描述: 头部拦截器，加入公共参数
 */
public class AppHeadersInterceptor implements Interceptor {
    private String headerCode;

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpHeaders httpHeaders;
        // 添加公共头部参数Ò
        httpHeaders = new HttpHeaders();
        httpHeaders.put(KEY_API_HEADER_CLIENT, "android");
        if (LanguageUtil.newInstance().getLanguageCode().equals("zh-tw")){
            headerCode="zh-cn";
        }else{
            headerCode=LanguageUtil.newInstance().getLanguageCode();
        }
        httpHeaders.put(KEY_API_HEADER_CLIENT_LANG, headerCode);
        if (LoginManage.isLogin()) {
            httpHeaders.put(KEY_API_HEADER_USER_TOKEN, LoginManage.getUserToken());
            httpHeaders.put(KEY_API_HEADER_USER_ID, LoginManage.getUserId() + "");
//            httpHeaders.put(KEY_API_HEADER_USER_ID, "16");
        }
        httpHeaders.put("User-Agent", AppUtil.getUserAgent());
        Request.Builder builder = chain.request().newBuilder();
        if (httpHeaders.headersMap.isEmpty())
            return chain.proceed(builder.build());
        try {
            for (Map.Entry<String, String> entry : httpHeaders.headersMap.entrySet()) {
                // 去除重复的header
                builder.header(entry.getKey(), entry.getValue()).build();
                HttpLog.d(entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            HttpLog.e(e);
        }
        return chain.proceed(builder.build());
    }


}
