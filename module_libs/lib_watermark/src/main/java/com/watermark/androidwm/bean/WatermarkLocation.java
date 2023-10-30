package com.watermark.androidwm.bean;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * It's a class for saving the location of watermark.
 * Can be used for a single image/text or a set of
 * images/texts.
 * It corresponds to the position {@link WatermarkPosition}
 *
 * @author xxl
 * @since 23/10/2023
 */
@IntDef({
        WatermarkLocation.NONE,
        WatermarkLocation.TOP_CENTER,
        WatermarkLocation.BOTTOM_CENTER,
        WatermarkLocation.TOP_LEFT,
        WatermarkLocation.TOP_RIGHT,
        WatermarkLocation.CENTER,
        WatermarkLocation.BOTTOM_LEFT,
        WatermarkLocation.BOTTOM_RIGHT
})
@Retention(RetentionPolicy.SOURCE)
public @interface WatermarkLocation {

    /**
     * 未设置
     */
    int NONE = -1;

    /**
     * 上居中
     */
    int TOP_CENTER = 0;

    /**
     * 下居中
     */
    int BOTTOM_CENTER = 1;

    /**
     * 左上角
     */
    int TOP_LEFT = 2;

    /**
     * 右上角
     */
    int TOP_RIGHT = 3;

    /**
     * 居中
     */
    int CENTER = 4;

    /**
     * 左下角
     */
    int BOTTOM_LEFT = 5;

    /**
     * 右下角
     */
    int BOTTOM_RIGHT = 6;
}