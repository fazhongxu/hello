package com.xxl.hello.user.data.local;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.local.prefs.PreferencesKit;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;

/**
 * 用户模块本地数据源
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public class UserLocalDataStoreSourceIml implements UserLocalDataStoreSource {

    //region: 成员变量

    /**
     * 本地数据存储集合
     */
    private final PreferencesKit mPreferencesKit;

    //endregion

    //region: 构造函数

    public UserLocalDataStoreSourceIml(@NonNull final PreferencesKit preferencesKit) {
        // DBClientKit
        mPreferencesKit = preferencesKit;
    }

    //endregion

    //region: 与登录用户信息相关

    /**
     * 设置当前登录的用户信息
     *
     * @param loginUserEntity
     * @return
     */
    @Override
    public boolean setCurrentLoginUserEntity(@NonNull final LoginUserEntity loginUserEntity) {
        final UserPreferences<LoginUserEntity> userPreferences = mPreferencesKit.getUserPreferences();
        return userPreferences.setCurrentLoginUserEntity(loginUserEntity);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @Override
    public LoginUserEntity getCurrentLoginUserEntity() {
        final UserPreferences<LoginUserEntity> userPreferences = mPreferencesKit.getUserPreferences();
        return userPreferences.getCurrentLoginUserEntity();
    }

    /**
     * 获取用户token
     *
     * @return
     */
    @Override
    public String getUserToken() {
        final UserPreferences userPreferences = mPreferencesKit.getUserPreferences();
        return userPreferences.getToken();
    }

    //endregion
}