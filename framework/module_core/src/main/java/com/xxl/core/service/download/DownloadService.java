package com.xxl.core.service.download;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.xxl.kit.OnRequestCallBack;

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
     * 创建并启动下载任务
     *
     * @param activity        activity
     * @param downloadOptions 下载配置
     * @param callBack        回调监听
     * @return
     */
    void createDownloadTask(@NonNull final FragmentActivity activity,
                                        @NonNull final DownloadOptions downloadOptions,
                                        @NonNull final OnRequestCallBack<DownloadTaskInfo> callBack);

    /**
     * 停止下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    void stopDownloadTask(@Nullable final DownloadTaskInfo downloadTaskInfo);

    /**
     * 取消下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    void cancelDownloadTask(@Nullable final DownloadTaskInfo downloadTaskInfo);

    /**
     * 恢复下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    void resumeDownloadTask(@Nullable final DownloadTaskInfo downloadTaskInfo);
}