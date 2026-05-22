package com.xxl.hello.widget.ui.video;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * 视频下载页面
 *
 * @author xxl.
 * @date 2026/05/21.
 */
@Module
public class VideoDownloadFragmentModule {

    @Provides
    VideoDownloadViewModel provideVideoDownloadViewModel(@ForApplication final Application application,
                                                          @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new VideoDownloadViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideVideoDownloadViewModelFactory(@NonNull final VideoDownloadViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}
