package com.xxl.kit;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由工具
 *
 * @author xxl.
 * @date 2021/7/27.
 */
public class RouterUtils {

    private RouterUtils() {

    }

    /**
     * 初始化路由
     *
     * @param application
     * @param isDebug
     */
    public static void init(@NonNull final Application application,
                            final boolean isDebug) {
        ARouter.init(application);
        if (isDebug) {
            ARouter.openDebug();
        }
    }

    /**
     * 注入
     *
     * @param thiz
     */
    public static void inject(Object thiz) {
        ARouter.getInstance().inject(thiz);
    }

    /**
     * 跳转到指定页面
     *
     * @param path
     */
    public static void navigation(@NonNull final String path) {
        buildPostcard(path)
                .navigation();
    }

    /**
     * 跳转到指定页面
     *
     * @param activity
     * @param path
     */
    public static void navigation(@NonNull final Activity activity,
                                  @NonNull final String path) {
        buildPostcard(path)
                .navigation(activity);
    }

    /**
     * 跳转到指定页面
     *
     * @param activity
     * @param path
     * @param requestCode
     */
    public static void navigation(@NonNull final Activity activity,
                                  @NonNull final String path,
                                  final int requestCode) {
        buildPostcard(path)
                .navigation(activity, requestCode);
    }


    /**
     * 跳转到指定页面
     *
     * @param activity
     * @param path
     * @param bundle
     * @param requestCode
     */
    public static void navigation(@NonNull final Activity activity,
                                  @NonNull final String path,
                                  @NonNull final Bundle bundle,
                                  final int requestCode) {
        buildPostcard(path)
                .with(bundle)
                .navigation(activity, requestCode);
    }

    /**
     * 跳转到指定页面
     *
     * @param path
     * @param bundle
     */
    public static void navigation(@NonNull final String path,
                                  @NonNull final Bundle bundle) {
        buildPostcard(path)
                .with(bundle)
                .navigation();
    }

    /**
     * 跳转到指定页面
     *
     * @param activity
     * @param path
     * @param bundle
     */
    public static void navigation(@NonNull final Activity activity,
                                  @NonNull final String path,
                                  @NonNull final Bundle bundle) {
        buildPostcard(path)
                .with(bundle)
                .navigation(activity);
    }

    /**
     * 跳转到指定页面并关闭当前页
     *
     * @param path
     * @param activity
     */
    public static void navigationWithFinish(@NonNull final Activity activity,
                                            @NonNull final String path) {
        buildPostcard(path)
                .navigation(activity, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        activity.finish();
                    }
                });
    }

    /**
     * 跳转到指定页面并关闭当前页
     *
     * @param activity
     * @param path
     * @param bundle
     */
    public static void navigationWithFinish(@NonNull final Activity activity,
                                            @NonNull final String path,
                                            @NonNull final Bundle bundle) {
        buildPostcard(path)
                .with(bundle)
                .navigation(activity, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        activity.finish();
                    }
                });
    }

    /**
     * 跳转到指定页面并清除栈顶的其他页面
     *
     * @param path
     */
    public static void navigationAndClearTop(@NonNull final String path) {
        final List<Activity> waitClearActivities = new ArrayList<>();
        final List<Activity> activities = AppUtils.getActivityList();
        final Class<?> destination = getDestination(path);
        if (!ListUtils.isEmpty(activities)) {
            for (int i = activities.size() - 1; i >= 0; i--) {
                Activity activity = activities.get(i);
                if (destination != null && activity.hashCode() == destination.hashCode()) {
                    continue;
                }
                waitClearActivities.add(activity);
            }
        }
        buildPostcard(path)
                .navigation(AppUtils.getApplication(), new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        if (!ListUtils.isEmpty(waitClearActivities)) {
                            for (int i = 0; i < waitClearActivities.size(); i++) {
                                Activity activity = waitClearActivities.get(i);
                                if (!activity.isFinishing()) {
                                    activity.finish();
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 跳转到指定页面并关闭当前页
     *
     * @param activity
     * @param path
     * @param requestCode
     */
    public static void navigationWithFinish(@NonNull final Activity activity,
                                            @NonNull final String path,
                                            final int requestCode) {
        buildPostcard(path)
                .navigation(activity, requestCode, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        activity.finish();
                    }
                });
    }


    /**
     * 跳转到指定页面并关闭当前页
     *
     * @param activity
     * @param path
     * @param bundle
     * @param requestCode
     */
    public static void navigationWithFinish(@NonNull final Activity activity,
                                            @NonNull final String path,
                                            @NonNull final Bundle bundle,
                                            final int requestCode) {
        buildPostcard(path)
                .with(bundle)
                .navigation(activity, requestCode, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        activity.finish();
                    }
                });
    }

    /**
     * 跳转到指定页面
     *
     * @param path
     * @param flag Intent启动标识
     */
    public static void navigationWithFlag(@NonNull final String path,
                                          final int flag) {
        buildPostcard(path)
                .withFlags(flag)
                .navigation();
    }

    /**
     * 构建Postcard
     *
     * @param path
     * @return
     */
    public static Postcard buildPostcard(@NonNull final String path) {
        return ARouter.getInstance()
                .build(path);
    }

    public static void completion(@NonNull final Postcard postcard) {
        try {
            LogisticsCenter.completion(postcard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getDestination(@NonNull final String path) {
        Postcard postcard = RouterUtils.buildPostcard(path);
        completion(postcard);
        return postcard.getDestination();
    }

    /**
     * 打开指定页面
     *
     * @param postcard
     */
    public static void navigation(@Nullable final Postcard postcard) {
        if (postcard == null) {
            return;
        }
        postcard.navigation();
    }

    /**
     * 判断当前任务栈内是否包含某个页面
     *
     * @param routerPath 路由路径
     * @return
     */
    public static boolean hasActivity(@NonNull final String routerPath) {
        if (ListUtils.isEmpty(AppUtils.getActivityList()) || TextUtils.isEmpty(routerPath)) {
            return false;
        }
        try {
            Class<?> destination = getDestination(routerPath);
            for (Activity activity : AppUtils.getActivityList()) {
                if (destination != null && destination.hashCode() == activity.getClass().hashCode()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}