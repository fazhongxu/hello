package com.xxl.hellonexus.di.component;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hellonexus.NexusApplication;
import com.xxl.hellonexus.di.builder.AppActivityBuilder;
import com.xxl.hellonexus.di.module.AppModule;
import com.xxl.hellonexus.di.module.DataStoreModule;

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
public interface AppComponent extends AndroidInjector<NexusApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(@NonNull final Application application);

        AppComponent build();
    }
}