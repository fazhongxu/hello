package com.xxl.kit;

import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * shortcut 工具类 类似 iOS 3D Touch
 *
 * @author xxl.
 * @date 2022/3/8.
 */
public class ShortcutUtils {

    private ShortcutUtils() {

    }

    public ShortcutManager getShortcutManager(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return null;
        }
        return context.getSystemService(ShortcutManager.class);
    }

    public static void register(@NonNull final Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager == null) {
            return;
        }
        final List<ShortcutInfo> targetShortcuts = new ArrayList<>();

        ShortcutInfo shortcutInfoCrm = new ShortcutInfo.Builder(context, "shortcut_crm")
                .build();

        targetShortcuts.add(shortcutInfoCrm);
        shortcutManager.addDynamicShortcuts(targetShortcuts);
    }


}