package com.happy.todo.lib_common.image.boxing;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.boxing.loader.IBoxingCallback;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.happy.todo.lib_common.image.GlideApp;
import com.happy.todo.lib_common.image.GlideRequest;

/**
 * @作者: TwoSX
 * @时间: 2018/1/26 下午3:01
 * @描述:
 */
public class BoxingGlideLoader implements IBoxingMediaLoader {
    @Override
    public void displayThumbnail(@NonNull ImageView img, @NonNull String absPath, int width, int
            height) {
        String path = "file://" + absPath;
        try {
            // https://github.com/bumptech/glide/issues/1531
            GlideApp.with(img.getContext()).load(path).centerCrop().override(width, height).into(img);
        } catch(IllegalArgumentException ignore) {

        }
    }

    @Override
    public void displayRaw(@NonNull ImageView img, @NonNull String absPath, int width, int
            height, IBoxingCallback callback) {
        String path = "file://" + absPath;
        GlideRequest<Bitmap> request = GlideApp.with(img.getContext())
                .asBitmap()
                .load(path);

        if (width > 0 && height > 0) {
            request =  request.override(width, height);
        }
        request.listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap>
                    target, boolean isFirstResource) {
                if (callback != null) {
                    callback.onFail(e);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (resource != null && callback != null) {
                    img.setImageBitmap(resource);
                    callback.onSuccess();
                    return true;
                }
                return false;
            }
        }).into(img);
    }
}
