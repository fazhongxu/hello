package com.xxl.hello.service.data.repository.impl;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.ConfigRepositoryApi;
import com.xxl.hello.service.data.repository.api.ResourceRepositoryApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/21.
 */
@Module
public class ServiceRepositoryDataStoreModule {

    @Singleton
    @Provides
    DataRepositoryKit provideDataRepositoryKit(@NonNull final DataRepositoryKitImpl dataRepositoryKit) {
        return dataRepositoryKit;
    }

    @Singleton
    @Provides
    ConfigRepositoryApi provideConfigRepositoryImpl() {
        return new ConfigRepositoryImpl();
    }

    @Singleton
    @Provides
    ResourceRepositoryApi provideResourceRepositoryImpl() {
        return new ResourcesRepositoryImpl();
    }
}