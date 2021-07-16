package com.xxl.hello.di.module;

import com.xxl.hello.service.qunlifier.ForBaseUrl;
import com.xxl.hello.service.qunlifier.ForNetWorkDebug;
import com.xxl.hello.service.qunlifier.ForNetworkEncryptKey;
import com.xxl.hello.service.qunlifier.ForOkHttp;
import com.xxl.hello.service.qunlifier.ForRetrofit;
import com.xxl.hello.user.di.module.UserDataStoreModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
@Module(includes = UserDataStoreModule.class)
public class DataStoreModule {

    //region: 构建网络请求基础参数相关

    /**
     * 构建请求key
     *
     * @return
     */
    @ForNetworkEncryptKey
    @Singleton
    @Provides
    String provideEncryptKey() {
        return "abc";
    }

    /**
     * 构建网络请求是否是debug模式标识
     *
     * @return
     */
    @ForNetWorkDebug
    @Singleton
    @Provides
    boolean provideNetwordDebug() {
        // NetwordUtils#isNetworkDebug
        return true;
    }

    /**
     * 构建网络请求域名地址
     *
     * @return
     */
    @ForBaseUrl
    @Singleton
    @Provides
    String provideHostUrl(@ForNetWorkDebug boolean isNetworkDebug) {
        return isNetworkDebug ? "https://192.168.1.1:8080" : "https://github.com/fazhongxu";
    }

    //endregion

    //region: 构建网络请求相关对象

    /**
     * 构建 OkHttp
     *
     * @return
     */
    @ForOkHttp
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient()
                .newBuilder()
                .build();
    }

    /**
     * 构建 Retrofit
     *
     * @return
     */
    @ForRetrofit
    @Singleton
    @Provides
    Retrofit provideRetrofit(@ForOkHttp final OkHttpClient okHttpClient,
                             @ForBaseUrl final String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    //endregion
}