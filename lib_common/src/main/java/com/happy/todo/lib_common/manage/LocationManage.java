/*
package com.ifreegroup.app.ebbly.lib_common.manage;

import android.annotation.SuppressLint;
import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ifreegroup.app.ebbly.lib_common.db.DBHelper;
import com.ifreegroup.app.ebbly.lib_common.db.table.UserLocation;
import com.ifreegroup.app.ebbly.lib_common.utils.AppLogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

*/
/**
 * @作者: TwoSX
 * @时间: 2018/1/14 下午9:37
 * @描述: 定位管理类
 *//*

public class LocationManage {
    private static LocationManage sLocationManage;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationListener mLocationListener;

    private List<WeakReference<OnLocationListener>> mReferenceList;

    private LocationManage() {
    }

    public static void init(Context context) {
        sLocationManage = new LocationManage();
        sLocationManage._init(context);
    }

    public static LocationManage getInstance() {
        if (sLocationManage == null) {
            throw new ExceptionInInitializerError("请先进行初始化");
        }
        return sLocationManage;
    }

    @SuppressLint("CheckResult")
    private void _init(Context context) {
        mReferenceList = Collections.synchronizedList(new
                ArrayList<WeakReference<OnLocationListener>>());
        mLocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)
        // 接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)
        // 接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //设置定位监听

        mLocationListener = aMapLocation -> {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    UserLocation userLocation = new UserLocation();
                    userLocation.setLat(aMapLocation.getLatitude() + "");
                    userLocation.setLon(aMapLocation.getLongitude() + "");
                    userLocation.setCountry(aMapLocation.getCountry());
                    userLocation.setProvince(aMapLocation.getProvince());
                    userLocation.setCity(aMapLocation.getCity());
                    userLocation.setAddress(aMapLocation.getAddress());
                    userLocation.setDate(new Date(aMapLocation.getTime()));

                    if (LoginManage.isLogin()) {
                        userLocation.setUser_id(LoginManage.getUserId());
                    }

                    // 将定位信息保存到数据库
                    Flowable.create((FlowableOnSubscribe<UserLocation>) emitter -> {
                                DBHelper.getInstance().getEbblyDatabase().getUserLocationDao()
                                        .insert(userLocation);
                                emitter.onNext(userLocation);
                                emitter.onComplete();
                            },
                            BackpressureStrategy.BUFFER)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(userLocation1 -> {
                        // 进行回调
                        if (mReferenceList != null) {
                            Iterator<WeakReference<OnLocationListener>> sListIterator =
                                    mReferenceList.iterator();
                            while (sListIterator.hasNext()) {
                                WeakReference<OnLocationListener> onLocationListenerWeakReference
                                        = sListIterator.next();
                                if (onLocationListenerWeakReference.get() == null) {
                                    sListIterator.remove();
                                } else {
                                    onLocationListenerWeakReference.get().onSuccess(userLocation1);
                                    sListIterator.remove();
                                }
                            }
                        }
                    });

                } else {
                    String errMsg = "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo();
                    if (mReferenceList != null) {
                        Iterator<WeakReference<OnLocationListener>> sListIterator =
                                mReferenceList.iterator();
                        while (sListIterator.hasNext()) {
                            WeakReference<OnLocationListener> onLocationListenerWeakReference
                                    = sListIterator.next();
                            if (onLocationListenerWeakReference.get() == null) {
                                sListIterator.remove();
                            } else {
                                onLocationListenerWeakReference.get().onError(errMsg);
                                sListIterator.remove();
                            }
                        }
                    }
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    AppLogUtil.d("LocationManage", errMsg);
                }
            }
        };
        mLocationClient.setLocationListener(mLocationListener);
    }

    //    开始一个定位
    public void startLocation(OnLocationListener onLocationListener) {
        if (mLocationOption != null) {
            //统一用英文
//            if (LanguageUtil.getInstance().isZhCn()) {
//                mLocationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.ZH);
//
//            } else {
                mLocationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.EN);
//            }

            mLocationClient.setLocationOption(mLocationOption);
        }
        if (mReferenceList != null) {
            mReferenceList.add(new WeakReference<OnLocationListener>(onLocationListener));
        }

        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }
    }

    // 获取上一次定位
    @SuppressLint("CheckResult")
    public void getLastLocation(OnLocationListener onLocationListener) {
        DBHelper.getInstance().getEbblyDatabase().getUserLocationDao().getLocation(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userLocations -> {
                    if (userLocations != null && userLocations.size() > 0) {
                        onLocationListener.onSuccess(userLocations.get(0));
                    } else {
                        onLocationListener.onError("无数据");
                    }
                });
    }

    public interface OnLocationListener {
        void onSuccess(UserLocation userLocation);

        void onError(String msg);
    }
}
*/
