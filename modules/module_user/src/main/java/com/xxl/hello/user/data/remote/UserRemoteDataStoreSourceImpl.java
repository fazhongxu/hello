package com.xxl.hello.user.data.remote;

import androidx.annotation.NonNull;

import com.xxl.hello.core.data.remote.ApiHeader;
import com.xxl.hello.core.data.remote.BaseRemoteDataStoreSource;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.data.model.api.QueryUserInfoRequest;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.model.api.UserLoginResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public class UserRemoteDataStoreSourceImpl extends BaseRemoteDataStoreSource implements UserRemoteDataStoreSource {

    //region: 成员变量

    /**
     * 用户模块Retrofit
     */
    private Retrofit mUserRetrofit;

    private final UserRemoteDataSourceService mUserRemoteDataSourceService;

    //endregion

    //region: 构造函数

    public UserRemoteDataStoreSourceImpl(@NonNull final ApiHeader apiHeader,
                                         @NonNull final Retrofit retrofit,
                                         @NonNull final Retrofit userRetrofit) {
        super(apiHeader, retrofit);
        mUserRetrofit = userRetrofit;
        mUserRemoteDataSourceService = mUserRetrofit.create(UserRemoteDataSourceService.class);
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
    public Observable<UserLoginResponse> login(@NonNull UserLoginRequest request) {
        // FIXME: 2021/7/21 换成网络请求
        //return mUserRemoteDataSourceService.login(request.getPhoneNumber(),request.getVerifyCode());
        //getPublicApiHeader()
        //登录成功后更新网络请求头信息
        return Observable.create(emitter -> {
            final UserLoginResponse response = UserLoginResponse.obtain();
            response.setLoginUserEntity(LoginUserEntity.obtainTestUserEntity());
            emitter.onNext(response);
            emitter.onComplete();
        });
    }

    /**
     * 查询用户信息
     *
     * @param request 请求参数
     * @return
     */
    @Override
    public Observable<QueryUserInfoResponse> queryUserInfo(@NonNull final QueryUserInfoRequest request) {
        return mUserRemoteDataSourceService.queryUserInfo(request.getTargetUserName());
    }

    //endregion

    //region: get or set


    //endregion
}