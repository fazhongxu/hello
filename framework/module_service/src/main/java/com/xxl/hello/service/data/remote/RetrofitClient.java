package com.xxl.hello.service.data.remote;

import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.kit.LogUtils;

import java.io.IOException;

import okhttp3.Headers;
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
 * @date 2025/1/23.
 */
public class RetrofitClient {

    private static RetrofitClient sRetrofitClient;

    private Retrofit mRetrofit;

    private Retrofit mUserRetrofit;

    private RetrofitClient() {

    }

    public static RetrofitClient getInstance() {
        if (sRetrofitClient == null) {
            synchronized (RetrofitClient.class) {
                if (sRetrofitClient == null) {
                    sRetrofitClient = new RetrofitClient();
                }
            }
        }
        return sRetrofitClient;
    }

    public <T> T getService(final Class<T> service) {
        return getRetrofit().create(service);
    }

    public <T> T getUserService(final Class<T> service) {
        return getUserRetrofit().create(service);
    }

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            createRetrofit(NetworkConfig.Companion.getHostUrl());
        }
        return mRetrofit;
    }

    private Retrofit getUserRetrofit() {
        if (mUserRetrofit == null) {
            mUserRetrofit = createRetrofit(NetworkConfig.Companion.getUserHostUrl());
        }
        return mUserRetrofit;
    }

    private Retrofit createRetrofit(String baseUrl) {
        final OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder();

        if (NetworkConfig.Companion.isDebug()) {
            final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                LogUtils.d("okhttp" + message);
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(httpLoggingInterceptor);
            builder.addInterceptor(new HeaderInterceptor());
        }
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    private class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Headers headers = request.headers();
            Request.Builder builder = request.newBuilder();
            // TODO: 2025/1/24
            String userAgent = NetworkConfig.Companion.getUserAgent();

            return chain.proceed(builder.build());
        }
    }

}