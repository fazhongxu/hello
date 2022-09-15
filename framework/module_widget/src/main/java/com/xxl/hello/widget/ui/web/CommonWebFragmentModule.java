package com.xxl.hello.widget.ui.web;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public class CommonWebFragmentModule {

    @Provides
    CommonWebViewModel provideCommonWebViewModel(@ForApplication final Application application,
                                                 @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new CommonWebViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideCommonWebViewModelFactory(@NonNull final CommonWebViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}