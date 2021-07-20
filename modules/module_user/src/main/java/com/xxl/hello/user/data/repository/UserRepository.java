package com.xxl.hello.user.data.repository;

import androidx.annotation.NonNull;

import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.model.api.UserLoginResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public interface UserRepository {

    /**
     * 用户登录
     *
     * @param request 请求参数
     * @return
     */
    Observable<UserLoginResponse> login(@NonNull final UserLoginRequest request);
}