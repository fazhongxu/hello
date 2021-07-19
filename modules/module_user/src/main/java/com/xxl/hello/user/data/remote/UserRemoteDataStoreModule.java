package com.xxl.hello.user.data.remote;

import com.xxl.hello.service.qunlifier.ForRetrofit;
import com.xxl.hello.user.data.local.UserLocalDataStoreSource;
import com.xxl.hello.user.data.local.UserLocalDataStoreSourceIml;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * 用户模块远程数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public class UserRemoteDataStoreModule {

    @Singleton
    @Provides
    UserLocalDataStoreSource provideUserLocalDataStoreModule() {
        return new UserLocalDataStoreSourceIml();
    }

    @Singleton
    @Provides
    UserRemoteDataStoreSource provideUserRemoteDataStoreModule(@ForRetrofit final Retrofit retrofit) {
        return new UserRemoteDataStoreSourceImpl(retrofit);
    }
}