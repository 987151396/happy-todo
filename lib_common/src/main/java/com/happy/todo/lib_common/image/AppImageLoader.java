package com.happy.todo.lib_common.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.happy.todo.lib_common.image.progress.OnProgressListener;
import com.happy.todo.lib_common.image.progress.ProgressManager;

/**
 * Created by twosx on 2017/11/23.
 */

public class AppImageLoader {

    private AppImageLoaderConfig mConfig;

    public static AppImageLoader create(ImageView imageView) {
        return new AppImageLoader(imageView);
    }

    private AppImageLoader(ImageView imageView) {
        mConfig = new AppImageLoaderConfig(imageView);
    }

    public AppImageLoader with(Context v) {
        mConfig.setGlideRequests(GlideApp.with(v));
        return this;
    }

    public AppImageLoader with(Activity v) {
        mConfig.setGlideRequests(GlideApp.with(v));
        return this;
    }

    public AppImageLoader with(Fragment v) {
        mConfig.setGlideRequests(GlideApp.with(v));
        return this;
    }

    public AppImageLoader with(View v) {
        mConfig.setGlideRequests(GlideApp.with(v));
        return this;
    }

    public AppImageLoader with(android.app.Fragment v) {
        mConfig.setGlideRequests(GlideApp.with(v));
        return this;
    }

    public AppImageLoader load(Object object) {
        mConfig.setImageUrlObj(object);
        return this;
    }

    public AppImageLoader apply(RequestOptions options) {
        mConfig.setRequestOptions(options);
        return this;
    }

    public AppImageLoader asGif() {
        mConfig.setGif(true);
        return this;
    }

    public AppImageLoader crossFade(boolean isCrossFade) {
        mConfig.setNeedCrossFade(isCrossFade);
        return this;
    }

    public AppImageLoader placeholder(@DrawableRes int arg0) {
        mConfig.setPlaceholder(arg0);
        return this;
    }

    public AppImageLoader error(int arg0) {
        mConfig.setError(arg0);
        return this;
    }

    public AppImageLoader listener(OnProgressListener onProgressListener) {
        mConfig.setOnProgressListener(onProgressListener);
        return this;
    }

    public AppImageLoader circle() {
        mConfig.circle();
        return this;
    }
    public AppImageLoader centerCrop() {
        mConfig.centerCrop();
        return this;
    }

    /**
     * 设置圆角
     * @param cornerSize 单位px
     * @return
     */
    public AppImageLoader corner(int cornerSize) {
        mConfig.corner(cornerSize);
        return this;
    }

    public AppImageLoader corner(Context context,int cornerSize) {
        mConfig.corner1(context,cornerSize);
        return this;
    }

    public AppImageLoader diskCacheStrategy(DiskCacheStrategy diskCacheStrategy){
        mConfig.setDiskCacheStrategy(diskCacheStrategy);
        return this;
    }

    public AppImageLoader skipMemoryCache(boolean bool){
        mConfig.setSkipMemoryCache(bool);
        return this;
    }

    @SuppressLint("CheckResult")
    public void execute() {
        GlideRequest glideRequest;
        if (mConfig.isGif()) {
            glideRequest = mConfig.getGlideRequests().asGif().load(mConfig.getImageUrlObj());
        } else {
            glideRequest = mConfig.getGlideRequests().load(mConfig.getImageUrlObj());
        }

        if (mConfig.getRequestOptions() != null) {
            glideRequest = glideRequest.apply(mConfig.getRequestOptions());
        }

        if (mConfig.getOnProgressListener() != null) {
            glideRequest = glideRequest.listener(new RequestListener<Drawable>() {
                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    mConfig.mainThreadCallback(mConfig.getLastBytesRead(), mConfig.getTotalBytes(), true, null);
                    ProgressManager.removeProgressListener(mConfig.getImageUrl());
                    return false;
                }

                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                    mConfig.mainThreadCallback(mConfig.getLastBytesRead(), mConfig.getTotalBytes(), true, e);
                    ProgressManager.removeProgressListener(mConfig.getImageUrl());
                    return false;
                }
            });
        }

        if(mConfig.isNeedCrossFade()){
            glideRequest = glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        glideRequest.into(mConfig.getImageView());
    }

}
