package com.xxl.hello.user.data.remote;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public class UserRemoteDataStoreSourceImpl implements UserRemoteDataStoreSource {

    //region: 成员变量

    private Retrofit mRetrofit;

    private final UserRemoteDataSourceService mUserRemoteDataSourceService;

    //endregion

    //region: 构造函数

    public UserRemoteDataStoreSourceImpl(@NonNull final Retrofit retrofit) {
        mRetrofit = retrofit;
        mUserRemoteDataSourceService = mRetrofit.create(UserRemoteDataSourceService.class);
    }

    //endregion

    //region: 用户登录相关

    /**
     * 用户登录
     *
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     * @return
     */
    @Override
    public void login(@NonNull final String phoneNumber,
                      @NonNull final String verifyCode) {
        mUserRemoteDataSourceService.login(phoneNumber, verifyCode);
    }

    //endregion

    //region: get or set

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    //endregion
}