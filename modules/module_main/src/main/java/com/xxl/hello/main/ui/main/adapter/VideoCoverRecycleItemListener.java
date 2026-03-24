package com.xxl.hello.main.ui.main.adapter;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseRecycleItemListener;

/**
 * 视频封面条目监听器
 *
 * @author xxl.
 * @date 2026/3/24.
 */
public interface VideoCoverRecycleItemListener extends BaseRecycleItemListener {

    /**
     * 封面点击
     *
     * @param targetEntity
     */
    void onCoverClick(@NonNull final VideoCoverEntity targetEntity);
}
