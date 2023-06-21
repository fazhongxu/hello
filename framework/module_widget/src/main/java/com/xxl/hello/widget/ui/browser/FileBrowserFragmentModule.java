package com.xxl.hello.widget.ui.browser;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * 文件浏览页面
 *
 * @author xxl.
 * @date 2023/07/21.
 */
@Module
public class FileBrowserFragmentModule {

    @Provides
    FileBrowserViewModel provideFileBrowserModel(@ForApplication final Application application,
                                                   @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new FileBrowserViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideFileBrowserModelFactory(@NonNull final FileBrowserViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}