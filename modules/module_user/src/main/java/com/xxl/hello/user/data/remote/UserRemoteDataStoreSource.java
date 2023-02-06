package com.xxl.hello.user.data.remote;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.api.QueryUserInfoRequest;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.model.api.UserLoginResponse;

import io.reactivex.rxjava3.core.Observable;

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
     * @param request 请求参数
     * @return
     */
    Observable<UserLoginResponse> login(@NonNull final UserLoginRequest request);

    /**
     * 查询用户信息
     *
     * @param request 请求参数
     * @return
     */
    Observable<QueryUserInfoResponse> queryUserInfo(@NonNull final QueryUserInfoRequest request);

    /**
     * 更新头部信息
     *
     * @param loginUserEntity
     * @return
     */
    LoginUserEntity updateProtectedApiHeader(@NonNull final LoginUserEntity loginUserEntity);
}