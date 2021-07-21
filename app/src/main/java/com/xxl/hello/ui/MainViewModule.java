package com.xxl.hello.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.hello.service.ui.ViewModelProviderFactory;

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
                                       @NonNull final DataRepositoryKit dataRepositoryKit){
        return new MainViewModel(application,dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideMainViewModelFactory(@NonNull final MainViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }
}
