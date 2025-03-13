package com.xxl.hello.widget.ui.view.plugin.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xxl.core.image.selector.MediaSelector;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.ui.view.keyboard.CommonKeyboardLayout;
import com.xxl.hello.widget.ui.view.plugin.Plugin;
import com.xxl.kit.ListUtils;
import com.xxl.kit.ToastUtils;

import java.util.List;

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
     * @param context
     * @param keyboardLayout
     */
    @Override
    public void onClick(@NonNull FragmentActivity context,
                        @NonNull CommonKeyboardLayout keyboardLayout) {
        MediaSelector.create(context)
                .openGallery(PictureMimeType.ofAll())
                .forResult(getRequestCode());
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
        if (context instanceof AlbumPluginObservable) {
            List<LocalMedia> targetMedias = MediaSelector.obtainMultipleResult(data);
            if (!ListUtils.isEmpty(targetMedias)) {
                ((AlbumPluginObservable) context).handleAlbumPluginResult(targetMedias);
            }
            return;
        }
        ToastUtils.warning(String.format("插件功能请实现%s接口", AlbumPluginObservable.class.getSimpleName())).show();
    }

    //endregion

    //region: AlbumPluginObservable

    public interface AlbumPluginObservable {

        /**
         * 处理相册插件返回结果
         *
         * @param targetMedias
         */
        void handleAlbumPluginResult(List<LocalMedia> targetMedias);
    }

    //endregion


}