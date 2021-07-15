package com.xxl.hellonexus.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
@Module
public class DataStoreModule {

    //region: 构建网络请求基础参数相关

    /**
     * 构建请求key
     *
     * @return
     */
    @Singleton
    @Provides
    String provideSecureteKey() {
        return "abc";
    }

    //endregion

    //region: 构建网络请求相关对象

    //endregion
}