package com.xxl.hello.widget.ui.preview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
@Module
public class MediaPreviewFragmentModule {

    @Provides
    MediaPreviewModel provideMediaPreviewModel(@ForApplication final Application application,
                                                 @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new MediaPreviewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideMediaPreviewModelFactory(@NonNull final MediaPreviewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}