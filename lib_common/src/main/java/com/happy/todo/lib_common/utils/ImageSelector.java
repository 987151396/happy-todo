package com.happy.todo.lib_common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.config.BoxingCropOption;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.utils.BoxingFileHelper;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.happy.todo.lib_common.R;

import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 *
 * 相册选择器，已结合AndPermission的使用，因此无需关心使用该相册选择器时的权限申请问题
 *
 * 1. 初始化对象{@link #init(Activity)}
 * ImageSelector imageSelector = new ImageSelector();
 * imageSelector.init(activity, photoSize);
 *
 * 2. 使用{@link #start()}
 * imageSelector.start();
 *
 * 3. 处理回调{@link #handleImageSelectResult(int, int, Intent, OnImageSelectedListener)}
 * imageSelector.handleImageSelectResult(int, int, Intent, OnImageSelectedListener);
 *
 * Created by Jaminchanks on 2018/1/29.
 */

public class ImageSelector implements EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 0x00051;

    private static final int REQUEST_CODE_SELECT_IMAGE = 0x00052;

    private static final int REQUEST_CODE_SETTING = 0x00053;

    private List<String> mPermission;

    private Activity mActivity;
    private int mPhotoSize;
    private boolean mNeedCrop = false;

    public ImageSelector init(Activity activity) {
        init(activity, 1);
        return this;
    }

    public ImageSelector init(Activity activity, int photoSize) {
        this.mActivity = activity;
        this.mPhotoSize = photoSize;

        return this;
    }

    public void start() {
        applyCameraPermission();
    }

    /**
     * 设置所需的照片个数
     *
     * @param size
     */
    public ImageSelector setPhotoSize(int size) {
        this.mPhotoSize = size;
        return this;
    }

    public void setSingleImageNeedCrop(boolean isNeed) {
        this.mNeedCrop = isNeed;
    }

    /**
     * 打开相册选择器
     * 单张图片可剪裁，否则不可剪裁
     */
    private void openSelect() {
        BoxingConfig config;
        if (mPhotoSize == 1 && mNeedCrop) {
            String cachePath = BoxingFileHelper.getCacheDir(mActivity);
            Uri destUri = new Uri.Builder()
                    .scheme("file")
                    .appendPath(cachePath)
                    .appendPath(String.format(Locale.US, "%s.png", System.currentTimeMillis()))
                    .build();

            config = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
                    .withCropOption(new BoxingCropOption(destUri))
                    .needCamera(android.R.drawable.ic_menu_camera);
        } else {
            config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG)
                    .needCamera(android.R.drawable.ic_menu_camera).withMaxCount(mPhotoSize);
        }

        Boxing.of(config).withIntent(mActivity, BoxingActivity.class).start(mActivity, REQUEST_CODE_SELECT_IMAGE);
    }


    public void handleImageSelectResult(int requestCode, int resultCode, Intent data, OnImageSelectedListener listener) {
        //权限处理的回调
        if (requestCode == ImageSelector.REQUEST_CODE_SETTING) { //从设置中心回来
            applyCameraPermission();
        } else {
            if (RESULT_OK == resultCode && requestCode == ImageSelector.REQUEST_CODE_SELECT_IMAGE && data != null) {
                listener.handleResult(Boxing.getResult(data));
            }
        }
    }


    /**
     * 对照片选择的回调处理
     */
    public interface OnImageSelectedListener {
        void handleResult(List<BaseMedia> baseMedias);
    }


    /**
     * 申请权限
     */
    private static final String[] PERMISSIONS =
            {Manifest.permission.CAMERA};

    private static final int PERMISSIONCODE = 124;

    /**
     * 最终获取到权限成功之后，才开始倒计时和执行热修复相关的操作
     */
    private void afterGrantedPermission() {
        openSelect();
    }

    @AfterPermissionGranted(PERMISSIONCODE)
    public void applyCameraPermission() {
        if (hasPermissions()) {
            afterGrantedPermission();
        } else {
            EasyPermissions.requestPermissions(
                    mActivity,mActivity.getString(R.string.i_permission_rejected_hint),
                    PERMISSIONCODE,
                    PERMISSIONS);
        }
    }

    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(mActivity, PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        afterGrantedPermission();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(mActivity, perms)) {
            new AppSettingsDialog.Builder(mActivity).setRationale(R.string.i_permission_rejected_hint)
                    .setPositiveButton(R.string.i_permission_go_setting)
                    .setNegativeButton(R.string.i_permission_cancel).build().show();
        } else {
            new AppSettingsDialog.Builder(mActivity).setRationale(R.string.i_permission_rejected_hint)
                    .setPositiveButton(R.string.i_permission_go_setting)
                    .setNegativeButton(R.string.i_permission_cancel).build().show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mActivity.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
