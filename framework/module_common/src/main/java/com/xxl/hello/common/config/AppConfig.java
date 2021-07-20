package com.xxl.hello.common.config;

import androidx.annotation.NonNull;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class AppConfig {

    //region: 成员变量

    /**
     * 本地数据存储文件名称
     */
    private static final String PREFERENCE_FILE_NAME_FORMAT = "preference_file_name_format_%s";

    //endregion

    //region: 构造函数

    private AppConfig() {

    }

    //endregion

    //region: 提供方法

    /**
     * 构建本地数据存储文件名称
     *
     * @param targetName 文件名称
     * @return
     */
    public static String buildPreferencesName(@NonNull final String targetName) {
        if (NetworkConfig.isNetworkDebug()) {
            return String.format(PREFERENCE_FILE_NAME_FORMAT, targetName + "_DEBUG");
        } else {
            return String.format(PREFERENCE_FILE_NAME_FORMAT, targetName);
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}