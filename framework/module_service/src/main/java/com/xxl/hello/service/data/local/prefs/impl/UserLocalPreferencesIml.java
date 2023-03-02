package com.xxl.hello.service.data.local.prefs.impl;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;
import com.xxl.hello.common.config.AppConfig;
import com.xxl.core.utils.AppExpandUtils;
import com.xxl.hello.service.data.local.prefs.api.UserLocalPreferences;

/**
 * 用户模块本地数据存储
 *
 * @author xxl.
 * @date 2021/7/20.
 */
public class UserLocalPreferencesIml implements UserLocalPreferences {

    //region: 成员变量

    /**
     * mmkv 数据存储组件
     */
    private MMKV mUserLocalCache;

    //endregion

    //region: 构造函数

    public UserLocalPreferencesIml() {
        mUserLocalCache = MMKV.mmkvWithID(AppConfig.Companion.buildPreferencesName("local_user"), MMKV.MULTI_PROCESS_MODE);
    }

    //endregion

    //region: 提供方法

    //region: 基础操作

    /**
     * 保存数据
     *
     * @param isLogged 是否需要跟着登录用户数据走
     * @param key
     * @param value
     * @return
     */
    public boolean encode(final boolean isLogged,
                          @NonNull final String key,
                          @NonNull final String value) {
        return isLogged ? getLoginUserLocalCache().encode(key, value) : mUserLocalCache.encode(key, value);
    }

    /**
     * 获取数据
     *
     * @param isLogged 是否需要跟着登录用户数据走
     * @param key
     * @return
     */
    public String decodeString(final boolean isLogged,
                               @NonNull final String key) {
        return isLogged ? getLoginUserLocalCache().decodeString(key) : mUserLocalCache.decodeString(key);
    }

    /**
     * 保存数据
     *
     * @param isLogged 是否需要跟着登录用户数据走
     * @param key
     * @param value
     * @return
     */
    public boolean encode(final boolean isLogged,
                          @NonNull final String key,
                          final boolean value) {
        return isLogged ? getLoginUserLocalCache().encode(key, value) : mUserLocalCache.encode(key, value);
    }

    /**
     * 获取数据
     *
     * @param isLogged 是否需要跟着登录用户数据走
     * @param key
     * @return
     */
    public boolean decodeBool(final boolean isLogged,
                              @NonNull final String key,
                              final boolean defaultValue) {
        return isLogged ? getLoginUserLocalCache().decodeBool(key) : mUserLocalCache.decodeBool(key, defaultValue);
    }

    //endregion

    //region: 用户"隐私政策"相关

    /**
     * 用户"隐私政策"同意状态
     */
    public static final String PREF_KEY_SERVICE_PRIVACY_POLICY_AGREE_STATUS = "pref_key_service_privacy_policy_agree_status";

    /**
     * 设置用户同意"隐私协议"的状态
     *
     * @return
     */
    @Override
    public boolean setAgreePrivacyPolicyStatus(final boolean isAgree) {
        return encode(false, PREF_KEY_SERVICE_PRIVACY_POLICY_AGREE_STATUS, isAgree);
    }

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    @Override
    public boolean isAgreePrivacyPolicy() {
        return decodeBool(false, PREF_KEY_SERVICE_PRIVACY_POLICY_AGREE_STATUS, false);
    }

    //endregion

    //endregion

    //region: 内部辅助方法

    /**
     * 获取登录用户数据存储组件
     *
     * @return
     */
    private MMKV getLoginUserLocalCache() {
        return MMKV.mmkvWithID(AppConfig.Companion.buildPreferencesName("local_login_user" + AppExpandUtils.getCurrentUserId()), MMKV.MULTI_PROCESS_MODE);
    }

    //endregion

}