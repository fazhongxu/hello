package com.xxl.hello.main.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.core.ui.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

/**
 * 启动页面
 *
 * @author xxl
 * @date 2021/08/13.
 */
@Module
public class SplashFragmentModule {

    @Provides
    SplashViewModel provideSplashViewModel(@ForApplication final Application application,
                                           @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new SplashViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideSplashViewModelFactory(@NonNull final SplashViewModel splashViewModel) {
        return new ViewModelProviderFactory<>(splashViewModel);
    }
}
