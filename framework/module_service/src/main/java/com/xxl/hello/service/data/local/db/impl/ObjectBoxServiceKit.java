package com.xxl.hello.service.data.local.db.impl;

import com.xxl.hello.service.data.local.db.DBServiceKit;
import com.xxl.hello.service.data.local.db.api.CacheDBDataService;
import com.xxl.hello.service.data.local.db.api.OrderDBDataService;
import com.xxl.hello.service.data.local.db.api.UploadQueueResourceDBDataService;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Object 数据库服务集合
 *
 * @Author: xxl
 * @Date: 2021/11/21 1:09 AM
 **/
public class ObjectBoxServiceKit implements DBServiceKit {

    @Inject
    public ObjectBoxServiceKit() {

    }

    /**
     * 数据缓存数据库服务
     *
     * @return
     */
    @Inject
    Lazy<CacheDBDataService> mCacheDBDataServiceLazy;

    /**
     * 获取数据缓存数据库服务
     *
     * @return
     */
    @Override
    public CacheDBDataService getCacheDBDataService() {
        return mCacheDBDataServiceLazy.get();
    }

    /**
     * 上传队列资源数据库服务
     *
     * @return
     */
    @Inject
    Lazy<UploadQueueResourceDBDataService> mUploadQueueResourceDBDataService;

    /**
     * 获取上传队列资源数据库服务
     *
     * @return
     */
    @Override
    public UploadQueueResourceDBDataService getUploadQueueResourceDBDataService() {
        return mUploadQueueResourceDBDataService.get();
    }

    /**
     * 获取订单数据库服务
     *
     * @return
     */
    @Inject
    Lazy<OrderDBDataService> mOrderDBDataServiceLazy;

    /**
     * 获取订单数据库服务
     *
     * @return
     */
    @Override
    public OrderDBDataService getOrderDBDataService() {
        return mOrderDBDataServiceLazy.get();
    }
}
