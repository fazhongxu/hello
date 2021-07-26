package com.xxl.hello.common.config;

import com.xxl.hello.common.utils.AppUtils;
import com.xxl.hello.common.utils.CacheUtils;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class NetworkConfig {

    //region: 成员变量

    /**
     * 网络环境是否是debug模式，上线必须改为false
     */
    private static final boolean sIsNetWorkDebug = true;

    /**
     * 开发环境一些配置信息存储key
     */
    private static final String PREF_KEY_DEVELOP = "pref_key_develop";

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
    public static boolean isNetworkDebug() {
        if (sIsNetWorkDebug) {
            return CacheUtils.decodeBool(PREF_KEY_DEVELOP);
        }
        return false;
    }

    /**
     * 切换环境 debug/release
     */
    public static void switchEnvironment() {
        final boolean isSuccess = CacheUtils.encode(PREF_KEY_DEVELOP, !isNetworkDebug());
        if (isSuccess) {
            AppUtils.restartApp();
        }
    }

    //endregion

}