package com.xxl.hello.common.utils;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class AppUtils {

    //region: 成员变量

    private static Application mApplication;

    //endregion

    //region: 构造函数

    private AppUtils() {

    }

    //endregion

    //region: 提供方法

    /**
     * 初始化
     *
     * @param application
     */
    public static void init(@NonNull final Application application) {
        mApplication = application;
    }

    public static Application getApplication() {
        return mApplication;
    }

    //endregion

}