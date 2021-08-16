package com.xxl.hello.core.data.remote;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * @author xxl.
 * @date 2021/8/16.
 */
public abstract class BaseRemoteDataStoreSource {

    //region: 成员变量

    /**
     * 网络请求头信息
     */
    private final ApiHeader mApiHeader;

    /**
     * 网络请求Retrofit
     */
    private final Retrofit mRetrofit;

    //endregion

    //region: 构造函数

    public BaseRemoteDataStoreSource(@NonNull final ApiHeader apiHeader,
                                     @NonNull final Retrofit retrofit) {
        mApiHeader = apiHeader;
        mRetrofit = retrofit;
    }

    //endregion

    //region: 提供方法

    // TODO: 2021/8/16 可扩展参数加解密方法

    /**
     * 获取网络请求头信息
     *
     * @return
     */
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    /**
     * 获取网络请求Retrofit
     *
     * @return
     */
    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    /**
     * 获取公共的网络请求头信息
     *
     * @return
     */
    public ApiHeader.PublicApiHeader getPublicApiHeader() {
        return mApiHeader.getPublicApiHeader();
    }

    /**
     * 获取用户登录后的网络请求头信息
     *
     * @return
     */
    public ApiHeader.ProtectedApiHeader getProtectedApiHeader() {
        return mApiHeader.getProtectedApiHeader();
    }

    //endregion


}