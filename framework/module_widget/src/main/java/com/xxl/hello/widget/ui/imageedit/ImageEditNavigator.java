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
     * 矩形选择点击
     */
    void onRectSelectClick();

    /**
     * 涂抹点击
     */
    void onBrushClick();

    /**
     * 重绘点击
     */
    void onRedrawClick();

    /**
     * 撤销点击
     */
    void onUndoClick();

    /**
     * 重做点击
     */
    void onRedoClick();

}
