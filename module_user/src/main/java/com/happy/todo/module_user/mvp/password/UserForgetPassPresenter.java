package com.happy.todo.module_user.mvp.password;

import android.annotation.SuppressLint;

import com.happy.todo.lib_common.db.DBHelper;
import com.happy.todo.lib_common.http.IFreeHttpHelper;
import com.happy.todo.module_user.bean.response.ForgetPasswordResult;
import com.happy.todo.module_user.mvp.base.ToastType;
import com.happy.todo.module_user.api.UserApi;
import com.happy.todo.lib_common.http.SimpleLoadingCallBack;
import com.happy.todo.module_user.mvp.base.UserMvpBasePresenter;
import com.happy.todo.lib_http.exception.ApiException;
import com.happy.todo.lib_http.model.HttpParams;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 用户忘记密码操作
 * Created by Jaminchanks on 2018/2/1.
 */

public class UserForgetPassPresenter extends UserMvpBasePresenter<UserForgetPassContract.View>
        implements UserForgetPassContract.Presenter {

    private static long mLastCountDownTime = 0; //上次请求验证码的时间

    @SuppressLint("CheckResult")
    @Override
    public void autoSendVerifyCode(String phoneNum) {
        long currentTime = System.currentTimeMillis();
        long remainTime = 60 * 1000 - (currentTime - mLastCountDownTime); //剩余需要显示的倒计时时间

        if (remainTime > 0) {
            getView().startSendPhoneVerifyCode(remainTime);
            return;
        }

        Disposable disposable = DBHelper.getInstance().getEbblyDatabase().getUserPhoneAreaCodeDao()
                .getPhoneAreaCode(phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPhoneAreaCodeTable -> {
                    if (userPhoneAreaCodeTable != null) {
                        getView().setAreaCode(userPhoneAreaCodeTable.getAreaCode());
                        sendVerifyCode(true, phoneNum, userPhoneAreaCodeTable.getAreaCode());
                    }
                });

        addDisposable(disposable);
    }

    /**
     * 使用邮箱或者手机发送验证码
     *
     * @param isUsePhone 是否使用手机
     * @param account
     * @param areaCode
     */
    @Override
    public void sendVerifyCode(boolean isUsePhone, String account, String areaCode) {

        HttpParams httpParams = new HttpParams();
        httpParams.put("account", account);

        if (isUsePhone) {
            httpParams.put("area_code", areaCode);
        }

        String url = isUsePhone ? UserApi.USER_FORGET_PWD_PHONE : UserApi.USER_FORGET_PWD_EMAIL;

        Disposable disposable = IFreeHttpHelper.post(url, httpParams,
                new SimpleLoadingCallBack<ForgetPasswordResult>(getView(), false, ToastType.TOP_ALTER) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        if (isUsePhone) {
                            getView().startSendPhoneVerifyCode(60 * 1000);
                            mLastCountDownTime = System.currentTimeMillis();
                        }
                    }

                    @Override
                    public void onFailure(ApiException e) {
                        if (isUsePhone) {
                            getView().endSendPhoneVerifyCode(e.getMessage(), e.getCode());
                            mLastCountDownTime = 0;
                        }else{
                            getView().showSendVerifyCodeFaild(e.getMessage(),e.getCode());
                        }
                        if (false)
                            super.onFailure(e);
                    }

                    @Override
                    public void onSuccess(ForgetPasswordResult o) {
                        getView().showSendVerifyCodeSuccess(isUsePhone, "");
                    }
                });

        addDisposable(disposable);
    }


    /**
     * 1. 通过邮箱的方式找回密码的话，需要用户通过其他方式登录邮箱后再点击相应的链接
     * 2. 通过手机的方式找回密码的话，可以直接在客户端处理重置密码的操作,在这个步骤里验证获取到的手机验证码是否正确
     *
     * @param areaCode
     * @param phoneNumber
     * @param verifyCode
     */
    @Override
    public void checkMobileVerifyCode(String areaCode, String phoneNumber, String verifyCode) {
        //这里分两步，先检查验证码；再发送重置请求
        HttpParams httpParams = new HttpParams();
        httpParams.put("area_code", areaCode);
        httpParams.put("account", phoneNumber);
        httpParams.put("code", verifyCode);

        Disposable disposable = IFreeHttpHelper.post(UserApi.USER_FORGET_PWD_CHECK_CODE, httpParams,
                new SimpleLoadingCallBack<ForgetPasswordResult>(getView(), ToastType.TOP_ALTER) {
                    @Override
                    public void onSuccess(ForgetPasswordResult result) {
                        getView().showCheckMobileVerifyCodeSuccess(phoneNumber, result.getEncryption_code(), result.getToken());
                    }

                    @Override
                    public void onFailure(ApiException e) {
                        getView().showCheckMobileVerifyCodeError(e.getMessage(), e.getCode());
                        //拦截不让弹出模态框
                        if (false)
                            super.onFailure(e);
                    }
                });

        addDisposable(disposable);
    }

}
