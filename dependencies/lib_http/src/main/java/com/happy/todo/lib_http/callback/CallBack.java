
package com.happy.todo.lib_http.callback;


import com.happy.todo.lib_http.exception.ApiException;
import com.happy.todo.lib_http.utils.Utils;

import java.lang.reflect.Type;

/**
 * <p>描述：网络请求回调</p>
 */
public abstract class CallBack<T> implements IType<T> {
    public abstract void onStart();

    public abstract void onCompleted();

    public abstract void onError(ApiException e);

    public abstract void onSuccess(T result);

    @Override
    public Type getType() {//获取需要解析的泛型T类型
        return Utils.findNeedClass(getClass());
    }

    public Type getRawType() {//获取需要解析的泛型T raw类型
        return Utils.findRawType(getClass());
    }
}
