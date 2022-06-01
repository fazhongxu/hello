package com.xxl.hello.service.upload.impl

import android.app.Application
import com.xxl.hello.service.data.repository.DataRepositoryKit
import com.xxl.hello.service.qunlifier.ForApplication
import com.xxl.hello.service.qunlifier.ForQiNiuUpload
import com.xxl.hello.service.qunlifier.ForTencentUpload
import com.xxl.hello.service.upload.api.UploadService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 上传模块依赖注入
 *
 * @author xxl.
 * @date 2022/05/30.
 */
@Module
class UploadDataStoreModule {

    /**
     * 构建七牛云上传服务
     *
     * @param application
     * @param dataRepositoryKit
     * @return
     */
    @ForQiNiuUpload
    @Singleton
    @Provides
    fun provideUploadService(@ForApplication application: Application?,
                             dataRepositoryKit: DataRepositoryKit): UploadService {
        return QiNiuUploadServiceImpl(application!!, dataRepositoryKit)
    }

    /**
     * 构建腾讯云上传服务
     *
     * @param application
     * @param dataRepositoryKit
     * @return
     */
    @ForTencentUpload
    @Singleton
    @Provides
    fun provideTencentUploadService(@ForApplication application: Application?,
                                    dataRepositoryKit: DataRepositoryKit): UploadService {
        return TencentUploadServiceImpl(application!!, dataRepositoryKit)
    }

}