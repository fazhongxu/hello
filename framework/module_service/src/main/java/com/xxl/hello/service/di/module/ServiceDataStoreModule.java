package com.xxl.hello.service.di.module;

import android.app.Application;

import com.xxl.hello.service.ServiceWrapper;
import com.xxl.hello.service.data.local.prefs.impl.PreferencesDataStoreModule;
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
    ServiceWrapper provideServiceWrapper(@ForApplication final Application application) {
        return new ServiceWrapper(application);
    }

}