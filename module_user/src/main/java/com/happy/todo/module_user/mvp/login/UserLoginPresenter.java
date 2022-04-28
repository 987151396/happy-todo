package com.happy.todo.module_user.mvp.login;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.happy.todo.lib_common.bean.AccountPlatformType;
import com.happy.todo.lib_common.bean.AppUpDateBean;
import com.happy.todo.lib_common.bean.UserInfoResult;
import com.happy.todo.lib_common.db.DBHelper;
import com.happy.todo.lib_common.db.table.UserPhoneAreaCodeTable;
import com.happy.todo.lib_common.event.LoginEvent;
import com.happy.todo.lib_common.http.IFreeHttpHelper;
import com.happy.todo.lib_common.http.SimpleAppCallBack;
import com.happy.todo.lib_common.http.SimpleLoadingCallBack;
import com.happy.todo.lib_common.http.api.AppApi;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.AppUtil;
import com.happy.todo.lib_common.utils.DateUtil;
import com.happy.todo.lib_common.utils.LocationUtils;
import com.happy.todo.lib_common.utils.StringUtils;
import com.happy.todo.lib_common.utils.UserUtils;
import com.happy.todo.module_user.api.UserApi;
import com.happy.todo.module_user.mvp.base.ToastType;
import com.happy.todo.module_user.mvp.base.UserMvpBasePresenter;
import com.happy.todo.lib_http.exception.ApiException;
import com.happy.todo.lib_http.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.happy.todo.lib_common.utils.PreChecker.ifNull;

/**
 * 登录:
 * Created by Jaminchanks on 2018/1/18.
 */

public class UserLoginPresenter extends UserMvpBasePresenter<UserLoginContract.View>
        implements UserLoginContract.Presenter {

    @Override
    public void handleManualLogin(String account, String password, String areaCode) {

        HttpParams params = new HttpParams();
        params.put("account", account);
        params.put("password", password);

        if (StringUtils.isPhone(account)) {
            params.put("area_code", areaCode);
        }

        params.put("login_type", StringUtils.isPhone(account) ? "2" : "1"); //登录类型：1=邮箱登录，2=手机号码登录

        Disposable disposable = IFreeHttpHelper.post(UserApi.USER_LOGIN, params,
                new SimpleLoadingCallBack<UserInfoResult>(getView(), ToastType.TOP_ALTER) {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onSuccess(UserInfoResult loginResult) {
                        //如果参数中的地区号不为空，说明是使用手机号进行登录，将该手机号和所对应的地区号储存到数据库
                        if (!TextUtils.isEmpty(areaCode)) {
                            UserPhoneAreaCodeTable item = new UserPhoneAreaCodeTable();
                            item.setPhoneCode(account);
                            item.setAreaCode(areaCode);

                            Disposable subscribe = Completable.fromAction(() -> {
                                DBHelper.getInstance()
                                        .getEbblyDatabase()
                                        .getUserPhoneAreaCodeDao()
                                        .insertPhoneAreaCode(item);
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        UserUtils.saveFullUserInfo(loginResult);
                                        EventBus.getDefault().post(new LoginEvent());
                                        getView().showManualLoginResult(true, "", "");
                                    });
                        } else {
                            UserUtils.saveFullUserInfo(loginResult);
                            EventBus.getDefault().postSticky(new LoginEvent());
                            getView().showManualLoginResult(true, "", "");
                        }
                        checkAppVersionChange();
                    }

                    @Override
                    public void onFailure(ApiException e) {
                        getView().showManualLoginResult(false, e.getMessage(), e.getCode());
                        //拦截不让弹出模态框
                        if (false)
                            super.onFailure(e);
                    }
                }
        );

        addDisposable(disposable);
    }


    @Override
    public void handle3rdPartLogin(String typeId) {
//        WeakReference<Activity> activityRef;
//        if (getView() instanceof BaseFragment) {
//            activityRef = new WeakReference<>(((BaseFragment) getView()).getActivity());
//        } else {
//            activityRef = new WeakReference<>((BaseActivity) getView());
//        }

        //IFree登录
        if (AccountPlatformType.TYPE_IFREE.equals(typeId)) {
            //getView().go2AuthActivity(AppConstant.IFREE_AUTH_URI);
            getAppIDAndToken();
            return;
        }

    }


    /**
     * @param openid   openId
     * @param avatar   头像
     * @param nickName 昵称
     */
    @Override
    public void realHandleIFreeLogin(String openid, String avatar, String nickName, String email) {

    }




    /**
     * iApp登陸
     */
    @Override
    public void IAppLogin(String email, String password) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("account", ifNull(email, ""));
        httpParams.put("password", ifNull(password, ""));



    }

    @Override
    public void getAppIDAndToken() {

    }

    @Override
    public void getIPLocation() {
        Disposable mDisposable = Observable.create((ObservableOnSubscribe<String>) e -> {
            //AppLogUtil.OY_D("location : "+ LocationUtils.Ip2Location("66.220.151.20"));
            e.onNext(LocationUtils.GetNetIp());
            e.onComplete();
        }).delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                    getView().getIPLocationResult(location);
                });

        addDisposable(mDisposable);
    }


    private void checkAppVersionChange() {
        AppLogUtil.OY_D("checkAppVersionChange : 登录");
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

    @Override
    public void detachView() {
        //处理内存泄漏
        super.detachView();
    }
}
