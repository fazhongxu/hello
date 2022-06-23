package com.xxl.hello.service.process;

import com.xxl.kit.ExceptionServiceManager;

/**
 * 资源压缩回调
 *
 * @author : xxl
 * @date 2022/5/28.
 **/
public interface OnResourcesCompressCallback {

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