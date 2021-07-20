package com.xxl.hello.common.config;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class AppConfig {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private AppConfig() {

    }

    public final static AppConfig obtain() {
        return new AppConfig();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}