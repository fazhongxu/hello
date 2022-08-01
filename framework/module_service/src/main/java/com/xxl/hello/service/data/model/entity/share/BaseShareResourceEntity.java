package com.xxl.hello.service.data.model.entity.share;

import androidx.annotation.NonNull;

/**
 * 资源分享实体基础类
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public abstract class BaseShareResourceEntity<T extends BaseShareResourceEntity> {

    //region: 成员变量

    /**
     * item垂直间隙
     */
    private String mVerticalSpancing;

    /**
     * item水平间隙
     */
    private String mHorizontalSpancing;

    /**
     * 描述文本
     */
    private String mDescription;

    /**
     * 链接地址
     */
    private String mLinkUrl;

    //endregion

    //region: 构造函数

    //endregion

    //region: 提供方法

    /**
     * 获取描述文本
     *
     * @return
     */
    public String getDescription() {
        return mDescription;
    }
    
    /**
     * 设置描述文本
     *
     * @param description
     * @return
     */
    public T setDescription(@NonNull final String description) {
        this.mDescription = description;
        return (T) this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}