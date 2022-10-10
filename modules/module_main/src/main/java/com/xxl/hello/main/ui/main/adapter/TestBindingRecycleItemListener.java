package com.xxl.hello.main.ui.main.adapter;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseRecycleItemListener;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public interface TestBindingRecycleItemListener extends BaseRecycleItemListener {

    /**
     * 条目点击
     *
     * @param value
     */
    void onItemClick(@NonNull final String value);
}