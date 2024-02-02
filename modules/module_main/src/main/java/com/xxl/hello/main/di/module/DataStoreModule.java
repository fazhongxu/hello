package com.xxl.hello.main.di.module;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;

import com.xxl.core.data.remote.ApiHeader;
import com.xxl.core.service.download.DownloadService;
import com.xxl.core.service.download.aira.AriaDownloadServiceImpl;
import com.xxl.core.service.download.aira.ForAriaDownload;
import com.xxl.core.service.download.hello.ForHelloDownload;
import com.xxl.core.service.download.hello.HelloDownloadServiceImpl;
import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.main.BuildConfig;
import com.xxl.hello.service.data.local.db.impl.objectbox.ObjectBoxDataStoreModel;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;
import com.xxl.hello.service.di.module.ServiceDataStoreModule;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.hello.service.qunlifier.ForDebug;
import com.xxl.hello.service.qunlifier.ForHost;
import com.xxl.hello.service.qunlifier.ForNetWorkDebug;
import com.xxl.hello.service.qunlifier.ForNetworkEncryptKey;
import com.xxl.hello.service.qunlifier.ForOkHttpClient;
import com.xxl.hello.service.qunlifier.ForRetrofit;
import com.xxl.hello.service.qunlifier.ForUserAgent;
import com.xxl.hello.service.qunlifier.ForUserHost;
import com.xxl.hello.service.qunlifier.ForUserPreference;
import com.xxl.hello.service.qunlifier.ForUserRetrofit;
import com.xxl.hello.user.di.module.UserDataStoreModule;
import com.xxl.hello.widget.di.module.WidgetDataStoreModule;
import com.xxl.kit.LogUtils;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
@Module(includes = {ServiceDataStoreModule.class,
        WidgetDataStoreModule.class,
        ObjectBoxDataStoreModel.class,
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
        return NetworkConfig.Companion.isNetworkDebug();
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
        return NetworkConfig.Companion.isDebug();
    }

    /**
     * 构建UserAgent
     *
     * @return
     */
    @ForUserAgent
    @Singleton
    @Provides
    String provideUserAgent() {
        return BuildConfig.VERSION_NAME + "Android" + Build.BRAND;
    }

    /**
     * 构建网络请求域名地址
     *
     * @return
     */
    @ForHost
    @Singleton
    @Provides
    String provideHostUrl(@ForNetWorkDebug boolean isNetworkDebug) {
        return isNetworkDebug ? NetworkConfig.API_HOST_DEBUG : NetworkConfig.API_HOST;
    }

    /**
     * 构建网络请求域名地址
     *
     * @return
     */
    @ForUserHost
    @Singleton
    @Provides
    String provideUserHostUrl(@ForNetWorkDebug boolean isNetworkDebug) {
        return isNetworkDebug ? NetworkConfig.API_USER_HOST_DEBUG : NetworkConfig.API_USER_HOST;
    }

    /**
     * 构建公用网络请求头
     *
     * @param userAgent
     * @return
     */
    @Singleton
    @Provides
    ApiHeader.PublicApiHeader providePublicApiHeader(@ForUserAgent final String userAgent) {
        return new ApiHeader.PublicApiHeader(userAgent);
    }

    /**
     * 构建用户登录后的网络请求头
     *
     * @param userAgent
     * @param userPreferences
     * @return
     */
    @Singleton
    @Provides
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ForUserAgent final String userAgent,
                                                           @ForUserPreference final UserPreferences userPreferences) {
        // TODO: 2021/8/16 登录后更新请求头信息 ApiHeader.ProtectedApiHeade#setAccessToken
        return new ApiHeader.ProtectedApiHeader(userAgent, userPreferences.getUserId(), userPreferences.getToken());
    }

    /**
     * 构建网络请求头信息
     *
     * @param publicApiHeader
     * @param protectedApiHeader
     * @return
     */
    @Singleton
    @Provides
    ApiHeader provideApiHeader(@NonNull final ApiHeader.PublicApiHeader publicApiHeader,
                               @NonNull final ApiHeader.ProtectedApiHeader protectedApiHeader) {
        return new ApiHeader(publicApiHeader, protectedApiHeader);
    }

    //endregion

    //region: 构建网络请求相关对象

    /**
     * 常用公共参数拦截器
     */
    private class CommonParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originRequest = chain.request();
            Request newRequest = originRequest.newBuilder()
                    .addHeader("xxx", "123")
                    .build();
            return chain.proceed(newRequest);
        }
    }

    /**
     * 构建 OkHttp
     *
     * @return
     */
    @ForOkHttpClient
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

        builder.addInterceptor(new CommonParamsInterceptor());

        //SSLSocketFactory socketFactory = CertificationUtils.getP12SSLSocketFactory(AppUtils.getApplication(), R.raw.xxx, "");
        //X509TrustManager x509TrustManager = CertificationUtils.getX509TrustManager();
        //builder.sslSocketFactory(socketFactory,x509TrustManager);

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
    Retrofit provideRetrofit(@ForOkHttpClient final OkHttpClient okHttpClient,
                             @ForHost final String baseUrl) {
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
    Retrofit provideUserRetrofit(@ForOkHttpClient final OkHttpClient okHttpClient,
                                 @ForUserHost final String userBaseUrl) {
        return new Retrofit.Builder()
                .baseUrl(userBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    //endregion

    //region: 构建下载服务相关对象

    /**
     * 构建Aria下载服务
     *
     * @param application
     * @return
     */
    @ForAriaDownload
    @Singleton
    @Provides
    DownloadService provideAriaDownloadServiceImpl(@ForApplication Application application) {
        return new AriaDownloadServiceImpl();
    }

    /**
     * 构建Aria下载服务
     *
     * @param application
     * @return
     */
    @ForHelloDownload
    @Singleton
    @Provides
    DownloadService provideHelloDownloadServiceImpl(@ForApplication Application application) {
        return new HelloDownloadServiceImpl();
    }

    //endregion
}