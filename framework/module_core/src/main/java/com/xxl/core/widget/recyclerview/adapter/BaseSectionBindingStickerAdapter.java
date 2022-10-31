package com.xxl.core.widget.recyclerview.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;

/**
 * binding 带头部的adapter基础类
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseSectionBindingStickerAdapter<T extends SectionEntity, L extends BaseRecycleItemListener, HeaderBinding extends ViewDataBinding, Binding extends ViewDataBinding>
        extends BaseSectionBindingAdapter<T, L, HeaderBinding, Binding> {

    //region: 构造函数

    public BaseSectionBindingStickerAdapter(int headerResId, int layoutResId) {
        super(headerResId, layoutResId);
    }

    //endregion

    //region: 页面生命周期

    /**
     * 设置绑定粘性头部到列表上
     *
     * @param recyclerView
     */
    public void bindStickerHeader(@NonNull final RecyclerView recyclerView) {
        bindStickerHeader(recyclerView, SectionEntity.HEADER_TYPE);
    }

    /**
     * 设置粘性头部
     *
     * @param recyclerView
     * @param pinnedHeaderType 头部条目类型
     */
    public void bindStickerHeader(@NonNull final RecyclerView recyclerView,
                                  final int pinnedHeaderType) {
        recyclerView.addItemDecoration(createPinnedHeaderItemDecoration(pinnedHeaderType));
    }

    /**
     * 创建粘性头部
     *
     * @param pinnedHeaderType 头部条目类型
     * @return
     */
    protected RecyclerView.ItemDecoration createPinnedHeaderItemDecoration(final int pinnedHeaderType) {
        return new PinnedHeaderItemDecoration.Builder(pinnedHeaderType)
                .create();
    }

    //endregion

}