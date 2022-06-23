package com.xxl.hello.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.xxl.kit.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2022/3/8.
 */
public class ShortcutConfig {
    /**
     * crm shortcut id
     */
    public static final String CRM_SHORTCUT_ID = "crm_shortcut_id";

    /**
     * 构建shortcut
     *
     * @param context        上下文
     * @param intent         点击跳转的页面
     * @param id             唯一标识
     * @param shortLabel     名称
     * @param iconRes        图标
     * @param disableMessage 禁用后的点击提示
     * @return
     */
    public static ShortcutInfo buildShortcutInfo(@NonNull final Context context,
                                                @NonNull final Intent intent,
                                                @NonNull final String id,
                                                @NonNull final String shortLabel,
                                                @DrawableRes final int iconRes,
                                                @NonNull final String disableMessage) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return null;
        }
        return new ShortcutInfo.Builder(context, id)
                .setShortLabel(shortLabel)
                .setIcon(Icon.createWithResource(context, iconRes))
                .setDisabledMessage(disableMessage)
                .setIntent(intent)
                .build();

    }

    /**
     * 注册shortcuts
     *
     * @param context
     * @param shortcutInfoList
     */
    public static void registerShortcuts(@NonNull final Context context,
                                         @NonNull final List<ShortcutInfo> shortcutInfoList) {
        if (ListUtils.isEmpty(shortcutInfoList) || Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager == null) {
            return;
        }
        final List<ShortcutInfo> targetShortcuts = new ArrayList<>(shortcutInfoList);
        shortcutManager.addDynamicShortcuts(targetShortcuts);
    }

    /**
     * 取消注册shortcuts
     *
     * @param context
     * @param targetShortcutIds
     */
    public static void unRegisterShortcuts(@NonNull final Context context,
                                           @NonNull List<String> targetShortcutIds) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager == null) {
            return;
        }
        final List<String> shortcutIds = new ArrayList<>();
        final List<ShortcutInfo> pinnedShortcuts = shortcutManager.getDynamicShortcuts();
        for (ShortcutInfo pinnedShortcut : pinnedShortcuts) {
            if (targetShortcutIds.contains(pinnedShortcut.getId())) {
                shortcutIds.add(pinnedShortcut.getId());
            }
        }
        shortcutManager.disableShortcuts(shortcutIds);
        shortcutManager.removeDynamicShortcuts(shortcutIds);

    }

}