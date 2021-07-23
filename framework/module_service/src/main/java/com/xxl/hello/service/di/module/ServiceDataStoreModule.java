package com.xxl.hello.service.di.module;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.ServiceWrapper;
import com.xxl.hello.service.data.local.prefs.impl.PreferencesDataStoreModule;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.impl.ServiceRepositoryDataStoreModule;
import com.xxl.hello.service.qunlifier.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/21.
 */
@Module(includes = {PreferencesDataStoreModule.class,
        ServiceRepositoryDataStoreModule.class})
public class ServiceDataStoreModule {

    @Singleton
    @Provides
    ServiceWrapper provideServiceWrapper(@ForApplication final Application application,
                                         @NonNull final DataRepositoryKit dataRepositoryKit) {
        // TODO: 2021/7/23  构造入 DBClientKit,UploadService 等
        return new ServiceWrapper(application, dataRepositoryKit);
    }

}