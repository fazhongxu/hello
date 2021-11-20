package com.xxl.hello.service.data.local.db.api;

import androidx.annotation.NonNull;

/**
 * 数据缓存数据库服务
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:36 PM
 **/
public interface CacheDBDataService {

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    boolean removeCacheData(@NonNull final String key);

    /**
     * 设置缓存数据
     *
     * @param key   缓存key
     * @param value 缓存的值
     * @param <T>
     * @return
     */
    <T> boolean putCacheData(@NonNull final String key,
                             @NonNull final T value);

    /**
     * 获取缓存数据
     *
     * @param key          缓存key
     * @param clazz 默认值
     * @param <T>
     * @return
     */
    <T> T getCacheData(@NonNull final String key,
                       @NonNull final Class<T> clazz);

}
