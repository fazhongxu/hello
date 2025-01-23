package com.xxl.hello.service.data.model.entity.im;

/**
 * 消息类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class FeedBackRecordsEntity {

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

    //endregion

    //region: 构造函数

    public FeedBackRecordsEntity(SDKMessage sdkMessage) {
        mSdkMessage = sdkMessage;
    }

    public static FeedBackRecordsEntity obtain(SDKMessage sdkMessage) {
        return new FeedBackRecordsEntity(sdkMessage);
    }

    //endregion

    //region: get or set

    /**
     * 设置消息类型
     *
     * @param messageType
     * @return
     */
    public FeedBackRecordsEntity setMessageType(int messageType) {
        mMessageType = messageType;
        return this;
    }

    /**
     * 设置消息方向
     *
     * @param messageDirection
     * @return
     */
    public FeedBackRecordsEntity setMessageDirection(@MessageDirection int messageDirection) {
        mMessageDirection = messageDirection;
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

    //endregion
}