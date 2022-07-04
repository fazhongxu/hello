package com.xxl.core.service.download;

import android.app.Application;

import com.arialyy.aria.core.Aria;
import com.xxl.kit.DeviceUtils;
import com.xxl.kit.EncryptUtils;

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

    public static void init(Application application,
                     final boolean isDebug) {
        sIsDebug = isDebug;
        Aria.init(application);
    }

    /**
     * 构建下载唯一标识
     *
     * @param downloadUrl
     * @return
     */
    public static String buildDownloadKey(String downloadUrl) {
        return EncryptUtils.encryptMD5ToString(downloadUrl);
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