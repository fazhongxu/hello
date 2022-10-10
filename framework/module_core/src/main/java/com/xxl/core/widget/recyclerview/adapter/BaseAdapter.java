package com.xxl.core.widget.recyclerview.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseAdapter<T,L extends BaseRecycleItemListener, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> {

    //region: 成员变量

    /**
     * 点击事件
     */
    protected L mListener;

    //endregion

    //region: 构造函数

    public BaseAdapter() {
        this(null);
    }

    public BaseAdapter(@Nullable List<T> data) {
        super(0);
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

    public void setListener(L listener) {
        mListener = listener;
    }

    //endregion

}