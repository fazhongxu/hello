package com.xxl.hello.service.data.local.prefs.impl;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;
import com.xxl.hello.core.config.AppConfig;
import com.xxl.hello.service.data.local.prefs.api.UserLocalPreferences;
import com.xxl.hello.core.utils.AppExpandUtils;

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
        mUserLocalCache = MMKV.mmkvWithID(AppConfig.buildPreferencesName("local_user"), MMKV.MULTI_PROCESS_MODE);
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

    //endregion

    //region: 用户信息相关


    //endregion

    //endregion

    //region: 内部辅助方法

    /**
     * 获取登录用户数据存储组件
     *
     * @return
     */
    private MMKV getLoginUserLocalCache() {
        return MMKV.mmkvWithID(AppConfig.buildPreferencesName("local_login_user" + AppExpandUtils.getCurrentUserId()), MMKV.MULTI_PROCESS_MODE);
    }

    //endregion

}