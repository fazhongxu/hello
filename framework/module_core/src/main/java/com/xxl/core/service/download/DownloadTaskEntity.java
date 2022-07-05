package com.xxl.core.service.download;

/**
 * 下载任务信息
 *
 * @author xxl.
 * @date 2022/7/4.
 */
public interface DownloadTaskEntity {

    /**
     * 获取下载的key，一般为下载的url
     *
     * @return
     */
    String getKey();

    /**
     * 获取文件地址
     */
    String getSavePath();

    /**
     * 获取文件大小
     *
     * @return
     */
    long getFileSize();

    /**
     * 获取当前进度
     *
     * @return
     */
    long getCurrentProgress();

    /**
     * 获取任务状态
     *
     * @return
     */
    @DownloadState
    int getTaskState();

}