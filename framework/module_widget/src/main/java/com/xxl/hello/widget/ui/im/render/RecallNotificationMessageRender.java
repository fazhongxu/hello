package com.xxl.hello.widget.ui.im.render;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.NotificationMessageType;
import com.xxl.hello.widget.R;
import com.xxl.kit.StringUtils;

/**
 * 消息撤回通知消息渲染
 *
 * @author xxl.
 * @date 2025/12/5.
 */
public class RecallNotificationMessageRender extends NotificationMessageRender {

    //region: 成员变量


    //endregion

    //region: 构造函数

    private RecallNotificationMessageRender() {

    }

    public final static RecallNotificationMessageRender obtain() {
        return new RecallNotificationMessageRender();
    }

    //endregion

    //region: 生命周期

    /**
     * 获取消息标识
     *
     * @return
     */
    @Override
    public String getMessageTag() {
        return MessageType.NOTIFICATION + NotificationMessageType.RECALL;
    }

    /**
     * 获取通知消息内容
     *
     * @param messageEntity
     * @return
     */
    @Override
    public CharSequence getNotificationContent(MessageEntity messageEntity) {
        return StringUtils.getString(R.string.resources_message_recall_format,StringUtils.getString(R.string.resources_you_text));
    }

    //endregion

}