package com.happy.todo.lib_common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkRequest;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Describe：断网重连监听工具类
 * @Date： 2019/02/27
 * @Author： dengkewu
 * @Contact：
 */
public class NetWorkListenerUtil {
    private Disposable subscribe;
    private  NetWorkListenerUtil(){}
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    private static NetWorkListenerUtil mNetWorkListenerUtil;

    public static NetWorkListenerUtil getInstance() {
        if (mNetWorkListenerUtil == null) {
            synchronized (NetWorkListenerUtil.class) {
                if (mNetWorkListenerUtil == null) {
                    mNetWorkListenerUtil = new NetWorkListenerUtil();
                }
            }
        }
        return mNetWorkListenerUtil;
    }


    public interface NetWorkListener {
        /**
         * 已连接到网络
         */
        void onReconnect();

    }

    private NetWorkListener netWorkListener;

    public void setNetWorkListener(NetWorkListener netWorkListener) {
        this.netWorkListener = netWorkListener;
    }

    public void register(Context context) {
        if (connectivityManager==null) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 请注意这里会有一个版本适配bug，所以请在这里添加非空判断
            if (connectivityManager != null) {
                //网络改变时出发
                networkCallback = new ConnectivityManager.NetworkCallback() {
                    /**
                     * 当建立网络连接时，回调连接的属性
                     */
                    @Override
                    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                        super.onLinkPropertiesChanged(network, linkProperties);
                        if (netWorkListener != null) {
                            AppLogUtil.d("网络重新连接");
                            subscribe = Observable.just(network)
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(network1 -> {
                                        netWorkListener.onReconnect();
                                    });
                        }

                    }


                };
                connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), networkCallback);
            }
        }
    }

    public void unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    public void removeListener() {
        netWorkListener = null;
        if (subscribe!=null&&!subscribe.isDisposed())
            subscribe.dispose();
    }

}
