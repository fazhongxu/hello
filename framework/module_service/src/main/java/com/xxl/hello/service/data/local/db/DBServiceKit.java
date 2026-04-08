package com.xxl.hello.service.data.local.db;

import com.xxl.hello.service.data.local.db.api.CacheDBDataService;
import com.xxl.hello.service.data.local.db.api.CategoryDBDataService;
import com.xxl.hello.service.data.local.db.api.OrderDBDataService;
import com.xxl.hello.service.data.local.db.api.UploadQueueResourceDBDataService;

/**
 * 数据库服务集合
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:31 PM
 **/
public interface DBServiceKit {

    /**
     * 获取数据缓存数据库服务
     *
     * @return
     */
    CacheDBDataService getCacheDBDataService();

    /**
     * 获取分类数据库服务
     *
     * @return
     */
    CategoryDBDataService getCategoryDBDataService();

    /**
     * 获取上传队列资源数据库服务
     *
     * @return
     */
    UploadQueueResourceDBDataService getUploadQueueResourceDBDataService();

    /**
     * 获取订单数据库服务
     *
     * @return
     */
    OrderDBDataService getOrderDBDataService();

}
