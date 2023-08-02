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
     * 获取导航栏背景颜色
     *
     * @return
     */
    default int getBackgroundColor() {
        return 0;
    }

    /**
     * 是否展示左边视图
     *
     * @return
     */
    default boolean isDisplayLeft() {
        return false;
    }

    /**
     * 是否展示右边自定义视图
     *
     * @return
     */
    default boolean isDisplayRightCustom() {
        return false;
    }

    /**
     * 是否展示右边文本视图
     *
     * @return
     */
    default boolean isDisplayRightText() {
        return false;
    }

    /**
     * 是否展示右边图标
     *
     * @return
     */
    default boolean isDisplayRightIcon() {
        return false;
    }

    /**
     * 获取左边按钮文字
     *
     * @return
     */
    default CharSequence getLeftText() {
        return "";
    }

    /**
     * 获取右边按钮文字
     *
     * @return
     */
    default CharSequence getRightText() {
        return "";
    }

    /**
     * 获取右边按钮图标
     *
     * @return
     */
    default int getRightIcon() {
        return 0;
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
     * @return
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

    /**
     * 右边边按钮长按点击
     *
     * @param view
     * @return
     */
    default boolean onToolbarRightLongClick(View view) {
        return false;
    }
}