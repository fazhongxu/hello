package com.xxl.hello.user.data.repository;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.api.user.QueryUserInfoRequest;
import com.xxl.hello.service.data.model.api.user.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.model.event.OnUserEventApi;
import com.xxl.hello.service.data.repository.impl.BaseRepositoryIml;
import com.xxl.hello.user.data.local.UserLocalDataStoreSource;
import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.model.api.UserLoginResponse;
import com.xxl.hello.user.data.remote.UserRemoteDataStoreSource;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public class UserRepositoryIml extends BaseRepositoryIml implements UserRepository {

    //region: 成员变量

    /**
     * 用户模块本地数据源
     */
    private final UserLocalDataStoreSource mUserLocalDataStoreModule;

    /**
     * 用户模块远程数据源
     */
    private final UserRemoteDataStoreSource mUserRemoteDataStoreSource;

    //region: 构造函数

    public UserRepositoryIml(@NonNull final UserLocalDataStoreSource userLocalDataStoreModule,
                             @NonNull final UserRemoteDataStoreSource userRemoteDataStoreSource) {
        mUserLocalDataStoreModule = userLocalDataStoreModule;
        mUserRemoteDataStoreSource = userRemoteDataStoreSource;
    }

    //endregion

    //region: 用户登录相关

    /**
     * 用户登录
     * 注意：调用此方法会触发 {@link OnUserEventApi.OnUpdateUserInfoEvent} 通知事件
     *
     * @param request 请求参数
     * @return
     */
    @Override
    public Observable<UserLoginResponse> login(@NonNull final UserLoginRequest request) {
        return mUserRemoteDataStoreSource.login(request)
                .doOnNext(loginResponse -> {
                    if (loginResponse != null && loginResponse.getLoginUserEntity() != null) {
                        setCurrentLoginUserEntity(loginResponse.getLoginUserEntity(), true);
                    }
                });
    }

    /**
     * 保存登录用户信息
     * 注意：调用此方法可能会触发 {@link OnUserEventApi.OnUpdateUserInfoEvent} 通知事件
     *
     * @param loginUserEntity 用户信息
     * @param isNotice        是否发送通知事件
     */
    @Override
    public void setCurrentLoginUserEntity(@NonNull final LoginUserEntity loginUserEntity,
                                          final boolean isNotice) {
        final boolean isSuccess = mUserLocalDataStoreModule.setCurrentLoginUserEntity(loginUserEntity);
        if (isNotice && isSuccess) {
            postEventBus(OnUserEventApi.OnUpdateUserInfoEvent.obtain(loginUserEntity));
        }
    }

    //endregion

    //region: UserRepositoryApi

    /**
     * 查询用户信息
     *
     * @param request 请求参数
     * @return
     */
    @Override
    public Observable<QueryUserInfoResponse> queryUserInfo(@NonNull final QueryUserInfoRequest request) {
        return mUserRemoteDataStoreSource.queryUserInfo(request);
    }

    /**
     * 设置用户同意"隐私协议"的状态
     *
     * @return
     */
    @Override
    public boolean setAgreePrivacyPolicyStatus(final boolean isAgree){
        return mUserLocalDataStoreModule.setAgreePrivacyPolicyStatus(isAgree);
    }

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    @Override
    public boolean isAgreePrivacyPolicy() {
        return mUserLocalDataStoreModule.isAgreePrivacyPolicy();
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    @Override
    public LoginUserEntity getCurrentLoginUserEntity() {
        return mUserLocalDataStoreModule.getCurrentLoginUserEntity();
    }


    //endregion

}