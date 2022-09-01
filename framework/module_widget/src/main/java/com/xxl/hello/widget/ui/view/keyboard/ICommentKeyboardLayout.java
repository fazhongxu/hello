package com.xxl.hello.widget.ui.view.keyboard;

import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2022/8/31.
 */
public interface ICommentKeyboardLayout {

    /**
     * 显示评论键盘
     *
     * @param hint
     */
    void show(@Nullable final CharSequence hint);

    /**
     * 显示评论键盘
     *
     * @param text
     * @param hint
     */
    void show(@Nullable final CharSequence text,
              @Nullable final CharSequence hint);

    /**
     * 隐藏评论键盘
     */
    void hide();

}