package com.xxl.kit;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

/**
 * app路由
 *
 * @author xxl.
 * @date 2021/7/27.
 */
public class AppRouterApi {

    //region: 静态常量

    /**
     * 主模块名称
     */
    public static final String MAIN_MODULE_NAME = "/main_module_name";

    /**
     * 用户模块名称
     */
    public static final String USER_MODULE_NAME = "/user_module";

    /**
     * 启动页面路径地址
     */
    public static final String SPLASH_PATH = MAIN_MODULE_NAME + "/splash";

    /**
     * 首页路径地址
     */
    public static final String MAIN_PATH = MAIN_MODULE_NAME + "/main";

    /**
     * 登录页面路径
     */
    public static final String LOGIN_PATH = USER_MODULE_NAME + "/login";

    //endregion

    //region: 构造函数

    private AppRouterApi() {

    }

    //endregion

    //region: 启动页路由相关

    /**
     * 导航到首页
     */
    public static void navigationToSplash() {
        RouterUtils.navigation(SPLASH_PATH, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    //endregion

    //region: 首页路由相关

    /**
     * 导航到首页
     */
    public static void navigationToMain() {
        RouterUtils.navigation(MAIN_PATH);
    }

    /**
     * 导航到首页
     */
    public static void navigationWithFinish(@NonNull final Activity activity) {
        RouterUtils.navigationWithFinish(activity, MAIN_PATH);
    }

    /**
     * 导航到登录页
     */
    public static void navigationToLogin() {
        RouterUtils.navigation(LOGIN_PATH);
    }

    //endregion
}