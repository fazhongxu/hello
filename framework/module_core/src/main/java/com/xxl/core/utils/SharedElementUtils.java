package com.xxl.core.utils;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 共享元素工具
 *
 * @author xxl.
 * @date 2023/5/29.
 */
public class SharedElementUtils {

    /**
     * 解决共享元素拖拽漏出上一个页面看到的控件空白问题
     * reference https://github.com/bauer-bao/DragCloseHelper/tree/master
     *
     * @param activity
     * @param targetView
     */
    public static void setExitSharedElementCallback(@Nullable final Activity activity,
                                                    @Nullable final View targetView) {
        if (activity == null || targetView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //解决共享元素拖拽结束后回到上一个页面空白问题
            activity.setExitSharedElementCallback(new SharedElementCallback() {

                @Override
                public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                    sharedElement.setAlpha(1f);
                    return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
                }
            });

            // 解决共享元素开始拖拽时看到底部原来的targetView空白问题
            targetView.postDelayed(() -> targetView.setAlpha(1F), 600);
        }
    }
}