package com.xxl.hello.widget.ui.view.plugin.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.xxl.core.image.selector.MediaSelector;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.ui.view.keyboard.CommonKeyboardLayout;
import com.xxl.hello.widget.ui.view.plugin.Plugin;

/**
 * 拍照插件
 *
 * @author xxl.
 * @date 2023/9/15.
 */
public class CapturePlugin extends Plugin {

    //region: 成员变量

    private static final int REQUEST_CODE = 0x10002;

    //endregion

    //region: 提供方法

    /**
     * 操作请求码
     *
     * @return
     */
    @Override
    public int getRequestCode() {
        return REQUEST_CODE;
    }

    /**
     * 创建图标
     *
     * @param context
     * @return
     */
    @Override
    public int obtainDrawable(@NonNull Context context) {
        return R.drawable.resources_ext_plugin_capture;
    }

    /**
     * 创建标题
     *
     * @param context
     * @return
     */
    @Override
    public int obtainTitle(@NonNull Context context) {
        return R.string.resources_ext_plugin_capture;
    }

    /**
     * 点击事件
     *
     * @param context
     * @param keyboardLayout
     */
    @Override
    public void onClick(@NonNull FragmentActivity context,
                        @NonNull CommonKeyboardLayout keyboardLayout) {
        // TODO: 2024/12/20
    }

    /**
     * 处理页面返回结果
     *
     * @param context
     * @param requestCode
     * @param data
     */
    @Override
    public void handleOnActivityResult(@NonNull Activity context,
                                       int requestCode,
                                       @Nullable Intent data) {
        // TODO: 2024/12/20
    }


    //endregion

    //region: CapturePluginObservable

    public interface CapturePluginObservable {

    }

    //endregion


}