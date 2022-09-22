package com.xxl.core.service.download.hello;

import androidx.annotation.NonNull;

import com.xxl.core.service.download.DownloadServiceUtils;
import com.xxl.core.service.download.DownloadState;
import com.xxl.core.service.download.DownloadTaskEntity;
import com.xxl.kit.DownloadUtil;

/**
 * hello 下载任务信息
 *
 * @author xxl.
 * @date 2022/7/4.
 */
public class HelloDownloadTaskEntity implements DownloadTaskEntity {

    //region: 成员变量

    /**
     * 下载任务信息
     */
    private DownloadTask mDownloadTask;

    //endregion

    //region: 构造函数

    private HelloDownloadTaskEntity(@NonNull final DownloadTask downloadTask) {
        mDownloadTask = downloadTask;
    }

    public final static HelloDownloadTaskEntity obtain(@NonNull final DownloadTask downloadTask) {
        return new HelloDownloadTaskEntity(downloadTask);
    }

    /**
     * 获取下载的key，一般为下载的url
     *
     * @return
     */
    @Override
    public String getKey() {
        return mDownloadTask == null ? "" : DownloadServiceUtils.buildDownloadKey(mDownloadTask.getKey());
    }

    /**
     * 获取文件地址
     */
    @Override
    public String getSavePath() {
        return mDownloadTask == null ? "" : mDownloadTask.getSavePath();
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
        return mDownloadTask == null ? DownloadState.STATE_OTHER : mDownloadTask.getTaskState();
    }

    //endregion

    //region: DownloadTaskEntity

    //endregion

    //region: 内部辅助方法

    //endregion

}