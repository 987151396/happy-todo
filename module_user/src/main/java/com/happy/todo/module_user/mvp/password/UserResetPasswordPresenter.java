package com.happy.todo.module_user.mvp.password;

import com.happy.todo.lib_common.http.IFreeHttpHelper;
import com.happy.todo.module_user.mvp.base.ToastType;
import com.happy.todo.module_user.api.UserApi;
import com.happy.todo.lib_common.http.SimpleLoadingCallBack;
import com.happy.todo.module_user.mvp.base.UserMvpBasePresenter;
import com.happy.todo.lib_http.model.ApiResult;
import com.happy.todo.lib_http.model.HttpParams;

import io.reactivex.disposables.Disposable;

/**
 * 用户重置密码
 * Created by Jaminchanks on 2018/1/23.
 */

public class UserResetPasswordPresenter extends UserMvpBasePresenter<UserResetPasswordContract.View> implements UserResetPasswordContract.Presenter{

    @Override
    public void resetPassword(String account, String newPassword, String encryptionCode, String token, String areaCode) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("account", account);
        httpParams.put("password", newPassword);
        httpParams.put("password2", newPassword);
        httpParams.put("encryption_code", encryptionCode);
        httpParams.put("token", token);
        httpParams.put("area_code", areaCode);

        Disposable disposable = IFreeHttpHelper.post(UserApi.USER_FORGET_PWD_RESET, httpParams,
                new SimpleLoadingCallBack<ApiResult>(getView(), ToastType.TOP_ALTER) {
                    @Override
                    public void onSuccess(ApiResult apiResult) {
                        getView().showResetPasswordResult(true, "");
                    }
                });

        addDisposable(disposable);
    }
}
