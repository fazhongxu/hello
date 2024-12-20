package com.xxl.hello.widget.ui.view.plugin;

import androidx.annotation.NonNull;

/**
 * @author xxl.
 * @date 2023/9/15.
 */
public interface PluginClickListener {

    /**
     * 插件条目点击
     *
     * @param plugin
     * @param position
     */
    void onPluginItemClick(@NonNull Plugin plugin,
                           int position);
}