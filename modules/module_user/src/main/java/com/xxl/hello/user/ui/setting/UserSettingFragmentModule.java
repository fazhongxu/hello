package com.xxl.hello.user.ui.setting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.hello.service.ui.ViewModelProviderFactory;
import com.xxl.hello.user.data.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public class UserSettingFragmentModule {

    @Provides
    UserSettingModel provideUserSettingViewModel(@ForApplication final Application application,
                                                 @NonNull final DataRepositoryKit dataRepositoryKit,
                                                 @NonNull final UserRepository userRepository) {
        return new UserSettingModel(application, dataRepositoryKit, userRepository);
    }

    @Provides
    ViewModelProvider.Factory provideUserSettingActivityViewModelFactory(@NonNull final UserSettingModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}