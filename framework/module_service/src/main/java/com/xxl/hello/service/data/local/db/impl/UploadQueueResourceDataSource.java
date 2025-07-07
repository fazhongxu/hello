package com.xxl.hello.service.data.local.db.impl;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.api.UploadQueueResourceDBDataService;
import com.xxl.hello.service.data.local.db.entity.UploadQueueResourceDBEntity;
import com.xxl.hello.service.data.local.db.entity.UploadQueueResourceDBEntity_;

import java.util.List;

import io.objectbox.Property;

/**
 * 订单数据库服务
 * 实现{@link UploadQueueResourceDBDataService} 针对 {@link UploadQueueResourceDBEntity} 数据表操作服务
 *
 * @Author: xxl
 * @Date: 2023/07/12 11:36 PM
 **/
public class UploadQueueResourceDataSource extends BaseDataSource<UploadQueueResourceDBEntity> implements UploadQueueResourceDBDataService {

    //region: 构造函数

    public UploadQueueResourceDataSource(@NonNull final ObjectBoxDBClientKit objectBoxDBClientKit) {
        super(objectBoxDBClientKit);
    }

    //endregion

    //region: 生命周期

    /**
     * 获取数据表主键ID
     *
     * @return
     */
    @Override
    public Property<UploadQueueResourceDBEntity> getPrimaryKey() {
        return UploadQueueResourceDBEntity_.resourcesUploadId;
    }

    //endregion

    //region: 与资源信息相关

    /**
     * 获取资源数据
     *
     * @param count
     * @return
     */
    @Override
    public List<UploadQueueResourceDBEntity> getResourceDBEntities(int count) {
        return getOperateBox()
                .query()
                .build()
                .find(0, count);
    }

    //endregion
}
