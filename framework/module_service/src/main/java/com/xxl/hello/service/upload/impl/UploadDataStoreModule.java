package com.xxl.hello.service.upload.impl;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.queue.api.ResourcesUploadServiceQueue;
import com.xxl.hello.service.queue.impl.ResourcesUploadServiceQueueImpl;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.hello.service.qunlifier.ForQiNiuUpload;
import com.xxl.hello.service.qunlifier.ForTencentUpload;
import com.xxl.hello.service.upload.api.UploadService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 上传模块依赖注入
 *
 * @author xxl.
 * @date 2022/05/30.
 */
@Module
public class UploadDataStoreModule {

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
    UploadService provideUploadService(@ForApplication final Application application,
                                       @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new QiNiuUploadServiceImpl(application, dataRepositoryKit);
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
    UploadService provideTecentUploadService(@ForApplication final Application application,
                                             @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new TecentUploadServiceImpl(application, dataRepositoryKit);
    }


}