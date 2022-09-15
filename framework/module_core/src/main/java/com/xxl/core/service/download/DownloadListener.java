package com.xxl.core.service.download;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 下载监听
 *
 * @author xxl.
 * @date 2022/6/29.
 */
public interface DownloadListener {

    /**
     * 下载预处理完成
     *
     * @param taskEntity
     */
    default void onTaskPre(@NonNull final DownloadTaskEntity taskEntity) {

    }

    /**
     * 下载开始
     *
     * @param taskEntity
     */
    default void onTaskStart(@NonNull final DownloadTaskEntity taskEntity) {

    }

    /**
     * 下载完成
     *
     * @param taskEntity
     */
    default void onTaskComplete(@NonNull final DownloadTaskEntity taskEntity) {

    }

    /**
     * 下载中
     *
     * @param taskEntity
     */
    default void onTaskRunning(@NonNull final DownloadTaskEntity taskEntity) {

    }

    /**
     * 下载停止
     *
     * @param taskEntity
     */
    default void onTaskStop(@NonNull final DownloadTaskEntity taskEntity) {

    }

    /**
     * 下载继续
     *
     * @param taskEntity
     */
    default void onTaskResume(@NonNull final DownloadTaskEntity taskEntity) {

    }

    /**
     * 下载失败
     *
     * @param taskEntity
     * @param throwable
     * @param
     */
    default void onTaskFail(@NonNull final DownloadTaskEntity taskEntity,
                            @Nullable final Throwable throwable) {

    }

    /**
     * 下载取消
     *
     * @param taskEntity
     */
    default void onTaskCancel(@NonNull final DownloadTaskEntity taskEntity) {

    }

}