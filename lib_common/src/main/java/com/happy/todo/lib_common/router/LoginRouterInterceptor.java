package com.happy.todo.lib_common.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.manage.LoginManage;

/**
 * 登录拦截器
 * Created by Jaminchanks on 2018/1/19.
 */

@Interceptor(priority = 1, name = "needLoginPermission")
public class LoginRouterInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getExtra() == RouterPath.NEED_LOGIN && !LoginManage.isLogin()) {
            LoginBackTagUtil.getInstance().setTag(postcard);
            ARouter.getInstance()
                    .build(RouterPath.USER_LOGIN)
                    .greenChannel()
                    .navigation();
            callback.onInterrupt(null);//路由中断
        } else {
            callback.onContinue(postcard);
        }
    }


    @Override
    public void init(Context context) {
    }
}
