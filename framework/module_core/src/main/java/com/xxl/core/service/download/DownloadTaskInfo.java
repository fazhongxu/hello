package com.xxl.core.service.download;

import androidx.annotation.NonNull;

/**
 * 下载任务信息
 *
 * @author xxl.
 * @date 2022/7/2.
 */
public class DownloadTaskInfo {

    //region: 成员变量

    /**
     * 下载key，唯一标识
     */
    private String mDownloadTag;

    /**
     * 下载任务id
     */
    private String mTaskId;

    /**
     * 下载链接地址
     */
    private String mUrl;

    //endregion

    //region: 构造函数

    private DownloadTaskInfo(@NonNull final String downloadTag) {
        mDownloadTag = downloadTag;
    }

    public final static DownloadTaskInfo create(@NonNull final String downloadKey) {
        return new DownloadTaskInfo(downloadKey);
    }

    //endregion

    //region: 提供方法

    /**
     * 获取任务ID
     *
     * @return
     */
    public long getTaskLongId() {
        try {
            return Long.parseLong(mTaskId);
        } catch (Exception e) {

        }
        return -1;
    }


    /**
     * 获取下载唯一标识
     *
     * @return
     */
    public String getDownloadTag() {
        return mDownloadTag;
    }

    /**
     * 获取下载任务ID
     *
     * @return
     */
    public String getTaskId() {
        return mTaskId;
    }

    /**
     * 获取下载地址
     *
     * @return
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * 设置下载唯一标识
     *
     * @param downloadTag
     * @return
     */
    public DownloadTaskInfo setDownloadTag(@NonNull final String downloadTag) {
        this.mDownloadTag = downloadTag;
        return this;
    }

    /**
     * 设置任务id
     *
     * @param taskId
     * @return
     */
    public DownloadTaskInfo setTaskId(@NonNull final String taskId) {
        this.mTaskId = taskId;
        return this;
    }

    /**
     * 设置链接地址
     *
     * @param url
     * @return
     */
    public DownloadTaskInfo setUrl(@NonNull final String url) {
        this.mUrl = url;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}