package com.xxl.hello.user.ui;

import androidx.annotation.NonNull;

import com.xxl.hello.user.data.model.api.UserLoginResponse;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public interface LoginActivityNavigator {

    /**
     * 请求登录完成
     *
     * @param loginResponse
     */
    void onRequestLoginComplete(@NonNull final UserLoginResponse loginResponse);

    /**
     * 登录按钮点击
     */
    void onLoginClick();
}