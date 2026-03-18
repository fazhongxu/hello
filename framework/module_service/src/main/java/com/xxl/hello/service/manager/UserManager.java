package com.xxl.hello.service.manager;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;
import com.xxl.hello.common.config.AppConfig;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.kit.GsonUtils;

/**
 * 用户信息管理
 *
 * @author xxl.
 * @date 2025/1/24.
 * // TODO: 2026/3/18 这种方式获取用户信息，在mvvm结构下 还需要再考虑下 暂不使用
 */
public class UserManager {

    /**
     * 登录用户信息存储key
     */
    private static final String KEY_LOGIN_USER_ENTITY = "key_login_user_entity";

    private static UserManager sUserManager;

    private MMKV mUserCache;

    /**
     * 用户信息
     */
    private LoginUserEntity mLoginUserEntity;

    private UserManager() {
        mUserCache = MMKV.mmkvWithID(AppConfig.Companion.buildPreferencesName("user"), MMKV.MULTI_PROCESS_MODE);
    }

    public static UserManager getInstance() {
        if (sUserManager == null) {
            synchronized (UserManager.class) {
                if (sUserManager == null) {
                    sUserManager = new UserManager();
                }
            }
        }
        return sUserManager;
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLogged() {
        return mLoginUserEntity != null;
    }

    /**
     * 是否是VIP
     *
     * @return
     */
    public boolean isVip() {
        LoginUserEntity userEntity = getUserEntity();
        if (userEntity != null) {
            return userEntity.isVip();
        }
        return false;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public String getUserId() {
        LoginUserEntity userEntity = getUserEntity();
        if (userEntity != null) {
            return userEntity.getUserId();
        }
        return "";
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getAccessToken() {
        LoginUserEntity userEntity = getUserEntity();
        if (userEntity != null) {
            return userEntity.getAccessToken();
        }
        return "";
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public String getNickname() {
        LoginUserEntity userEntity = getUserEntity();
        if (userEntity != null) {
            return userEntity.getUserName();
        }
        return "";
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public String getAvatar() {
        LoginUserEntity userEntity = getUserEntity();
        if (userEntity != null) {
            return userEntity.getAvatar();
        }
        return "";
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    public LoginUserEntity getUserEntity() {
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
    public boolean setUserEntity(@NonNull final LoginUserEntity currentLoginUserEntity) {
        synchronized (this) {
            mLoginUserEntity = currentLoginUserEntity;
            return mUserCache.encode(KEY_LOGIN_USER_ENTITY, GsonUtils.toJson(mLoginUserEntity));
        }
    }

    /**
     * 清除用户信息
     */
    public void clearUserEntity() {
        mLoginUserEntity = null;
        mUserCache.removeValueForKey(KEY_LOGIN_USER_ENTITY);
    }

}