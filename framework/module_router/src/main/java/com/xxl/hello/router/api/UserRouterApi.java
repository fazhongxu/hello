package com.xxl.hello.router.api;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.xxl.kit.AppRouterApi;
import com.xxl.kit.RouterUtils;

/**
 * 用户模块路由路径
 *
 * @author xxl.
 * @date 2021/7/27.
 */
public class UserRouterApi {

    //region: 成员变量

    /**
     * 用户模块名称
     */
    private static final String MODULE_NAME = AppRouterApi.USER_MODULE_NAME;

    //endregion

    //region: 构造函数

    private UserRouterApi() {

    }

    //endregion

    //region: 登录路由相关

    /**
     * 登录页面路由相关
     */
    public static class Login extends AppRouterApi.Login {

    }

    //endregion

    //region: 用户设置页路由相关

    public static class UserSetting {

        /**
         * 用户设置页面路径
         */
        public static final String PATH = MODULE_NAME + "/user_setting";

        public static void navigation() {
            RouterUtils.navigation(PATH);
        }

        public static void navigationWithFinish(@NonNull final Activity activity) {
            RouterUtils.navigationWithFinish(activity, PATH);
        }
    }

    //endregion

}