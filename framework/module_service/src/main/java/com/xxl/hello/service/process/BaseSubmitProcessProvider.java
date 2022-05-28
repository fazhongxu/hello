package com.xxl.hello.service.process;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.listener.OnRequestCallBack;
import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ResourcesSubmitType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.upload.api.UploadService;

import lombok.experimental.Accessors;

/**
 * 资源提交处理
 *
 * @author xxl.
 * @date 2022/5/28.
 */
@Accessors(prefix = "m")
public abstract class BaseSubmitProcessProvider<T> extends BaseProcessProvider {

    //region: 构造函数

    public BaseSubmitProcessProvider(@NonNull final Application application,
                                     @NonNull final DataRepositoryKit dataRepositoryKit,
                                     @NonNull final UploadService uploadService) {
        super(application, dataRepositoryKit, uploadService);
    }

    //endregion

    //region: 抽象方法相关

    /**
     * 获取提交资源类型
     *
     * @return
     */
    @ResourcesSubmitType
    public abstract String getSubmitType();

    /**
     * 处理资源提交
     *
     * @param targetResourcesUploadQueueDBEntity 资源上传队列数据
     * @param targetRequest                      请求参数
     * @param callback                           回调
     */
    public abstract void handleSubmit(@NonNull final ResourcesUploadQueueDBEntity targetResourcesUploadQueueDBEntity,
                                      @NonNull final T targetRequest,
                                      @NonNull final OnRequestCallBack<Boolean> callback);

    //endregion

    //region: 资源提交相关

    //endregion

}