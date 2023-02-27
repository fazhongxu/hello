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
    default void setToolbarTitle(@NonNull CharSequence title){

    }

    /**
     * 设置标题
     *
     * @param resId
     */
    default void setToolbarTitle(@StringRes int resId){

    }

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
}