package com.xxl.hello.service.upload.api

/**
 *
 * @author xxl.
 * @date 2022/5/31.
 */
interface UploadService {

    /**
     * 开始上传
     *
     * @param key
     */
    fun onStart(key: String)

    /**
     * 上传完成
     *
     * @param domain
     * @param key
     */
    fun onComplete(domain: String,
                   key: String)
}