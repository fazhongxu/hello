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
     * 获取当前登录用户的userId
     *
     * @return
     */
    public static String getCurrentUserId() {
        return getApplication() == null ? null : getApplication().getCurrentUserId();
    }

}