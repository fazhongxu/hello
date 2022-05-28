package com.xxl.hello.service.data.repository.impl;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;
import com.xxl.hello.service.data.repository.api.ResourceRepositoryApi;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 获取资源数据源接口
 *
 * @author xxl.
 * @date 2022/05/28.
 */
public class ResourcesRepositoryImpl implements ResourceRepositoryApi {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public ResourcesRepositoryImpl() {
        // local
        // remote
    }

    //endregion


    //region: 资源上传相关

    /**
     * 添加上传资源到数据库中
     *
     * @param resourcesUploadQueueDBEntities
     * @return
     */
    @Override
    public Observable<Boolean> putResourcesUploadQueueDBEntities(@NonNull final List<ResourcesUploadQueueDBEntity> resourcesUploadQueueDBEntities) {
        // TODO: 2022/5/28
        // TODO: 2022/5/28 添加数据库，发送EventBus事件通知上传队列服务运行，队列服务监听Bus事件，检查队列服务运行状态，开始上传资源
        return Observable.just(true);
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}