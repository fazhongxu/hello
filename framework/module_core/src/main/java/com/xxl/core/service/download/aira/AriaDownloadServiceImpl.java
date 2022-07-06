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
public class AriaDownloadServiceImpl implements DownloadService {

    //region: 成员变量

    /**
     * tag
     */
    private static final String TAG = "Aria ";

    /**
     * 下载监听
     */
    private List<DownloadListener> mDownloadListeners = new ArrayList<>();

    //endregion

    //region: 构造函数

    public AriaDownloadServiceImpl() {

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

    /**
     * 取消下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    @Override
    public void cancelDownloadTask(@Nullable final DownloadTaskInfo downloadTaskInfo) {
        if (downloadTaskInfo == null) {
            return;
        }
        Aria.download(this)
                .load(downloadTaskInfo.getTaskLongId())
                .cancel(true);
    }

    /**
     * 恢复下载任务
     *
     * @param downloadTaskInfo 下载任务信息
     * @return
     */
    @Override
    public void resumeDownloadTask(@Nullable final DownloadTaskInfo downloadTaskInfo) {
        if (downloadTaskInfo == null) {
            return;
        }
        Aria.download(this)
                .load(downloadTaskInfo.getTaskLongId())
                .resume(true);
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
            LogUtils.d(TAG + "onTaskPre " + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskPre(AriaDownloadTaskEntity.obtain(task));
            }
        }
    }

    /**
     * 任务预处理完成
     *
     * @param task
     */
    @Download.onTaskStart
    void onTaskStart(@Nullable final DownloadTask task) {
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onTaskStart " + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskStart(AriaDownloadTaskEntity.obtain(task));
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
            LogUtils.d(TAG + "onTaskComplete " + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskComplete(AriaDownloadTaskEntity.obtain(task));
            }
        }
    }

    /**
     * 任务下载完成
     *
     * @param task
     */
    @Download.onTaskRunning
    void onTaskRunning(@Nullable final DownloadTask task) {
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onTaskRunning " + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskRunning(AriaDownloadTaskEntity.obtain(task));
            }
        }
    }

    /**
     * 任务下载停止
     *
     * @param task
     */
    @Download.onTaskStop
    void onTaskStop(@Nullable final DownloadTask task) {
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onTaskStop " + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskStop(AriaDownloadTaskEntity.obtain(task));
            }
        }
    }

    /**
     * 任务下载继续
     *
     * @param task
     */
    @Download.onTaskResume
    void onTaskResume(@Nullable final DownloadTask task) {
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onTaskResume " + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskResume(AriaDownloadTaskEntity.obtain(task));
            }
        }
    }

    /**
     * 任务下载失败
     *
     * @param task
     * @param exception
     */
    @Download.onTaskFail
    void onTaskFail(@Nullable final DownloadTask task,
                    @NonNull final Exception exception) {
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onTaskFail " + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskFail(AriaDownloadTaskEntity.obtain(task), exception);
            }
        }
    }

    /**
     * 任务下载取消
     *
     * @param task
     */
    @Download.onTaskCancel
    void onTaskCancel(@Nullable final DownloadTask task) {
        if (task != null && DownloadServiceUtils.isDebug()) {
            LogUtils.d(TAG + "onTaskCancel " + task.getTaskName());
        }
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            for (DownloadListener downloadListener : mDownloadListeners) {
                downloadListener.onTaskCancel(AriaDownloadTaskEntity.obtain(task));
            }
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}