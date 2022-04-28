package com.happy.todo.lib_common.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import com.aitangba.swipeback.SwipeBackActivity;
import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.event.LanguageEvent;
import com.happy.todo.lib_common.utils.BarUtil;
import com.happy.todo.lib_common.utils.ToastUtil;
import com.happy.todo.lib_common.utils.language.LanguageUtil;
import com.happy.todo.lib_common.widget.dialog.AppLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @作者: TwoSX
 * @时间: 2017/11/28 下午4:30
 * @描述: 所有 ACTIVITY 的基类
 */
public abstract class BaseActivity extends SwipeBackActivity {

    private Unbinder mUnbinder;
    public String mActivityName;
    private AppLoadingDialog mLoadingDialog;
    private BaseApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindowConfig();
        setContentView(getLayoutId());
        application = (BaseApplication) getApplication();
        mUnbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initStatusBar();
        initToolbar();
        mActivityName = getClass().getSimpleName();
        EventBus.getDefault().register(this);
        initView();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtil.newInstance().attachBaseContext(newBase));
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    /**
     * 设置窗口类型，比如全屏，横竖屏等
     */
    @SuppressLint("SourceLockedOrientationActivity")
    protected void initWindowConfig() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    // 获取视图 id
    protected abstract int getLayoutId();

    //初始化视图
    protected abstract void initView();

    // 初始化状态栏
    protected void initStatusBar() {
        BarUtil.setStatusBarLightMode(this);
        BarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.white));
    }

    //初始化Toolbar
    protected abstract void initToolbar();

    @Override
    protected void onDestroy() {
        //解注册EventBus
        EventBus.getDefault().unregister(this);
        //解注册ButterKnife
        mUnbinder.unbind();
        //输入法的内存泄漏问题
//        InputManagerLeakFixer.removeOOMObject(this);

        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LanguageEvent event) {
        finish();//刷新界面
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }


    /**
     * 弹出Toast
     */
    public void showToast(String str) {
        if (!TextUtils.isEmpty(str)) {
            ToastUtil.showShort(str);
        }
    }

    /**
     * 弹出Toast
     */
    public void showToast(int str) {
        ToastUtil.showShort(str);
    }


    /**
     * 显示loading 窗口
     *
     * @param msg 标题
     */
    public void showLoading(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new AppLoadingDialog.Builder(this)
                    .setTxt(msg)
                    .create();
        }

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }


    /**
     * 取消loading 窗口
     */
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 设置可取消
     */
    public void setDialogIsCalcel(boolean isCancel) {
        mLoadingDialog.setCancelable(isCancel);
    }

    /**
     * 显示loading 窗口
     *
     * @param msg 标题
     */
    public void showLoading(String msg, boolean isCancel) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new AppLoadingDialog.Builder(this)
                    .setTxt(msg)
                    .create();
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
        setDialogIsCalcel(isCancel);

    }

}
