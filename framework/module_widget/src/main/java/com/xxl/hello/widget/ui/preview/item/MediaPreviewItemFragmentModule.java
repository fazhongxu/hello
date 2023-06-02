package com.xxl.hello.widget.ui.preview.item;

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
public class MediaPreviewItemFragmentModule {

    @Provides
    MediaPreviewItemModel provideMediaPreviewItemModel(@ForApplication final Application application,
                                                 @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new MediaPreviewItemModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideMediaPreviewItemModelFactory(@NonNull final MediaPreviewItemModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}