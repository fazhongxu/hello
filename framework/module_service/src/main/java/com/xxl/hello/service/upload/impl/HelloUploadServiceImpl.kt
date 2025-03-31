package com.xxl.hello.service.upload.impl

import android.app.Application
import android.os.Handler
import android.util.Log
import com.xxl.core.service.ProgressRequestBody
import com.xxl.core.service.upload.UploadListener
import com.xxl.core.service.upload.UploadOptions
import com.xxl.hello.service.data.repository.DataRepositoryKit
import com.xxl.hello.service.upload.api.UploadService
import com.xxl.kit.LogUtils
import okhttp3.*
import java.io.File
import java.io.IOException
import java.net.FileNameMap
import java.net.URLConnection

/**
 * hello上传服务实现类
 *
 * @author xxl.
 * @date 2022/5/28.
 */
class HelloUploadServiceImpl(
    application: Application,
    dataRepositoryKit: DataRepositoryKit
) : UploadService {

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
    override fun upload(
        file: File,
        listener: UploadListener
    ) {
        upload(UploadOptions.create(file), listener)
    }

    /**
     * 上传
     * @param options
     * @param listener
     */
    override fun upload(
        options: UploadOptions,
        listener: UploadListener
    ) {
        val file = options.targetFile
        Log.e("aaa", "upload: 我是hello上传${file.absolutePath}")
        Handler().postDelayed(object : Runnable {
            override fun run() {
                listener.onUploadComplete(file.absolutePath, "https://hello${file.absolutePath}")
            }
        }, 2000)

        /*// TODO: 2024/7/18 模拟上传

         val requestBody: RequestBody = MultipartBody.Builder()
             .setType(MultipartBody.FORM)
             .addFormDataPart(
                 "file",
                 file.getName(),
                 RequestBody.create(MediaType.parse(guessMimeType(file.absolutePath)), file)
             )
             .build()

         val progressRequestBody =
             ProgressRequestBody(requestBody, object : ProgressRequestBody.OnRequstCallBack {
                 override fun onProgress(currentSize: Long, totalSize: Long) {
                     // progress
                     LogUtils.e("upload progress " + currentSize + "  " + totalSize)
                 }

                 override fun onError(e: Throwable?) {
                     LogUtils.e("upload onError " + e?.message)
                     listener.onUploadFailure(options.key, e)
                 }

             })
         val request: Request = Request.Builder()
             .url("https://file.io")// 测试可用，只是返回值不知道文件存在了哪里
             .post(progressRequestBody)
             .build()

         mOkHttpClient.newCall(request)
             .enqueue(object : Callback {

                 override fun onFailure(call: Call, e: IOException) {
                     LogUtils.e("upload failure " + e.message)
                     listener.onUploadFailure(options.key, e)
                 }

                 override fun onResponse(call: Call, response: Response) {
                     LogUtils.e("upload onResponse")
                     val string = response.body()?.string()
                     // TODO: 2024/7/22
                     //listener.onUploadComplete()
                 }
             })

*/
    }

    private fun guessMimeType(filename: String): String? {
        val fileNameMap: FileNameMap = URLConnection.getFileNameMap()
        var contentTypeFor: String = fileNameMap.getContentTypeFor(filename)
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream"
        }
        return contentTypeFor
    }

    //endregion
}