package com.xxl.hello.service.data.repository.api;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.api.QueryUserInfoRequest;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;

import io.reactivex.rxjava3.core.Observable;

/**
 * 用户模块对外开放的API接口
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public interface UserRepositoryApi {

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    LoginUserEntity getCurrentLoginUserEntity();

    /**
     * 查询用户信息
     *
     * @param request 请求参数
     * @return
     */
    Observable<QueryUserInfoResponse> queryUserInfo(@NonNull final QueryUserInfoRequest request);


}