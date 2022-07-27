package com.xxl.hello.common.config;

import com.xxl.core.utils.AppExpandUtils;
import com.xxl.core.utils.CacheUtils;
import com.xxl.kit.AppUtils;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class NetworkConfig {

    //region: 成员变量

    /**
     * 开发环境一些配置信息存储名称
     */
    private static final String PREF_DEVELOP_NAME = "pref_key_develop_name";

    /**
     * 是否是debug环境
     */
    private static final String PREF_KEY_IS_NETWORK_DEBUG = "pref_key_is_network_debug";

    //endregion

    //region: 构造函数

    private NetworkConfig() {

    }

    //endregion

    //region: 提供方法

    /**
     * 是否是Debug模式
     *
     * @return
     */
    public static boolean isDebug() {
        return AppExpandUtils.isDebug();
    }

    /**
     * 网络环境是否是Debug模式
     *
     * @return
     */
    public static boolean isNetworkDebug() {
        if (isDebug()) {
            return CacheUtils.decodeBool(PREF_DEVELOP_NAME, PREF_KEY_IS_NETWORK_DEBUG);
        }
        return false;
    }

    /**
     * 切换环境 debug/release
     */
    public static void switchEnvironment() {
        CacheUtils.encode(PREF_DEVELOP_NAME, PREF_KEY_IS_NETWORK_DEBUG, !isNetworkDebug());
        AppUtils.restartApp();
    }

    //endregion

}