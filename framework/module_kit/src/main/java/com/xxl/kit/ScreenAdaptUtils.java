package com.xxl.kit;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * 头条适配方案工具类
 *
 * @author xxl
 * @date 2022/10/13.
 */
public class ScreenAdaptUtils {

    /**
     * 设计稿标准
     */
    private static float width = 375f;
    private static float high = 667f;

    private static float textDensity = 0;
    private static float textScaledDensity = 0;

    /**
     * @param w
     * @param h
     */
    public static void initDesignSize(float w, float h) {
        width = w;
        high = h;
    }

    /**
     * 设置适配
     *
     * @param activity
     */
    public static void setCustomDensity(@NonNull Activity activity) {
        setCustomDensity(activity, false);
    }

    /**
     * @param activity    上下文
     * @param isLandscape 是否是横屏
     */
    public static void setCustomDensity(@NonNull final Activity activity, boolean isLandscape) {
        final Application application = activity.getApplication();
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (textDensity == 0) {
            textDensity = displayMetrics.density;
            textScaledDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration configuration) {
                    if (configuration != null && configuration.fontScale > 0) {
                        textScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity;
        if (isLandscape) {
            //当前UI标准375*667
            targetDensity = displayMetrics.widthPixels / (high / 2);
        } else {
            //当前UI标准375*667
            targetDensity = displayMetrics.widthPixels / (width / 2);
        }
        final float targetScaledDensity = targetDensity * (textScaledDensity / textDensity);
        final int targetDpi = (int) (160 * targetDensity);
        displayMetrics.density = targetDensity;
        displayMetrics.scaledDensity = targetScaledDensity;
        displayMetrics.densityDpi = targetDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDpi;
    }

}