package com.xxl.hello.service.utils;

import android.content.Context;
import android.text.TextUtils;

import com.danikula.videocache.HttpProxyCacheServer;
import com.xxl.kit.AppUtils;
import com.xxl.kit.FileUtils;

import java.io.File;

/**
 * doc https://www.jianshu.com/p/4745de02dcdc
 * @author xxl.
 * @date 2025/8/5.
 */
public class VideoCacheUtils {

    private static HttpProxyCacheServer sProxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        if (sProxy == null) {
            sProxy = new HttpProxyCacheServer.Builder(context)
                    .maxCacheSize(512 * 1024 * 1024)
                    .build();
        }
        return sProxy;
    }

    /**
     * 获取代理url
     *
     * @param url
     * @return
     */
    public static String getProxyUrl(String url) {
        String proxyUrl = null;
        try {
            proxyUrl = getProxy(AppUtils.getApplication())
                    .getProxyUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TextUtils.isEmpty(proxyUrl) ? url : proxyUrl;
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        File cacheDir = getProxy(AppUtils.getApplication()).getCacheRoot();
        FileUtils.deleteFile(cacheDir);
    }
}