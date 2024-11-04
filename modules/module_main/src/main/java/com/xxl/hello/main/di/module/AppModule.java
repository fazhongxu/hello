package com.xxl.hello.main.di.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.xxl.hello.main.HelloApplicationWrapper;
import com.xxl.hello.service.ServiceWrapper;
import com.xxl.hello.service.qunlifier.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
@InstallIn(SingletonComponent.class)
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
    HelloApplicationWrapper provideApplicationWrapper(@ForApplication final Application application,
                                                      @NonNull final ServiceWrapper serviceWrapper) {
        return new HelloApplicationWrapper(application, serviceWrapper);
    }

    //endregion
}