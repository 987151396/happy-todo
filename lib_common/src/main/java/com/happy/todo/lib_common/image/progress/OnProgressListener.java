package com.happy.todo.lib_common.image.progress;

import com.bumptech.glide.load.engine.GlideException;

/**
 * 监听下载进度回调
 * Created by twosx on 2017/11/23.
 */
public interface OnProgressListener {
    void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone,
                    GlideException exception);
}
