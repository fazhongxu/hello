package com.xxl.hello.core.utils;

import com.xxl.hello.core.BaseApplication;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class AppExpandUtils {

    public static BaseApplication getApplication() {
        if (AppUtils.getApplication() instanceof BaseApplication) {
            return (BaseApplication) AppUtils.getApplication();
        }
        return null;
    }

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    public static boolean isAgreePrivacyPolicy() {
        return getApplication() != null && getApplication().isAgreePrivacyPolicy();
    }

    /**
     * 在用户统一"隐私政策"后初始化插件
     */
    public static void initPluginAfterAgreePrivacyPolicy() {
        if (getApplication() == null) {
            return;
        }
        getApplication().initPluginAfterAgreePrivacyPolicy();
    }

    /**
     * 获取当前登录用户的userId
     *
     * @return
     */
    public static String getCurrentUserId() {
        return getApplication() == null ? null : getApplication().getCurrentUserId();
    }

    /**
     * 是否是debug模式
     *
     * @return
     */
    public static boolean isDebug() {
        return getApplication() == null ? null : getApplication().isDebug();
    }


}