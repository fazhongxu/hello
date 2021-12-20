package com.xxl.core.data.model.enums;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用户性别
 *
 * @author xxl.
 * @date 2021/7/15.
 */
@StringDef({UserSex.NONE,
        UserSex.MALE,
        UserSex.FEMALE})
@Retention(RetentionPolicy.SOURCE)
public @interface UserSex {

    /**
     * 未知
     */
    String NONE = "未知";

    /**
     * 男
     */
    String MALE = "男";

    /**
     * 女
     */
    String FEMALE = "女";

}