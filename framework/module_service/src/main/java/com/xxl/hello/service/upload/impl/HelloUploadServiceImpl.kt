package com.xxl.hello.service.upload.impl

import android.app.Application
import android.util.Log
import com.xxl.core.service.upload.UploadListener
import com.xxl.hello.service.data.repository.DataRepositoryKit
import com.xxl.hello.service.upload.api.UploadService
import okhttp3.*
import java.io.File
import java.io.IOException

/**
 * hello上传服务实现类
 *
 * @author xxl.
 * @date 2022/5/28.
 */
class HelloUploadServiceImpl(application: Application,
                             dataRepositoryKit: DataRepositoryKit) : UploadService {

    val mOkHttpClient: OkHttpClient

    init {
        mOkHttpClient = OkHttpClient.Builder()
                .build()
    }

    //region: 页面生命周期

    /**
     * 上传
     * @param file
     * @param listener
     */
    override fun upload(file: File,
               listener: UploadListener){
        Log.e("aaa", "upload: 我是hello上传${file.absolutePath}")

        val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build()

        val request: Request = Request.Builder()
                .url("upload server url ")
                .post(requestBody)
                .build()

        mOkHttpClient.newCall(request)
                .enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("aaa", "upload onFailure: " + e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.e("aaa", "upload onResponse: ")
                    }
                })
    }

    //endregion
}