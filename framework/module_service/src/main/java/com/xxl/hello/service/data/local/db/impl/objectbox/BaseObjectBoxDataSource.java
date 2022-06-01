package com.xxl.hello.service.data.local.db.impl.objectbox;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.entity.BaseDBEntity;

import io.objectbox.BoxStore;
import io.objectbox.Property;

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
     * 获取数据表ID
     *
     * @return
     */
    public abstract Property<E> getTableId();

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
    public BoxStore getOoxStore() {
        return mObjectBoxDBClientKit != null ? mObjectBoxDBClientKit.getOoxStore() : null;
    }

    // TODO: 2021/11/21 操作方法抽取

    //endregion

}
