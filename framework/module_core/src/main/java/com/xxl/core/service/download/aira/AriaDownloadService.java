package com.xxl.core.service.download.aira;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.AriaManager;
import com.arialyy.aria.core.task.DownloadTask;
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
 * aria 下载服务
 *
 * @author xxl.
 * @date 2022/6/29.
 */
public class AriaDownloadService implements DownloadService {

    //region: 成员变量

    /**
     * 下载监听
     */
    private List<DownloadListener> mDownloadListeners = new ArrayList<>();

    //endregion

    //region: 构造函数

    public AriaDownloadService() {

    }

    //endregion

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
        if (AriaManager.getInstance() == null) {
            Aria.init(application);
        }
        Aria.download(this).register();
        if (downloadListener != null) {
            mDownloadListeners.add(downloadListener);
        }
    }

    @Override
    public void unRegister(@Nullable DownloadListener downloadListener) {
        if (!ListUtils.isEmpty(mDownloadListeners)) {
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
    public DownloadTaskInfo createDownloadTask(@NonNull final Object object,
                                               @NonNull final DownloadOptions downloadOptions) {
        long taskId = Aria.download(object)
                .load(downloadOptions.getUrl())
                .setFilePath(downloadOptions.getFilePath())
                .create();
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
    public void stopDownloadTask(@Nullable final DownloadTaskInfo downloadTaskInfo) {
        if (downloadTaskInfo == null) {
            return;
        }
        Aria.download(this)
                .load(downloadTaskInfo.getTaskLongId())
                .stop();
    }

    //endregion

    //region: 下载监听相关

    /**
     * 任务预处理完成
     *
     * @param task
     */
    @Download.onTaskPre
    void onTaskPre(@Nullable final DownloadTask task) {
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d("Aria onTaskPre" + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskPre(AriaDownloadTaskEntity.obtain(task));
            }
        }
    }

    /**
     * 任务下载完成
     *
     * @param task
     */
    @Download.onTaskComplete
    void onTaskComplete(@Nullable final DownloadTask task) {
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d("Aria onTaskPre" + task.getTaskName());
        }
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d("Aria onTaskComplete" + task.getTaskName());
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}