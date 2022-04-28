package com.happy.todo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import androidx.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;

import com.happy.todo.lib_common.base.BaseActivity;

import com.happy.todo.lib_common.router.RouterPath;

import com.happy.todo.lib_common.utils.SPUtil;
import com.happy.todo.lib_common.utils.Utils;
import com.happy.todo.module_main.R;
import com.happy.todo.server.SplashServer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Jaminchanks on 2018/6/21.
 */

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        Intent intent = new Intent(Utils.getApp(), SplashServer.class);
        try {
            startService(intent);
        } catch (Exception e) {

        }

        handlePermissions();
    }

    @Override
    protected void initToolbar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }


    @Override
    protected void initStatusBar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_splash;
    }

    /**
     * 最终获取到权限成功之后，才开始倒计时和执行热修复相关的操作
     */
    private void afterGrantedPermission() {
        CountTimeToGoMainPage();
    }


    @SuppressLint("CheckResult")
    private void CountTimeToGoMainPage() {
        Observable.just((boolean) SPUtil.newInstance().get("isGuideOne", true))
                .subscribeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isGuideOne -> {
                    if (!isGuideOne) {
                        ARouter.getInstance().build(RouterPath.MAIN)
                                .withTransition(R.anim.common_popup_fade_in, R.anim.common_popup_fade_out)
//                                .withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(this))
                                .navigation(this);
                        //延时销毁，避免白屏
                        new Handler().postDelayed(this::finish, 3000);

                    } else {
                        ARouter.getInstance().build(RouterPath.GUIDE_PAGE)
                                .navigation();
                        finish();
                    }

                });


    }



    /**
     * 申请权限
     */
    private static final String[] STRORAGE =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int RC_READ_EXTERNAL_STORAGE = 124;

    @AfterPermissionGranted(RC_READ_EXTERNAL_STORAGE)
    public void handlePermissions() {
        if (hasPermissions()) {
            afterGrantedPermission();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.i_permission_rejected_hint),
                    RC_READ_EXTERNAL_STORAGE,
                    STRORAGE);
        }
    }

    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, STRORAGE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        afterGrantedPermission();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).setRationale(R.string.i_permission_rejected_hint)
                    .setPositiveButton(R.string.i_permission_go_setting)
                    .setNegativeButton(R.string.i_permission_cancel).build().show();
        } else {
            CountTimeToGoMainPage();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
