package com.xxl.hello.user.data.remote;

import androidx.annotation.NonNull;

import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.model.api.UserLoginResponse;

import io.reactivex.rxjava3.core.Observable;
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
     * @param request 请求参数
     * @return
     */
    @Override
    public Observable<UserLoginResponse> login(@NonNull UserLoginRequest request) {
        return mUserRemoteDataSourceService.login(request.getPhoneNumber(),request.getVerifyCode());
    }

    //endregion

    //region: get or set

    public Retrofit getRetrofit() {
        return mRetrofit;
    }


    //endregion
}