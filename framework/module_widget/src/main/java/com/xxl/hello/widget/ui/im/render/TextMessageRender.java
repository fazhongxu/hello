package com.xxl.hello.widget.ui.im.render;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;

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
public class TextMessageRender extends BaseMessageRender<WidgetRecycleItemMessageTextBinding> {

    //region: 构造函数

    private TextMessageRender() {

    }

    public final static TextMessageRender obtain() {
        return new TextMessageRender();
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
        return String.valueOf(MessageType.TEXT);
    }

    /**
     * 获取消息摘要
     *
     * @param messageEntity
     * @return
     */
    @Override
    public CharSequence getContentSummary(MessageEntity messageEntity) {
        if (!TextUtils.isEmpty(messageEntity.getMessageText())) {
            return messageEntity.getMessageText();
        }
        return "[文本]";
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
     * 获取资源视图
     *
     * @return
     */
    @Override
    public int getLayoutRes() {
        return R.layout.widget_recycle_item_message_text;
    }

    /**
     * 渲染
     *
     * @param messageBinding
     * @param messageEntity
     * @param listener
     */
    @Override
    public void render(WidgetRecycleItemMessageTextBinding messageBinding, MessageEntity messageEntity, OnMessageTemplateListener listener) {
        messageBinding.tvContent.setText(messageEntity.getMessageText());
        messageBinding.tvContent.setOnClickListener(v -> {
            if (listener != null && listener.onMessageItemClick(messageEntity)) {
                return;
            }
        });
        messageBinding.llItemContainer.setOnLongClickListener(v -> {
            if (listener != null && listener.onMessageItemLongClick(messageBinding.llItemContainer, messageEntity)) {
                return true;
            }
            return false;
        });
        messageBinding.tvContent.setOnLongClickListener(v -> {
            if (listener != null && listener.onMessageItemLongClick(messageBinding.llItemContainer, messageEntity)) {
                return true;
            }
            return false;
        });

        messageBinding.executePendingBindings();
    }

    //endregion

}