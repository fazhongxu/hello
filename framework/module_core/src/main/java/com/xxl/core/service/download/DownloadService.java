package com.xxl.core.service.download;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 下载服务
 *
 * @author xxl.
 * @date 2022/6/29.
 */
public interface DownloadService {

    /**
     * 注册下载服务
     *
     * @param application      上下文
     * @param downloadListener 下载监听
     */
    void register(@NonNull final Application application,
                  @Nullable final DownloadListener downloadListener);

    /**
     * 注销下载服务
     *
     * @param downloadListener 下载监听
     */
    void unRegister(@Nullable final DownloadListener downloadListener);

    /**
     * 创建并启动下载任务
     *
     * @param object          Activity、Service、Application、DialogFragment、Fragment、PopupWindow、Dialog
     * @param downloadOptions 下载配置
     * @return
     */
    DownloadTaskInfo createDownloadTask(@NonNull final Object object,
                                        @NonNull final DownloadOptions downloadOptions);

    /**
     * 停止下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    void stopDownloadTask(@Nullable final DownloadTaskInfo downloadTaskInfo);
}