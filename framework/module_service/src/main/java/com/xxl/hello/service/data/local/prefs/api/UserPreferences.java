package com.xxl.hello.service.data.local.prefs.api;

import androidx.annotation.NonNull;

/**
 * 获取用户信息存储
 * 主要是登录用户信息
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public interface UserPreferences<T> {

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    T getCurrentLoginUserEntity();

    /**
     * 设置当前登录用户的信息
     *
     * @param currentLoginUserEntity
     */
    boolean setCurrentLoginUserEntity(@NonNull final T currentLoginUserEntity);

    /**
     * 获取登录用户的token
     *
     * @return
     */
    String getToken();

    /**
     * 获取用户ID
     *
     * @return
     */
    String getUserId();

    /**
     * 退出登录
     */
    void logout();
}