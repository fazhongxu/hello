package com.xxl.core.service.download.aira;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.arialyy.aria.core.task.DownloadTask;
import com.xxl.core.service.download.DownloadOptions;
import com.xxl.core.service.download.DownloadServiceUtils;
import com.xxl.core.service.download.DownloadState;
import com.xxl.core.service.download.DownloadTaskEntity;
import com.xxl.kit.GsonUtils;

/**
 * aria 下载任务信息
 *
 * @author xxl.
 * @date 2022/7/4.
 */
public class AriaDownloadTaskEntity implements DownloadTaskEntity {

    //region: 成员变量

    /**
     * 下载任务
     */
    private DownloadTask mDownloadTask;

    //endregion

    //region: 构造函数

    private AriaDownloadTaskEntity(@NonNull final DownloadTask task) {
        mDownloadTask = task;
    }

    public final static AriaDownloadTaskEntity obtain(@NonNull final DownloadTask task) {
        return new AriaDownloadTaskEntity(task);
    }

    /**
     * 获取下载的key，一般为下载的url
     *
     * @return
     */
    @Override
    public String getKey() {
        String taskKey = "";
        if (mDownloadTask != null) {
            final String json = mDownloadTask.getExtendField();
            if (!TextUtils.isEmpty(json)) {
                final DownloadOptions downloadOptions = GsonUtils.fromJson(json, DownloadOptions.class);
                if (downloadOptions != null) {
                    taskKey = downloadOptions.getDownloadKey();
                }
            }
            if (TextUtils.isEmpty(taskKey)) {
                taskKey = DownloadServiceUtils.buildDownloadKey(mDownloadTask.getKey());
            }
        }
        return taskKey;
    }

    /**
     * 获取文件地址
     */
    @Override
    public String getSavePath() {
        return mDownloadTask == null ? "" : mDownloadTask.getFilePath();
    }

    /**
     * 获取文件大小
     *
     * @return
     */
    @Override
    public long getFileSize() {
        return mDownloadTask == null ? 0L : mDownloadTask.getFileSize();
    }

    /**
     * 获取当前进度
     *
     * @return
     */
    @Override
    public long getCurrentProgress() {
        return mDownloadTask == null ? 0L : mDownloadTask.getCurrentProgress();
    }

    /**
     * 获取任务状态
     *
     * @return
     */
    @Override
    public int getTaskState() {
        return mDownloadTask == null ? DownloadState.STATE_OTHER : mDownloadTask.getState();
    }

    //endregion

    //region: DownloadTaskEntity

    //endregion

    //region: 内部辅助方法

    //endregion

}