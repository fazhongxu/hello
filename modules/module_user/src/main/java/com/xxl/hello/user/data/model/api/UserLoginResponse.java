package com.xxl.hello.user.data.model.api;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.LoginUserEntity;



/**
 * 用户登录响应数据
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Keep
public class UserLoginResponse {

    //region: 成员变量

    /**
     * 登录用户信息
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


    public LoginUserEntity getLoginUserEntity() {
        return mLoginUserEntity;
    }

    /**
     * 设置用户信息
     *
     * @param loginUserEntity
     */
    public void setLoginUserEntity(@NonNull final LoginUserEntity loginUserEntity) {
        this.mLoginUserEntity = loginUserEntity;
    }

    //endregion

}