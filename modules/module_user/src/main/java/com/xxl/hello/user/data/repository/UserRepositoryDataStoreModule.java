package com.xxl.hello.user.data.repository;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.api.UserRepositoryApi;
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

    /**
     * 构建用户模块数据服务接口
     *
     * @param userLocalDataStoreModule
     * @param userRemoteDataStoreSource
     * @return
     */
    @Singleton
    @Provides
    UserRepository provideUserRepository(@NonNull final UserLocalDataStoreSource userLocalDataStoreModule,
                                         @NonNull final UserRemoteDataStoreSource userRemoteDataStoreSource) {
        return new UserRepositoryIml(userLocalDataStoreModule, userRemoteDataStoreSource);
    }

    /**
     * 构建用户模块对外数据服务接口
     *
     * @param userRepository
     * @return
     */
    @Singleton
    @Provides
    UserRepositoryApi provideUserRepositoryApi(@NonNull final UserRepository userRepository) {
        return userRepository;
    }
}