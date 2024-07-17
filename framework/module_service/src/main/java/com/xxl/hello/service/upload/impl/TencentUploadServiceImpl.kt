package com.xxl.hello.service.upload.impl

import android.app.Application
import android.util.Log
import com.xxl.core.service.upload.UploadListener
import com.xxl.hello.service.data.repository.DataRepositoryKit
import com.xxl.hello.service.upload.api.UploadService
import java.io.File

/**
 * 腾讯云上传服务实现类
 *
 * @author xxl.
 * @date 2022/5/28.
 */
class TencentUploadServiceImpl(application: Application, dataRepositoryKit: DataRepositoryKit) : UploadService {

    //region: 页面生命周期

    /**
     * 上传
     * @param file
     * @param listener
     */
    override fun upload(file: File,
                        listener: UploadListener) {
        Log.e("aaa", "upload: 我是腾讯云上传${file.absolutePath}")
    }

    //endregion
}