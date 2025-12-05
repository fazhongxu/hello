package com.xxl.hello.widget.ui.im.render;

import com.xxl.hello.service.data.model.entity.im.MessageType;

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
        int messageType = render.getMessageType();
        if (messageType <= 0) {
            throw new RuntimeException("registerMessageRender message type must greater than 0");
        }
        sMessageRenderMap.put(String.valueOf(messageType), render);
    }

    /**
     * 获取消息渲染器
     *
     * @param messageType
     * @return
     */
    public static MessageRender getMessageRender(@MessageType int messageType) {
        return sMessageRenderMap.get(String.valueOf(messageType));
    }


}