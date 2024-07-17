package com.xxl.hello.service.upload.api

import com.xxl.core.service.upload.UploadListener
import java.io.File

/**
 *
 * @author xxl.
 * @date 2022/5/31.
 */
interface UploadService {

    /**
     * 上传
     * @param file
     * @param listener
     */
    fun upload(file: File,
               listener: UploadListener)
}