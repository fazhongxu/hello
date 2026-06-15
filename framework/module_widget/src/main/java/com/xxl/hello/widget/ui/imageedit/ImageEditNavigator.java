package com.xxl.hello.widget.ui.imageedit;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

/**
 * 图片编辑页面导航接口
 *
 * @author xxl
 * @date 2026/06/15
 */
public interface ImageEditNavigator {

    /**
     * 编辑完成回调
     *
     * @param editedBitmap 编辑后的图片
     */
    void onEditComplete(@Nullable Bitmap editedBitmap);

    /**
     * 取消编辑
     */
    void onEditCancel();
}
