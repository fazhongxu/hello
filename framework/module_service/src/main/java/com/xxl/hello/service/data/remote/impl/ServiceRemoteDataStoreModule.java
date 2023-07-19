package com.xxl.hello.service.data.remote.impl;

import androidx.annotation.NonNull;

import com.xxl.core.data.remote.ApiHeader;
import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.service.data.local.db.DBServiceKit;
import com.xxl.hello.service.data.local.prefs.PreferencesKit;
import com.xxl.hello.service.data.local.source.api.ConfigLocalDataSource;
import com.xxl.hello.service.data.local.source.impl.ConfigLocalDataSourceImpl;
import com.xxl.hello.service.data.remote.api.ConfigRemoteDataSource;
import com.xxl.hello.service.qunlifier.ForConfigHost;
import com.xxl.hello.service.qunlifier.ForConfigRetrofit;
import com.xxl.hello.service.qunlifier.ForNetWorkDebug;
import com.xxl.hello.service.qunlifier.ForOkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xxl.
 * @date 2021/7/21.
 */
@Module
public class ServiceRemoteDataStoreModule {

    /**
     * 构建config主机地址
     *
     * @param isDebug
     * @return
     */
    @ForConfigHost
    @Singleton
    @Provides
    String provideConfigHost(@ForNetWorkDebug boolean isDebug) {
        return isDebug ? NetworkConfig.CONFIG_HOST_DEBUG : NetworkConfig.API_HOST;
    }

    /**
     * 构建 config Retrofit
     *
     * @return
     */
    @ForConfigRetrofit
    @Singleton
    @Provides
    Retrofit provideConfigRetrofit(@ForOkHttpClient final OkHttpClient okHttpClient,
                                   @ForConfigHost final String host) {
        return new Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    /**
     * 构建config本地数据服务
     *
     * @param preferencesKit
     * @param dbServiceKit
     * @return
     */
    @Singleton
    @Provides
    ConfigLocalDataSource provideConfigLocalDataSource(@NonNull final PreferencesKit preferencesKit,
                                                       @NonNull final DBServiceKit dbServiceKit) {
        return new ConfigLocalDataSourceImpl(preferencesKit, dbServiceKit);
    }


    /**
     * 构建config远程数据服务
     *
     * @param apiHeader
     * @param retrofit
     * @return
     */
    @Singleton
    @Provides
    ConfigRemoteDataSource provideConfigRemoteDataSource(@NonNull ApiHeader apiHeader,
                                                         @ForConfigRetrofit Retrofit retrofit) {
        return new ConfigRemoteDataSourceImpl(apiHeader, retrofit);
    }

}