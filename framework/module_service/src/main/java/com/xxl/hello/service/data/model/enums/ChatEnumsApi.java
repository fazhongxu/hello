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

    @IntDef({SessionType.NONE,
            SessionType.PRIVATE,
            SessionType.GROUP,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionType {

        /**
         * 未知类型
         */
        int NONE = -1;

        /**
         * 单聊
         */
        int PRIVATE = 0;

        /**
         * 群聊
         */
        int GROUP = 1;
    }

    //endregion

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

    //region: 消息状态

    @IntDef({MessageStatus.UNKNOW,
            MessageStatus.SEND_SUCCESS,
            MessageStatus.SEND_FAILURE,
            MessageStatus.SENDING,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageStatus {

        /**
         * 未知
         */
        int UNKNOW = 0;

        /**
         * 发送成功
         */
        int SEND_SUCCESS = 1;

        /**
         * 发送失败
         */
        int SEND_FAILURE = 2;

        /**
         * 发送中
         */
        int SENDING = 3;
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
            MenuOperateType.MULTI_SELECT,
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

        /**
         * 多选
         */
        String MULTI_SELECT = "multi_select";
    }

    //endregion
}