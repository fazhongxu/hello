package com.xxl.hello.widget.ui.im.render;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.databinding.DataBindingUtil;

import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MessageType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageTextBinding;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;
import com.xxl.kit.DrawableUtils;

/**
 * 文本消息渲染
 *
 * @author xxl.
 * @date 2025/12/5.
 */
public class TextMessageRender implements MessageRender {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private TextMessageRender() {

    }

    public final static TextMessageRender obtain() {
        return new TextMessageRender();
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
        return String.valueOf(MessageType.TEXT);
    }

    /**
     * 获取背景
     *
     * @param messageEntity
     */
    @Override
    public Drawable getBackground(MessageEntity messageEntity) {
        int direction = messageEntity.getMessageDirection();
        if (direction == MessageDirection.LEFT) {
            return DrawableUtils.getDrawable(R.drawable.resources_bg_chat_text_left);
        } else {
            return DrawableUtils.getDrawable(R.drawable.resources_bg_chat_text_right);
        }
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
        WidgetRecycleItemMessageTextBinding messageBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.widget_recycle_item_message_text, container, false);
        container.addView(messageBinding.getRoot());
        messageBinding.tvContent.setText(messageEntity.getMessageText());

        messageBinding.tvContent.setOnClickListener(v -> {
            if (listener != null && listener.onMessageItemClick(messageEntity)) {
                return;
            }
        });
        messageBinding.llItemContainer.setOnLongClickListener(v -> {
            if (listener != null && listener.onMessageItemLongClick(messageBinding.llItemContainer,messageEntity)){
                return true;
            }
            return false;
        });
        messageBinding.tvContent.setOnLongClickListener(v -> {
            if (listener != null && listener.onMessageItemLongClick(messageBinding.llItemContainer,messageEntity)){
                return true;
            }
            return false;
        });

        messageBinding.executePendingBindings();
        return messageBinding.getRoot();
    }

    //endregion

}