package com.xxl.core.service.download;

import androidx.annotation.NonNull;

/**
 * 下载任务信息
 *
 * @author xxl.
 * @date 2022/7/4.
 */
public class DefaultDownloadTaskEntity implements DownloadTaskEntity {

    //region: 成员变量

    private String mDownloadKey;

    private String mDownloadUrl;

    private String mSavePath;

    //endregion

    //region: 构造函数

    private DefaultDownloadTaskEntity(@NonNull final String downloadKey) {

    }

    public static DefaultDownloadTaskEntity obtain(@NonNull final String downLoadKey) {
        return new DefaultDownloadTaskEntity(downLoadKey);
    }

    //endregion

    //region: 提供方法

    /**
     * 获取下载的key，一般为下载的url
     *
     * @return
     */
    @Override
    public String getKey() {
        return mDownloadKey;
    }

    /**
     * 获取文件保存路径
     */
    @Override
    public String getSavePath() {
        return mSavePath;
    }

    /**
     * 获取文件大小
     *
     * @return
     */
    @Override
    public long getFileSize() {
        return 0;
    }

    /**
     * 获取当前进度
     *
     * @return
     */
    @Override
    public long getCurrentProgress() {
        return 0;
    }

    /**
     * 获取任务状态
     *
     * @return
     */
    @Override
    public int getTaskState() {
        return DownloadState.STATE_FAIL;
    }

    /**
     * 设置下载的URL
     *
     * @param downloadUrl
     * @return
     */
    public DefaultDownloadTaskEntity setDownloadUrl(@NonNull final String downloadUrl) {
        mDownloadUrl = downloadUrl;
        return this;
    }

    /**
     * 设置保存的目录
     *
     * @param savePath
     * @return
     */
    public DefaultDownloadTaskEntity setSavePath(@NonNull final String savePath) {
        mSavePath = savePath;
        return this;
    }

    //region
}