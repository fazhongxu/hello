package com.xxl.hello.widget.ui.view.plugin.impl;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xxl.hello.widget.R;
import com.xxl.hello.widget.ui.view.keyboard.CommonKeyboardLayout;
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

    private static final int REQUEST_CODE = 0x10001;

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
     * @param targetFragment
     * @param targetKeyboardLayout
     */
    @Override
    public void onClick(@NonNull Fragment targetFragment,
                        @NonNull CommonKeyboardLayout targetKeyboardLayout) {
        ToastUtils.success("相册").show();
    }

    //endregion


}