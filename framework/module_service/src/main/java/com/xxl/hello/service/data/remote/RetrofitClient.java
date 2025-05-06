package com.xxl.hello.service.data.remote;

import android.text.TextUtils;

import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.service.manager.UserManager;
import com.xxl.kit.LogUtils;

import java.io.IOException;

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
            mRetrofit = createRetrofit(NetworkConfig.Companion.getHostUrl());
        }
        return mRetrofit;
    }

    private Retrofit getUserRetrofit() {
        if (mUserRetrofit == null) {
            mUserRetrofit = createRetrofit(NetworkConfig.Companion.getUserHostUrl());
        }
        return mUserRetrofit;
    }

    public Retrofit createRetrofit(String baseUrl) {
        final OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder();

        if (NetworkConfig.Companion.isDebug()) {
            final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                LogUtils.d("okhttp" + message);
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(httpLoggingInterceptor);
        }
        builder.addInterceptor(new HeaderInterceptor());
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
            Request.Builder builder = request.newBuilder();
            String userAgent = NetworkConfig.Companion.getUserAgent();
            String userId = UserManager.getInstance().getUserId();
            String userToken = UserManager.getInstance().getUserToken();

            builder.addHeader("User-Agent", userAgent);
            if (!TextUtils.isEmpty(userId)) {
                builder.addHeader("user_id", userAgent);
            }

            if (!TextUtils.isEmpty(userToken)) {
                builder.addHeader("access_token", userToken);
            }

            return chain.proceed(builder.build());
        }
    }

}