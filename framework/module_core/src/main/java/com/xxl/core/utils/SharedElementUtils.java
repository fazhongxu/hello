package com.xxl.core.utils;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 共享元素工具
 *
 * @author xxl.
 * @date 2023/5/29.
 */
public class SharedElementUtils {

    /**
     * 解决共享元素拖拽结束后回到上一个页面空白问题
     * reference https://github.com/bauer-bao/DragCloseHelper/tree/master
     * // TODO: 2023/5/29 使用还是有点麻烦，多个页面如果用了同一个查看图片，有可能有问题，解决方案2，在目标view下面盖一个fake view，共享元素动画让fake view 来做（也是有点麻烦）
     *
     * @param activity
     */
    public static void setExitSharedElementCallback(@NonNull final Activity activity,
                                                    @NonNull OnMediaPreviewSharedElementListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.setExitSharedElementCallback(new SharedElementCallback() {

                @Override
                public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                    sharedElement.setAlpha(1f);
                    return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
                }
            });
            setMediaPreviewSharedElementListener(listener);
        }
    }

    private static OnMediaPreviewSharedElementListener sMediaPreviewSharedElementListener;

    public static void setMediaPreviewSharedElementListener(@Nullable final OnMediaPreviewSharedElementListener listener) {
        sMediaPreviewSharedElementListener = listener;
    }

    /**
     * 清除多媒体查看页面共享元素监听事件
     * 在跳转查看多媒体的上一个页面的 onResume 里面调用
     */
    public static void clearMediaPreviewSharedElementListener() {
        setMediaPreviewSharedElementListener(null);
    }

    /**
     * 多媒体查看页面共享元素拖拽开始
     */
    public static void onMediaPreviewSharedElementDragStart() {
        if (sMediaPreviewSharedElementListener != null) {
            sMediaPreviewSharedElementListener.onSharedElementDragStart();
        }
    }

    /**
     * 多媒体查看页面共享元素监听
     */
    public interface OnMediaPreviewSharedElementListener {

        /**
         * 共享元素拖拽开始
         */
        void onSharedElementDragStart();
    }
}