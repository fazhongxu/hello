package com.xxl.hello.widget.ui.view.plugin;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xxl.hello.widget.ui.view.keyboard.CommonKeyboardLayout;

/**
 * @author xxl.
 * @date 2023/9/15.
 */
public abstract class Plugin {

    //region: 提供方法

    /**
     * 操作请求码
     *
     * @return
     */
    public abstract int getRequestCode();

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
     * @param targetFragment
     * @param targetKeyboardLayout
     */
    public abstract void onClick(@NonNull Fragment targetFragment,
                                 @NonNull CommonKeyboardLayout targetKeyboardLayout);

    //endregion
}