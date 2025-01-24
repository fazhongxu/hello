package com.xxl.hello.service.data.local.prefs.impl;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.prefs.api.UserPreferences;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.manager.UserManager;
import com.xxl.kit.TimeUtils;

/**
 * 用户信息存储
 * 主要存放登录用户信息
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public class UserPreferencesImpl implements UserPreferences<LoginUserEntity> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public UserPreferencesImpl() {

    }

    //endregion

    //region: UserPreferences

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    @Override
    public LoginUserEntity getCurrentLoginUserEntity() {
        return UserManager.getInstance().getUserEntity();
    }

    /**
     * 设置当前登录用户的信息
     *
     * @param currentLoginUserEntity
     */
    @Override
    public boolean setCurrentLoginUserEntity(@NonNull final LoginUserEntity currentLoginUserEntity) {
        synchronized (this) {
            return UserManager.getInstance().setUserEntity(currentLoginUserEntity);
        }
    }

    /**
     * 获取登录用户的token
     *
     * @return
     */
    @Override
    public String getToken() {
        if (getCurrentLoginUserEntity() != null) {
            return getCurrentLoginUserEntity().getAccessToken();
        }
        return null;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    @Override
    public String getUserId() {
        if (getCurrentLoginUserEntity() != null) {
            return getCurrentLoginUserEntity().getUserId();
        }
        return null;
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        synchronized (this) {
            UserManager.getInstance().removeUserEntity();
        }
    }

    //endregion
}