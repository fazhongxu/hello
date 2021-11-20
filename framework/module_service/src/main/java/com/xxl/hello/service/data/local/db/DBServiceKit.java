package com.xxl.hello.service.data.local.db;

import com.xxl.hello.service.data.local.db.api.CacheDBDataService;

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
}
