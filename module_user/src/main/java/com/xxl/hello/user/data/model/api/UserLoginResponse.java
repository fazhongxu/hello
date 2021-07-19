package com.xxl.hello.user.data.model.api;

import androidx.annotation.Keep;

import com.xxl.hello.user.data.model.entity.LoginUserEntity;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 用户登录响应数据
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Keep
@Getter
@Accessors(prefix = "m")
public class UserLoginResponse {

    //region: 成员变量

    /**
     *  登录用户信息
     */
    private LoginUserEntity mLoginUserEntity;

    //endregion

    //region: 构造函数

    private UserLoginResponse() {

    }

    public final static UserLoginResponse obtain() {
        return new UserLoginResponse();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}