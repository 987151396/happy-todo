package com.happy.todo.lib_common.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;

import com.aitangba.swipeback.ActivityLifecycleHelper;
import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;

import com.happy.todo.lib_common.BuildConfig;

import com.happy.todo.lib_common.db.DBHelper;
import com.happy.todo.lib_common.http.IFreeHttpHelper;
import com.happy.todo.lib_common.image.boxing.BoxingGlideLoader;
import com.happy.todo.lib_common.image.boxing.BoxingUcrop;
import com.happy.todo.lib_common.router.RouterHelper;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.AppUtil;
import com.happy.todo.lib_common.utils.CrashHandlerUtils;
import com.happy.todo.lib_common.utils.FileUtil;
import com.happy.todo.lib_common.utils.NetWorkListenerUtil;
import com.happy.todo.lib_common.utils.SPUtil;
import com.happy.todo.lib_common.utils.Utils;
import com.happy.todo.lib_common.utils.language.LanguageUtil;
import com.happy.todo.lib_common.widget.refresh.DefaultRefreshFooter;
import com.happy.todo.lib_common.widget.refresh.DefaultRefreshHeader;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaomai.environmentswitcher.EnvironmentSwitcher;


/**
 * @author: TwoSX
 * @description: Application 基类
 * @projectName: ebbly
 * @date: 2017/11/24
 * @time: 下午4:58
 */
public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    /*当前显示的Activity*/
    private Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
        //初始化内存泄漏分析
        if (AppUtil.isDebug()) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
           
        }

        Utils.init(this);

        SPUtil.newInstance().init(BaseApplication.this);
        DBHelper.init(this);
        RouterHelper.newInstance().init(this);
        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());


        LanguageUtil.newInstance().changeAppLanguage(getApplicationContext());

        // 初始化网络请求
        IFreeHttpHelper.init(this, BuildConfig.IS_DEBUG);


        // 初始化图片选择器
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());

        // 初始化美洽客服
        MQConfig.init(this, "5f265124dacea95975af14d09a85fc83", new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
            }

            @Override
            public void onFailure(int code, String message) {
            }
        });

        // 初始化 bugly
        CrashReport.initCrashReport(getApplicationContext(), "39bba8207c", AppUtil.isDebug());

        //初始化下拉刷新配置
        configRefreshLayout();

        //初始化崩溃通知
        CrashHandlerUtils.getInstance().init(this);
        try {
            //初始化网络监听
            NetWorkListenerUtil.getInstance().register(this);
        }catch (Exception e){
            AppLogUtil.e("虚拟机或部分手机没有权限不支持监听网络");
        }
        //添加切换测试环境的回调
        if(AppUtil.isDebug()) {
            initEnvironmentSwitcher();
        }
    }

    private void initEnvironmentSwitcher() {
        EnvironmentSwitcher.addOnEnvironmentChangeListener((module, oldEnvironment, newEnvironment) -> {
            if (module.equals(EnvironmentSwitcher.MODULE_APP)) {
                if (newEnvironment.equals(EnvironmentSwitcher.APP_ONLINE_ENVIRONMENT)) {
                    // 初始化网络请求
                    IFreeHttpHelper.init(this, BuildConfig.IS_DEBUG);
                } else if (newEnvironment.equals(EnvironmentSwitcher.APP_TEST_ENVIRONMENT)) {
                    // 初始化网络请求
                    IFreeHttpHelper.init(this, BuildConfig.IS_DEBUG);
                } else if (newEnvironment.equals(EnvironmentSwitcher.APP_RELEASETEST_ENVIRONMENT)){
                    // 初始化网络请求
                    IFreeHttpHelper.init(this, BuildConfig.IS_DEBUG);
                } else if (newEnvironment.equals(EnvironmentSwitcher.APP_UATTEST_ENVIRONMENT)){
                    // 初始化网络请求
                    IFreeHttpHelper.init(this, BuildConfig.IS_DEBUG);
                }
            }
        });
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
     * Handling Configuration Changes
     *
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LanguageUtil.newInstance().changeAppLanguage(getApplicationContext());
    }


    /**
     * 设置下拉刷新
     */
    private void configRefreshLayout() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new DefaultRefreshHeader(getApplicationContext()));

        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new DefaultRefreshFooter(getApplicationContext()));
    }


    /**
     * 获取 APP 在 SD 卡上的目录，没有则进行创建
     *
     * @return
     */
    public static String getAppSDDirectory() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (FileUtil.createOrExistsDir(path + "/ebbly")) {
            FileUtil.createOrExistsDir(path + "/ebbly/image");
        }
        return path + "/ebbly";
    }

    public static boolean isOneStart = true;


    /**
     * 显示View
     *
     * @param view 需要显示到Activity的视图
     */
    public void showView(View view) {
        /*Activity不为空并且没有被释放掉*/
        if (this.activity != null && !this.activity.isFinishing()) {
            /*获取Activity顶层视图,并添加自定义View*/
            ((ViewGroup) this.activity.getWindow().getDecorView()).addView(view);
        }
    }

    /**
     * 隐藏View
     *
     * @param view 需要从Activity中移除的视图
     */
    public void hideView(View view) {
        /*Activity不为空并且没有被释放掉*/
        if (this.activity != null && !this.activity.isFinishing()) {
            /*获取Activity顶层视图*/
            ViewGroup root = ((ViewGroup) this.activity.getWindow().getDecorView());
            /*如果Activity中存在View对象则删除*/
            if (root.indexOfChild(view) != -1) {
                /*从顶层视图中删除*/
                root.removeView(view);
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        /*获取当前显示的Activity*/
        //this.activity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        /*获取当前显示的Activity*/
        this.activity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        mService.shutdown();
//    }
}
