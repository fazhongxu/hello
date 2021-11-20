package com.xxl.hello.service.data.local.db.entity;

import androidx.annotation.NonNull;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;
import lombok.Getter;

/**
 * 数据缓存
 *
 * @Author: xxl
 * @Date: 2021/11/21 1:10 AM
 **/
@Getter
@Entity
@NameInDb("h_t_cache")
public class CacheDBEntity extends BaseDBEntity<CacheDBEntity> {

    //region: 成员变量

    /**
     * 缓存数据的key
     */
    @Unique
    @NameInDb("key")
    private String key;

    /**
     * 缓存数据的值
     */
    @NameInDb("value")
    private String value;

    //endregion

    //region: 构造函数

    //endregion

    //region: 提供方法

    public CacheDBEntity setKey(@NonNull final String key) {
        this.key = key;
        return this;
    }

    public CacheDBEntity setValue(@NonNull final String value) {
        this.value = value;
        return this;
    }


    //endregion


}
