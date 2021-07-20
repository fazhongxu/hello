package com.xxl.hello.service.utils;

import com.xxl.hello.common.utils.AppUtils;
import com.xxl.hello.service.BaseApplication;

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
       return getApplication() == null?null:getApplication().getCurrentUserId();
    }
}