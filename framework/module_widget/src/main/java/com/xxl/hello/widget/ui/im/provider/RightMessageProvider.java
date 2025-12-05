package com.xxl.hello.widget.ui.im.provider;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageRightBinding;
import com.xxl.hello.widget.ui.im.render.MessageRender;
import com.xxl.hello.widget.ui.im.render.MessageRenderWrapper;
import com.xxl.hello.widget.ui.im.template.OnMessageTemplateListener;

/**
 * 右边模板提供类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class RightMessageProvider extends BaseItemProvider<MessageEntity> {

    //region: 成员变量

    private OnMessageTemplateListener mListener;

    //endregion

    //region: 构造函数

    public RightMessageProvider(OnMessageTemplateListener listener){
        mListener = listener;
    }

    public static RightMessageProvider obtain(OnMessageTemplateListener listener) {
        return new RightMessageProvider(listener);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public int getItemViewType() {
        return MessageDirection.RIGHT;
    }

    @Override
    public int getLayoutId() {
        return R.layout.widget_recycle_item_message_right;
    }

    @Override
    public void convert(@NonNull BaseViewHolder viewHolder, MessageEntity messageEntity) {
        WidgetRecycleItemMessageRightBinding itemBinding = DataBindingUtil.bind(viewHolder.itemView);
        MessageRender messageRender = MessageRenderWrapper.getMessageRender(messageEntity.getMessageType());
        Drawable background = messageRender.getBackground(messageEntity);
        itemBinding.flMessageContainer.setBackground(background);
        messageRender.render(getContext(),itemBinding.flMessageContainer,messageEntity,mListener);
        itemBinding.executePendingBindings();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}