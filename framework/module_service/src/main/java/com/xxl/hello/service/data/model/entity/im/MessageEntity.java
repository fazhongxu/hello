package com.xxl.hello.service.data.model.entity.im;

import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;

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
    @MessageType
    private int mMessageType;

    /**
     * 消息方向
     */
    @MessageDirection
    private int mMessageDirection;

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
    public MessageEntity setMessageType(@MessageType int messageType) {
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
     * 获取消息类型
     *
     * @return
     */
    public int getMessageType() {
        return mMessageType;
    }

    /**
     * 获取消息模板类型
     *
     * @return
     */
    public String getMessageTemplateType() {
        if (mMessageType == MessageType.TEXT) {
            return MessageTemplateType.TEXT;
        } else if (mMessageType == MessageType.IMAGE) {
            return MessageTemplateType.IMAGE;
        } else if (mMessageType == MessageType.VIDEO) {
            return MessageTemplateType.VIDEO;
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
     * 获取消息发送人ID
     *
     * @return
     */
    public String getSenderId() {
        return "";
    }

    /**
     * 获取消息发送人昵称
     *
     * @return
     */
    public String getSenderNickname() {
        return "";
    }

    /**
     * 获取消息时间
     *
     * @return
     */
    public long getMessageTime() {
        if (mSdkMessage != null) {
            return mSdkMessage.getMessageTime();
        }
        return 0;
    }

    /**
     * 获取消息文本
     *
     * @return
     */
    public String getMessageText() {
        if (mSdkMessage != null) {
            return mSdkMessage.getTextContent();
        }
        return null;
    }

    public String getMediaPath() {
        if (mSdkMessage != null) {
            return mSdkMessage.getMediaPath();
        }
        return null;
    }

    /**
     * 获取通知消息内容
     *
     * @return
     */
    public String getNotificationContent(){
        if (mSdkMessage != null) {
            return mSdkMessage.getNotificationContent();
        }
        return null;
    }

    //endregion
}