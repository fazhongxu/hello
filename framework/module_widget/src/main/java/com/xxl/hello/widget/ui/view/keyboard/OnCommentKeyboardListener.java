package com.xxl.hello.widget.ui.view.keyboard;

import androidx.annotation.Nullable;

/**
 * 评论键盘监听事件
 *
 * @author xxl.
 * @date 2024/7/1.
 */
public interface OnCommentKeyboardListener {

    /**
     * 发送点击
     *
     * @param content
     */
    void onSendClick(@Nullable String content);
}