package com.xxl.hello.service.data.local.prefs.impl;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;
import com.xxl.hello.common.config.AppConfig;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.kit.GsonUtils;

/**
 * 用户信息存储
 * 主要存放登录用户信息
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public class UserPreferencesImpl implements UserPreferences<LoginUserEntity> {

    //region: 成员变量

    /**
     * 登录用户信息存储key
     */
    private static final String KEY_LOGIN_USER_ENTITY = "key_login_user_entity";

    /**
     * mmkv 数据存储组件
     */
    private MMKV mUserCache;

    /**
     * 当前登录用户的信息
     */
    private static LoginUserEntity mLoginUserEntity;

    //endregion

    //region: 构造函数

    public UserPreferencesImpl() {
        mUserCache = MMKV.mmkvWithID(AppConfig.Companion.buildPreferencesName("user"), MMKV.MULTI_PROCESS_MODE);
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
        if (mLoginUserEntity != null) {
            return mLoginUserEntity;
        }
        final String json = mUserCache.decodeString(KEY_LOGIN_USER_ENTITY);
        if (!TextUtils.isEmpty(json)) {
            LoginUserEntity loginUserEntity = GsonUtils.fromJson(json, LoginUserEntity.class);
            if (loginUserEntity != null) {
                mLoginUserEntity = loginUserEntity;
            }
        }
        return mLoginUserEntity;
    }

    /**
     * 设置当前登录用户的信息
     *
     * @param currentLoginUserEntity
     */
    @Override
    public boolean setCurrentLoginUserEntity(@NonNull final LoginUserEntity currentLoginUserEntity) {
        synchronized (this) {
            mLoginUserEntity = currentLoginUserEntity;
            return mUserCache.encode(KEY_LOGIN_USER_ENTITY, GsonUtils.toJson(mLoginUserEntity));
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
            mLoginUserEntity = null;
            mUserCache.removeValueForKey(KEY_LOGIN_USER_ENTITY);
        }
    }

    //endregion
}