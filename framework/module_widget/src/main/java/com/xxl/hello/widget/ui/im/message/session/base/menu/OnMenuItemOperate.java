package com.xxl.hello.widget.ui.im.message.session.base.menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;

/**
 * 菜单item点击操作
 *
 * @author xxl.
 * @date 2025/6/30.
 */
public interface OnMenuItemOperate {

    /**
     * 点击操作
     *
     * @param fragment
     * @param messageEntity
     */
    void handle(@NonNull Fragment fragment,
                @NonNull MessageEntity messageEntity);

}