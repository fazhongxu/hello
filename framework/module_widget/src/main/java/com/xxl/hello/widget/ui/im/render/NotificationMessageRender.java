package com.xxl.hello.widget.ui.im.render;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageNotificationBinding;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;

/**
 * 通知消息渲染
 *
 * @author xxl.
 * @date 2025/12/5.
 */
public abstract class NotificationMessageRender extends BaseMessageRender<WidgetRecycleItemMessageNotificationBinding> {

    //region: 构造函数

    public NotificationMessageRender() {

    }

    //endregion

    //region: 生命周期

    /**
     * 获取背景
     *
     * @param messageEntity
     */
    @Override
    public Drawable getBackground(MessageEntity messageEntity) {
        return null;
    }

    @Override
    public int getResLayout() {
        return R.layout.widget_recycle_item_message_notification;
    }

    @Override
    public void render(WidgetRecycleItemMessageNotificationBinding messageBinding, MessageEntity messageEntity, OnMessageTemplateListener listener) {
        CharSequence content = getNotificationContent(messageEntity);
        messageBinding.tvContent.setText(content);

        messageBinding.tvContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null && listener.onMessageItemLongClick(messageBinding.llItemContainer, messageEntity)) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取通知消息内容
     *
     * @param messageEntity
     * @return
     */
    public abstract CharSequence getNotificationContent(MessageEntity messageEntity);

    //endregion

}