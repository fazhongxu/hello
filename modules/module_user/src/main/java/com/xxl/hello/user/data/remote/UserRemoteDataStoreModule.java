package com.xxl.hello.user.data.remote;

import androidx.annotation.NonNull;

import com.xxl.hello.core.data.remote.ApiHeader;
import com.xxl.hello.service.qunlifier.ForRetrofit;
import com.xxl.hello.service.qunlifier.ForUserRetrofit;

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
    UserRemoteDataStoreSource provideUserRemoteDataStoreModule(@NonNull final ApiHeader apiHeader,
                                                               @ForRetrofit final Retrofit retrofit,
                                                               @ForUserRetrofit final Retrofit userRetrofit) {
        return new UserRemoteDataStoreSourceImpl(apiHeader,retrofit,userRetrofit);
    }
}