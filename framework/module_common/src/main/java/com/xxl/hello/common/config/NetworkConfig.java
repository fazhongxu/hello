package com.xxl.hello.common.config;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class NetworkConfig {

    //region: 成员变量

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
        // 1.是否可以切换 未打包可以切换
        // 2.mmkv 取出切换环境的值
        return true;
    }

    /**
     * 设置是否是Debug 模式
     *
     * @param isNetworkDebug
     */
    public static void setNetworkDebug(final boolean isNetworkDebug) {
        // mmkv 存入值
    }

    /**
     * 切换环境 debug/release
     */
    public static void switchEnvironment() {
        setNetworkDebug(!isNetworkDebug());
        // 重启app
    }

    //endregion

}