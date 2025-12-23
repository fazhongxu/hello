package com.xxl.hello.widget.ui.im.render;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.NotificationMessageType;

/**
 * 普通文本通知消息渲染
 *
 * @author xxl.
 * @date 2025/12/5.
 */
public class NormalTextNotificationMessageRender extends NotificationMessageRender {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private NormalTextNotificationMessageRender() {

    }

    public final static NormalTextNotificationMessageRender obtain() {
        return new NormalTextNotificationMessageRender();
    }

    //endregion

    //region: 生命周期

    /**
     * 获取渲染标识
     *
     * @return
     */
    @Override
    public String getRenderTag() {
        return MessageType.NOTIFICATION + NotificationMessageType.NORMAL_TEXT;
    }

    /**
     * 获取消息摘要
     *
     * @param messageEntity
     * @return
     */
    @Override
    public CharSequence getContentSummary(MessageEntity messageEntity) {
        return "[通知]";
    }

    /**
     * 获取通知消息内容
     *
     * @param messageEntity
     * @return
     */
    @Override
    public CharSequence getNotificationContent(MessageEntity messageEntity) {
        return messageEntity.getNotificationContent();
    }

    //endregion

}