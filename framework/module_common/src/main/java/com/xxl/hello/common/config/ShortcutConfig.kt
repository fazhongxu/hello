package com.xxl.hello.common.config

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.DrawableRes
import com.xxl.kit.ListUtils

/**
 * 小组件配置
 *
 * @author xxl.
 * @date 2023/3/2.
 */
class ShortcutConfig private constructor() {

    companion object {

        /**
         * crm shortcut id
         */
        const val CRM_SHORTCUT_ID = "crm_shortcut_id"

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
        fun buildShortcutInfo(context: Context,
                              intent: Intent,
                              id: String,
                              shortLabel: String,
                              @DrawableRes iconRes: Int,
                              disableMessage: String): ShortcutInfo? {
            if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1)) {
                return null
            }
            return ShortcutInfo.Builder(context, id)
                    .setShortLabel(shortLabel)
                    .setIcon(Icon.createWithResource(context, iconRes))
                    .setDisabledMessage(disableMessage)
                    .setIntent(intent)
                    .build()
        }

        /**
         * 注册shortcuts
         *
         * @param context
         * @param targetShortcutInfo
         */
        fun registerShortcut(context: Context,
                             targetShortcutInfo: ShortcutInfo?) {
            if (targetShortcutInfo == null) {
                return
            }
            val shortcutInfoList: MutableList<ShortcutInfo> = ArrayList()
            shortcutInfoList.add(targetShortcutInfo)
            registerShortcuts(context, shortcutInfoList)
        }

        /**
         * 注册shortcuts
         *
         * @param context
         * @param shortcutInfoList
         */
        fun registerShortcuts(context: Context,
                              shortcutInfoList: List<ShortcutInfo>) {
            if (ListUtils.isEmpty(shortcutInfoList) || Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                return
            }
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
                    ?: return
            val targetShortcuts: List<ShortcutInfo> = ArrayList(shortcutInfoList)
            shortcutManager.addDynamicShortcuts(targetShortcuts)
        }

        /**
         * 取消注册shortcuts
         *
         * @param context
         * @param targetShortcutIds
         */
        fun unRegisterShortcuts(context: Context,
                                targetShortcutIds: List<String?>) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                return
            }
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
                    ?: return
            val shortcutIds: MutableList<String> = ArrayList()
            val pinnedShortcuts = shortcutManager.dynamicShortcuts
            for (pinnedShortcut in pinnedShortcuts) {
                if (targetShortcutIds.contains(pinnedShortcut.id)) {
                    shortcutIds.add(pinnedShortcut.id)
                }
            }
            shortcutManager.disableShortcuts(shortcutIds)
            shortcutManager.removeDynamicShortcuts(shortcutIds)
        }
    }
}