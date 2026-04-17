package com.xxl.hello.service.data.local.db.api;

import com.xxl.hello.service.data.local.db.entity.CategoryDBEntity;

/**
 * 分类数据库服务
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:36 PM
 **/
public interface CategoryDBDataService {

    /**
     * 添加分类
     *
     * @param categoryDBEntity
     * @return
     */
    boolean putCategory(CategoryDBEntity categoryDBEntity);

    /**
     * 获取分类
     *
     * @param categoryId
     * @return
     */
    CategoryDBEntity getCategory(long categoryId);

    /**
     * 删除分类
     *
     * @param categoryId
     * @return
     */
    boolean removeCategory(long categoryId);
}
