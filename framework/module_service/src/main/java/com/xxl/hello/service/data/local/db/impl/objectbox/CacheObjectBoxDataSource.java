package com.xxl.hello.service.data.local.db.impl.objectbox;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.api.CacheDBDataService;
import com.xxl.hello.service.data.local.db.entity.CacheDBEntity;
import com.xxl.hello.service.data.local.db.entity.CacheDBEntity_;
import com.xxl.kit.GsonUtils;

import io.objectbox.Property;

/**
 * 实现{@link CacheDBDataService} 针对 {@link CacheDBEntity} 数据表操作服务
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:38 PM
 **/
public class CacheObjectBoxDataSource extends BaseObjectBoxDataSource<CacheDBEntity> implements CacheDBDataService {

    //region: 构造函数

    public CacheObjectBoxDataSource(@NonNull final ObjectBoxDBClientKit objectBoxDBClientKit) {
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
    public Property<CacheDBEntity> getPrimaryKey() {
        return CacheDBEntity_.key;
    }

    //endregion

    //region: CacheDBDataService

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    @Override
    public boolean removeCacheData(@NonNull final String key) {
        return remove(key);
    }

    /**
     * 设置缓存数据
     *
     * @param key   缓存key
     * @param value 缓存的值
     * @return
     */
    @Override
    public <T> boolean putCacheData(@NonNull final String key,
                                    @NonNull final T value) {
        // TODO: 2021/11/21 先获取，如果有，修改更新时间，否则创建 get()
        CacheDBEntity cacheDBEntity = new CacheDBEntity();
        cacheDBEntity.setKey(key)
                .setValue(GsonUtils.toJson(value));
        return put(cacheDBEntity) > 0;
    }

    /**
     * 获取缓存数据
     *
     * @param key   缓存key
     * @param clazz 默认值
     * @return
     */
    @Override
    public <T> T getCacheData(@NonNull final String key,
                              @NonNull final Class<T> clazz) {
        CacheDBEntity cacheDBEntity = get(key);
        if (cacheDBEntity == null) {
            return null;
        }
        return GsonUtils.fromJson(cacheDBEntity.getValue(), clazz);

    }

    //endregion
}
