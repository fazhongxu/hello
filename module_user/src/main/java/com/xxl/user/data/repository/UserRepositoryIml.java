package com.xxl.user.data.repository;

import androidx.annotation.NonNull;

import com.xxl.user.data.local.UserLocalDataStoreSource;
import com.xxl.user.data.remote.UserRemoteDataStoreSource;

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
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     * @return
     */
    @Override
    public void login(@NonNull final String phoneNumber,
                      @NonNull final String verifyCode) {
        mUserRemoteDataStoreSource.login(phoneNumber, verifyCode);
    }

    //endregion

}