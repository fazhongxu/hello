package com.xxl.hello.service.data.local.prefs.impl;

import com.xxl.hello.service.data.local.prefs.PreferencesKit;
import com.xxl.hello.service.data.local.prefs.api.UserLocalPreferences;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * 本地数据存储
 *
 * @author xxl.
 * @date 2021/7/20.
 */
public class PreferencesKitImpl implements PreferencesKit {

    //region: 构造函数

    @Inject
    public PreferencesKitImpl() {

    }

    //endregion

    //region: 用户信息存储

    @Inject
    Lazy<UserLocalPreferences> mLocalPreferences;

    /**
     * 获取用户设置信息存储
     *
     * @return
     */
    @Override
    public UserLocalPreferences getUserLocalPreferences() {
        return mLocalPreferences.get();
    }

    @Inject
    Lazy<UserPreferences> mUserPreferences;

    /**
     * 获取用户信息存储
     *
     * @return
     */
    @Override
    public UserPreferences getUserPreferences() {
        return mUserPreferences.get();
    }

    //endregion

}