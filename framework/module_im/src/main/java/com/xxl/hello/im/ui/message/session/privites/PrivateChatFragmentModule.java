package com.xxl.hello.im.ui.message.session.privites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * 单聊会话
 *
 * @author xxl.
 * @date 2024/06/28.
 */
@Module
public class PrivateChatFragmentModule {

    @Provides
    PrivateChatSessionViewModel providePrivateChatSessionViewModel(@ForApplication final Application application,
                                                                   @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new PrivateChatSessionViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory providePrivateChatSessionModelFactory(@NonNull final PrivateChatSessionViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}