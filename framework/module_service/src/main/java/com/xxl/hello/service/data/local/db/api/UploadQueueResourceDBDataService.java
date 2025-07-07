package com.xxl.hello.service.data.local.db.api;

import com.xxl.hello.service.data.local.db.entity.UploadQueueResourceDBEntity;

import java.util.List;

/**
 * 上传队列资源数据库服务
 *
 * @Author: xxl
 * @Date: 2023/07/12 11:36 PM
 **/
public interface UploadQueueResourceDBDataService {

    //region: 与资源信息相关

    /**
     * 获取资源数据
     *
     * @param count
     * @return
     */
    List<UploadQueueResourceDBEntity> getResourceDBEntities(int count);

    //endregion
}
