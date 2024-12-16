package com.xxl.hello.im.data.model.entity;

/**
 * 消息类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class MessageEntity {

    //region: 成员变量

    private SDKMessage mSdkMessage;

    /**
     * 消息类型
     */
    private int mMessageType;

    /**
     * 消息方向
     */
    @MessageDirection
    private int mMessageDirection;

    /**
     * 消息文本
     */
    private String mMessageText;

    //endregion

    //region: 构造函数

    public MessageEntity(SDKMessage sdkMessage) {
        mSdkMessage = sdkMessage;
    }

    public static MessageEntity obtain(SDKMessage sdkMessage) {
        return new MessageEntity(sdkMessage);
    }

    //endregion

    //region: get or set

    /**
     * 设置消息类型
     *
     * @param messageType
     * @return
     */
    public MessageEntity setMessageType(int messageType) {
        mMessageType = messageType;
        return this;
    }

    /**
     * 设置消息方向
     *
     * @param messageDirection
     * @return
     */
    public MessageEntity setMessageDirection(@MessageDirection int messageDirection) {
        mMessageDirection = messageDirection;
        return this;
    }

    /**
     * 设置消息文本
     *
     * @param messageText
     * @return
     */
    public MessageEntity setMessageText(String messageText) {
        mMessageText = messageText;
        return this;
    }

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

    /**
     * 获取消息方向
     *
     * @return
     */
    @MessageDirection
    public int getMessageDirection() {
        return mMessageDirection;
    }

    /**
     * 获取消息文本
     *
     * @return
     */
    public String getMessageText() {
        return mMessageText;
    }

    //endregion
}