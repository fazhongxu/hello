package com.xxl.hello.common.config

/**
 *
 * @author xxl.
 * @date 2023/3/2.
 */
class AppConfig private constructor() {

    //region: companion

    companion object {

        //region: 成员变量

        /**
         * 本地数据存储文件名称
         */
        private const val PREFERENCE_FILE_NAME_FORMAT = "preference_file_name_format_%s"

        /**
         * app 包名
         */
        const val APP_PACKAGE_NAME = "com.xxl.hello"

        /**
         * scheme
         */
        const val APP_DEFAULT_SCHEME = "scheme"

        /**
         * scheme 标识
         */
        const val APP_SCHEME_TAG = "hl://hello.com"

        /**
         * bugly appId
         */
        const val BUGLY_APPID = "8e8e5e32db"

        /**
         * 构建本地数据存储文件名称
         *
         * @param targetName 文件名称
         * @return
         */
        fun buildPreferencesName(targetName: String): String {
            if (NetworkConfig.isNetworkDebug()) {
                return String.format(PREFERENCE_FILE_NAME_FORMAT, targetName + "_DEBUG")
            }
            return String.format(PREFERENCE_FILE_NAME_FORMAT, targetName)
        }
    }

    //endregion

    //region: 用户模块相关配置

    class User private constructor() {
        companion object {

            const val GITHUB_USER_NAME = "fazhongxu"

            const val GITHUB_USER_AVATAR = "https://avatars.githubusercontent.com/u/24353536?s=400&u=43f37f2e73f15a1dfad58f0d63c35418715a5621&v=4"
        }
    }

    //endregion
}