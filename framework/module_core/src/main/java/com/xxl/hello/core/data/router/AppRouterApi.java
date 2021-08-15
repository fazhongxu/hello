package com.xxl.hello.core.data.router;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.xxl.hello.core.utils.RouterUtils;

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
     * 启动页面路径地址
     */
    public static final String SPLASH_PATH = MAIN_MODULE_NAME + "/splash";

    /**
     * 首页路径地址
     */
    public static final String MAIN_PATH = MAIN_MODULE_NAME + "/main";

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
        RouterUtils.navigation(SPLASH_PATH);
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

    //endregion
}