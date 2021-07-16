package com.xxl.hello.user.data.repository;

import androidx.annotation.NonNull;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public interface UserRepository {
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