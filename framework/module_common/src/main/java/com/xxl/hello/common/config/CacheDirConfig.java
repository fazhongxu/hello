package com.xxl.hello.common.config;

import com.xxl.kit.PathUtils;

import java.io.File;

/**
 * @Description 缓存文件夹配置
 * @Author: xxl
 * @Date: 2021/8/29 12:40 AM
 **/
public class CacheDirConfig {

    /**
     * 缓存文件夹
     */
    public static final String CACHE_DIR = PathUtils.getAppExtCachePath();

    /**
     * 图片压缩缓存文件夹
     */
    public static final String COMPRESSION_FILE_DIR = PathUtils.getAppExtCachePath() + "/.compression_file_dir";

    /**
     * 分享缓存文件夹
     */
    public static final String SHARE_FILE_DIR = PathUtils.getExtStoragePath() + File.separator + "hello_share";

    /**
     * Tbs缓存文件夹
     */
    public static final String TBS_CACHE_DIR = PathUtils.getAppExtCachePath() + "/.tbs_cache_dir";
}
