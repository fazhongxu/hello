package com.xxl.core.widget.toolbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * @author xxl.
 * @date 2022/7/30.
 */
public interface OnToolbarListener {

    /**
     * 设置标题
     *
     * @param title
     */
    default void setTitle(@NonNull CharSequence title) {

    }

    /**
     * 设置标题
     *
     * @param resId
     */
    default void setTitle(@StringRes int resId) {

    }

    /**
     * 设置子标题
     *
     * @param title
     */
    default void setSubTitle(@NonNull CharSequence title) {

    }


    /**
     * 左边按钮点击
     *
     * @param view
     */
    default void onLeftClick(View view) {

    }

    /**
     * 右边按钮点击
     *
     * @param view
     */
    default void onRightClick(View view) {

    }

}