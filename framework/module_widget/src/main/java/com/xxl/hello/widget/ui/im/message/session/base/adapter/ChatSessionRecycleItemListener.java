package com.xxl.hello.widget.ui.im.message.session.base.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseRecycleItemListener;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi.MenuOperateType;

/**
 * 会话条目监听
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public interface ChatSessionRecycleItemListener extends BaseRecycleItemListener {

    /**
     * 头像点击
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    void onAvatarClick(@NonNull View targetView,
                       @NonNull String targetUserId,
                       @NonNull String targetNickname);

    /**
     * 头像双击
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    void onAvatarDoubleClick(@NonNull View targetView,
                             @NonNull String targetUserId,
                             @NonNull String targetNickname);

    /**
     * 头像长按
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    boolean onAvatarLongClick(@NonNull View targetView,
                              @NonNull String targetUserId,
                              @NonNull String targetNickname);


    /**
     * 消息菜单条目
     *
     * @param operateType
     * @param messageEntity
     */
    void onMessageMenuItemClick(@MenuOperateType String operateType,
                                @NonNull MessageEntity messageEntity);

    /**
     * 多选模式下选中数量变化
     *
     * @param selectedCount 当前选中数量
     */
    void onMultiSelectCountChanged(int selectedCount);

}