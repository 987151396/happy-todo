package com.happy.todo.module_user.mvp.register;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.happy.todo.lib_common.bean.AppUpDateBean;
import com.happy.todo.lib_common.bean.UserInfoResult;
import com.happy.todo.lib_common.db.DBHelper;
import com.happy.todo.lib_common.db.table.UserPhoneAreaCodeTable;
import com.happy.todo.lib_common.event.LoginEvent;
import com.happy.todo.lib_common.http.IFreeHttpHelper;
import com.happy.todo.lib_common.http.SimpleAppCallBack;
import com.happy.todo.lib_common.http.api.AppApi;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.AppUtil;
import com.happy.todo.lib_common.utils.DateUtil;
import com.happy.todo.lib_common.utils.UserUtils;
import com.happy.todo.module_user.mvp.base.ToastType;
import com.happy.todo.module_user.api.UserApi;
import com.happy.todo.lib_common.http.SimpleLoadingCallBack;
import com.happy.todo.module_user.mvp.base.UserMvpBasePresenter;
import com.happy.todo.lib_http.exception.ApiException;
import com.happy.todo.lib_http.model.ApiResult;
import com.happy.todo.lib_http.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jaminchanks on 2018/1/22.
 */

public class UserRegisterPresenter extends UserMvpBasePresenter<UserRegisterContract.View> implements UserRegisterContract.Presenter {

    @Override
    public void register(Map<String, String> params) {
        HttpParams httpParams = new HttpParams();
        httpParams.put(params);

        Disposable disposable =
                IFreeHttpHelper.post(UserApi.USER_REGISTER, httpParams,
                        new SimpleLoadingCallBack<UserInfoResult>(getView(), ToastType.TOP_ALTER) {
                            @SuppressLint("CheckResult")
                            @Override
                            public void onSuccess(UserInfoResult apiResult) {
                                //保存用户信息到本地，直接跳转到主页
                                UserUtils.saveFullUserInfo(apiResult);
                                EventBus.getDefault().post(new LoginEvent());

                                getView().showRegisterResult(true, "","");
                                checkAppVersionChange();

                                String account = params.get("account");
                                String areaCode = params.get("area_code");
                                //如果参数中的地区号不为空，说明是使用手机号进行登录，将该手机号和所对应的地区号储存到数据库
                                if (!TextUtils.isEmpty(areaCode) && !TextUtils.isEmpty(account)) {
                                    UserPhoneAreaCodeTable item = new UserPhoneAreaCodeTable();
                                    item.setPhoneCode(account);
                                    item.setAreaCode(areaCode);
                                    Completable.fromAction(() -> {
                                        DBHelper.getInstance()
                                                .getEbblyDatabase()
                                                .getUserPhoneAreaCodeDao()
                                                .insertPhoneAreaCode(item);
                                    }).subscribeOn(Schedulers.io());

                                }
                            }
                            @Override
                            public void onFailure(ApiException e) {
                                getView().showRegisterResult(false,e.getMessage(), e.getCode());
                                //拦截不让弹出模态框
                                if (false)
                                    super.onFailure(e);
                            }
                        });

        addDisposable(disposable);
    }

    /**
     * 先验证手机是否存在，然后再发送验证码, 发送验证码就不loading了
     * @param phoneNum
     * @param areaCode
     */
    @Override
    public void sendVerifyCode(String phoneNum, String areaCode) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("area_code", areaCode);
        httpParams.put("mobile", phoneNum);

        Disposable disposable =
                IFreeHttpHelper.post(UserApi.SEND_VERIFY_CODE_REG, httpParams,
                        new SimpleLoadingCallBack<ApiResult>(getView(), false, ToastType.TOP_ALTER) {
                            @Override
                            public void onSuccess(ApiResult o) {
                                getView().showSendVerifyCodeResult(true, "");
                            }
                            @Override
                            public void onFailure(ApiException e) {
                                getView().showRegisterResult(false,e.getMessage(), e.getCode());
                                //拦截不让弹出模态框
                                if (false)
                                    super.onFailure(e);
                            }
                        });

        addDisposable(disposable);
    }

    @Override
    public void sendEmailVerifyCode(String email) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("account", email);
        httpParams.put("type", "1");

        Disposable disposable =
                IFreeHttpHelper.post(UserApi.USER_SEND_EMAIL, httpParams,
                        new SimpleLoadingCallBack<ApiResult>(getView(), false, ToastType.TOP_ALTER) {
                            @Override
                            public void onSuccess(ApiResult apiResult) {
                                getView().showSendVerifyCodeResult(true, "");
                            }

                            @Override
                            public void onFailure(ApiException e) {
                                getView().showRegisterResult(false,e.getMessage(), e.getCode());
                                //拦截不让弹出模态框
                                if (false)
                                    super.onFailure(e);
                            }
                        });

        addDisposable(disposable);
    }

    private void checkAppVersionChange() {
        AppLogUtil.OY_D("checkAppVersionChange : 注册");
        HttpParams params = new HttpParams();
        params.put("time", DateUtil.timestampToString1(DateUtil.getTimestamp()/1000));
        params.put("version", AppUtil.getAppVersionName());

        Disposable appUpDateDisposable = IFreeHttpHelper.post(AppApi.APP_CLIENT_VERSION, params, new SimpleAppCallBack<AppUpDateBean>() {
            @Override
            public void onFailure(ApiException e) {}

            @Override
            public void onSuccess(AppUpDateBean data) {
                UserUtils.saveAppVersionChangedName();
            }
        });
        addDisposable(appUpDateDisposable);
    }

}
