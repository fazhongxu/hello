package com.xxl.core.widget.recyclerview.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseBindingAdapter<T extends Object, VH extends BaseViewHolder> extends BaseAdapter {


    //region: 成员变量



    //endregion

    //region: 构造函数

    public BaseBindingAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 int position) {

    }

    //endregion

    //region: 页面生命周期

    //endregion

    //region: 页面视图渲染

    //endregion

}