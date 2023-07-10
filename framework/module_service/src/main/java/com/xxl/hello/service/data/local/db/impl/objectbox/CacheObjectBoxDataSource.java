package com.xxl.hello.service.data.local.db.impl.objectbox;

import androidx.annotation.NonNull;

import com.xxl.kit.GsonUtils;
import com.xxl.hello.service.data.local.db.api.CacheDBDataService;
import com.xxl.hello.service.data.local.db.entity.CacheDBEntity;
import com.xxl.hello.service.data.local.db.entity.CacheDBEntity_;

import io.objectbox.Property;
import io.objectbox.query.QueryBuilder;

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

    /**
     * 获取数据表ID
     *
     * @return
     */
    @Override
    public Property<CacheDBEntity> getTableId() {
        return CacheDBEntity_.key;
    }

    //endregion

    //region: 生命周期

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
        // TODO: 2021/11/21 操作方法抽象，抽取基础类 get getAll put remove(key),removeAll等等

        if (getOoxStore() == null) {
            return false;
        }
        Property<CacheDBEntity> tableId = getTableId();
        return getOoxStore().boxFor(CacheDBEntity.class)
                .query()
                .in(getTableId(), new String[]{key}, QueryBuilder.StringOrder.CASE_INSENSITIVE)
                .build()
                .remove() > 0;
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
        if (getOoxStore() == null) {
            return false;
        }
        Property<CacheDBEntity> tableId = getTableId();
        // TODO: 2021/11/21 先获取，如果有，修改更新时间，否则创建 get()
        CacheDBEntity cacheDBEntity = new CacheDBEntity();
        cacheDBEntity.setKey(key)
                .setValue(GsonUtils.toJson(value));

        return getOoxStore().boxFor(CacheDBEntity.class)
                .put(cacheDBEntity) > 0;
    }

    /**
     * 获取缓存数据
     *
     * @param key          缓存key
     * @param clazz 默认值
     * @return
     */
    @Override
    public <T> T getCacheData(@NonNull final String key,
                              @NonNull final Class<T> clazz) {
        if (getOoxStore() == null) {
            return null;
        }
        CacheDBEntity cacheDBEntity = getOoxStore().boxFor(CacheDBEntity.class)
                .query()
                .equal(getTableId(), key, QueryBuilder.StringOrder.CASE_INSENSITIVE)
                .build()
                //.find(/*"final long offset, final long limit"*/) 分页操作
                .findFirst();
        if (cacheDBEntity == null) {
            return null;
        }
        return GsonUtils.fromJson(cacheDBEntity.getValue(),clazz);

    }

    //endregion
}
