package com.xxl.hello.user.data.remote;

import androidx.annotation.NonNull;

/**
 * 用户模块远程数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public interface UserRemoteDataStoreSource {

    /**
     * 用户登录
     *
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     * @return
     */
    void login(@NonNull final String phoneNumber,
               @NonNull final String verifyCode);
}