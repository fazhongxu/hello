package com.xxl.hello.service.process;

import com.xxl.core.manager.ExceptionServiceManager;

/**
 * 资源上传回调
 *
 * @author xxl.
 * @date 2022/5/28.
 */
public interface OnResourcesUploadCallback {

    /**
     * 开始上传
     */
    default void onStart() {

    }

    /**
     * 上传进度
     *
     * @param progress
     */
    default void onProgress(final float progress) {

    }

    /**
     * 上传完成
     *
     * @param targetUrl
     */
    void onComplete(final String targetUrl);

    /**
     * 上传失败
     *
     * @param throwable
     */
    default void onFailure(final Throwable throwable) {
        ExceptionServiceManager.postCaughtException(throwable);
    }
}