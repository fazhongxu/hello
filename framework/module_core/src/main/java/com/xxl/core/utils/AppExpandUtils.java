package com.xxl.core.utils;

import android.app.Application;

import com.xxl.core.BaseApplication;
import com.xxl.core.listener.IApplication;
import com.xxl.kit.AppUtils;

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
    public static void initPluginsAfterAgreePrivacyPolicy() {
        if (getApplication() == null) {
            return;
        }
        getApplication().initPluginsAfterAgreePrivacyPolicy();
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
     * 判断当前用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        final Application application = AppUtils.getApplication();
        if (application == null) {
            return false;
        }
        if (application instanceof IApplication) {
            return ((IApplication) application).isLogin();
        }
        return false;
    }

    /**
     * 是否是debug模式
     *
     * @return
     */
    public static boolean isDebug() {
        return getApplication() != null && getApplication().isDebug();
    }


}