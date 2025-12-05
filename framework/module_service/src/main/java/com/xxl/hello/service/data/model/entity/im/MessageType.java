package com.xxl.hello.service.data.model.entity.im;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 消息类型
 *
 * @author xxl.
 * @date 2024/6/14.
 */
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