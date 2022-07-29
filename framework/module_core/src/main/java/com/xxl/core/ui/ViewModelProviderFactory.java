package com.xxl.core.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


/**
 * @author xxl
 * @date 2021/07/16
 */
public class ViewModelProviderFactory<V> implements ViewModelProvider.Factory {

    private V mViewModel;

    public ViewModelProviderFactory(V viewModel) {
        this.mViewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(mViewModel.getClass())) {
            return (T) mViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
