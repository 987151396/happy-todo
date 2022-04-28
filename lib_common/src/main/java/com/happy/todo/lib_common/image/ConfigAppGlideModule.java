package com.happy.todo.lib_common.image;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.happy.todo.lib_common.image.progress.ProgressManager;

import java.io.InputStream;

/**
 * Created by twosx on 2017/11/23.
 */

@GlideModule
public class ConfigAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置Bitmap的缓存池
        builder.setBitmapPool(new LruBitmapPool(30 * 1024 * 1024));
        //设置内存缓存
        builder.setMemoryCache(new LruResourceCache(30 * 1024 * 1024));
        //设置磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 100 * 1024 * 1024));
        //设置读取不在缓存中资源的线程
        builder.setResizeExecutor(GlideExecutor.newSourceExecutor());

        //设置读取磁盘缓存中资源的线程
        builder.setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor());

        //设置日志级别
        builder.setLogLevel(Log.DEBUG);

//        //设置全局选项
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.encodeFormat(Bitmap.CompressFormat.WEBP);//图片格式
//        requestOptions.encodeQuality(90);//图片质量
//        requestOptions.format(DecodeFormat.PREFER_RGB_565);//图片模式
//        builder.setDefaultRequestOptions(requestOptions);

    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
