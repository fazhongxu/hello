package com.xxl.core.service.download;

import android.text.TextUtils;

import androidx.annotation.NonNull;

/**
 * 下载参数配置
 *
 * @author xxl.
 * @date 2022/7/2.
 */
public class DownloadOptions {

    //region: 成员变量

    /**
     * 下载key，唯一标识
     */
    private String mDownloadTag;

    /**
     * 下载链接地址
     */
    private String mUrl;

    /**
     * 下载文件完整路径
     */
    private String mFilePath;

    //endregion

    //region: 构造函数

    private DownloadOptions() {

    }

    public final static DownloadOptions create() {
        return new DownloadOptions();
    }

    //endregion

    //region: 提供方法

    /**
     * 获取下载唯一标识
     *
     * @return
     */
    public String getDownloadTag() {
        return mDownloadTag;
    }

    /**
     * 获取下载唯一标识
     *
     * @return
     */
    public String getTargetDownloadTag() {
        return TextUtils.isEmpty(mDownloadTag) ? DownloadServiceUtils.buildDownloadKey(mUrl) : mDownloadTag;
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
     * 获取文件路径
     *
     * @return
     */
    public String getFilePath() {
        return mFilePath;
    }

    /**
     * 设置下载唯一标识
     *
     * @param downloadTag
     * @return
     */
    public DownloadOptions setDownloadTag(@NonNull final String downloadTag) {
        this.mDownloadTag = downloadTag;
        return this;
    }

    /**
     * 设置链接地址
     *
     * @param url
     * @return
     */
    public DownloadOptions setUrl(@NonNull final String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 设置下载文件路径
     *
     * @param filePath
     * @return
     */
    public DownloadOptions setFilePath(@NonNull final String filePath) {
        this.mFilePath = filePath;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}