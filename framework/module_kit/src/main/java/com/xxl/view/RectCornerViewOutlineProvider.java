package com.xxl.view;

import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RectCornerViewOutlineProvider extends ViewOutlineProvider {

    /**
     * 圆角的值
     */
    private float mRadius;

    private RectCornerViewOutlineProvider(@FloatRange(from = 0) final float radius) {
        mRadius = radius;
    }

    public final static RectCornerViewOutlineProvider obtain(@FloatRange(from = 0) final float radius) {
        return new RectCornerViewOutlineProvider(radius);
    }

    @Override
    public void getOutline(@NonNull final View view,
                           @NonNull final Outline outline) {
        final Rect selfRect = new Rect(0, 0, view.getWidth(), view.getHeight());
        outline.setRoundRect(selfRect, mRadius);
    }
}
