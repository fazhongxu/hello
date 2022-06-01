package com.xxl.hello.service.data.local.db.entity;

import com.xxl.core.utils.TimeUtils;

import io.objectbox.annotation.BaseEntity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;


/**
 * 数据表基础实体类
 *
 * @Author: xxl
 * @Date: 2021/11/21 12:28 AM
 **/
@BaseEntity
public abstract class BaseDBEntity<T extends BaseDBEntity> {

    //region: 成员变量

    /**
     * ID
     */
    @Id
    @NameInDb("id")
    private long id;

    /**
     * 创建时间
     */
    @NameInDb("create_time")
    private long createTime;

    /**
     * 更新时间
     */
    @NameInDb("update_time")
    private long updateTime;

    //endregion

    //region: 构造函数

    public BaseDBEntity() {
        createTime = TimeUtils.currentTimeMillis();
        updateTime = TimeUtils.currentTimeMillis();
    }

    //endregion

    //region: 提供方法

    public long getId() {
        return id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }


    /**
     * 设置id
     *
     * @param id
     * @return
     */
    public T setId(final long id) {
        this.id = id;
        return (T) this;
    }

    /**
     * 设置创建时间
     *
     * @param createTime
     * @return
     */
    public T setCreateTime(final long createTime) {
        this.createTime = createTime;
        return (T) this;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime
     * @return
     */
    public T setUpdateTime(final long updateTime) {
        this.createTime = updateTime;
        return (T) this;
    }

    //endregion


}
