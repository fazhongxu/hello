package com.xxl.core.widget.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseBindingAdapter<T, L extends BaseRecycleItemListener,
        Binding extends ViewDataBinding> extends BaseAdapter<T, L, BaseBindingViewHolder<Binding>> {

    //region: 成员变量

    private LayoutInflater mLayoutInflater;

    //endregion

    //region: 构造函数

    public BaseBindingAdapter(int layoutResId) {
        super(layoutResId, null);
    }

    //endregion

    //region: 页面生命周期

    @NotNull
    @Override
    protected BaseBindingViewHolder<Binding> createBaseViewHolder(@NotNull View view) {
        return new BaseBindingViewHolder<>(view);
    }

    @NotNull
    @Override
    protected BaseBindingViewHolder<Binding> createBaseViewHolder(@NotNull ViewGroup parent, int layoutResId) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
        }
        final ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        return new BaseBindingViewHolder<>(binding.getRoot());
    }

    @Override
    protected void convert(@NotNull BaseBindingViewHolder<Binding> holder, T item) {
        convert(holder.getDataBinding(), item);
        Binding dataBinding = holder.getDataBinding();
        if (dataBinding != null) {
            dataBinding.executePendingBindings();
        }
    }

    public abstract void convert(Binding binding,
                                 T item);


    //endregion

    //region: 页面视图渲染

    //endregion

}