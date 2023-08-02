package com.xxl.hello.widget.ui.qrcode;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.ui.ViewModelProviderFactory;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * 二维码页面
 *
 * @author xxl.
 * @date 2023/08/02.
 */
@Module
public class QRCodeFragmentModule {

    @Provides
    QRCodeViewModel provideQRCodeViewModel(@ForApplication final Application application,
                                           @NonNull final DataRepositoryKit dataRepositoryKit) {
        return new QRCodeViewModel(application, dataRepositoryKit);
    }

    @Provides
    ViewModelProvider.Factory provideQRCodeViewModelFactory(@NonNull final QRCodeViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}