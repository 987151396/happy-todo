package com.happy.todo.module_main.ui.version;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.happy.todo.lib_common.http.IFreeHttpHelper;
import com.happy.todo.lib_common.http.SimpleAppCallBack;
import com.happy.todo.lib_common.manage.LoginManage;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.AppUtil;
import com.happy.todo.lib_common.utils.Utils;
import com.happy.todo.lib_common.bean.VersionBean;
import com.happy.todo.lib_http.exception.ApiException;
import com.happy.todo.lib_http.model.HttpParams;

import io.reactivex.disposables.Disposable;

/**
 * @功能描述：更新服务
 * @创建日期： 2018/07/20
 * @作者： dengkewu
 */

public class UpdateServer extends Service {

    private Disposable mDisposable;
    private Disposable mDisposable1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public static class MyBinder extends Binder {
    }


    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppLogUtil.e("onStartCommand");
        //上传设备码
        if (LoginManage.isLogin()) {

        } else {
            getVersion();
        }

        return super.onStartCommand(intent, flags, startId);
    }


    public void getVersion() {
        HttpParams params = new HttpParams();
        params.put("type", "2");//客户端 1 ios 2 android
        mDisposable = IFreeHttpHelper.get("/version", params, new SimpleAppCallBack<VersionBean>() {
            @Override
            public void onFailure(ApiException e) {
                stopSelf();
            }

            @Override
            public void onSuccess(VersionBean bean) {
                if (bean != null && !TextUtils.isEmpty(bean.getAppurl())) {
                    // 判断下线上版本与目前版本是否一致，不一致，提醒更新
                    try {
                        if (!bean.getSystem_version().equals(AppUtil.getAppVersionName())
                                && Integer.valueOf(bean.getCostom_version()) > AppUtil
                                .getAppVersionCode()) {
                            boolean isForcedUpdate = false;
                            if(!TextUtils.isEmpty(bean.getMinimum_version()) &&
                                    AppUtil.compareVersion(AppUtil.getAppVersionName(),bean.getMinimum_version()) < 0)
                            //    if(!TextUtils.isEmpty("1.9.2") &&
                            //            AppUtil.compareVersion("1.9.2","1.9.2.1") < 0)
                                isForcedUpdate = true;
                                Intent intent = UpdateAppActivity.getIntent(Utils.getApp(), bean,isForcedUpdate);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //如果没有更新销毁服务
                // stopService(new Intent(UpdateServer.this, UpdateServer.class));
                stopSelf();
            }
        });
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        AppLogUtil.e("onDestroy");
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }

        if (mDisposable1 != null) {
            mDisposable1.dispose();
        }
    }
}
