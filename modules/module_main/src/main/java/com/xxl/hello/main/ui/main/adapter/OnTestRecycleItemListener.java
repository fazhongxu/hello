package com.xxl.hello.main.ui.main.adapter;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseRecycleItemListener;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public interface OnTestRecycleItemListener extends BaseRecycleItemListener {

    /**
     * 条目点击
     *
     * @param providerMultiEntity
     */
    void onItemClick(@NonNull final TestProviderMultiEntity providerMultiEntity);
}