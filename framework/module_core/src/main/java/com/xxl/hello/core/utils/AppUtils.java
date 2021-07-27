package com.xxl.hello.core.utils;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.core.listener.ActivityLifecycle;
import com.xxl.hello.core.listener.OnAppStatusChangedListener;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class AppUtils {

    //region: 成员变量

    /**
     * 应用上下文
     */
    private static Application mApplication;

    /**
     * Activity 页面生命周期监听
     */
    private static ActivityLifecycle sActivityLifecycle;

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
    public static void init(@NonNull final Application application,
                            @NonNull final ActivityLifecycle activityLifecycle) {
        mApplication = application;
        sActivityLifecycle = activityLifecycle;
        mApplication.registerActivityLifecycleCallbacks(sActivityLifecycle);
    }

    /**
     * 获取应用上下文
     *
     * @return
     */
    public static Application getApplication() {
        return mApplication;
    }

    /**
     * 获取顶部activity
     *
     * @return
     */
    public static Activity getTopActivity() {
        return sActivityLifecycle == null ? null : sActivityLifecycle.getTopActivity();
    }

    /**
     * 添加前后台状态切换监听
     *
     * @param listener
     */
    public static void addOnAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener) {
        if (sActivityLifecycle == null) {
            return;
        }
        sActivityLifecycle.addOnAppStatusChangedListener(listener);
    }

    /**
     * 移除前后台状态切换监听
     *
     * @param listener
     */
    public static void removeOnAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener) {
        if (sActivityLifecycle == null) {
            return;
        }
        sActivityLifecycle.removeOnAppStatusChangedListener(listener);
    }

    /**
     * app是否进入到前台
     *
     * @return
     */
    public static boolean isForeground() {
        return sActivityLifecycle != null && sActivityLifecycle.isForeground();
    }

    /**
     * 重启应用
     */
    public static void restartApp() {
        System.exit(0);
    }

    //endregion

}