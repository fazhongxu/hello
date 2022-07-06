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

    //endregion

}