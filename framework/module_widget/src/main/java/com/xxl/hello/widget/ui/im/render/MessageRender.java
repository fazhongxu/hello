package com.xxl.hello.widget.ui.im.render;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageType;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;

/**
 * 消息渲染
 *
 * @author xxl.
 * @date 2025/12/3.
 */
public interface MessageRender {

    /**
     * 获取消息类型
     *
     * @return
     */
    @MessageType
    int getMessageType();

    /**
     * 获取背景
     *
     * @param messageEntity
     * @return
     */
    Drawable getBackground(MessageEntity messageEntity);

    /**
     * 渲染
     *
     * @param context
     * @param container
     * @param messageEntity
     * @return
     */
    View render(Context context, FrameLayout container, MessageEntity messageEntity,OnMessageTemplateListener listener);

}