package com.xxl.hello.common.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import com.xxl.kit.LogUtils;

/**
 * 图标管理类
 *
 * @author xxl
 * @date 2026/4/7
 */
public class IconManager {

    private static final String TAG = "IconManager ";

    /**
     * 图标类型枚举
     */
    public enum IconType {
        DEFAULT(".main.DefaultIconAlias"),//多给了个main是因为启动页在包package="com.xxl.hello.main"下
        VIP1(".main.Vip1IconAlias"),
        VIP2(".main.Vip2IconAlias");

        private String aliasName;

        IconType(String aliasName) {
            this.aliasName = aliasName;
        }

        public String getAliasName() {
            return aliasName;
        }
    }

    /**
     * 切换图标
     */
    public static boolean changeIcon(Context context, IconType iconType) {
        try {
            LogUtils.d(TAG + "准备切换图标到: " + iconType.name());

            // 先禁用所有图标
            disableAllIcons(context);

            // 启用目标图标
            ComponentName componentName = new ComponentName(context, context.getPackageName() + iconType.getAliasName());

            LogUtils.d(TAG + "ComponentName: " + componentName.flattenToString());

            context.getPackageManager()
                    .setComponentEnabledSetting(componentName,
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

            LogUtils.d(TAG + "成功切换到图标: " + iconType.name());
            return true;

        } catch (Exception e) {
            LogUtils.e(TAG + "切换图标失败: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 切换到默认图标
     *
     * @param context
     * @return
     */
    public static boolean setDefaultIcon(Context context) {
        return changeIcon(context, IconType.DEFAULT);
    }

    /**
     * 禁用所有图标
     *
     * @param context
     */
    private static void disableAllIcons(Context context) {
        for (IconType type : IconType.values()) {
            try {
                ComponentName componentName = new ComponentName(context, context.getPackageName() + type.getAliasName());
                context.getPackageManager().setComponentEnabledSetting(componentName,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
                LogUtils.d(TAG + "禁用组件: " + type.getAliasName());
            } catch (Exception e) {
                LogUtils.e(TAG + "禁用组件失败: " + type.getAliasName(), e);
            }
        }
    }

    /**
     * 获取当前图标类型
     *
     * @param context
     * @return
     */
    public static IconType getCurrentIcon(Context context) {
        for (IconType type : IconType.values()) {
            try {
                ComponentName componentName = new ComponentName(context, context.getPackageName() + type.getAliasName());

                int state = context.getPackageManager()
                        .getComponentEnabledSetting(componentName);

                if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                    return type;
                }
            } catch (Exception e) {
                LogUtils.e(TAG + "获取组件状态失败: " + type.getAliasName(), e);
            }
        }
        return IconType.DEFAULT;
    }

    /**
     * 获取当前图标类型
     *
     * @param context
     * @return
     */
    public static boolean isDefaultIcon(Context context) {
        return getCurrentIcon(context) == IconType.DEFAULT;
    }

}