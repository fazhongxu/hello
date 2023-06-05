package com.xxl.kit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 导航栏工具类
 *
 * @author xxl.
 * @date 2023/6/5.
 */
public class NavigationUtils {

    private static final String VIVO_NAVIGATION_GESTURE = "navigation_gesture_on";
    private static final String XIAOMI_FULLSCREEN_GESTURE = "force_fsg_nav_bar";
    private static final String DEFAULT_NAVIGATION_GESTURE = "navigationbar_is_min";
    private static final String HUAWAI_DISPLAY_NOTCH_STATUS = "display_notch_status";
    private static final String XIAOMI_DISPLAY_NOTCH_STATUS = "force_black";

    /**
     * 判断是否显示虚拟了菜单
     *
     * @return
     */
    public boolean isNavigationBarMenuExist(@NonNull final Activity activity) {
        return getResourceNavHeight(activity) > DisplayUtils.dp2px(activity,20);
    }

    /**
     * 获取vivo手机设置中的"navigation_gesture_on"值，判断当前系统是使用导航键还是手势导航操作
     *
     * @param context app Context
     * @return false 表示使用的是虚拟导航键(NavigationBar)， true 表示使用的是手势， 默认是false
     */
    public static boolean vivoNavigationGestureEnabled(Context context) {
        int val = Settings.Secure.getInt(context.getContentResolver(), VIVO_NAVIGATION_GESTURE, 0);
        return val != 0;
    }

    public static boolean xiaomiNavigationGestureEnabled(Context context) {
        int val = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val = Settings.Global.getInt(context.getContentResolver(), XIAOMI_FULLSCREEN_GESTURE, 0);
        }
        return val != 0;
    }

    public static boolean navigationGestureEnabled(Context context) {
        if (RomUtils.isVivo() || RomUtils.isOppo()) {
            return vivoNavigationGestureEnabled(context);
        } else if (RomUtils.isXiaomi()) {
            return xiaomiNavigationGestureEnabled(context);
        } else {
            int val = Settings.Secure.getInt(context.getContentResolver(), DEFAULT_NAVIGATION_GESTURE, 0);
            return val != 0;
        }
    }

    public static int getResourceNavHeight(Context context) {
        // 小米4没有nav bar, 而 navigation_bar_height 有值
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    public  static int getNavigationBarHeight(Activity activity){
        return isNavigationBarShow(activity) && navigationGestureEnabled(activity) ? getResourceNavHeight(activity) : 0;
    }

    /**
     * 获取显示的获取虚拟菜单的高度
     *
     * @param activity
     * @return
     */
    public static int getShowNavMenuHeight(Activity activity) {
        if (isNavigationBarShow(activity)) {
            return DisplayUtils.getNavMenuHeight(activity);
        } else {
            return 0;
        }
    }

    /**
     * 非全面屏下 虚拟按键是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isNavigationBarShow(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Display display = activity.getWindowManager().getDefaultDisplay();
                Point size = new Point();
                Point realSize = new Point();
                display.getSize(size);
                display.getRealSize(realSize);
                return realSize.y != size.y;
            } else {
                boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
                boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
                if (menu || back) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return false;
    }

    /**
     * 设置底部导航栏是否显示
     *
     * @param activity
     * @param visibility true显示，否则隐藏
     */
    public static void setBottomNavigationVisibility(@Nullable final Activity activity,
                                                     boolean visibility) {
        if (activity == null) {
            return;
        }
        try {
            if (visibility) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置导航栏颜色
     *
     * @param activity
     * @param color
     */
    public static void setNavigationBarColor(@Nullable final Activity activity,
                                             @ColorInt final int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().setNavigationBarColor(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}