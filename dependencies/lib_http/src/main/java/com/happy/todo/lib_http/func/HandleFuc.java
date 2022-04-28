package com.happy.todo.lib_http.func;

import com.happy.todo.lib_http.exception.ApiException;
import com.happy.todo.lib_http.exception.ApiNotOkException;
import com.happy.todo.lib_http.exception.ServerException;
import com.happy.todo.lib_http.model.ApiResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * <p>描述：ApiResult<T>转换T</p>
 */
public class HandleFuc<T> implements Function<ApiResult<T>, T> {
    @Override
    public T apply(@NonNull ApiResult<T> tApiResult) throws Exception {
        if (ApiException.isOk(tApiResult)) {
            if (tApiResult.getData() == null && tApiResult.isOk()) {
                throw new ServerException("886688", tApiResult.getMsg());
            } else {
                return tApiResult.getData();// == null ? Optional.ofNullable(tApiResult.getData())
            }
            // .orElse(null) : tApiResult.getData();
        } else if (!ApiException.isOk(tApiResult) && tApiResult.getData() != null) { //状态码不Ok,但需要处理其中的data数据
            throw new ApiNotOkException(tApiResult.getCode(), tApiResult.getMsg(), tApiResult.getData());
        } else {
            throw new ServerException(tApiResult.getCode(), tApiResult.getMsg());
        }
    }
}
