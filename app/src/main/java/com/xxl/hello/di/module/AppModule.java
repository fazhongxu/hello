package com.xxl.hello.di.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.xxl.hello.HelloApplicationWrapper;
import com.xxl.hello.service.ServiceWrapper;
import com.xxl.hello.service.qunlifier.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
@Module
public class AppModule {

    //region: 构建上下文相关对象

    @ForApplication
    @Singleton
    @Provides
    Application provideApplication(@NonNull final Application application) {
        return application;
    }

    @Singleton
    @Provides
    Context provideContext(@NonNull final Application application) {
        return application;
    }

    @Singleton
    @Provides
    ServiceWrapper provideServiceWrapper(@ForApplication final Application application) {
        return new ServiceWrapper(application);
    }

    @Singleton
    @Provides
    HelloApplicationWrapper provideApplicationWrapper(@ForApplication final Application application,
                                                      @NonNull final ServiceWrapper serviceWrapper) {
        return new HelloApplicationWrapper(application, serviceWrapper);
    }

    //endregion
}