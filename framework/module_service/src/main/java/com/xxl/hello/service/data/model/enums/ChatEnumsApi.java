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

    //region: 消息类型

    @IntDef({MessageType.UNKNOW,
            MessageType.TEXT,
            MessageType.IMAGE,
            MessageType.VIDEO,
            MessageType.NOTIFICATION,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageType {

        /**
         * 未知消息
         */
        int UNKNOW = 0;

        /**
         * 文本消息
         */
        int TEXT = 1;

        /**
         * 图片消息
         */
        int IMAGE = 2;

        /**
         * 视频消息
         */
        int VIDEO = 3;

        /**
         * 通知消息
         */
        int NOTIFICATION = 100;
    }

    //endregion

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

    //region: 消息渲染类型

    @StringDef({MessageRenderType.UNKNOW,
            MessageRenderType.TEXT,
            MessageRenderType.IMAGE,
            MessageRenderType.VIDEO,
            MessageRenderType.NOTIFICATION,
            MessageRenderType.COMMAND,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageRenderType {

        /**
         * 未知消息
         */
        String UNKNOW = "unknow";

        /**
         * 文本消息
         */
        String TEXT = "text";

        /**
         * 图片消息
         */
        String IMAGE = "image";

        /**
         * 视频消息
         */
        String VIDEO = "video";

        /**
         * 通知消息（显示到视图）
         */
        String NOTIFICATION = "notification";

        /**
         * 命令消息（不显示到视图）
         */
        String COMMAND = "command";
    }

    //endregion

    //region: 通知消息类型

    @StringDef({NotificationMessageType.UNKNOW,
            NotificationMessageType.NORMAL_TEXT,
            NotificationMessageType.RECALL,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationMessageType {

        /**
         * 未知消息
         */
        String UNKNOW = "unknow";

        /**
         * 普通文本
         */
        String NORMAL_TEXT = "normal_text";

        /**
         * 撤回
         */
        String RECALL = "recall";
    }

    //endregion

    //region: 消息菜单操作类型

    @StringDef({MenuOperateType.COPY,
            MenuOperateType.EDIT,
            MenuOperateType.SHARE,
            MenuOperateType.RECALL,
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
         * 编辑
         */
        String EDIT = "edit";

        /**
         * 分享
         */
        String SHARE = "share";

        /**
         * 撤回
         */
        String RECALL = "recall";

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