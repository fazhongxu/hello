package com.xxl.core.widget.recyclerview.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * adapter基础类
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseAdapter<T, L extends BaseRecycleItemListener, V extends BaseViewHolder> extends BaseQuickAdapter<T, V>
        implements LoadMoreModule {

    //region: 成员变量

    /**
     * 点击事件
     */
    protected L mListener;

    //endregion

    //region: 构造函数

    public BaseAdapter(int layoutResId) {
        super(layoutResId, null);
    }

    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
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

    /**
     * 通知数据更新
     *
     * @param position 数据更新
     */
    public void notifyDataChanged(int position) {
        if (position >= 0) {
            position = getHeaderLayoutCount() + position;
            notifyItemChanged(position);
        }
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