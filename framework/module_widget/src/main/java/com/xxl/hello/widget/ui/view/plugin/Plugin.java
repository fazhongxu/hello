package com.xxl.hello.widget.ui.view.plugin;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author xxl.
 * @date 2023/9/15.
 */
public abstract class Plugin {

    //region: 提供方法

    /**
     * 创建图标
     *
     * @param context
     * @return
     */
    public abstract int obtainDrawable(@NonNull final Context context);

    /**
     * 创建标题
     *
     * @param context
     * @return
     */
    public abstract int obtainTitle(@NonNull final Context context);

    /**
     * 点击事件
     *
     * @param targetView
     */
    public abstract void onClick(@NonNull View targetView);

    //endregion
}