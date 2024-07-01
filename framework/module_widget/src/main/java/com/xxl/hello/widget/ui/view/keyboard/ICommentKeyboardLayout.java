package com.xxl.hello.widget.ui.view.keyboard;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2022/8/31.
 */
public interface ICommentKeyboardLayout {

    /**
     * 初始化
     *
     * @param activity
     * @param contentView
     */
    void init(@NonNull final Activity activity,
              @NonNull final View contentView);

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

    /**
     * 设置评论键盘监听
     *
     * @param listener
     */
    void setOnCommentKeyboardListener(@Nullable OnCommentKeyboardListener listener);

}