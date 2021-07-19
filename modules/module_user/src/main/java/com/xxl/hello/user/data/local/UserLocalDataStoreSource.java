package com.xxl.hello.user.data.local;

import androidx.annotation.NonNull;

import com.xxl.hello.user.data.model.entity.LoginUserEntity;

/**
 * 用户模块本地数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public interface UserLocalDataStoreSource {

    /**
     * 设置当前登录的用户信息
     *
     * @param loginUserEntity
     * @return
     */
    boolean setCurrentLoginUserEntity(@NonNull final LoginUserEntity loginUserEntity);

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    LoginUserEntity getCurrentLoginUserEntity();
}