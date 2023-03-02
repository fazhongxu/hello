package com.xxl.hello.common.config

import com.xxl.core.utils.AppExpandUtils
import com.xxl.core.utils.CacheUtils
import com.xxl.kit.AppUtils

/**
 * 网络配置相关
 *
 * @author xxl.
 * @date 2023/3/2.
 */
class NetworkConfig private constructor() {

    companion object {

        //region: 成员变量

        /**
         *  开发环境一些配置信息存储名称
         */
        var PREF_DEVELOP_NAME = "pref_key_develop_name"

        /**
         * 是否是debug环境
         */
        var PREF_KEY_IS_NETWORK_DEBUG = "pref_key_is_network_debug"

        /**
         * 正式环境主机地址
         */
        const val API_HOST = "https://github.com/"

        /**
         * 测试环境主机地址
         */
        const val API_HOST_DEBUG = "http://192.168.1.1/8081/"

        /**
         * 测试环境用户模块主机地址
         */
        const val API_USER_HOST_DEBUG = "http://192.168.1.2/8081/"

        /**
         * 正式环境用户模块主机地址
         */
        const val API_USER_HOST = "https://api.github.com/"

        //endregion

        //region: 提供方法

        /**
         * 是否是Debug模式
         */
        fun isDebug(): Boolean {
            return AppExpandUtils.isDebug()
        }

        /**
         * 网络环境是否是Debug模式
         */
        fun isNetworkDebug(): Boolean {
            if (isDebug()) {
                return CacheUtils.decodeBool(PREF_DEVELOP_NAME, PREF_KEY_IS_NETWORK_DEBUG)
            }
            return false
        }

        /**
         *
         * 切换环境 debug/release
         */
        fun switchEnvironment() {
            CacheUtils.encode(PREF_DEVELOP_NAME, PREF_KEY_IS_NETWORK_DEBUG, !isNetworkDebug())
            AppUtils.restartApp()
        }

        //endregion
    }


}