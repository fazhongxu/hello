package com.xxl.hello.widget.ui.imageedit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * 图片编辑页面 Dagger Module
 *
 * @author xxl
 * @date 2026/06/15
 */
@Module
public class ImageEditFragmentModule {

    @Provides
    ImageEditViewModel provideImageEditViewModel(@ForApplication final Application application,
                                                  @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new ImageEditViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideImageEditViewModelFactory(@NonNull final ImageEditViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
