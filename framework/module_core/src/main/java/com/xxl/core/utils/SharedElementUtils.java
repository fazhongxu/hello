package com.xxl.core.utils;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

/**
 * 共享元素工具
 *
 * @author xxl.
 * @date 2023/5/29.
 */
public class SharedElementUtils {

    /**
     * 多媒体预览页面共享元素拖拽开始事件
     */
    public static final String MEDIA_PREVIEW_SHARED_ELEMENT_DRAG_START_EVENT = "media_preview_shared_element_drag_start_event";

    /**
     * 解决共享元素拖拽结束后回到上一个页面空白问题
     * reference https://github.com/bauer-bao/DragCloseHelper/tree/master
     *
     * @param activity
     */
    public static void setExitSharedElementCallback(@NonNull final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.setExitSharedElementCallback(new SharedElementCallback() {

                @Override
                public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                    sharedElement.setAlpha(1f);
                    return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
                }
            });
        }
    }

    /**
     * 发送多媒体预览页面共享元素拖拽开始事件
     * 解决共享元素拖拽时上一个页面空白问题
     * reference https://github.com/bauer-bao/DragCloseHelper/tree/master
     * 还需要在对应页面监听此事件，调用目标view#setAlpha(1F) 解决
     */
    public static void postMediaPreviewSharedElementDragStartEvent() {
        EventBus.getDefault().post(MEDIA_PREVIEW_SHARED_ELEMENT_DRAG_START_EVENT);
    }
}