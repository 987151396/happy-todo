
package com.happy.todo.lib_http.request;

import com.google.gson.reflect.TypeToken;
import com.happy.todo.lib_http.cache.model.CacheResult;
import com.happy.todo.lib_http.callback.CallBack;
import com.happy.todo.lib_http.callback.CallBackProxy;
import com.happy.todo.lib_http.func.ApiResultFunc;
import com.happy.todo.lib_http.func.CacheResultFunc;
import com.happy.todo.lib_http.func.RetryExceptionFunc;
import com.happy.todo.lib_http.model.ApiResult;
import com.happy.todo.lib_http.observer.CallBackObserver;
import com.happy.todo.lib_http.utils.RxUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * <p>描述：删除请求</p>
 */
public class DeleteRequest extends BaseRequest<DeleteRequest> {
    public DeleteRequest(String url) {
        super(url);
    }

    public <T> Disposable execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {});
    }

    public <T> Disposable execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Observable<CacheResult<T>> observable = build().toObservable(generateRequest(), proxy);
        if (CacheResult.class != proxy.getCallBack().getRawType()) {
            return observable.compose(new ObservableTransformer<CacheResult<T>, T>() {
                @Override
                public ObservableSource<T> apply(@NonNull Observable<CacheResult<T>> upstream) {
                    return upstream.map(new CacheResultFunc<T>());
                }
            }).subscribeWith(new CallBackObserver<T>(context, proxy.getCallBack()));
        } else {
            return observable.subscribeWith(new CallBackObserver<CacheResult<T>>(context, proxy.getCallBack()));
        }
    }

    private <T> Observable<CacheResult<T>> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        return observable.map(new ApiResultFunc(proxy != null ? proxy.getType() : new TypeToken<ResponseBody>() {
        }.getType()))
                .compose(isSyncRequest ? RxUtil._main() : RxUtil._io_main())
                .compose(rxCache.transformer(cacheMode, proxy.getCallBack().getType()))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiManager.delete(url, params.urlParamsMap);
    }
}
