package com.xxl.hello.di.component;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.HelloApplication;
import com.xxl.hello.di.builder.AppActivityBuilder;
import com.xxl.hello.di.module.AppModule;
import com.xxl.hello.di.module.DataStoreModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AppModule.class,
        AppActivityBuilder.class,
        DataStoreModule.class})
public interface AppComponent extends AndroidInjector<HelloApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(@NonNull final Application application);

        AppComponent build();
    }
}