package com.xxl.core.ui;

import android.view.View;

/**
 * 导航栏事件监听
 *
 * @author xxl.
 * @date 2022/8/26.
 */
public interface OnToobarListener {

    /**
     * 左边按钮点击
     *
     * @param view
     */
    default void onToobarLeftClick(View view) {

    }

    /**
     * 右边按钮点击
     *
     * @param view
     */
    default void onToobarRightClick(View view) {

    }

    /**
     * 设置标题
     *
     * @param title
     */
    default void setToobarTitle(CharSequence title) {

    }

}