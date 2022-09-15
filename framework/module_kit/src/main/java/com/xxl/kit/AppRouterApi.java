package com.xxl.kit;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;

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

    public static class Splash {
        /**
         * 导航到首页
         */
        public static void navigation() {
            RouterUtils.navigationWithFlag(SPLASH_PATH, Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        /**
         * 导航到首页
         */
        public static void navigationAndClearTop() {
            RouterUtils.navigationAndClearTop(SPLASH_PATH);
        }
    }

    //endregion

    //region: 首页路由相关

    public static class Main {

        /**
         * 导航到首页
         */
        public static void navigation(@NonNull final Activity activity) {
            RouterUtils.navigation(activity, MAIN_PATH);
        }

        /**
         * 导航到首页
         */
        public static void navigationWithFinish(@NonNull final Activity activity) {
            RouterUtils.navigationWithFinish(activity, MAIN_PATH);
        }

        /**
         * 导航到首页
         */
        public static void navigationAndClearTop() {
            RouterUtils.navigationAndClearTop(MAIN_PATH);
        }
    }

    //endregion

    //region: 登录路由相关

    public static class Login {

        /**
         * 登录页请求码
         */
        public static final int LOGIN_REQUEST_CODE = 10011;

        /**
         * 设置页面返回结果
         *
         * @param activity
         */
        public static void setActivityResult(@NonNull final Activity activity) {
            final Intent intent = new Intent();
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        }

        /**
         * 判断是否是登录页面的请求码 (默认请求码才可以用此方法）
         *
         * @param requestCode
         * @return
         */
        public static boolean isRequestCode(final int requestCode) {
            return LOGIN_REQUEST_CODE == requestCode;
        }

        /**
         * 导航到登录页
         *
         * @param activity
         */
        public static void navigation(@NonNull final Activity activity) {
            navigation(activity, LOGIN_REQUEST_CODE);
        }

        /**
         * 导航到登录页
         *
         * @param activity
         */
        public static void navigationWithFinish(@NonNull final Activity activity) {
            navigationWithFinish(activity, LOGIN_REQUEST_CODE);
        }

        /**
         * 导航到登录页
         *
         * @param activity
         * @param requestCode
         */
        public static void navigation(@NonNull final Activity activity,
                                      final int requestCode) {
            RouterUtils.navigation(activity, LOGIN_PATH, requestCode);
        }


        /**
         * 导航到登录页
         *
         * @param activity
         * @param requestCode
         */
        public static void navigationWithFinish(@NonNull final Activity activity,
                                                final int requestCode) {
            RouterUtils.navigationWithFinish(activity, LOGIN_PATH, requestCode);
        }

        /**
         * 导航到登录页
         *
         * @param fragment
         * @param requestCode
         */
        public static void navigation(@NonNull final Fragment fragment,
                                      final int requestCode) {
            Postcard postcard = RouterUtils.buildPostcard(LOGIN_PATH);
            RouterUtils.completion(postcard);
            Intent intent = new Intent(fragment.getActivity(), postcard.getDestination());
            fragment.startActivityForResult(intent, requestCode);
        }

    }

    //endregion
}