package com.xxl.core.ui;

import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * Activity 回调结果监听
 *
 * @author xxl.
 * @date 2022/11/24.
 */
public interface OnActivityResultListener {

    /**
     * Activity 回调结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(final int requestCode,
                          final int resultCode,
                          @Nullable final Intent data);

}