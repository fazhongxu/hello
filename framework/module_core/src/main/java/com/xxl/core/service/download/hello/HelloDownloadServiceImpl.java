package com.xxl.core.service.download.hello;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.core.service.download.DownloadListener;
import com.xxl.core.service.download.DownloadOptions;
import com.xxl.core.service.download.DownloadService;
import com.xxl.core.service.download.DownloadServiceUtils;
import com.xxl.core.service.download.DownloadTaskInfo;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello 下载服务
 *
 * @author xxl.
 * @date 2022/7/5.
 */
public class HelloDownloadServiceImpl implements DownloadService, HelloDownloadWrapper.OnDownloadListener {

    //region: 成员变量

    /**
     * tag
     */
    private static final String TAG = "Hello ";

    /**
     * 下载监听
     */
    private List<DownloadListener> mDownloadListeners = new ArrayList<>();

    //endregion

    //region: 构造函数

    public HelloDownloadServiceImpl() {

    }

    //region: DownloadService

    /**
     * 注册下载服务
     *
     * @param application      上下文
     * @param downloadListener 下载监听
     */
    @Override
    public void register(@NonNull Application application,
                         @Nullable DownloadListener downloadListener) {
        if (downloadListener != null) {
            mDownloadListeners.add(downloadListener);
        }
    }

    /**
     * 注销下载服务
     *
     * @param downloadListener 下载监听
     */
    @Override
    public void unRegister(@Nullable DownloadListener downloadListener) {

        if (downloadListener != null) {
            mDownloadListeners.remove(downloadListener);
        }
    }

    /**
     * 创建并启动下载任务
     *
     * @param object          Activity、Service、Application、DialogFragment、Fragment、PopupWindow、Dialog
     * @param downloadOptions 下载配置
     * @return
     */
    @Override
    public DownloadTaskInfo createDownloadTask(@NonNull Object object,
                                               @NonNull DownloadOptions downloadOptions) {
        final int taskId = HelloDownloadWrapper.getInstance()
                .create(downloadOptions.getUrl(), downloadOptions.getFilePath(), this);

        return DownloadTaskInfo.create(downloadOptions.getTargetDownloadTag())
                .setTaskId(String.valueOf(taskId))
                .setUrl(downloadOptions.getUrl());
    }

    /**
     * 停止下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    @Override
    public void stopDownloadTask(@Nullable DownloadTaskInfo downloadTaskInfo) {

    }

    /**
     * 取消下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    @Override
    public void cancelDownloadTask(@Nullable DownloadTaskInfo downloadTaskInfo) {

    }

    /**
     * 恢复下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    @Override
    public void resumeDownloadTask(@Nullable DownloadTaskInfo downloadTaskInfo) {

    }

    //endregion

    //region: OnDownloadListener

    /**
     * 下载成功
     *
     * @param downloadTask
     */
    @Override
    public void onDownloadSuccess(@NonNull DownloadTask downloadTask) {
        if (DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onDownloadSuccess ");
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskComplete(HelloDownloadTaskEntity.obtain(downloadTask));
            }
        }
    }

    /**
     * 下载中
     *
     * @param downloadTask
     */
    @Override
    public void onDownloading(@NonNull DownloadTask downloadTask) {
        if (DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onDownloading ");
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskRunning(HelloDownloadTaskEntity.obtain(downloadTask));
            }
        }
    }

    /**
     * 下载异常信息
     *
     * @param downloadTask
     * @param throwable
     */
    @Override
    public void onDownloadFailed(@NonNull DownloadTask downloadTask,
                                 @Nullable Throwable throwable) {
        if (DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onDownloadFailed ");
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskFail(HelloDownloadTaskEntity.obtain(downloadTask), throwable);
            }
        }
    }

    //endregion

}