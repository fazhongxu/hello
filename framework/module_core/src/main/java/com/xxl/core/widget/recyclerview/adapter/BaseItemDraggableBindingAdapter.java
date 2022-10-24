package com.xxl.core.widget.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * 条目可以拖拽的适配器
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseItemDraggableBindingAdapter<T, L extends BaseRecycleItemListener,
        Binding extends ViewDataBinding> extends BaseItemDraggableAdapter<T, L, BaseBindingViewHolder<Binding>> {

    //region: 成员变量

    private LayoutInflater mLayoutInflater;

    //endregion

    //region: 构造函数

    public BaseItemDraggableBindingAdapter(int layoutResId) {
        super(layoutResId, null);
    }

    //endregion

    //region: 页面生命周期

    @NonNull
    @Override
    protected BaseBindingViewHolder<Binding> createBaseViewHolder(@NonNull View view) {
        return new BaseBindingViewHolder<>(view);
    }

    @NonNull
    @Override
    protected BaseBindingViewHolder<Binding> createBaseViewHolder(@NonNull ViewGroup parent, int layoutResId) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
        }
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        BaseBindingViewHolder<Binding> holder = new BaseBindingViewHolder<>(binding.getRoot());
        holder.setDataBinding((Binding) binding);
        return holder;
    }

    @Override
    protected void convert(@NonNull BaseBindingViewHolder<Binding> holder, T item) {
        convert(holder.getDataBinding(), item);
        Binding dataBinding = holder.getDataBinding();
        if (dataBinding != null) {
            dataBinding.executePendingBindings();
        }
    }

    /**
     * convert
     *
     * @param binding
     * @param item
     */
    public abstract void convert(@NonNull final Binding binding,
                                 @NonNull final T item);


    //endregion

}