package com.xxl.hello.service.upload.impl

import android.app.Application
import android.os.Handler
import android.util.Log
import com.xxl.core.service.upload.UploadListener
import com.xxl.hello.service.data.repository.DataRepositoryKit
import com.xxl.hello.service.upload.api.UploadService
import java.io.File

/**
 * 七牛上传服务实现类
 *
 * @author xxl.
 * @date 2022/5/28.
 */
class QiNiuUploadServiceImpl(application: Application,
                             dataRepositoryKit: DataRepositoryKit) : UploadService {

    //region: 页面生命周期

    /**
     * 上传
     * @param file
     * @param listener
     */
    override fun upload(file: File,
                        listener: UploadListener) {
        Log.e("aaa", "upload: 我是七牛云上传${file.absolutePath}")
        Handler().postDelayed(object : Runnable {
            override fun run() {
                listener.onUploadComplete(file.absolutePath, "https://qiniu${file.absolutePath}")
            }
        }, 2000)
        // TODO: 2024/7/18 模拟上传 
    }

    //endregion
}