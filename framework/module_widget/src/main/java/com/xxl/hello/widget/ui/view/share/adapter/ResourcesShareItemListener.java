package com.xxl.hello.widget.ui.view.share.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.widget.recyclerview.adapter.BaseRecycleItemListener;
import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;

/**
 * 资源分享条目点击监听
 *
 * @author xxl.
 * @date 2022/10/24.
 */
public interface ResourcesShareItemListener extends BaseRecycleItemListener {

    /**
     * 分享条目点击
     *
     * @param targetView
     * @param itemPosition
     * @param targetItem
     */
    void onShareItemClick(@NonNull final View targetView,
                          @NonNull final int itemPosition,
                          @NonNull final ShareOperateItem targetItem);
}