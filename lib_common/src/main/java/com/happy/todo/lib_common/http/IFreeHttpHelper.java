package com.happy.todo.lib_common.http;

import android.app.Application;
import android.text.TextUtils;

import com.happy.todo.lib_common.BuildConfig;
import com.happy.todo.lib_http.IFreeHttp;
import com.happy.todo.lib_http.cache.converter.GsonDiskConverter;
import com.happy.todo.lib_http.cache.model.CacheMode;
import com.happy.todo.lib_http.callback.DownloadProgressCallBack;
import com.happy.todo.lib_http.model.HttpParams;
import com.happy.todo.lib_http.request.DownloadRequest;
import com.happy.todo.lib_http.request.GetRequest;
import com.happy.todo.lib_http.request.PostRequest;
import com.xiaomai.environmentswitcher.EnvironmentSwitcher;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Description:
 * Author: 贰师兄
 * Date: 2017/10/12 下午2:52
 */

public class IFreeHttpHelper {
    private static IFreeHttpHelper sIFreeHttpHelper;

    private IFreeHttpHelper() {
    }

    public static IFreeHttpHelper newInstance() {
        if (sIFreeHttpHelper == null) {
            synchronized (IFreeHttpHelper.class) {
                if (sIFreeHttpHelper == null) {
                    sIFreeHttpHelper = new IFreeHttpHelper();
                }
            }
        }
        return sIFreeHttpHelper;
    }

    public static void init(Application application, boolean isDebug) {
        newInstance()._init(application, isDebug);
    }

    private void _init(Application application, boolean isDebug) {
        IFreeHttp.init(application);
        IFreeHttp instance = IFreeHttp.getInstance();

        instance
                //可以全局统一设置全局URL
                .setBaseUrl(EnvironmentSwitcher.getAppEnvironment(application, BuildConfig.DEBUG))//设置全局URL
                .setRecommendedHotelUrl(EnvironmentSwitcher.getRecommendedHotelEnvironment(application, BuildConfig.DEBUG))//设置推荐酒店URL
                .setCacheDiskConverter(new GsonDiskConverter())
                .setCacheMaxSize(200 * 1024 * 1024)
                .setCacheVersion(1) // 设置缓存版本，修改版本后旧缓存都失效
                .setConnectTimeout(45 * 1000)
                .setWriteTimeOut(45 * 1000)
                .setReadTimeOut(45 * 1000)
                .setRetryCount(0) //网络不好自动重试1次 不需要可以设置为0
                .setRetryDelay(500) //每次延时500ms重试
                .setSSLSocketFactory(SSLFactoryUtil.getSocketFactory(application), SSLFactoryUtil.getTrustManager())
                .addInterceptor(new AppHeadersInterceptor()) // 添加头部拦截器，请求时带上公共头部信息
                .addInterceptor(new CustomSignInterceptor())//添加参数签名拦截器
                .addInterceptor(new TwInterceptor())//简体转繁体
                .debug("IFREE_HTTP", isDebug)
        ;

    }

    /**
     * 无缓存
     *
     * @param url
     * @param httpParams
     * @param callBack
     * @return
     */
    public static Disposable get(String url, HttpParams httpParams, BaseAppCallBack callBack) {
        return newInstance()._get(url, httpParams, callBack, false, null);
    }

    public static Disposable get(String url, HttpParams httpParams, boolean isCache, String key,
                                 BaseAppCallBack callBack) {
        return newInstance()._get(url, httpParams, callBack, isCache, key);
    }

    public static Disposable get(String url, HttpParams httpParams, boolean isCache,
                                 BaseAppCallBack callBack) {
        return newInstance()._get(url, httpParams, callBack, isCache, url);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable get(String url, HttpParams httpParams, String cacheKey,
                                 BaseAppCallBack callBack) {
        return newInstance()._post(url, httpParams, callBack, true, cacheKey);
    }


    public static <T> Disposable get(String url, BaseAppCallBack<T> callBack) {
        return newInstance()._get(url, null, callBack, false, null);
    }

    public static Disposable get(String url, boolean isCache, String key, BaseAppCallBack
            callBack) {
        return newInstance()._get(url, null, callBack, isCache, key);
    }

    public static Disposable get(String url, boolean isCache, BaseAppCallBack callBack) {
        return newInstance()._get(url, null, callBack, isCache, url);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable get(String url, String cacheKey, BaseAppCallBack callBack) {
        return newInstance()._post(url, null, callBack, true, cacheKey);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable getFirstNetwork(String url, String cacheKey, BaseAppCallBack
            callBack) {
        return newInstance()._get(url, null, callBack, true, cacheKey);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable getFirstNetwork(String url, BaseAppCallBack callBack) {
        return newInstance()._get(url, null, callBack, true, url);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable getFirstNetwork(String url, HttpParams httpParams, BaseAppCallBack
            callBack) {
        return newInstance()._get(url, httpParams, callBack, true, url);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable getFirstNetwork(String url, HttpParams httpParams, String cacheKey,
                                             BaseAppCallBack callBack) {
        return newInstance()._get(url, httpParams, callBack, true, cacheKey);
    }


    // 先请求网络，失败再请求缓存
    public static Disposable postFirstNetwork(String url, String cacheKey, BaseAppCallBack
            callBack) {
        return newInstance()._post(url, null, callBack, true, cacheKey);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable postFirstNetwork(String url, BaseAppCallBack callBack) {
        return newInstance()._post(url, null, callBack, true, url);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable postFirstNetwork(String url, HttpParams httpParams, BaseAppCallBack
            callBack) {
        return newInstance()._post(url, httpParams, callBack, true, url);
    }

    // 先请求网络，失败再请求缓存
    public static Disposable postFirstNetwork(String url, HttpParams httpParams, String cacheKey,
                                              BaseAppCallBack callBack) {
        return newInstance()._post(url, httpParams, callBack, true, cacheKey);
    }


    /**
     * post 无缓存
     *
     * @param url
     * @param httpParams
     * @param callBack
     * @return
     */
    public static <T> Disposable post(String url, HttpParams httpParams, BaseAppCallBack<T>
            callBack) {
        return newInstance()._post(url, httpParams, callBack, false, null);
    }

    /**
     * post 无缓存
     *
     * @param url
     * @param httpParams
     * @param callBack
     * @return
     */
    public static <T> Disposable post(String baseUrl, String url, HttpParams httpParams, BaseAppCallBack<T>
            callBack) {
        return newInstance()._post(baseUrl, url, httpParams, callBack, false, null);
    }
    /**
     * post请求
     * 可独立设置超时时间
     * @param timeout 时间单位 毫秒
     */
    public static <T> Disposable post(String url,HttpParams httpParams, long timeout, BaseAppCallBack<T> callBack) {
        PostRequest postRequest = IFreeHttp.post(url);

        if (httpParams != null) {
            postRequest = postRequest.params(httpParams);
        }

        postRequest = postRequest.connectTimeout(timeout);

        return postRequest.execute(callBack);
    }


    /**
     * @param url
     * @param httpParams
     * @param callBack
     * @param isCache
     * @param key
     * @return
     */
    public static Disposable post(String url, HttpParams httpParams, boolean isCache, String key,
                                  BaseAppCallBack callBack) {
        return newInstance()._post(url, httpParams, callBack, isCache, key);
    }

    /**
     * post请求， key 为 url
     *
     * @param url
     * @param httpParams
     * @param callBack
     * @param isCache
     * @return
     */
    public static Disposable post(String url, HttpParams httpParams, boolean isCache,
                                  BaseAppCallBack callBack) {
        return newInstance()._post(url, httpParams, callBack, isCache, url);
    }

    /**
     * post请求 先加载网络再加载缓存，传入 key
     *
     * @param url
     * @param httpParams
     * @param callBack
     * @param cacheKey
     * @return
     */
    public static Disposable post(String url, HttpParams httpParams, String cacheKey,
                                  BaseAppCallBack callBack) {
        return newInstance()._post(url, httpParams, callBack, true, cacheKey);
    }

    /**
     * post 无缓存
     *
     * @param url
     * @param callBack
     * @return
     */
    public static <T> Disposable post(String url, BaseAppCallBack<T> callBack) {
        return newInstance()._post(url, null, callBack, false, null);
    }

    /**
     * @param url
     * @param callBack
     * @param isCache
     * @param key
     * @return
     */
    public static Disposable post(String url, boolean isCache, String key, BaseAppCallBack
            callBack) {
        return newInstance()._post(url, null, callBack, isCache, key);
    }

    /**
     * post请求， key 为 url
     *
     * @param url
     * @param callBack
     * @param isCache
     * @return
     */
    public static Disposable post(String url, boolean isCache, BaseAppCallBack callBack) {
        return newInstance()._post(url, null, callBack, isCache, url);
    }

    /**
     * post请求 先加载网络再加载缓存，传入 key
     *
     * @param url
     * @param callBack
     * @param cacheKey
     * @return
     */
    public static Disposable post(String url, String cacheKey, BaseAppCallBack callBack) {
        return newInstance()._post(url, null, callBack, true, cacheKey);
    }

    /**
     * 返回一个Observable 对象，这种方式下可以使用返回对象来进行操作符的操作
     * @return
     */
    public static <T> Observable<T>  post(String url, HttpParams httpParams, String cacheKey, Class<T> clazz) {
        return newInstance()._post(url, httpParams, clazz, true, cacheKey);
    }


    private Disposable _post(String url, HttpParams httpParams, BaseAppCallBack callBack, boolean
            isCache, String key) {
        PostRequest postRequest = IFreeHttp.post(url);

        if (httpParams != null) {
            postRequest = postRequest.params(httpParams);
        }

        if (isCache) {
            postRequest = postRequest.cacheMode(CacheMode.FIRSTREMOTE)
                    .cacheKey(key)
                    .cacheTime(-1);
        }

        return postRequest.execute(callBack);
    }

    private Disposable _post(String baseUrl, String url, HttpParams httpParams, BaseAppCallBack callBack, boolean
            isCache, String key) {
        PostRequest postRequest = IFreeHttp.post(url);

        if (httpParams != null) {
            postRequest = postRequest.params(httpParams);
        }
        if (isCache) {
            postRequest = postRequest.cacheMode(CacheMode.FIRSTREMOTE)
                    .cacheKey(key)
                    .cacheTime(-1);
        }
        if(!TextUtils.isEmpty(baseUrl)){
            postRequest = postRequest.baseUrl(baseUrl);
        }

        return postRequest.execute(callBack);
    }

    private static <T> Observable<T> _post(String url,HttpParams httpParams, Class<T> callBack,boolean isCache,String key){
        PostRequest postRequest = IFreeHttp.post(url);

        if (httpParams != null) {
            postRequest = postRequest.params(httpParams);
        }

        if (isCache) {
            postRequest = postRequest.cacheMode(CacheMode.FIRSTREMOTE)
                    .cacheKey(key)
                    .cacheTime(-1);
        }
        return postRequest.execute(callBack);
    }



    private Disposable _get(String url, HttpParams httpParams, BaseAppCallBack callBack, boolean
            isCache, String key) {

        GetRequest getRequest = IFreeHttp.get(url);
        if (httpParams != null) {
            getRequest = getRequest.params(httpParams);
        }

        if (isCache) {
            getRequest = getRequest.cacheMode(CacheMode.FIRSTREMOTE)
                    .cacheKey(key)
                    .cacheTime(-1);
        }

        return getRequest.execute(callBack);

    }

    /**
     * 下载文件
     * @param url
     * @param filePath
     * @param fileName
     * @param callBack
     * @param <T>
     * @return
     */
    public static <T> Disposable downloadFile(String url, String filePath, String fileName,  DownloadProgressCallBack<T> callBack) {
        DownloadRequest request = IFreeHttp.downLoad(url);
        request.savePath(filePath);
        request.saveName(fileName);
        return request.execute(callBack);
    }



    /**
     * 取消指定请求
     *
     * @param disposables
     */
    public static void cancel(Disposable disposables) {
        IFreeHttp.cancelDisposable(disposables);
    }

    /**
     * 取消指定请求
     *
     * @param disposables
     */
    public static void cancel(Disposable... disposables) {
        IFreeHttp.cancelDisposables(disposables);
    }

}
