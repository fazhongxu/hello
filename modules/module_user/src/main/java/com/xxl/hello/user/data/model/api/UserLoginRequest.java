package com.xxl.hello.user.data.model.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * 用户登录请求参数
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Getter
@Accessors(prefix = "m")
public class UserLoginRequest {

    //region: 成员变量

    /**
     * 手机号
     */
    private String mPhoneNumber;

    /**
     * 验证码
     */
    private String mVerifyCode;

    //endregion

    //region: 构造函数

    private UserLoginRequest() {

    }

    public final static UserLoginRequest obtain() {
        return new UserLoginRequest();
    }

    //endregion

    //region: get or set

    /**
     * 设置手机号
     *
     * @param phoneNumber
     * @return
     */
    public UserLoginRequest setPhoneNumber(@NonNull final String phoneNumber) {
        mPhoneNumber = phoneNumber;
        return this;
    }


    /**
     * 设置手机号
     *
     * @param verifyCode
     * @return
     */
    public UserLoginRequest setVerifyCode(@NonNull final String verifyCode) {
        mVerifyCode = verifyCode;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}