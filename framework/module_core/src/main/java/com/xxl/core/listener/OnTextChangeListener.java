package com.xxl.core.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author xxl.
 * @date 2022/9/1.
 */
public interface OnTextChangeListener extends TextWatcher {

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    default void afterTextChanged(Editable s) {

    }
}