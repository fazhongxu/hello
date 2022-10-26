package com.xxl.core.widget.recyclerview.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * 带头部的adapter基础类
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseSectionAdapter<T extends SectionEntity, L extends BaseRecycleItemListener, V extends BaseViewHolder> extends BaseSectionQuickAdapter<T, V>
        implements LoadMoreModule {

    //region: 成员变量

    protected int mHeaderResId;

    protected int mLayoutResId;

    /**
     * 点击事件
     */
    protected L mListener;

    //endregion

    //region: 构造函数

    public BaseSectionAdapter(int headerResId,int layoutResId) {
        super(headerResId,layoutResId,null);
        mHeaderResId = headerResId;
        mLayoutResId = layoutResId;
    }

    //endregion

    //region: 提供方法

    /**
     * 判断数据是否为空
     *
     * @return
     */
    public boolean isDataEmpty() {
        return getData() == null || getData().size() == 0;
    }

    /**
     * 获取条目位置
     *
     * @param targetItemEntity
     * @return
     */
    public int findItemPositon(T targetItemEntity) {
        return getItemPosition(targetItemEntity);
    }

    /**
     * 移除条目
     *
     * @param targetItemEntity
     */
    public void removeItem(T targetItemEntity) {
        remove(targetItemEntity);
    }

    public void setListener(L listener) {
        mListener = listener;
    }

    @Override
    @NonNull
    public BaseLoadMoreModule addLoadMoreModule(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseLoadMoreModule(baseQuickAdapter);
    }

    //endregion

}