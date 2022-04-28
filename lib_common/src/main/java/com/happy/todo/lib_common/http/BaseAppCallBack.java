package com.happy.todo.lib_common.http;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.manage.LoginManage;
import com.happy.todo.lib_common.router.RouterPath;
import com.happy.todo.lib_common.utils.ResourceUtils;
import com.happy.todo.lib_common.utils.UserUtils;
import com.happy.todo.lib_common.utils.Utils;
import com.happy.todo.lib_http.callback.CallBack;
import com.happy.todo.lib_http.exception.ApiException;

/**
 * Description:
 * Author: 贰师兄
 * Date: 2017/10/13 下午1:53
 */

public abstract class BaseAppCallBack<T> extends CallBack<T> {
    @Override
    public void onError(ApiException e) {
        switch (e.getCode()) {
            case ApiException.ERROR.PARSE_ERROR + "":
                e.setMessage(ResourceUtils.getString(R.string.http_error_hint1));
                break;
            case ApiException.ERROR.CAST_ERROR + "":
                e.setMessage(ResourceUtils.getString(R.string.http_error_hint2));
                break;
            case ApiException.ERROR.NETWORD_ERROR + "":
                e.setMessage(ResourceUtils.getString(R.string.http_error_hint3));
                break;
            case ApiException.ERROR.SSL_ERROR + "":
                e.setMessage(ResourceUtils.getString(R.string.http_error_hint4));
                break;
            case ApiException.ERROR.TIMEOUT_ERROR + "":
                e.setMessage(ResourceUtils.getString(R.string.request_timed_out));
                break;
            case ApiException.ERROR.UNKNOWNHOST_ERROR + "":
                e.setMessage(ResourceUtils.getString(R.string.http_error_hint5));
                break;
            case ApiException.ERROR.NULLPOINTER_EXCEPTION + "":
                e.setMessage("NullPointerException");
                break;
            default:
                e.setMessage(e.getMessage());
                break;
        }

        if (isTokenInValid(e)) {
            handleTokenInValid();
            return;
        }

        if (shouldHandleBySuccess(e)) {
            return;
        }
        onFailure(e);
    }

    /**
     * 解决Rxjava2 中 onNext 不能传 Null 的问题
     *
     * @param e
     * @return boolean true 已处理，false:继续处理
     */
    protected boolean shouldHandleBySuccess(ApiException e) {
        if (e.getCode().equals("886688")) {
            onSuccess(null);
            return true;
        }
        return false;
    }

    public abstract void onFailure(ApiException e);


    /**
     * token 失效处理
     */
    @SuppressLint("WrongConstant")
    protected void handleTokenInValid() {
        //如果账号被其他人登录，清除用户数据重新登录
        LoginManage.logout();
        UserUtils.clearFullUserInfo();
        ARouter.getInstance().build(RouterPath.MAIN)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
//            Toast.makeText(Utils.getApp(), e.getMessage(), Toast.LENGTH_SHORT).show();
        Toast.makeText(Utils.getApp(), ResourceUtils.getString(R.string.user_account_login_err), Toast.LENGTH_SHORT).show();
    }


    /**
     * 判断是否token 失效
     *
     * @param e
     * @return
     */
    protected boolean isTokenInValid(ApiException e) {
        return e.getCode().equals("E_7000");
    }


}
