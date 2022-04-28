package com.happy.todo.lib_common.http;

import com.happy.todo.lib_common.utils.chinese.ChineseUtils;
import com.happy.todo.lib_common.utils.language.LanguageUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;

/**
 * 简体转繁体拦截器
 */
public class TwInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (LanguageUtil.newInstance().isZhTw()) {
            return simple2traditional(chain.proceed(chain.request()));
        }
        return chain.proceed(chain.request());
    }

    /**
     * 简体转繁体
     *
     * @param response
     * @return
     */
    private Response simple2traditional(Response response) {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        try {
            if (HttpHeaders.hasBody(clone)) {
                String body = responseBody.string();
                responseBody = ResponseBody.create(responseBody.contentType(), ChineseUtils.toTraditional(body));
                return response.newBuilder().body(responseBody).build();
            }
        } catch (Exception e) {

        } finally {

        }
        return response;
    }
}
