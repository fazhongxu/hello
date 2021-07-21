package com.xxl.hello.service.data.local.prefs;

import com.xxl.hello.service.data.local.prefs.api.UserLocalPreferences;
import com.xxl.hello.service.data.local.prefs.api.UserPreferences;

/**
 * 本地数据存储集合
 *
 * @author xxl.
 * @date 2021/7/20.
 */
public interface PreferencesKit {
    // 获取懒加载的dagger注入的用户、圈子信息存储接口对象

    /**
     * 获取用户信息存储
     * 主要是登录用户信息
     *
     * @return
     */
    UserPreferences getUserPreferences();

    /**
     * 获取用户设置信息存储
     *
     * @return
     */
    UserLocalPreferences getUserLocalPreferences();


}