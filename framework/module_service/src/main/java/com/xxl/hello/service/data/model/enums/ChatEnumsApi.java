package com.xxl.hello.service.data.model.enums;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xxl.
 * @date 2025/6/30.
 */
public class ChatEnumsApi {

    //region: 媒体数据类型

    @IntDef({SceneType.NORMAL,
            SceneType.CHAT,
            SceneType.FAVORITE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SceneType {

        /**
         * 普通展示
         */
        int NORMAL = 0;

        /**
         * 会话展示
         */
        int CHAT = 1;

        /**
         * 收藏展示
         */
        int FAVORITE = 2;
    }

    //endregion
}