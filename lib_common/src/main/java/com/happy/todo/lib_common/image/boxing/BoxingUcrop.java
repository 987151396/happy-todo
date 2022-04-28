package com.happy.todo.lib_common.image.boxing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bilibili.boxing.loader.IBoxingCrop;
import com.bilibili.boxing.model.config.BoxingCropOption;
import com.happy.todo.lib_common.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

/**
 * @作者: TwoSX
 * @时间: 2018/1/26 下午3:09
 * @描述:
 */
public class BoxingUcrop implements IBoxingCrop {
    @Override
    public void onStartCrop(Context context, Fragment fragment, @NonNull BoxingCropOption
            cropConfig, @NonNull String path, int requestCode) {
        Uri uri = new Uri.Builder()
                .scheme("file")
                .appendPath(path)
                .build();

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(context, R.color.black));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(context, R.color.black));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(false);
        //设置裁剪图片的宽高比，比如16：9
        options.withAspectRatio(1, 1);


        UCrop.of(uri, cropConfig.getDestination())
                .withOptions(options)
                .start(context, fragment, requestCode);


    }

    @Override
    public Uri onCropFinish(int resultCode, Intent data) {
        if (data == null) {
            return null;
        }
        Throwable throwable = UCrop.getError(data);
        if (throwable != null) {
            return null;
        }
        return UCrop.getOutput(data);
    }
}
