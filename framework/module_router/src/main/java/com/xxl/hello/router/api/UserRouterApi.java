package com.xxl.hello.router.api;

import com.xxl.kit.AppRouterApi;
import com.xxl.kit.RouterUtils;

/**
 * 用户模块路由路径
 *
 * @author xxl.
 * @date 2021/7/27.
 */
public class UserRouterApi {

    /**
     * 用户模块名称
     */
    private static final String MODULE_NAME = AppRouterApi.USER_MODULE_NAME;

    private UserRouterApi() {

    }

    public static class Login {

        /**
         * 登录页面路径
         */
        public static final String PATH = AppRouterApi.LOGIN_PATH;

        public static void navigation() {
            AppRouterApi.navigationToLogin();
        }
    }


    public static class UserSetting {

        /**
         * 用户设置页面路径
         */
        public static final String PATH = MODULE_NAME + "/user_setting";

        public static void navigation() {
            RouterUtils.navigation(PATH);
        }
    }

}