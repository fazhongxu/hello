package com.xxl.core.widget.recyclerview.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @author xxl.
 * @date 2022/10/10.
 */
public class BaseBindingViewHolder<Binding extends ViewDataBinding> extends BaseViewHolder {

    public Binding mBinding;

    public BaseBindingViewHolder(@NotNull View view) {
        super(view);
    }

    public Binding getDataBinding() {
        return mBinding;
    }

    public void setDataBinding(Binding binding) {
        this.mBinding = binding;
    }
}