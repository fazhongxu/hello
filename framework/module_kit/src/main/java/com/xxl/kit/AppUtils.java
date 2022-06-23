package com.xxl.kit;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

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
     * 获取activity集合
     *
     * @return
     */
    public static List<Activity> getActivityList() {
        return sActivityLifecycle == null ? null : sActivityLifecycle.getActivityList();
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
     * Relaunch the application.
     *
     * @param isKillProcess True to kill the process, false otherwise.
     */
    public static void relaunchApp(final boolean isKillProcess) {
        Intent intent = getLaunchAppIntent(AppUtils.getApplication().getPackageName());
        if (intent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        AppUtils.getApplication().startActivity(intent);
        if (!isKillProcess) {
            return;
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * Return the intent of launch app.
     *
     * @param pkgName The name of the package.
     * @return the intent of launch app
     */
    public static Intent getLaunchAppIntent(final String pkgName) {
        String launcherActivity = getLauncherActivity(pkgName);
        if (StringUtils.isSpace(launcherActivity)) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClassName(pkgName, launcherActivity);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Return the name of launcher activity.
     *
     * @return the name of launcher activity
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(AppUtils.getApplication().getPackageName());
    }

    /**
     * Return the name of launcher activity.
     *
     * @param pkg The name of the package.
     * @return the name of launcher activity
     */
    public static String getLauncherActivity(@NonNull final String pkg) {
        if (StringUtils.isSpace(pkg)) {
            return "";
        }
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pkg);
        PackageManager pm = AppUtils.getApplication().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        if (info == null || info.size() == 0) {
            return "";
        }
        return info.get(0).activityInfo.name;
    }

    /**
     * 重启应用
     */
    public static void restartApp() {
        if (!ListUtils.isEmpty(AppUtils.getActivityList())) {
            for (Activity activity : AppUtils.getActivityList()) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        AppRouterApi.navigationToSplash();
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    /**
     * 退出应用
     */
    public static void exitApp() {
        if (getActivityList() != null) {
            for (Activity activity : getActivityList()) {
                activity.finish();
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
    //endregion

}