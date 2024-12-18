package com.xxl.hello.widget.ui.view.plugin.impl;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.hello.widget.R;
import com.xxl.hello.widget.ui.view.plugin.Plugin;
import com.xxl.kit.ToastUtils;

/**
 * 相册插件
 *
 * @author xxl.
 * @date 2023/9/15.
 */
public class AlbumPlugin extends Plugin {

    //region: 成员变量

    //endregion

    //region: 提供方法

    /**
     * 创建图标
     *
     * @param context
     * @return
     */
    @Override
    public int obtainDrawable(@NonNull Context context) {
        return R.drawable.resources_ext_plugin_album;
    }

    /**
     * 创建标题
     *
     * @param context
     * @return
     */
    @Override
    public int obtainTitle(@NonNull Context context) {
        return R.string.resources_ext_plugin_album;
    }

    /**
     * 点击事件
     *
     * @param targetView
     */
    @Override
    public void onClick(@NonNull View targetView) {
        ToastUtils.success("相册").show();
    }

    //endregion


}