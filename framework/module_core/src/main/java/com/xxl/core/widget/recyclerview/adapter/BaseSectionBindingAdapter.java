package com.xxl.core.widget.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.entity.SectionEntity;

import org.jetbrains.annotations.NotNull;

/**
 * binding 带头部的adapter基础类
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseSectionBindingAdapter<T extends SectionEntity, L extends BaseRecycleItemListener, HeaderBinding extends ViewDataBinding, Binding extends ViewDataBinding>
        extends BaseSectionAdapter<T, L, BaseBindingViewHolder> {

    //region: 成员变量

    private LayoutInflater mLayoutInflater;

    //endregion

    //region: 构造函数

    public BaseSectionBindingAdapter(int headerResId, int layoutResId) {
        super(headerResId, layoutResId);
    }

    //endregion

    //region: 页面生命周期

    @NotNull
    @Override
    protected BaseBindingViewHolder onCreateDefViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
        }
        if (viewType == SectionEntity.HEADER_TYPE) {
            return onCreateHeaderViewHolder(DataBindingUtil.inflate(mLayoutInflater, mHeaderResId, parent, false));
        }
        return onCreateItemViewHolder(DataBindingUtil.inflate(mLayoutInflater, mLayoutResId, parent, false));
    }

    @Override
    protected void convertHeader(@NotNull BaseBindingViewHolder holder, @NotNull T item) {
        convertHeader((HeaderBinding) holder.getDataBinding(), item);
        holder.getDataBinding().executePendingBindings();
    }

    @Override
    protected void convert(@NotNull BaseBindingViewHolder holder, T item) {
        convert((Binding) holder.getDataBinding(), item);
        holder.getDataBinding().executePendingBindings();
    }

    public BaseBindingViewHolder<HeaderBinding> onCreateHeaderViewHolder(@NonNull final HeaderBinding headerBinding) {
        BaseBindingViewHolder<HeaderBinding> holder = new BaseBindingViewHolder<>(headerBinding.getRoot());
        holder.setDataBinding(headerBinding);
        return holder;
    }

    public BaseBindingViewHolder<Binding> onCreateItemViewHolder(@NonNull final Binding binding) {
        BaseBindingViewHolder<Binding> holder = new BaseBindingViewHolder<>(binding.getRoot());
        holder.setDataBinding(binding);
        return holder;
    }

    /**
     * convertHeader
     *
     * @param headerBinding
     * @param item
     */
    public abstract void convertHeader(@NonNull final HeaderBinding headerBinding,
                                       @NonNull final T item);

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