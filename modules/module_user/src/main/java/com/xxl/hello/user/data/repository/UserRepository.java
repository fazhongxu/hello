package com.xxl.hello.user.data.repository;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.model.event.OnUserEventApi;
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
     * 注意：调用此方法会触发 {@link OnUserEventApi.OnUpdateUserInfoEvent} 通知事件
     *
     * @param request 请求参数
     * @return
     */
    Observable<UserLoginResponse> login(@NonNull final UserLoginRequest request);


    /**
     * 保存登录用户信息
     * 注意：调用此方法可能会触发 {@link OnUserEventApi.OnUpdateUserInfoEvent} 通知事件
     *
     * @param loginUserEntity 用户信息
     * @param isNotice        是否发送通知事件
     */
    void setCurrentLoginUserEntity(@NonNull final LoginUserEntity loginUserEntity,
                                   final boolean isNotice);

}