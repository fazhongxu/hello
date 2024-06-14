package com.xxl.hello.im.data.model.entity;

/**
 * 消息类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class MessageEntity {

    /**
     * 消息类型
     */
    private int mMessageType;

    /**
     * 获取消息模板类型
     *
     * @return
     */
    public String getMessageTemplateType() {
        if (mMessageType == 1) {
            return MessageTemplateType.TEXT;
        } else if (mMessageType == 2) {
            return MessageTemplateType.IMAGE;
        }
        return MessageTemplateType.UNKNOW;
    }
}