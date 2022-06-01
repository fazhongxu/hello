package com.xxl.hello.service;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.queue.api.ResourcesUploadServiceQueue;
import com.xxl.hello.service.upload.api.UploadService;

/**
 * 资源处理服务
 *
 * @author xxl.
 * @date 2022/5/30.
 */
public class ResourcesProcessService extends BaseService {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public ResourcesProcessService(@NonNull final Application application,
                                   @NonNull final DataRepositoryKit dataRepositoryKit,
                                   @NonNull final UploadService uploadService,
                                   @NonNull final UploadService tencentUploadService,
                                   @NonNull final ResourcesUploadServiceQueue resourcesUploadServiceQueue) {
        super(application, dataRepositoryKit);
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}