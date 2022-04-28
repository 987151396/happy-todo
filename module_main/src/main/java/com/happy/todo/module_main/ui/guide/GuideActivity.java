package com.happy.todo.module_main.ui.guide;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.base.BaseActivity;
import com.happy.todo.lib_common.router.RouterPath;
import com.happy.todo.lib_common.utils.ResourceUtils;
import com.happy.todo.lib_common.utils.SPUtil;
import com.happy.todo.lib_common.utils.SizeUtil;
import com.happy.todo.lib_common.utils.ViewOnClickUtils;
import com.happy.todo.lib_common.widget.MyViewPager;
import com.happy.todo.module_main.R;
import com.happy.todo.module_main.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

@Route(path = RouterPath.GUIDE_PAGE)
public class GuideActivity extends BaseActivity  implements EasyPermissions.PermissionCallbacks{

    private MyViewPager vpGuide;
    private LinearLayout llGuidePointParent;
    private TextView tvGuideGreenPoint;
    private int mPointWidth;
    private List<GuideBean> guides;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_guide;
    }

    @Override
    protected void initView() {
        initData();
        vpGuide = findViewById(R.id.vp_guide);
        vpGuide.setScrollble(false);
        llGuidePointParent = findViewById(R.id.ll_guide_point_parent);
        tvGuideGreenPoint = findViewById(R.id.tv_guide_green_point);
//        tvNotNow = findViewById(R.id.tv_not_now);
//        btnGuideEnter = findViewById(R.id.btn_guide_enter);
        VpGuideAdapter vpGuideAdapter = new VpGuideAdapter(this, guides);
        vpGuide.setAdapter(vpGuideAdapter);
        vpGuideAdapter.setOnItemOnClickLisenter(new VpGuideAdapter.onItemOnClickLisenter() {
            @Override
            public void onEnter(View v, int pos) {
                if (ViewOnClickUtils.isFastClick()){
                    return;
                }
                applyLocationPermission();
            }

            @Override
            public void onCancel(View v, int pos) {
                if (ViewOnClickUtils.isFastClick()){
                    return;
                }
                afterGrantedPermission();
            }
        });
        //初始化viewpage
//        vpGuide.setPageTransformer(true, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {//-1 0 1
//                imitateQQ(page, position);
//            }
//        });
//        btnGuideEnter.setOnClickListener(v -> {
//
//        });
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //后期加页面判断
//                if (position==0){
//
//                }else{
//
//                }

                int len = (int) (mPointWidth * positionOffset) + position * mPointWidth;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvGuideGreenPoint.getLayoutParams();// 获取当前蓝点的布局参数
                params.leftMargin = len;// 设置左边距
                tvGuideGreenPoint.setLayoutParams(params);// 重新给蓝点设置布局参数
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        tvNotNow.setOnClickListener(v -> {
//
//        });

        // 获取视图树, 对layout结束事件进行监听
//        llGuidePointParent.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                    // 当layout执行结束后回调此方法
//                    @Override
//                    public void onGlobalLayout() {
//
//                        llGuidePointParent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                        mPointWidth = llGuidePointParent.getChildAt(1).getLeft() - llGuidePointParent.getChildAt(0).getLeft();
////                        AppLogUtil.e("圆点距离:" + mPointWidth);
//                    }
//                });

        //初始化圆点
        for (int i = 0; i < guides.size(); i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.main_shape_circle_gray);// 设置引导页默认圆点
            int size = SizeUtil.dp2px(8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            if (i > 0) {
                params.leftMargin = 10;// 设置圆点间隔
            }

            point.setLayoutParams(params);// 设置圆点的大小

            llGuidePointParent.addView(point);// 将圆点添加给线性布局
        }


    }

    @NonNull
    private void initData() {
        boolean isGuideOne = (boolean) SPUtil.newInstance().get("isGuideOne", true);
        if (!isGuideOne){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        guides = new ArrayList<>();
        GuideBean guideBean = new GuideBean();
//        GuideBean guideBean1 = new GuideBean();
//        GuideBean guideBean2 = new GuideBean();

        guideBean.setImages(R.mipmap.ic_main_guide1);
//        guideBean1.setImages(R.mipmap-xxhdpi.ic_main_guide2);
//        guideBean2.setImages(R.mipmap-xxhdpi.ic_main_guide3);

        //内容
        guideBean.setContent(ResourceUtils.getString(R.string.splash_content1));
//        guideBean1.setContent(ResourceUtils.getString(R.string.splash_content2));

//        guideBean2.setContent(ResourceUtils.getString(R.string.splash_content3));

        //标题
        guideBean.setTitle(ResourceUtils.getString(R.string.splash_title1));
//        guideBean1.setTitle(ResourceUtils.getString(R.string.splash_title2));
//        guideBean1.setBtnContent(ResourceUtils.getString(R.string.splash_btn_content1));
//        guideBean2.setTitle(ResourceUtils.getString(R.string.splash_title3));

        guideBean.setBtnContent(ResourceUtils.getString(R.string.splash_btn_content1));
//        guideBean1.setBtnContent(ResourceUtils.getString(R.string.splash_btn_content2));

        guides.add(guideBean);
//        guides.add(guideBean1);
//        guides.add(guideBean2);
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


    /**
     * 仿QQ的缩放动画效果
     */
    public void imitateQQ(View view, float position) {
        if (position >= -1 && position <= 1) {
            view.setPivotX(position > 0 ? 0 : view.getWidth() / 2);
            //view.setPivotY(view.getHeight()/2);
            view.setScaleX((float) ((1 - Math.abs(position) < 0.5) ? 0.5 : (1 - Math.abs(position))));
            view.setScaleY((float) ((1 - Math.abs(position) < 0.5) ? 0.5 : (1 - Math.abs(position))));
        }
    }

    /**
     * 申请权限
     */
    private static final String[] PERMISSIONS =
            {Manifest.permission.ACCESS_FINE_LOCATION};

    private static final int PERMISSIONCODE = 124;

    @AfterPermissionGranted(PERMISSIONCODE)
    public void applyLocationPermission() {
        if (hasPermissions()) {
            afterGrantedPermission();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.i_permission_rejected_hint),
                    PERMISSIONCODE,
                    PERMISSIONS);
        }
    }

    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        afterGrantedPermission();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)  .setRationale(R.string.i_permission_rejected_hint)
                    .setPositiveButton(R.string.i_permission_go_setting)
                    .setNegativeButton(R.string.i_permission_cancel).build().show();
        }else{
            afterGrantedPermission();
        }
    }

    private void afterGrantedPermission() {
        SPUtil.newInstance().putAndApply("isGuideOne", false);
        ARouter.getInstance().build(RouterPath.MAIN)
                .withTransition(R.anim.common_popup_fade_in, R.anim.common_popup_fade_out)
                .navigation(this);
        new Handler().postDelayed(() -> {
            finish();
        }, 3000);
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
