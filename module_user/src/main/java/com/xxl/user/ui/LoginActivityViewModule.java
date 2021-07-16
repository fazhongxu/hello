package com.xxl.user.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.service.anotation.qunlifier.ForApplication;
import com.xxl.service.ui.ViewModelProviderFactory;
import com.xxl.user.data.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public class LoginActivityViewModule {

    @Provides
    LoginActivityViewModel provideLoginActivityViewModel(@ForApplication final Application application,
                                                         @NonNull final UserRepository userRepository) {
        return new LoginActivityViewModel(application,userRepository);
    }

    @Provides
    ViewModelProvider.Factory provideLoginActivityViewModelFactory(@NonNull final LoginActivityViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}