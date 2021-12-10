package com.xxl.core.utils;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

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
     * 跳转到指定页面
     *
     * @param path
     */
    public static void navigation(@NonNull final String path) {
        ARouter.getInstance().build(path)
                .navigation();
    }

    /**
     * 跳转到指定页面
     *
     * @param path
     */
    public static void navigationWithFinish(@NonNull final Activity activity,
                                            @NonNull final String path) {
        ARouter.getInstance().build(path)
                .navigation(activity, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        if (activity != null) {
                            activity.finish();
                        }
                    }
                });
    }

}