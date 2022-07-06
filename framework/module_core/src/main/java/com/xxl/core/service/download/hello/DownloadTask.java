package com.xxl.core.service.download.hello;

import com.xxl.core.service.download.DownloadState;

/**
 * 下载任务信息
 *
 * @author xxl.
 * @date 2022/7/6.
 */
public class DownloadTask {

    //region: 成员变量

    /**
     * 下载的key，一般为下载的url
     */
    private String mDownloadKey;

    /**
     * 文件保存路径
     */
    private String mFileSavePath;

    /**
     * 文件大小
     */
    private long mFileSize;

    /**
     * 当前下载进度
     */
    private long mCurrentProgress;

    /**
     * 任务状态
     */
    @DownloadState
    private int mTaskState = DownloadState.STATE_OTHER;

    //endregion

    //region: 构造函数

    private DownloadTask() {

    }

    public final static DownloadTask create() {
        return new DownloadTask();
    }

    //endregion

    //region: 提供方法

    /**
     * 获取下载的key，一般为下载的url
     *
     * @return
     */
    public String getKey() {
        return mDownloadKey;
    }

    /**
     * 获取文件保存路径
     */
    public String getSavePath() {
        return mFileSavePath;
    }

    /**
     * 获取文件大小
     *
     * @return
     */
    public long getFileSize() {
        return mFileSize;
    }

    /**
     * 获取当前进度
     *
     * @return
     */
    public long getCurrentProgress() {
        return mCurrentProgress;
    }

    /**
     * 获取任务状态
     *
     * @return
     */
    @DownloadState
    public int getTaskState() {
        return mTaskState;
    }

    /**
     * 设置下载key
     *
     * @param downloadKey
     * @return
     */
    public DownloadTask setDownloadKey(String downloadKey) {
        this.mDownloadKey = downloadKey;
        return this;
    }

    /**
     * 设置下载保存路径
     *
     * @param fileSavePath
     * @return
     */
    public DownloadTask setFileSavePath(String fileSavePath) {
        this.mFileSavePath = fileSavePath;
        return this;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize
     * @return
     */
    public DownloadTask setFileSize(final long fileSize) {
        this.mFileSize = fileSize;
        return this;
    }

    /**
     * 设置当前进度
     *
     * @param currentProgress
     * @return
     */
    public DownloadTask setCurrentProgress(final long currentProgress) {
        this.mCurrentProgress = currentProgress;
        return this;
    }

    /**
     * 设置任务状态
     *
     * @param taskState
     * @return
     */
    public DownloadTask setTaskState(@DownloadState int taskState) {
        this.mTaskState = taskState;
        return this;
    }

    //endregion

}