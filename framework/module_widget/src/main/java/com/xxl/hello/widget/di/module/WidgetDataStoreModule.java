package com.xxl.hello.widget.di.module;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.service.download.DownloadService;
import com.xxl.core.service.download.aira.ForAriaDownload;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.view.share.api.ResourcesSharePickerKit;
import com.xxl.hello.widget.view.share.impl.ResourcesSharePickerKitImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2022/7/19.
 */
@Module
public class WidgetDataStoreModule {

    /**
     * 构建资源分享器
     *
     * @param application
     * @param downloadService
     * @param dataRepositoryKit
     * @return
     */
    @Provides
    ResourcesSharePickerKit provideResourcesSharePickerKit(@NonNull final Application application,
                                                           @ForAriaDownload final DownloadService downloadService,
                                                           @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new ResourcesSharePickerKitImpl(application, downloadService, dataRepositoryKit);
    }
}