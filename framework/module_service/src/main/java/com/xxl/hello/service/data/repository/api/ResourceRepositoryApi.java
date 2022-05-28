package com.xxl.hello.service.data.repository.api;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 获取资源数据源接口
 *
 * @author xxl.
 * @date 2022/05/28.
 */
public interface ResourceRepositoryApi {

    //region: 资源上传相关

    /**
     * 添加上传资源到数据库中
     *
     * @param resourcesUploadQueueDBEntities
     * @return
     */
    Observable<Boolean> putResourcesUploadQueueDBEntities(@NonNull final List<ResourcesUploadQueueDBEntity> resourcesUploadQueueDBEntities);

    //endregion
}