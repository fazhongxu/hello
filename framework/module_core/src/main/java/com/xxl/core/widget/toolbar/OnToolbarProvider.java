package com.xxl.core.widget.toolbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * @author xxl.
 * @date 2022/7/30.
 */
public interface OnToolbarProvider {

    /**
     * 是否展示左边视图
     *
     * @return
     */
    default boolean isDisplayLeft() {
        return false;
    }

    /**
     * 获取左边按钮文字
     *
     * @return
     */
    default String getLeftText() {
        return "";
    }

    /**
     * 设置标题
     *
     * @param title
     */
    default void setToolbarTitle(@NonNull CharSequence title) {

    }

    /**
     * 设置标题
     *
     * @param resId
     */
    default void setToolbarTitle(@StringRes int resId) {

    }

    /**
     * 左边按钮点击
     *
     * @param view
     */
    default void onToolbarLeftClick(View view) {

    }

    /**
     * 左边按钮长按点击
     *
     * @param view
     */
    default boolean onToolbarLeftLongClick(View view) {
        return false;
    }

    /**
     * 右边按钮点击
     *
     * @param view
     */
    default void onToolbarRightClick(View view) {

    }
}