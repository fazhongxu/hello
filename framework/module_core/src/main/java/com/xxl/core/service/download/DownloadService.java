package com.xxl.core.service.download;

import androidx.annotation.Nullable;

/**
 * 下载服务
 *
 * @author xxl.
 * @date 2022/6/29.
 */
public interface DownloadService {

    /**
     * 注册下载服务
     *
     * @param downloadListener
     */
    void register(@Nullable final DownloadListener downloadListener);

    /**
     * 注销下载服务
     *
     * @param downloadListener
     */
    void unRegister(@Nullable final DownloadListener downloadListener);
}