package com.xxl.hello.service.data.model.entity.im;

/**
 * 回话信息
 *
 * @author xxl.
 * @date 2024/12/16.
 */
public class ConversationEntity {

    //region: 成员变量

    private SDKConversation mSdkConversation;

    private MessageEntity mLastMessageEntity;

    //endregion

    //region: 构造函数

    private ConversationEntity(SDKConversation sdkConversation) {
        mSdkConversation = sdkConversation;
    }

    public final static ConversationEntity obtain(SDKConversation sdkConversation) {
        return new ConversationEntity(sdkConversation);
    }

    //endregion

    //region: 提供方法

    public MessageEntity getLastMessageEntity() {
        return mLastMessageEntity;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}