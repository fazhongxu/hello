package com.xxl.hello.service.queue.impl;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.DataRepositoryKit;

/**
 * 资源上传队列
 *
 * @author xxl.
 * @date 2022/5/27.
 */
public class ResourcesUploadServiceQueue extends BaseServiceQueueImpl {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public ResourcesUploadServiceQueue(@NonNull final Application application,
                                       @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application, dataRepositoryKit);
        // TODO: 2022/5/27 线程池 
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}