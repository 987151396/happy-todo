package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.image.AppImageLoader;
import com.happy.todo.lib_common.image.GlideApp;
import com.happy.todo.lib_common.utils.ResourceUtils;
import com.happy.todo.lib_common.utils.ViewUtil;
import com.happy.todo.lib_http.exception.ApiException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @作者: TwoSX
 * @时间: 2018/1/17 下午3:06
 * @描述: 数据加载层
 */
public class DataLoadingLayout extends FrameLayout {

    private static final int DEFAULT_LOADING_LAYOUT = R.layout.data_loading_default;
    private static final int DEFAULT_ERROR_LAYOUT = R.layout.data_loading_error_default;
    private static final int DEFAULT_EMPTY_LAYOUT = R.layout.data_loading_empty_default;
    private static final int DEFAULT_NET_ERROR_LAYOUT = R.layout.data_loading_error_default;

    private int mLoadingLayout;
    private int mErrorLayout;
    private int mEmptyLayout;
    private int mNetErrorLayout;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mNetErrorView;

    private String mErrorText;
    private String mEmptyText;
    private String mNetText;

    private boolean mIsLoadingText = true;

    private OnErrorReloadListener mOnErrorReloadListener;
    private onEmptyReloadListener mOnEmptyReloadListener;

    private Runnable runnable;



    public DataLoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public DataLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    public void setOnErrorReloadListener(OnErrorReloadListener onErrorReloadListener) {
        mOnErrorReloadListener = onErrorReloadListener;
    }

    public void setOnEmptyReloadListener(onEmptyReloadListener onEmptyReloadListener) {
        mOnEmptyReloadListener = onEmptyReloadListener;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getErrorView() {
        return mErrorView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    private void initView(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DataLoadingLayout);

        mLoadingLayout = typedArray.getResourceId(R.styleable.DataLoadingLayout_loadingLayout, DEFAULT_LOADING_LAYOUT);
        mErrorLayout = typedArray.getResourceId(R.styleable.DataLoadingLayout_errorLayout, DEFAULT_ERROR_LAYOUT);
        mNetErrorLayout = typedArray.getResourceId(R.styleable.DataLoadingLayout_netLayout, DEFAULT_NET_ERROR_LAYOUT);
        mEmptyLayout = typedArray.getResourceId(R.styleable.DataLoadingLayout_emptyLayout, DEFAULT_EMPTY_LAYOUT);

        mErrorText = typedArray.getString(R.styleable.DataLoadingLayout_errorText);
        mEmptyText = typedArray.getString(R.styleable.DataLoadingLayout_emptyText);
        mNetText = typedArray.getString(R.styleable.DataLoadingLayout_netText);

        mLoadingView = LayoutInflater.from(getContext()).inflate(mLoadingLayout, null);
        mErrorView = LayoutInflater.from(getContext()).inflate(mErrorLayout, null);
        mNetErrorView = LayoutInflater.from(getContext()).inflate(mNetErrorLayout, null);
        mEmptyView = LayoutInflater.from(getContext()).inflate(mEmptyLayout, null);

        addView(mEmptyView);
        addView(mErrorView);
        addView(mLoadingView);
        addView(mNetErrorView);

        if (mLoadingLayout == DEFAULT_LOADING_LAYOUT) {
            ImageView iv_loading = mLoadingView.findViewById(R.id.iv_loading);
            AppImageLoader.create(iv_loading).with(getContext()).asGif().load(R.mipmap.gif_loading).execute();
            TextView tv_loading = mLoadingView.findViewById(R.id.tv_loading);
            tv_loading.setVisibility(mIsLoadingText ? VISIBLE : GONE);
        }

        if (mErrorLayout == DEFAULT_ERROR_LAYOUT) {
            if (TextUtils.isEmpty(mErrorText)) {
                mErrorText = ResourceUtils.getString(R.string.hint_error);
            }
            TextView tv_error = mErrorView.findViewById(R.id.tv_error);
            tv_error.setText(mErrorText);
            ImageView iv_error = mErrorView.findViewById(R.id.iv_error);
            iv_error.setBackgroundResource(R.mipmap.hint_error_state);
            mErrorView.findViewById(R.id.tv_ok).setVisibility(GONE);
            //GlideApp.with(getContext()).asGif().load(R.mipmap.hint_error_state).into(iv_error);
        }
        if (mNetErrorLayout == DEFAULT_NET_ERROR_LAYOUT) {
            if (TextUtils.isEmpty(mNetText)) {
                mNetText = ResourceUtils.getString(R.string.network_null);
            }
            TextView tv_error = mNetErrorView.findViewById(R.id.tv_error);
            tv_error.setText(mNetText);
            ImageView iv_error = mNetErrorView.findViewById(R.id.iv_error);
            iv_error.setBackgroundResource(R.mipmap.hint_net_error_state);
            //GlideApp.with(getContext()).asGif().load(R.mipmap.hint_error_state).into(iv_error);
        }

        if (mEmptyLayout == DEFAULT_EMPTY_LAYOUT) {
            if (!TextUtils.isEmpty(mEmptyText)) {
                TextView tv_empty = mEmptyView.findViewById(R.id.tv_empty);
                tv_empty.setText(mEmptyText);
            }
        }

        mErrorView.setOnClickListener(view -> {
            if (mOnErrorReloadListener != null) {
                showLoading();
                mOnErrorReloadListener.onReload();
            }
        });

        mNetErrorView.setOnClickListener(view -> {
            if (mOnErrorReloadListener != null) {
                showLoading();
                mOnErrorReloadListener.onReload();
            }
        });

        mEmptyView.setOnClickListener(v -> {
            if (mOnEmptyReloadListener != null) {
                mOnEmptyReloadListener.onClick();
            }
        });
        mEmptyView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mNetErrorView.setVisibility(GONE);
        mLoadingView.setVisibility(VISIBLE);
    }

    public void showLoading() {
        mEmptyView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mNetErrorView.setVisibility(GONE);
        mLoadingView.setVisibility(VISIBLE);
    }

    public void showEmpty() {
        mEmptyView.setVisibility(VISIBLE);
        mErrorView.setVisibility(GONE);
        mNetErrorView.setVisibility(GONE);
        mLoadingView.setVisibility(GONE);
        setVisibility(VISIBLE);
    }

    public void showError() {
        if(ApiException.getEx() != null){
            switch (ApiException.getEx().getCode()) {
                case ApiException.ERROR.NETWORD_ERROR + "":
                case ApiException.ERROR.TIMEOUT_ERROR + "":
                    mErrorView.setVisibility(GONE);
                    mNetErrorView.setVisibility(VISIBLE);
                    break;
                default:
                    mErrorView.setVisibility(VISIBLE);
                    mNetErrorView.setVisibility(GONE);
                    break;
            }
        }
        mEmptyView.setVisibility(GONE);
        mLoadingView.setVisibility(GONE);
        setVisibility(VISIBLE);
    }

    /*public void showNetError() {
        mEmptyView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mNetErrorView.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        setVisibility(VISIBLE);
    }*/


    public void hide() {
        //设置延时可能出现闪屏
        ViewUtil.fadeOut(this, 0, null, true);
    }

    public void show() {
        setVisibility(VISIBLE);
        mEmptyView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mLoadingView.setVisibility(VISIBLE);
    }

    public boolean isLoading() {
        return getVisibility() == VISIBLE;
    }

    public interface OnErrorReloadListener {
        void onReload();
    }

    public interface onEmptyReloadListener {
        void onClick();
    }

    /**
     * 空白页的文字显示和按钮事件处理
     *
     * @param tip
     * @param onClickListener
     */
    public void setEmptyViewEvent(String tip, OnClickListener onClickListener) {
        ((TextView) mEmptyView.findViewById(R.id.tv_empty)).setText(tip + "");
        mEmptyView.findViewById(R.id.btn_empty).setOnClickListener(onClickListener);
    }


    /**
     * @param res    图片地址
     * @param isLoop 是否循环播放
     */
    public void showEmpty(int res, boolean isLoop) {
        showEmpty();
        ImageView iv_empty = mEmptyView.findViewById(R.id.iv_empty);
        if (isLoop) {
            GlideApp.with(getContext()).asGif().load(res).into(iv_empty);
        } else {
            GlideApp.with(getContext()).asGif().load(res).into(new SimpleTarget<GifDrawable>() {
                @Override
                public void onResourceReady(@NonNull GifDrawable drawable, @Nullable Transition<? super GifDrawable> transition) {
                    GifDrawable gifDrawable = drawable;
                    if (gifDrawable != null) {
                        gifDrawable.setLoopCount(1);
                        iv_empty.setImageDrawable(drawable);
                        gifDrawable.start();
                    }
                }
            });
        }
    }

    public void showEmpty(int res) {
        showEmpty();
        ImageView iv_empty = mEmptyView.findViewById(R.id.iv_empty);
        iv_empty.setImageResource(res);
    }

    private Runnable getRunnable( Handler handler) {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (gifDrawable!=null) {
                        gifDrawable.start();
                    }
                }catch (Exception e){
                }
                handler.postDelayed(this, 3000);
            }
        };
        return runnable;
    }
    private GifDrawable gifDrawable;
    private Disposable mTimeCountDisposable;

    /**
     * @param res  图片地址
     * @param time 循环播放间隔时间
     */
    public void showEmpty(int res, long time) {
        showEmpty();
        ImageView iv_empty = mEmptyView.findViewById(R.id.iv_empty);

        if (mTimeCountDisposable != null) {
            //取消上一次的订阅
            mTimeCountDisposable.dispose();
        }
        //0-count依次输出，延时0s执行，每1s发射一次。
        mTimeCountDisposable = Flowable.intervalRange(0, 100, 0, time, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                    try {
                        GlideApp.with(getContext()).asGif().load(res).into(new SimpleTarget<GifDrawable>() {
                            @Override
                            public void onResourceReady(@NonNull GifDrawable drawable, @Nullable Transition<? super GifDrawable> transition) {
                                gifDrawable = drawable;
                                gifDrawable.setLoopCount(1);
                                iv_empty.setImageDrawable(drawable);
                                gifDrawable.start();

                            }
                        });
                    }catch (Exception e){
                    }
                })
                .doOnComplete(() -> {
                    if (mTimeCountDisposable != null)
                        mTimeCountDisposable.dispose();
                })
                .subscribe();



    }

    /**
     * 显示空白页提示文字
     *
     * @param text
     */
    public void setEmptyViewTip(String text) {
        ((TextView) mEmptyView.findViewById(R.id.tv_empty)).setText(text + "");
    }


    /**
     * 显示错误页提示文字
     *
     * @param text
     */
    public void setErrorViewTip(String text) {
        ((TextView) mErrorView.findViewById(R.id.tv_error)).setText(text + "");
    }

    /**
     * 该方法与onReload方法可能冲突
     *
     * @param text
     * @param onErrorReloadListener
     */
    public void setErrorViewEvent(String text, OnErrorReloadListener onErrorReloadListener) {
        ((TextView) mErrorView.findViewById(R.id.tv_error)).setText(text + "");
        setOnErrorReloadListener(onErrorReloadListener);
    }

    /**
     * 显示加载也提示文字
     *
     * @param text
     */
    public void setLoadingViewTip(String text) {
        ((TextView) mLoadingView.findViewById(R.id.tv_loading)).setText(text + "");
    }


    /**
     * 销毁时触发
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTimeCountDisposable != null) {
            //取消上一次的订阅
            mTimeCountDisposable.dispose();
        }
        if (gifDrawable!=null){
            gifDrawable=null;
        }
    }
}



