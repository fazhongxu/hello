package com.xxl.hello.widget.ui.im.render;

import static com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.NotificationMessageType;

import java.util.LinkedHashMap;

/**
 * @author xxl.
 * @date 2025/12/5.
 */
public class MessageRenderWrapper {

    private static LinkedHashMap<String, MessageRender> sMessageRenderMap = new LinkedHashMap<>();

    static {
        registerMessageRender(TextMessageRender.obtain());
        registerMessageRender(ImageMessageRender.obtain());
        registerMessageRender(NormalTextNotificationMessageRender.obtain());
        registerMessageRender(RecallNotificationMessageRender.obtain());
    }

    /**
     * 注册消息渲染器
     *
     * @param render
     */
    public static void registerMessageRender(MessageRender render) {
        String messageTag = render.getMessageTag();
        if (messageTag == null) {
            throw new RuntimeException("registerMessageRender getMessageTag must not be empty");
        }
        sMessageRenderMap.put(messageTag, render);
    }

    /**
     * 获取消息渲染器
     *
     * @param messageEntity
     * @return
     */
    public static MessageRender getMessageRender(MessageEntity messageEntity) {
        String messageTag = getMessageTag(messageEntity);
        MessageRender messageRender = sMessageRenderMap.get(messageTag);
        if (messageRender != null) {
            return messageRender;
        }
        return UnKnowMessageRender.obtain();
    }

    /**
     * 获取消息标识
     *
     * @param messageEntity
     * @return
     */
    public static String getMessageTag(MessageEntity messageEntity) {
        if (messageEntity.getMessageType() == MessageType.NOTIFICATION) {
            if (NotificationMessageType.RECALL.equals(messageEntity.getNotificationContent())) {
                return messageEntity.getMessageType() + NotificationMessageType.RECALL;
            }
        }
        return String.valueOf(messageEntity.getMessageType());
    }
}