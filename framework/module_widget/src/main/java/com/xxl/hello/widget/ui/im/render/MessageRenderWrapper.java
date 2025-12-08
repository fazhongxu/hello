package com.xxl.hello.widget.ui.im.render;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;

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
        registerMessageRender(NotificationMessageRender.obtain());
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
        return sMessageRenderMap.get(messageTag);
    }

    /**
     * 获取消息标识
     *
     * @param messageEntity
     * @return
     */
    public static String getMessageTag(MessageEntity messageEntity) {
        return String.valueOf(messageEntity.getMessageType());//未来可能有命令消息/通知消息 通知消息还有具体类型到时候组合成为tag使用
    }
}