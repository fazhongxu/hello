package com.xxl.hello.main.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.hello.service.qunlifier.ForTencentUpload;
import com.xxl.hello.service.ui.ViewModelProviderFactory;
import com.xxl.hello.service.upload.api.UploadService;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl
 * @date 2021/07/16.
 */
@Module
public class MainViewModule  {

    @Provides
    MainViewModel provideMainViewModel(@ForApplication final Application application,
                                       @NonNull final DataRepositoryKit dataRepositoryKit,
                                       @ForTencentUpload final UploadService uploadService){
        return new MainViewModel(application,dataRepositoryKit,uploadService);
    }

    @Provides
    ViewModelProvider.Factory provideMainViewModelFactory(@NonNull final MainViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }
}
