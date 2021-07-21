package com.xxl.hello.user.data.repository;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;
import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.model.api.UserLoginResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * 用户模块服务接口
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public interface UserRepository extends UserRepositoryApi {

    /**
     * 用户登录
     *
     * @param request 请求参数
     * @return
     */
    Observable<UserLoginResponse> login(@NonNull final UserLoginRequest request);


}