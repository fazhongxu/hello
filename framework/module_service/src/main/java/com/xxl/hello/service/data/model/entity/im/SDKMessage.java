package com.xxl.hello.service.data.model.entity.im;

import com.xxl.kit.TimeUtils;

/**
 * 假设这个是IM SDK 消息实体
 * @author xxl.
 * @date 2024/12/16.
 */
public class SDKMessage {

    //region: 成员变量

    /**
     * 消息时间
     */
    private long mMessageTime;

    /**
     * 消息文本
     */
    private String mTextContent;

    /**
     * 多媒体路径
     */
    private String mMediaPath;

    //endregion

    //region: 构造函数

    private SDKMessage() {
        mMessageTime = TimeUtils.currentServiceTimeMillis();
    }

    public final static SDKMessage obtain() {
        return new SDKMessage();
    }

    //endregion

    //region: 提供方法


    public long getMessageTime() {
        return mMessageTime;
    }

    public String getTextContent() {
        return mTextContent;
    }

    public String getMediaPath() {
        return mMediaPath;
    }

    public SDKMessage setMessageTime(long messageTime) {
        this.mMessageTime = messageTime;
        return this;
    }

    public SDKMessage setTextContent(String content) {
        mTextContent = content;
        return this;
    }

    public SDKMessage setMediaPath(String mediaPath) {
        mMediaPath = mediaPath;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}