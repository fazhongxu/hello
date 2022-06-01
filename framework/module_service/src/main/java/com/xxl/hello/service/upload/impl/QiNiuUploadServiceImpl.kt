package com.xxl.hello.service.upload.impl

import android.app.Application
import android.util.Log
import com.xxl.hello.service.data.repository.DataRepositoryKit
import com.xxl.hello.service.upload.api.UploadService

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
     * 开始上传
     *
     * @param key
     */
    override fun onStart(key: String) {
        Log.e("aaa", "onStart: 我是七牛云上传$key")
    }

    /**
     * 上传完成
     *
     * @param domain
     * @param key
     */
    override fun onComplete(domain: String, key: String) {}

    //endregion
}