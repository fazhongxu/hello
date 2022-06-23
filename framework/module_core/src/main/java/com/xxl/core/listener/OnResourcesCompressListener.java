package com.xxl.core.listener;

import com.xxl.kit.ExceptionServiceManager;

/**
 * @Description 资源压缩回调监听
 * @Author: xxl
 * @Date: 2021/8/29 12:35 AM
 **/
public interface OnResourcesCompressListener {

    /**
     * 压缩完成
     *
     * @param filePath
     * @param width
     * @param height
     */
    void onComplete(final String filePath,
                    final long width,
                    final long height);

    /**
     * 压缩进度
     *
     * @param progress
     */
    default void onProgress(final float progress) {

    }

    /**
     * 压缩失败
     *
     * @param throwable
     */
    default void onFailure(final Throwable throwable) {
        ExceptionServiceManager.postCaughtException(throwable);
    }
}