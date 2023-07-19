package com.xxl.hello.service.data.model.enums;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用户模块枚举
 *
 * @author xxl.
 * @date 2021/7/23.
 */
public class UserEnumsApi {

    //region: 用户性别

    @IntDef({UserSex.NONE,
            UserSex.MALE,
            UserSex.FEMALE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UserSex {

        /**
         * 未知
         */
        int NONE = 0;

        /**
         * 男
         */
        int MALE = 1;

        /**
         * 女
         */
        int FEMALE = 2;
    }

    //endregion

    //region: VIP模块

    @StringDef({VipModel.USER,
            VipModel.MATERIAL,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface VipModel {

        /**
         * 用户模块
         */
        String USER = "user";

        /**
         * 素材模块
         */
        String MATERIAL = "material";
    }

    //endregion
}