package com.xxl.hello.service.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.ResourceProcessWrapper;
import com.xxl.hello.service.ResourcesProcessService;
import com.xxl.hello.service.ServiceWrapper;
import com.xxl.hello.service.data.local.prefs.impl.PreferencesDataStoreModule;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.impl.ServiceRepositoryDataStoreModule;
import com.xxl.hello.service.queue.api.ResourcesUploadServiceQueue;
import com.xxl.hello.service.queue.impl.ServiceQueueDataStoreModule;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.hello.service.qunlifier.ForQiNiuUpload;
import com.xxl.hello.service.qunlifier.ForTencentUpload;
import com.xxl.hello.service.upload.api.UploadService;
import com.xxl.hello.service.upload.impl.UploadDataStoreModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/21.
 */
@Module(includes = {PreferencesDataStoreModule.class,
        ServiceRepositoryDataStoreModule.class,
        ServiceQueueDataStoreModule.class,
        UploadDataStoreModule.class})
public class ServiceDataStoreModule {

    /**
     * 构建服务提供组件
     *
     * @param application
     * @param dataRepositoryKit
     * @return
     */
    @Singleton
    @Provides
    ServiceWrapper provideServiceWrapper(@ForApplication final Application application,
                                         @NonNull final DataRepositoryKit dataRepositoryKit,
                                         @NonNull final ResourcesProcessService resourcesProcessService) {
        // TODO: 2021/7/23  构造入 DBClientKit,UploadService 等
        return new ServiceWrapper(application, dataRepositoryKit, resourcesProcessService);
    }

    /**
     * 构建资源处理服务
     *
     * @param application
     * @param dataRepositoryKit
     * @param uploadService
     * @param tencentUploadService
     * @param resourcesUploadServiceQueue
     * @return
     */
    @Singleton
    @Provides
    ResourcesProcessService provideResourcesProcessService(@ForApplication final Application application,
                                                           @NonNull final DataRepositoryKit dataRepositoryKit,
                                                           @ForQiNiuUpload final UploadService uploadService,
                                                           @ForTencentUpload final UploadService tencentUploadService,
                                                           @NonNull final ResourcesUploadServiceQueue resourcesUploadServiceQueue) {
        return new ResourcesProcessService(application, dataRepositoryKit, uploadService, tencentUploadService, resourcesUploadServiceQueue);
    }

    /**
     * 构建资源处理包装类
     *
     * @param application
     * @param dataRepositoryKit
     * @param uploadService
     * @param tencentUploadService
     * @return
     */
    @Singleton
    @Provides
    ResourceProcessWrapper provideResourceProcessWrapper(@ForApplication final Application application,
                                                         @NonNull final DataRepositoryKit dataRepositoryKit,
                                                         @ForQiNiuUpload final UploadService uploadService,
                                                         @ForTencentUpload final UploadService tencentUploadService) {
        return new ResourceProcessWrapper(application, dataRepositoryKit, uploadService, tencentUploadService);
    }
}