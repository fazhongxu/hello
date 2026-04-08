package com.xxl.hello.service.data.local.db.impl;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.db.api.CategoryDBDataService;
import com.xxl.hello.service.data.local.db.entity.CategoryDBEntity;
import com.xxl.hello.service.data.local.db.entity.CategoryDBEntity_;

import io.objectbox.Property;

/**
 * 实现{@link CategoryDBDataService} 针对 {@link CategoryDBEntity} 数据表操作服务
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:38 PM
 **/
public class CategoryDataSource extends BaseDataSource<CategoryDBEntity> implements CategoryDBDataService {


    public CategoryDataSource(@NonNull ObjectBoxDBClientKit objectBoxDBClientKit) {
        super(objectBoxDBClientKit);
    }

    /**
     * 获取数据表主键ID
     *
     * @return
     */
    @Override
    public Property<CategoryDBEntity> getPrimaryKey() {
        return CategoryDBEntity_.categoryId;
    }

    /**
     * 添加分类
     *
     * @param categoryDBEntity
     * @return
     */
    @Override
    public boolean putCategory(CategoryDBEntity categoryDBEntity) {
        return put(categoryDBEntity) > 0;
    }

    /**
     * 获取分类
     *
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDBEntity getCategory(long categoryId) {
        return get(categoryId);
    }

    /**
     * 删除分类
     *
     * @param categoryId
     * @return
     */
    @Override
    public boolean removeCategory(long categoryId) {
        return remove(String.valueOf(categoryId));
    }
}
