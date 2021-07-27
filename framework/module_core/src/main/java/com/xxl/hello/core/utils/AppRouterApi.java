package com.xxl.hello.core.utils;

/**
 * app路由
 *
 * @author xxl.
 * @date 2021/7/27.
 */
public class AppRouterApi {

    /**
     * 主模块名称
     */
    public static final String MAIN_MODULE_NAME = "/main_module_name";

    /**
     * 首页路径地址
     */
    public static final String MAIN_PATH = MAIN_MODULE_NAME + "/main";

    private AppRouterApi() {

    }

    /**
     * 导航到首页
     */
    public static void navigationToMain() {
        RouterUtils.navigation(MAIN_PATH);
    }
}