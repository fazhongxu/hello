package com.xxl.hello.main.ui.jump;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * scheme跳转处理页
 *
 * @author xxl.
 * @date 2022/8/11.
 */
@Module
public class SchemeJumpFragmentModule {

    @Provides
    SchemeJumpViewModel provideSchemeJumpViewModel(@ForApplication final Application application,
                                                   @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new SchemeJumpViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideSchemeJumpViewModelFactory(@NonNull final SchemeJumpViewModel schemeJumpViewModel) {
        return new ViewModelProviderFactory<>(schemeJumpViewModel);
    }
}
