package com.xxl.hello.router.api;

import com.xxl.kit.AppRouterApi;

/**
 * 主模块路由路径
 *
 * @author xxl.
 * @date 2021/7/27.
 */
public class MainRouterApi {

    //region: 成员变量

    /**
     * 主模块名称
     */
    private static final String MAIN_MODULE_NAME = AppRouterApi.MAIN_MODULE_NAME;

    //endregion

    //region: 构造函数

    private MainRouterApi() {

    }

    //endregion

    //region: 首页路由相关

    /**
     * 首页路由
     */
    public static class Main extends AppRouterApi.Main {

    }

    //endregion
}