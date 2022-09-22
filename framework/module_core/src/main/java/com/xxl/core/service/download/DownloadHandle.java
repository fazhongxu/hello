package com.xxl.core.service.download;

import androidx.annotation.NonNull;

/**
 * 下载任务处理类
 *
 * @author xxl.
 * @date 2022/9/22.
 */
public class DownloadHandle {

    //region: 成员变量

    private final DownloadOptions mDownloadOptions;

    private final DownloadListener mDownloadListener;

    //endregion

    //region: 构造函数

    private DownloadHandle(@NonNull final DownloadOptions downloadOptions,
                           @NonNull final DownloadListener downloadListener) {
        mDownloadOptions = downloadOptions;
        mDownloadListener = downloadListener;
    }

    public final static DownloadHandle create(@NonNull final DownloadOptions downloadOptions,
                                              @NonNull final DownloadListener downloadListener) {
        return new DownloadHandle(downloadOptions, downloadListener);
    }

    //endregion

    //region: 提供方法

    public DownloadOptions getDownloadOptions() {
        return mDownloadOptions;
    }

    public DownloadListener getDownloadListener() {
        return mDownloadListener;
    }

    //endregion


}