package com.xxl.hello.im.data.model.entity;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 消息模板类型
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@StringDef({MessageTemplateType.UNKNOW,
        MessageTemplateType.TEXT,
        MessageTemplateType.IMAGE})
@Retention(RetentionPolicy.SOURCE)
public @interface MessageTemplateType {

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

}