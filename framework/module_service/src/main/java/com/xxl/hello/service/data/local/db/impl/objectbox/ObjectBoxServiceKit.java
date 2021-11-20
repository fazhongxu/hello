package com.xxl.hello.service.data.local.db.impl.objectbox;

import com.xxl.hello.service.data.local.db.DBServiceKit;
import com.xxl.hello.service.data.local.db.api.CacheDBDataService;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Object 数据库服务集合
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
}
