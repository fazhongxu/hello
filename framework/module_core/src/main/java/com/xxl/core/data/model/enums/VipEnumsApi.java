package com.xxl.core.data.model.enums;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * VIP模块枚举
 *
 * @author xxl.
 * @date 2023/7/19.
 */
public class VipEnumsApi {

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