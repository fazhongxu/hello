package com.xxl.core.service.download;

import androidx.annotation.NonNull;

/**
 * 下载监听
 *
 * @author xxl.
 * @date 2022/6/29.
 */
public interface DownloadListener {

    // 开始下载
    // 下载进度
    // 下载完成
    // 下载失败
    // 停止下载
    // 继续下载
    // 取消下载

    /**
     * 下载预处理完成
     *
     * @param taskEntity
     */
    default void onTaskPre(@NonNull final DownloadTaskEntity taskEntity) {

    }

    /**
     * 开始下载
     */
    default void onStart() {

    }

    /**
     * 下载进度
     *
     * @param totalSize
     * @param currentSize
     */
    default void onProgress(int totalSize,
                            int currentSize) {

    }


}