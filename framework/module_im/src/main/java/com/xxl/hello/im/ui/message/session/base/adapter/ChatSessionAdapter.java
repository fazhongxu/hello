package com.xxl.hello.im.ui.message.session.base.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseBindingAdapter;
import com.xxl.hello.im.R;
import com.xxl.hello.im.databinding.ImRecycleItemChatSessionBinding;
import com.xxl.hello.im.data.model.entity.MessageEntity;
import com.xxl.hello.widget.im.MessageTemplateWrapper;

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
        setSessionLayout(itemBinding, itemEntity);
        itemBinding.executePendingBindings();
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 设置会话视图
     *
     * @param itemBinding
     * @param itemEntity
     */
    private void setSessionLayout(@NonNull ImRecycleItemChatSessionBinding itemBinding,
                                  @NonNull MessageEntity itemEntity) {
        View view = MessageTemplateWrapper.bindView(itemBinding.flMessageProviderLayout, itemEntity,getItemPosition(itemEntity),null);

    }

    //endregion

}