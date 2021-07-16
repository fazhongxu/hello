package com.xxl.hello.user.data.repository;

import androidx.annotation.NonNull;

import com.xxl.hello.user.data.local.UserLocalDataStoreSource;
import com.xxl.hello.user.data.remote.UserRemoteDataStoreSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public class UserRepositoryDataStoreModule {

    @Singleton
    @Provides
    UserRepository provideUserRepository(@NonNull final UserLocalDataStoreSource userLocalDataStoreModule,
                                         @NonNull final UserRemoteDataStoreSource userRemoteDataStoreSource) {
        return new UserRepositoryIml(userLocalDataStoreModule, userRemoteDataStoreSource);
    }
}