package com.xxl.hello.common.config

import com.xxl.kit.PathUtils
import java.io.File

/**
 * 缓存文件夹相关
 *
 * @author xxl.
 * @date 2023/3/2.
 */
class CacheDirConfig private constructor() {

    companion object {

        /**
         * 缓存文件夹
         */
        @JvmField
        val CACHE_DIR = PathUtils.getAppExtCachePath()

        /**
         * 图片压缩缓存文件夹
         */
        @JvmField
        val COMPRESSION_FILE_DIR = PathUtils.getAppExtCachePath() + "/.compression_file_dir"

        /**
         * 分享缓存文件夹
         */
        @JvmField
        val SHARE_FILE_DIR = PathUtils.getExtPicturesPath() + File.separator + "hello_share"

        /**
         * Tbs缓存文件夹
         */
        @JvmField
        val TBS_CACHE_DIR = PathUtils.getAppExtCachePath() + "/.tbs_cache_dir"
    }
}