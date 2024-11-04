package com.xxl.hello.im.ui.message.session.base.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseBindingAdapter;
import com.xxl.hello.im.R;
import com.xxl.hello.im.data.model.entity.MessageDirection;
import com.xxl.hello.im.data.model.entity.MessageEntity;
import com.xxl.hello.im.data.model.entity.MessageTemplate;
import com.xxl.hello.im.databinding.ImRecycleItemChatSessionBinding;
import com.xxl.hello.im.ui.template.MessageTemplateWrapper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 会话列表适配器
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class ChatSessionAdapter extends BaseBindingAdapter<MessageEntity, ChatSessionRecycleItemListener, ImRecycleItemChatSessionBinding> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    @Inject
    public ChatSessionAdapter() {
        super(R.layout.im_recycle_item_chat_session);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void convert(@NonNull ImRecycleItemChatSessionBinding itemBinding,
                        @NonNull MessageEntity itemEntity) {
        ChatSessionRecycleItemViewModel viewModel = itemBinding.getViewModel();
        if (viewModel == null) {
            viewModel = new ChatSessionRecycleItemViewModel(itemEntity);
            itemBinding.setViewModel(viewModel);
        }
        itemBinding.setListener(mListener);
        setMessageLayout(itemBinding, itemEntity);
        itemBinding.executePendingBindings();
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置消息视图
     *
     * @param itemBinding
     * @param itemEntity
     */
    private void setMessageLayout(@NonNull ImRecycleItemChatSessionBinding itemBinding,
                                  @NonNull MessageEntity itemEntity) {
        View view = MessageTemplateWrapper.bindView(itemBinding.flMessageProviderLayout, itemEntity, getItemPosition(itemEntity), null);
        setMessageGravity(itemBinding, itemEntity);
        setUserLayout(itemBinding, itemEntity);
    }

    /**
     * 设置消息对齐方式
     *
     * @param itemBinding
     * @param itemEntity
     */
    private void setMessageGravity(@NonNull ImRecycleItemChatSessionBinding itemBinding,
                                   @NonNull MessageEntity itemEntity) {
        MessageTemplate messageTemplate = MessageTemplateWrapper.getMessageTemplateAnnotation(itemEntity.getMessageTemplateType());
        if (messageTemplate.isCenterHorizontal()) {
            itemBinding.flMessageProviderLayout.setChildGravityCenter();
        } else {
            if (itemEntity.getMessageDirection() == MessageDirection.LEFT) {
                itemBinding.flMessageProviderLayout.setChildGravityLeft();
            } else {
                itemBinding.flMessageProviderLayout.setChildGravityRight();
            }
        }
    }

    /**
     * 设置用户信息视图
     *
     * @param itemBinding
     * @param itemEntity
     */
    private void setUserLayout(@NonNull ImRecycleItemChatSessionBinding itemBinding,
                               @NonNull MessageEntity itemEntity) {
        if (itemEntity.getMessageDirection() == MessageDirection.LEFT) {
            itemBinding.ivLeftAvatar.setVisibility(View.VISIBLE);
            itemBinding.ivRightAvatar.setVisibility(View.INVISIBLE);
            itemBinding.tvLeftNickname.setVisibility(View.VISIBLE);
            itemBinding.tvRightNickname.setVisibility(View.INVISIBLE);
        } else {
            itemBinding.ivLeftAvatar.setVisibility(View.INVISIBLE);
            itemBinding.ivRightAvatar.setVisibility(View.VISIBLE);
            itemBinding.tvLeftNickname.setVisibility(View.INVISIBLE);
            itemBinding.tvRightNickname.setVisibility(View.VISIBLE);
        }
    }

    //endregion

}