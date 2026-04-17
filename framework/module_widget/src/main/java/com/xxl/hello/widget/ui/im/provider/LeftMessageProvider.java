package com.xxl.hello.widget.ui.im.provider;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.SessionType;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetRecycleItemMessageLeftBinding;
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

    public LeftMessageProvider(OnMessageTemplateListener listener) {
        super(listener);
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
    public void convert(@NonNull WidgetRecycleItemMessageLeftBinding itemBinding, @NonNull MessageEntity itemEntity) {
        render(itemBinding.flMessageContainer, itemEntity);
        setupMessageTime(itemBinding.includeMessageTime.tvMessageTime, itemEntity);
        setUserNickName(itemBinding, itemEntity);
        setUserAvatarListener(itemBinding.ivAvatar, itemEntity);
    }

    /**
     * 设置用户昵称
     *
     * @param itemBinding
     * @param itemEntity
     */
    private void setUserNickName(@NonNull WidgetRecycleItemMessageLeftBinding itemBinding, @NonNull MessageEntity itemEntity) {
        if (itemEntity.getSessionType() == SessionType.GROUP) {
            itemBinding.tvNickname.setText(itemEntity.getSenderNickname());
            itemBinding.tvNickname.setVisibility(View.VISIBLE);
        } else {
            itemBinding.tvNickname.setText("");
            itemBinding.tvNickname.setVisibility(View.GONE);
        }
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}