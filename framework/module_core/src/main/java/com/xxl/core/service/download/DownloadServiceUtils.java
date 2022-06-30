package com.xxl.core.service.download;

import android.app.Application;

import com.arialyy.aria.core.Aria;

/**
 * 下载服务工具类
 *
 * @author xxl.
 * @date 2022/6/29.
 */
public class DownloadServiceUtils {

    /**
     * 是否是debug环境
     */
    private static boolean sIsDebug;

    private DownloadServiceUtils() {

    }

    public void init(Application application,
                     final boolean isDebug) {
        sIsDebug = isDebug;
        Aria.init(application);
    }

    /**
     * 判断是否是debug环境
     *
     * @return
     */
    public static boolean isDebug() {
        return sIsDebug;
    }
}