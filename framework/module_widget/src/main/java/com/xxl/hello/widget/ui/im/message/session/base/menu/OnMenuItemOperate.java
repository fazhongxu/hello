package com.xxl.hello.widget.ui.im.message.session.base.menu;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.widget.ui.im.message.session.base.BaseChatSessionFragment;

/**
 * 菜单item点击操作
 *
 * @author xxl.
 * @date 2025/6/30.
 */
public interface OnMenuItemOperate<F extends BaseChatSessionFragment> {

    /**
     * 点击操作
     *
     * @param fragment
     * @param messageEntity
     */
    void handle(@NonNull F fragment,
                @NonNull MessageEntity messageEntity);

}