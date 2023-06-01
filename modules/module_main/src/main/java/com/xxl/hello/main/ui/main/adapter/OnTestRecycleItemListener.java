package com.xxl.hello.main.ui.main.adapter;

import android.view.View;

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
     * @param testListEntity
     */
    void onItemClick(@NonNull final TestListEntity testListEntity);

    /**
     * 媒体视图点击
     *
     * @param testListEntity
     * @param targetView
     */
    void onMediaItemClick(@NonNull final TestListEntity testListEntity,
                          @NonNull final View targetView);
}