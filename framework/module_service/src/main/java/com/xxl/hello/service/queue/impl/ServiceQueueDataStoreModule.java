package com.xxl.hello.service.queue.impl;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.ResourceProcessWrapper;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.queue.api.ResourcesUploadServiceQueue;
import com.xxl.hello.service.qunlifier.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 资源队列模块依赖注入
 *
 * @author xxl.
 * @date 2022/05/30.
 */
@Module
public class ServiceQueueDataStoreModule {

    /**
     * 构建资源上传队列
     *
     * @param application
     * @param dataRepositoryKit
     * @param resourceProcessWrapper
     * @return
     */
    @Singleton
    @Provides
    ResourcesUploadServiceQueue provideResourcesUploadServiceQueue(@ForApplication final Application application,
                                                                   @NonNull final DataRepositoryKit dataRepositoryKit,
                                                                   @NonNull final ResourceProcessWrapper resourceProcessWrapper) {
        return new ResourcesUploadServiceQueueImpl(application, dataRepositoryKit, resourceProcessWrapper);
    }

}