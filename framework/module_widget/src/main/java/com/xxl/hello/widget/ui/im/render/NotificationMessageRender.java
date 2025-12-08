package com.xxl.hello.widget.ui.im.render;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.MessageType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageNotificationBinding;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;

/**
 * 通知消息渲染
 *
 * @author xxl.
 * @date 2025/12/5.
 */
public class NotificationMessageRender implements MessageRender {

    //region: 成员变量


    //endregion

    //region: 构造函数

    private NotificationMessageRender() {

    }

    public final static NotificationMessageRender obtain() {
        return new NotificationMessageRender();
    }

    //endregion

    //region: 生命周期

    /**
     * 获取消息类型
     *
     * @return
     */
    @Override
    public int getMessageType() {
        return MessageType.NOTIFICATION;
    }

    /**
     * 获取背景
     *
     * @param messageEntity
     */
    @Override
    public Drawable getBackground(MessageEntity messageEntity) {
       return null;
    }

    /**
     * 渲染
     *
     * @param context
     * @param container
     * @param messageEntity
     */
    @Override
    public View render(Context context, FrameLayout container, MessageEntity messageEntity, OnMessageTemplateListener listener) {
        WidgetRecycleItemMessageNotificationBinding messageBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.widget_recycle_item_message_notification, container, false);
        container.addView(messageBinding.getRoot());
        messageBinding.executePendingBindings();
        return messageBinding.getRoot();
    }

    //endregion

}