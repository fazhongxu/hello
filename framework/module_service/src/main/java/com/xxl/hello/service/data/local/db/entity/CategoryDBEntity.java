package com.xxl.hello.service.data.local.db.entity;

import com.xxl.hello.service.data.local.db.convert.EncryptConverter;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;


/**
 * 分类表
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:33 PM
 **/
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
    private String categoryName;

    /**
     * 扩展字段
     */
    @NameInDb("category_ext")
    String categoryExt;

    /**
     * 分类code
     */
    @NameInDb("category_code")
    String categoryCode;

    /**
     * 加密的分类code
     */
    @Convert(converter = EncryptConverter.class, dbType = String.class)
    @NameInDb("encrypted_category_code")
    String encryptedCategoryCode;

    //endregion

    //region: 构造函数

    //endregion

    //region: 提供方法

    public long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryCode() {
        if (encryptedCategoryCode != null) {
            return encryptedCategoryCode;
        }
        return categoryCode;
    }

    public CategoryDBEntity setCategoryId(long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public CategoryDBEntity setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public CategoryDBEntity setCategoryCode(String categoryCode) {
        this.encryptedCategoryCode = categoryCode;
        return this;
    }

    //endregion
}
