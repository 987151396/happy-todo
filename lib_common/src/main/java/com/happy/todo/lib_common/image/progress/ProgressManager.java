package com.happy.todo.lib_common.image.progress;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.bumptech.glide.load.engine.GlideException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by twosx on 2017/11/23.
 */

public class ProgressManager {
    //    private static List<WeakReference<OnProgressListener>> listeners =
//            Collections.synchronizedList(new ArrayList<WeakReference<OnProgressListener>>());
    private static OkHttpClient okHttpClient;

    private static Map<String, WeakReference<OnProgressListener>> mListenerMap =
            Collections.synchronizedMap(new HashMap<String, WeakReference<OnProgressListener>>());

    private ProgressManager() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response = chain.proceed(request);
                            return response.newBuilder()
                                    .body(new ProgressResponseBody(request.url().toString(), response.body(), LISTENER))
                                    .build();
                        }
                    })
                    .build();
        }
        return okHttpClient;
    }

    private static final OnProgressListener LISTENER = new OnProgressListener() {
        @Override
        public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
            if (mListenerMap == null || mListenerMap.size() == 0) return;
            WeakReference<OnProgressListener> listener = mListenerMap.get(imageUrl);
            if (listener != null) {
                OnProgressListener progressListener = listener.get();
                if (progressListener == null) {
                    mListenerMap.remove(imageUrl);
                } else {
                    progressListener.onProgress(imageUrl, bytesRead, totalBytes, isDone, exception);
                }
            }
        }
    };

    public static void addProgressListener(String url, OnProgressListener progressListener) {
        if (progressListener == null || TextUtils.isEmpty(url)) return;

        if (mListenerMap.get(url) == null) {
            mListenerMap.put(url, new WeakReference<>(progressListener));
        }
    }

    public static void removeProgressListener(String url) {
        if (TextUtils.isEmpty(url)) return;
        mListenerMap.remove(url);
    }

}
