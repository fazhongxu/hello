package com.xxl.hello.widget.paths;

import com.xxl.hello.core.utils.RouterUtils;

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
    private static final String MODULE_NAME = "/module_user";

    private UserRouterApi() {

    }

    public static class Login {

        /**
         * 登录页面路径
         */
        public static final String PATH = MODULE_NAME + "/login";

        public static void navigation() {
            RouterUtils.navigation(PATH);
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