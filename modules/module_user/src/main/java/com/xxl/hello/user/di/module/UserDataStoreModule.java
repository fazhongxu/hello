package com.xxl.hello.user.di.module;

import com.xxl.hello.user.data.local.UserLocalDataStoreModule;
import com.xxl.hello.user.data.remote.UserRemoteDataStoreModule;
import com.xxl.hello.user.data.repository.UserRepositoryDataStoreModule;

import dagger.Module;

/**
 * 用户模块数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Module(includes = {UserLocalDataStoreModule.class,
        UserRemoteDataStoreModule.class,
        UserRepositoryDataStoreModule.class})
public class UserDataStoreModule {

}