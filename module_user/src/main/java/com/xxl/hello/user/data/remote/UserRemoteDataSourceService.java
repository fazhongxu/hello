package com.xxl.hello.user.data.remote;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     * @return
     */
    @FormUrlEncoded
    @POST(USER_NET_WORK_PREFIX + "login")
    String login(@Field("phone_number") final String phoneNumber,
                 @Field("verify_code") final String verifyCode);
}