package com.xxl.hello.widget.ui.im.template;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.im.MessageEntity;

/**
 * 模板监听事件
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public interface OnMessageTemplateListener {

    /**
     * 头像点击
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    default void onAvatarClick(@NonNull View targetView,
                               @NonNull String targetUserId,
                               @NonNull String targetNickname) {

    }

    /**
     * 头像双击
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    default void onAvatarDoubleClick(@NonNull View targetView,
                                     @NonNull String targetUserId,
                                     @NonNull String targetNickname) {

    }

    /**
     * 头像长按
     *
     * @param targetView
     * @param targetUserId
     * @param targetNickname
     */
    default boolean onAvatarLongClick(@NonNull View targetView,
                                      @NonNull String targetUserId,
                                      @NonNull String targetNickname) {
        return false;
    }

    /**
     * 消息点击
     *
     * @param messageEntity
     * @return
     */
    default boolean onMessageItemClick(MessageEntity messageEntity) {
        return false;
    }

    /**
     * 消息长按点击
     *
     * @param targetView
     * @param messageEntity
     * @return
     */
    default boolean onMessageItemLongClick(View targetView,
                                           MessageEntity messageEntity) {
        return false;
    }
}