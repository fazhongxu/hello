package com.xxl.hello.service.data.model.entity.im;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 消息方向
 *
 * @author xxl.
 * @date 2024/7/2.
 */
@IntDef({MessageDirection.LEFT,
        MessageDirection.RIGHT,
        MessageDirection.CENTER,
})
@Retention(RetentionPolicy.SOURCE)
public @interface MessageDirection {

    /**
     * 右边
     */
    int RIGHT = 0;

    /**
     * 左边
     */
    int LEFT = 1;

    /**
     * 中间
     */
    int CENTER = 2;
}