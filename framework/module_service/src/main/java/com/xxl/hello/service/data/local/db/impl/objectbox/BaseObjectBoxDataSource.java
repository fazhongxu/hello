package com.xxl.hello.service.data.local.db.impl.objectbox;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.entity.BaseDBEntity;

import java.lang.reflect.ParameterizedType;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.QueryBuilder;

/**
 * 数据表操作基础服务
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:38 PM
 **/
public abstract class BaseObjectBoxDataSource<E extends BaseDBEntity> {

    //region: 成员变量

    /**
     * 数据库服务
     */
    private ObjectBoxDBClientKit mObjectBoxDBClientKit;

    //endregion

    //region: 构造函数

    public BaseObjectBoxDataSource(@NonNull final ObjectBoxDBClientKit objectBoxDBClientKit) {
        mObjectBoxDBClientKit = objectBoxDBClientKit;
    }

    //endregion

    //region: 生命周期

    /**
     * 获取数据表主键ID
     *
     * @return
     */
    public abstract Property<E> getPrimaryKey();

    //endregion

    //region: 提供方法

    public ObjectBoxDBClientKit getObjectBoxDBClientKit() {
        return mObjectBoxDBClientKit;
    }

    /**
     * 获取ObjectBox数据库
     *
     * @return
     */
    public BoxStore getBoxStore() {
        return mObjectBoxDBClientKit != null ? mObjectBoxDBClientKit.getOoxStore() : null;
    }

    /**
     * 获取ObjectBox数据库操作对象
     *
     * @return
     */
    public Box<E> getOperateBox() {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<E> eClass = (Class<E>) parameterizedType.getActualTypeArguments()[0];
            return getBoxStore().boxFor(eClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加数据
     *
     * @param targetEntity
     * @return
     */
    public long put(@NonNull final E targetEntity) {
        return getOperateBox().put(targetEntity);
    }

    /**
     * 添加数据
     *
     * @param targetEntities
     * @return
     */
    public void put(@NonNull final E... targetEntities) {
        getOperateBox().put(targetEntities);
    }

    /**
     * 删除数据
     *
     * @param  　id 主键id
     * @return
     */
    public boolean remove(final long id) {
        return getOperateBox()
                .remove(id);
    }

    /**
     * 删除数据
     *
     * @param ids 主键id
     * @return
     */
    public void remove(final long... ids) {
        getOperateBox()
                .remove(ids);
    }

    /**
     * 删除数据
     *
     * @param primaryKey 主键
     * @return
     */
    public boolean remove(@NonNull final String primaryKey) {
        return getOperateBox()
                .query()
                .in(getPrimaryKey(), new String[]{primaryKey}, QueryBuilder.StringOrder.CASE_SENSITIVE)
                .build()
                .remove() > 0;
    }

    /**
     * 删除数据
     *
     * @param targetEntity
     * @return
     */
    public boolean remove(@NonNull final E targetEntity) {
        return getOperateBox().remove(targetEntity);
    }

    /**
     * 获取数据
     * 默认不忽略大小写
     *
     * @param primaryKey 数据库主键
     * @return
     */
    public E get(@NonNull final String primaryKey) {
        return getOperateBox().query()
                .equal(getPrimaryKey(), primaryKey, QueryBuilder.StringOrder.CASE_SENSITIVE)
                .build()
                .findFirst();
    }

    /**
     * 获取数据
     *
     * @param primaryKey  数据库主键
     * @param stringOrder 是否忽略大小写
     * @return
     */
    public E get(@NonNull final String primaryKey,
                 @NonNull final QueryBuilder.StringOrder stringOrder) {
        return getOperateBox().query()
                .equal(getPrimaryKey(), primaryKey, stringOrder)
                .build()
                .findFirst();
    }

    //endregion

}
