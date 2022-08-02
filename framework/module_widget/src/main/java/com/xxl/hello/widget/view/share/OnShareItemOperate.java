package com.xxl.hello.widget.view.share;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;

/**
 * 条目分享操作
 *
 * @author xxl.
 * @date 2022/7/19.
 */
public interface OnShareItemOperate {

    /**
     * 点击操作
     *
     * @param window      分享弹窗
     * @param operateItem 操作条目
     * @param targetView  点击的视图
     * @param position    点击的索引
     * @return 返回 true则自己处理，false则统一处理
     */
    boolean onClick(@NonNull final ResourcesShareWindow window,
                    @NonNull final ShareOperateItem operateItem,
                    @NonNull final View targetView,
                    final int position);
}