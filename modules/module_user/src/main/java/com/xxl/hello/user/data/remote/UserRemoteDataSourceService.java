package com.xxl.hello.user.data.remote;

import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.user.data.model.api.UserLoginResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public interface UserRemoteDataSourceService {

    //region: 静态常量

    /**
     * 用户模块网络请求前缀
     */
    String USER_NET_WORK_PREFIX = "/user/";

    //endregion

    /**
     * 用户登录
     *
     * @param headers     头部
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     * @return
     */
    @FormUrlEncoded
    @POST(USER_NET_WORK_PREFIX + "login")
    Observable<UserLoginResponse> login(@HeaderMap Map<String, String> headers,
                                        @Field("phone_number") final String phoneNumber,
                                        @Field("verify_code") final String verifyCode);

    /**
     * 查询用户信息
     *
     * @param userNickname 用户昵称
     * @return
     */
    @GET("users/{userNickname}")
    Observable<QueryUserInfoResponse> queryUserInfo(@Path("userNickname") String userNickname);
}