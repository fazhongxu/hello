package com.xxl.hello.core.config;

import com.xxl.hello.core.utils.PathUtils;

/**
 * @Description 缓存文件夹配置
 *
 * @Author: xxl
 * @Date: 2021/8/29 12:40 AM
 **/
public class CacheDirConfig {
    /**
     *  图片压缩缓存文件夹
     */
    public static final String COMPRESSION_FILE_DIR = PathUtils.getAppExtCachePath() +"./compression_file_dir";
}