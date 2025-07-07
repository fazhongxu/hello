package com.xxl.hello.widget.ui.im.message.session.base.menu;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.ui.im.message.session.base.BaseChatSessionFragment;

/**
 * 删除
 *
 * @author xxl.
 * @date 2025/6/30.
 */
public class OnDeleteOperate implements OnMenuItemOperate<BaseChatSessionFragment>{

    /**
     * 点击操作
     *
     * @param fragment
     * @param messageEntity
     */
    @Override
    public void handle(@NonNull BaseChatSessionFragment fragment,
                       @NonNull MessageEntity messageEntity) {
        fragment.onMessageDeleteClick(messageEntity);
    }
}