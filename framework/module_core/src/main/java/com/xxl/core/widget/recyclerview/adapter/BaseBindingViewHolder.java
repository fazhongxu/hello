package com.xxl.core.widget.recyclerview.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @author xxl.
 * @date 2022/10/10.
 */
public class BaseBindingViewHolder<Binding extends ViewDataBinding> extends BaseDataBindingHolder<Binding> {

    public BaseBindingViewHolder(@NotNull View view) {
        super(view);
    }
}