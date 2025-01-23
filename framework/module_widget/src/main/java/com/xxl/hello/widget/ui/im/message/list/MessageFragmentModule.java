package com.xxl.hello.widget.ui.im.message.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.hello.widget.ui.im.message.session.base.adapter.ChatSessionAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * 消息列表
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@Module
public class MessageFragmentModule {

    @Provides
    MessageViewModel provideMessageViewModel(@ForApplication final Application application,
                                             @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new MessageViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideMessageViewModelFactory(@NonNull final MessageViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    ChatSessionAdapter provideChatSessionAdapter() {
        return new ChatSessionAdapter();
    }

}