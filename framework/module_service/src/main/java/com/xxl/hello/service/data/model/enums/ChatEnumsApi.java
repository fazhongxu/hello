package com.xxl.hello.service.data.model.enums;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xxl.
 * @date 2025/6/30.
 */
public class ChatEnumsApi {

    //region: 消息展示场景

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

    //region: 消息菜单操作类型

    @StringDef({MenuOperateType.COPY,
            MenuOperateType.ADD_EMOTION,
            MenuOperateType.SHARE,
            MenuOperateType.DELETE,
            MenuOperateType.FAVORITE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface MenuOperateType {

        /**
         * 复制
         */
        String COPY = "copy";

        /**
         * 添加表情
         */
        String ADD_EMOTION = "add_emotion";

        /**
         * 分享
         */
        String SHARE = "share";

        /**
         * 删除
         */
        String DELETE = "delete";

        /**
         * 收藏
         */
        String FAVORITE = "favorite";
    }

    //endregion
}