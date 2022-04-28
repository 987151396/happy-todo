package com.happy.todo.lib_common.image;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.DrawableRes;
import android.widget.ImageView;

import com.android.annotations.NonNull;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.happy.todo.lib_common.image.progress.OnProgressListener;
import com.happy.todo.lib_common.image.progress.ProgressManager;
import com.happy.todo.lib_common.image.transform.GlideCircleTransform;
import com.happy.todo.lib_common.widget.CircleBitmap;

import java.lang.ref.WeakReference;

/**
 * Created by twosx on 2017/11/23.
 */

public class AppImageLoaderConfig {

    private Handler mMainThreadHandler;

    private long mTotalBytes = 0;
    private long mLastBytesRead = 0;
    private boolean mLastStatus = false;

    private GlideRequests mGlideRequests;
    private boolean isGif;
    private boolean isNeedCrossFade = true;
    private RequestOptions mRequestOptions;
    private WeakReference<ImageView> mImageView;
    private Object mImageUrlObj;

    private OnProgressListener internalProgressListener;
    private OnProgressListener onProgressListener;


    public AppImageLoaderConfig(ImageView imageView) {
        mImageView = new WeakReference<>(imageView);
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public void setPlaceholder(@DrawableRes int arg0) {
        if (mRequestOptions == null) {
            mRequestOptions = new RequestOptions();
        }
        mRequestOptions = mRequestOptions.placeholder(arg0);
    }

    public void setDiskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        if (mRequestOptions == null) {
            mRequestOptions = new RequestOptions();
        }
        mRequestOptions = mRequestOptions.diskCacheStrategy(diskCacheStrategy);
    }

    public void setSkipMemoryCache(@NonNull boolean bool) {
        if (mRequestOptions == null) {
            mRequestOptions = new RequestOptions();
        }
        mRequestOptions = mRequestOptions.skipMemoryCache(bool);
    }

    public void setError(@DrawableRes int arg0) {
        if (mRequestOptions == null) {
            mRequestOptions = new RequestOptions();
        }
        mRequestOptions = mRequestOptions.error(arg0);
    }

    public OnProgressListener getOnProgressListener() {
        return onProgressListener;
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        if (onProgressListener == null) return;
        this.onProgressListener = onProgressListener;
        addProgressListener();
    }

    public ImageView getImageView() {
        if (mImageView != null) {
            return mImageView.get();
        }
        return null;
    }

    public long getTotalBytes() {
        return mTotalBytes;
    }

    public long getLastBytesRead() {
        return mLastBytesRead;
    }

    public boolean isLastStatus() {
        return mLastStatus;
    }

    public OnProgressListener getInternalProgressListener() {
        return internalProgressListener;
    }

    public void setRequestOptions(RequestOptions requestOptions) {
        mRequestOptions = requestOptions;
    }

    public RequestOptions getRequestOptions() {
        return mRequestOptions;
    }

    public void setGif(boolean gif) {
        isGif = gif;
    }

    public boolean isGif() {
        return isGif;
    }

    public boolean isNeedCrossFade() {
        return isNeedCrossFade;
    }

    public void setNeedCrossFade(boolean needCrossFade) {
        isNeedCrossFade = needCrossFade;
    }

    public GlideRequests getGlideRequests() {
        return mGlideRequests;
    }

    public void setGlideRequests(GlideRequests glideRequests) {
        mGlideRequests = glideRequests;
    }

    public Object getImageUrlObj() {
        return mImageUrlObj;
    }

    public void setImageUrlObj(Object imageUrlObj) {
        mImageUrlObj = imageUrlObj;
    }

    public String getImageUrl() {
        if (mImageUrlObj == null) return null;
        if (!(mImageUrlObj instanceof String)) return null;
        return (String) mImageUrlObj;
    }

    private void addProgressListener() {
        if (getImageUrl() == null) return;
        String url = getImageUrl();
        if (!url.startsWith("http")) return;

        internalProgressListener = new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
                if (totalBytes == 0) return;
                if (mLastBytesRead == bytesRead && mLastStatus == isDone) return;

                mLastBytesRead = bytesRead;
                mTotalBytes = totalBytes;
                mLastStatus = isDone;
                mainThreadCallback(bytesRead, totalBytes, isDone, exception);

                if (isDone) {
                    ProgressManager.removeProgressListener(imageUrl);
                }
            }
        };
        ProgressManager.addProgressListener(url, internalProgressListener);
    }

    public void mainThreadCallback(final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f); // 获取到百分比
                if (onProgressListener != null) {
                    onProgressListener.onProgress(getImageUrl(), bytesRead, totalBytes, isDone, exception);
                }
            }
        });
    }

    public void circle() {
        if (mRequestOptions == null) {
            mRequestOptions = new RequestOptions();
        }
        mRequestOptions = mRequestOptions.transform(new GlideCircleTransform());
    }


    /**
     * 图片圆角处理
     * note: 该做法是将原始图片进行转换, 这里强制使用了centerCrop，布局中无需再使用缩放
     * @param cornerSize 单位px
     */
    public void corner(int cornerSize) {
        if (mRequestOptions == null) {
            mRequestOptions = new RequestOptions();
        }

        mRequestOptions = mRequestOptions.transforms(new CenterCrop(), new RoundedCorners(cornerSize));
    }
    public void corner1(Context context, int cornerSize) {
        if (mRequestOptions == null) {
            mRequestOptions = new RequestOptions();
        }
        CircleBitmap circleBitmap = new CircleBitmap(context, cornerSize);
        mRequestOptions = mRequestOptions.transforms(new CenterCrop(),circleBitmap);
    }

    public void centerCrop() {
        if (mRequestOptions == null) {
            mRequestOptions = new RequestOptions();
        }

        mRequestOptions = mRequestOptions.transforms(new CenterCrop());
    }
}
