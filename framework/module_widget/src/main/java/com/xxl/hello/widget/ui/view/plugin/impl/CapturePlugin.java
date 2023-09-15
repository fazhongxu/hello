package com.xxl.hello.widget.ui.view.plugin.impl;

import android.content.Context;

import androidx.annotation.NonNull;

import com.xxl.hello.widget.ui.view.plugin.Plugin;

/**
 * 拍照插件
 *
 * @author xxl.
 * @date 2023/9/15.
 */
public class CapturePlugin extends Plugin {

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
        return 0;
    }

    /**
     * 创建标题
     *
     * @param context
     * @return
     */
    @Override
    public int obtainTitle(@NonNull Context context) {
        return 0;
    }

    //endregion


}