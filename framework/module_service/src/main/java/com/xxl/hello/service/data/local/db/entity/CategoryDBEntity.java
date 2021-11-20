package com.xxl.hello.service.data.local.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;
import lombok.Getter;

/**
 * 分类表
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:33 PM
 **/
@Getter
@Entity
@NameInDb("h_t_category")
public class CategoryDBEntity extends BaseDBEntity<CategoryDBEntity> {

    //region: 成员变量

    /**
     * 分类ID
     */
    @Unique
    @Index
    @NameInDb("category_id")
    private long categoryId;

    /**
     * 分类名称
     */
    @NameInDb("category_name")
    private long categoryName;

    //endregion

    //region: 构造函数

    //endregion

    //region: 提供方法

    //endregion
}
