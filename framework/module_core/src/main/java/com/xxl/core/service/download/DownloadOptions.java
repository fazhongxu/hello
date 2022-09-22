package com.xxl.core.service.download;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xxl.kit.FileUtils;
import com.xxl.kit.LogUtils;

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

    /**
     * 下载文件存放的目录
     */
    private String mFileParentDir;

    //endregion

    //region: 构造函数

    private DownloadOptions(@NonNull final String targetUrl,
                            @NonNull final String parentDir) {
        mUrl = targetUrl;
        mFileParentDir = parentDir;
        try {
            // TODO: 2022/9/22  后续对parentDir 做支持 
            if (!TextUtils.isEmpty(mFileParentDir)) {
                if (!FileUtils.isFolderExist(mFileParentDir)) {
                    FileUtils.createOrExistsDir(parentDir);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e);
        }
    }

    public final static DownloadOptions create(@NonNull final String targetUrl) {
        return new DownloadOptions(targetUrl,"");
    }

    //endregion

    //region: 提供方法

    /**
     * 获取下载唯一标识
     *
     * @return
     */
    public String getDownloadKey() {
        return getTargetDownloadTag();
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
     * 设置下载文件完整路径
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