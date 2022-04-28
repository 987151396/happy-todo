package com.happy.todo.module_main.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.AppConstant;
import com.happy.todo.lib_common.base.BaseActivity;
import com.happy.todo.lib_common.bean.AppUpDateBean;
import com.happy.todo.lib_common.event.LoginEvent;
import com.happy.todo.lib_common.event.MainTabChangeEvent;
import com.happy.todo.lib_common.event.WebLanChangeEvnet;
import com.happy.todo.lib_common.http.IFreeHttpHelper;
import com.happy.todo.lib_common.http.SimpleAppCallBack;
import com.happy.todo.lib_common.http.api.AppApi;
import com.happy.todo.lib_common.manage.LoginManage;
import com.happy.todo.lib_common.router.RouterPath;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.AppUtil;
import com.happy.todo.lib_common.utils.DateUtil;
import com.happy.todo.lib_common.utils.DeviceUtil;
import com.happy.todo.lib_common.utils.UserUtils;
import com.happy.todo.lib_common.utils.Utils;
import com.happy.todo.lib_common.utils.language.LanguageUtil;
import com.happy.todo.lib_common.widget.NoScrollViewPager;
import com.happy.todo.module_main.R;
import com.happy.todo.module_main.R2;
import com.happy.todo.module_main.ui.version.UpdateServer;
import com.happy.todo.lib_http.exception.ApiException;
import com.happy.todo.lib_http.model.HttpParams;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static com.happy.todo.lib_common.AppConstant.EXTRA_SELECT_TAB;
import static com.happy.todo.lib_common.AppConstant.EXTRA_SELECT_TAB_DISCOVER;
import static com.happy.todo.lib_common.AppConstant.EXTRA_SELECT_TAB_DISCOVER_CITY;

@Route(path = RouterPath.MAIN)
public class MainActivity extends BaseActivity {

    @BindView(R2.id.vp_content)
    NoScrollViewPager mVpContent;
    @BindView(R2.id.iv_search)
    ImageView mIvSearch;
    @BindView(R2.id.view_tab_1)
    LinearLayout mViewTab1;
    @BindView(R2.id.iv_mine)
    ImageView mIvMine;
    @BindView(R2.id.iv_discover)
    ImageView mIvDiscover;
    @BindView(R2.id.iv_discover_anima)
    LottieAnimationView mIvDiscoverAnima;
    @BindView(R2.id.iv_mine_anima)
    LottieAnimationView ivMineAnima;
    @BindView(R2.id.iv_search_anima)
    LottieAnimationView ivSearchAnima;
    @BindView(R2.id.view_tab_2)
    LinearLayout mViewTab2;
    @BindView(R2.id.view_tab_3)
    LinearLayout mViewTab3;
    @Autowired(name = EXTRA_SELECT_TAB_DISCOVER)
    boolean isUpdate = false;
    @Autowired(name = EXTRA_SELECT_TAB_DISCOVER_CITY)
    String mCity = "";
    @Autowired(name = EXTRA_SELECT_TAB)
    int mSelectTab = 1;
    Fragment mHotelFragment;


    private MainContentViewPagerAdapter mMainContentViewPagerAdapter;
    private Disposable mDisposable;

    private Disposable appUpDateDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void initView() {
        canBeSlideBack();
        AppConstant.IS_REFRESH_SICOVER = isUpdate;
        if (!TextUtils.isEmpty(mCity))
            AppConstant.REFRESH_SICOVER_CITY_CODE = mCity;
        Intent intent = new Intent(Utils.getApp(), UpdateServer.class);
        startService(intent);
        // 获取Fragment
        mHotelFragment = (Fragment) ARouter.getInstance().build(RouterPath.HOTEL_MAIN)
                .navigation();


        mHotelFragment = mHotelFragment == null ? new TestFragment() : mHotelFragment;



        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(mHotelFragment);



        mMainContentViewPagerAdapter = new MainContentViewPagerAdapter(getSupportFragmentManager
                (), fragmentList);
        mVpContent.setAdapter(mMainContentViewPagerAdapter);
        mVpContent.setOffscreenPageLimit(fragmentList.size());
        mViewTab1.setOnClickListener(view -> MainActivity.this.selectTab(1));
        mViewTab2.setOnClickListener(view -> {
            MainActivity.this.selectTab(2);
        });
        mViewTab3.setOnClickListener(view -> MainActivity.this.selectTab(3));
        //开启动画的硬件加速
        mIvDiscoverAnima.useHardwareAcceleration();
        ivMineAnima.useHardwareAcceleration();
        ivSearchAnima.useHardwareAcceleration();

        selectTab(mSelectTab);

        checkAppVersionChange();
    }

    private void checkAppVersionChange() {
        if (LoginManage.isLogin() && !UserUtils.getAppVersionChangedName().equals(AppUtil.getAppVersionName())) {
            AppLogUtil.OY_D("checkAppVersionChange");
            HttpParams params = new HttpParams();
            params.put("time", DateUtil.timestampToString1(DateUtil.getTimestamp() / 1000));
            params.put("version", AppUtil.getAppVersionName());

            appUpDateDisposable = IFreeHttpHelper.post(AppApi.APP_CLIENT_VERSION, params, new SimpleAppCallBack<AppUpDateBean>() {
                @Override
                public void onFailure(ApiException e) {
                }

                @Override
                public void onSuccess(AppUpDateBean data) {
                    UserUtils.saveAppVersionChangedName();
                }
            });
        }
    }




    /**
     * bottom nav select
     *
     * @param index
     */
    private void selectTab(int index) {
        if (mMainContentViewPagerAdapter.getCount() >= index) {
            //playHideAnimation();
            if (index == 1) {
                mViewTab1.setSelected(true);
                mViewTab2.setSelected(false);
                mViewTab3.setSelected(false);

                mIvDiscoverAnima.setVisibility(View.INVISIBLE);
                ivMineAnima.setVisibility(View.INVISIBLE);
                ivSearchAnima.setVisibility(View.VISIBLE);

                ivSearchAnima.setSpeed(1.0f);
                ivSearchAnima.playAnimation();
            } else if (index == 2) {
//                LoginUtils.setLoginTag(LoginUtils.LoginTag.GO_HOME);
                mViewTab1.setSelected(false);
                mViewTab2.setSelected(true);
                mViewTab3.setSelected(false);

                mIvDiscoverAnima.setVisibility(View.VISIBLE);
                ivMineAnima.setVisibility(View.INVISIBLE);
                ivSearchAnima.setVisibility(View.INVISIBLE);

                mIvDiscoverAnima.setSpeed(1.0f);
                mIvDiscoverAnima.playAnimation();


            } else if (index == 3) {
//                LoginUtils.setLoginTag(LoginUtils.LoginTag.GO_HOME);
                mViewTab1.setSelected(false);
                mViewTab2.setSelected(false);
                mViewTab3.setSelected(true);

                mIvDiscoverAnima.setVisibility(View.INVISIBLE);
                ivMineAnima.setVisibility(View.VISIBLE);
                ivSearchAnima.setVisibility(View.INVISIBLE);

                ivMineAnima.setSpeed(1.0f);
                ivMineAnima.playAnimation();


            } else {
                return;
            }

            mVpContent.setCurrentItem(index - 1, false);
        }

    }

    public void playHideAnimation() {
        //ottie的setSpeed是可以通过设置为负数来实现动画倒放的
        if (mViewTab1.isSelected()) {
            ivSearchAnima.setSpeed((float) -4.0);
            ivSearchAnima.playAnimation();
        } else if (mViewTab2.isSelected()) {
            mIvDiscoverAnima.setSpeed((float) -4.0);
            mIvDiscoverAnima.playAnimation();
        } else if (mViewTab3.isSelected()) {
            ivMineAnima.setSpeed((float) -4.0);
            ivMineAnima.playAnimation();
        }
    }

    @Override
    protected void initStatusBar() {
        if (DeviceUtil.getSDKVersion() <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return;
        }
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       
        mHotelFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        selectTab(mSelectTab);
    }

    //登录触发
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent event) {
        AppLogUtil.e("登录成功");
        //上传设备码
        if (LoginManage.isLogin()) {


        }
    }



    @Override
    public boolean supportSlideBack() {
        return false;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IFreeHttpHelper.cancel(mDisposable);
        IFreeHttpHelper.cancel(appUpDateDisposable);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MainTabChangeEvent event) {
        selectTab(event.getTab());
    }


    @SuppressLint("WrongConstant")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebLanChangeEvnet event) {
        LanguageUtil.newInstance().updateLanguageForWeb(event.getLanguageType());
        if (LoginManage.isLogin()) {

        }
        new Handler().postDelayed(() -> ARouter.getInstance().build(RouterPath.MAIN)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation(),300);
    }
}
