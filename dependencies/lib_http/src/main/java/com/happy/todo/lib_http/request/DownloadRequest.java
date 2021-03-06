package com.happy.todo.lib_http.request;


import com.happy.todo.lib_http.callback.CallBack;
import com.happy.todo.lib_http.func.RetryExceptionFunc;
import com.happy.todo.lib_http.observer.DownloadObserver;
import com.happy.todo.lib_http.transformer.HandleErrTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * <p>描述：下载请求</p>
 */
public class DownloadRequest extends BaseRequest<DownloadRequest> {
    public DownloadRequest(String url) {
        super(url);
    }

    private String savePath;
    private String saveName;

    /**
     * 下载文件路径<br>
     * 默认在：/storage/emulated/0/Android/data/包名/files/1494647767055<br>
     */
    public DownloadRequest savePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    /**
     * 下载文件名称<br>
     * 默认名字是时间戳生成的<br>
     */
    public DownloadRequest saveName(String saveName) {
        this.saveName = saveName;
        return this;
    }

    public <T> Disposable execute(CallBack<T> callBack) {
        return (Disposable) build().apiManager.downloadFile(url).compose(new ObservableTransformer<ResponseBody, ResponseBody>() {
            @Override
            public ObservableSource<ResponseBody> apply(@NonNull Observable<ResponseBody> upstream) {
                if(isSyncRequest){
                    return upstream;//.observeOn(AndroidSchedulers.mainThread());
                }else {
                    return upstream.subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io());
                }
            }
        }).compose(new HandleErrTransformer()).retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay))
                .subscribeWith(new DownloadObserver(context,savePath, saveName, callBack));
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiManager.downloadFile(url);
    }
}
