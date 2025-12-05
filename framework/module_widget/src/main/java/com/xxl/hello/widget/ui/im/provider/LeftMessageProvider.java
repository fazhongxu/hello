package com.xxl.hello.widget.ui.im.provider;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageLeftBinding;
import com.xxl.hello.widget.ui.im.message.session.base.adapter.ChatSessionRenderAdapter;
import com.xxl.hello.widget.ui.im.render.MessageRender;
import com.xxl.hello.widget.ui.im.render.MessageRenderWrapper;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;

/**
 * 左边模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class LeftMessageProvider extends BaseMessageProvider<WidgetRecycleItemMessageLeftBinding, MessageEntity> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public LeftMessageProvider(ChatSessionRenderAdapter adapter, OnMessageTemplateListener listener) {
        super(adapter,listener);
    }

    public static LeftMessageProvider obtain(ChatSessionRenderAdapter adapter,OnMessageTemplateListener listener) {
        return new LeftMessageProvider(adapter,listener);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public int getItemViewType() {
        return MessageDirection.LEFT;
    }

    @Override
    public int getLayoutId() {
        return R.layout.widget_recycle_item_message_left;
    }

    @Override
    public void convert(@NonNull WidgetRecycleItemMessageLeftBinding itemBinding, MessageEntity itemEntity) {
        MessageRender messageRender = MessageRenderWrapper.getMessageRender(itemEntity.getMessageType());
        Drawable background = messageRender.getBackground(itemEntity);
        itemBinding.flMessageContainer.setBackground(background);
        messageRender.render(getContext(), itemBinding.flMessageContainer, itemEntity, mListener);
        setupMessageTime(itemBinding.tvMessageTime,itemEntity);
        setUserAvatarListener(itemBinding.ivAvatar, itemEntity);
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}