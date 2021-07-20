package com.xxl.hello.user.data.repository;

import androidx.annotation.NonNull;

import com.xxl.hello.user.data.local.UserLocalDataStoreSource;
import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.model.api.UserLoginResponse;
import com.xxl.hello.user.data.remote.UserRemoteDataStoreSource;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public class UserRepositoryIml implements UserRepository {

    //region: 成员变量

    /**
     * 用户模块远程数据源
     */
    private final UserRemoteDataStoreSource mUserRemoteDataStoreSource;

    //region: 构造函数

    public UserRepositoryIml(@NonNull final UserLocalDataStoreSource userLocalDataStoreModule,
                             @NonNull final UserRemoteDataStoreSource userRemoteDataStoreSource) {
        mUserRemoteDataStoreSource = userRemoteDataStoreSource;
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
    public Observable<UserLoginResponse> login(@NonNull final UserLoginRequest request) {
        return mUserRemoteDataStoreSource.login(request);
    }

    //endregion

}