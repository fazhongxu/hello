package com.xxl.hello.di.module;

import com.xxl.hello.core.config.NetworkConfig;
import com.xxl.hello.core.utils.LogUtils;
import com.xxl.hello.service.di.module.ServiceDataStoreModule;
import com.xxl.hello.service.qunlifier.ForBaseUrl;
import com.xxl.hello.service.qunlifier.ForDebug;
import com.xxl.hello.service.qunlifier.ForNetWorkDebug;
import com.xxl.hello.service.qunlifier.ForNetworkEncryptKey;
import com.xxl.hello.service.qunlifier.ForOkHttp;
import com.xxl.hello.service.qunlifier.ForRetrofit;
import com.xxl.hello.service.qunlifier.ForUserBaseUrl;
import com.xxl.hello.service.qunlifier.ForUserRetrofit;
import com.xxl.hello.user.di.module.UserDataStoreModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
@Module(includes = {ServiceDataStoreModule.class,
        UserDataStoreModule.class})
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
    boolean provideNetworkDebug() {
        return NetworkConfig.isNetworkDebug();
    }

    /**
     * 是否是Debug模式
     *
     * @return
     */
    @ForDebug
    @Singleton
    @Provides
    boolean provideDebug() {
        return NetworkConfig.isDebug();
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
        return isNetworkDebug ? "https://192.168.1.1:8000/" : "https://github.com/";
    }

    /**
     * 构建网络请求域名地址
     *
     * @return
     */
    @ForUserBaseUrl
    @Singleton
    @Provides
    String provideUserHostUrl(@ForNetWorkDebug boolean isNetworkDebug) {
        return isNetworkDebug ? "https://192.168.0.12:8080/" : "https://api.github.com/";
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
    OkHttpClient provideOkHttpClient(@ForDebug boolean isDebug) {
        final OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder();

        if (isDebug) {
            final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                LogUtils.d("okhttp" + message);
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(httpLoggingInterceptor);
        }

        return builder.build();
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
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    /**
     * 构建用户模块Retrofit
     *
     * @return
     */
    @ForUserRetrofit
    @Singleton
    @Provides
    Retrofit provideUserRetrofit(@ForOkHttp final OkHttpClient okHttpClient,
                                 @ForUserBaseUrl final String userBaseUrl) {
        return new Retrofit.Builder()
                .baseUrl(userBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    //endregion
}