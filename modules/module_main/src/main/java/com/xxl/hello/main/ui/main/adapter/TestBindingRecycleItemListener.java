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
     * @param targetEntity
     */
    void onItemClick(@NonNull final TestListEntity targetEntity);

    /**
     * 移除条目
     *
     * @param targetEntity
     */
    void onRemoveItemClick(@NonNull final TestListEntity targetEntity);

    /**
     * 置顶
     *
     * @param targetEntity
     */
    void onTopItemClick(@NonNull final TestListEntity targetEntity);

    /**
     * 刷新到顶部
     *
     * @param targetEntity
     */
    void onRefreshTopItemClick(@NonNull final TestListEntity targetEntity);
}