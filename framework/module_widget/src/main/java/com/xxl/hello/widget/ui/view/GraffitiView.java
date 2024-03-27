package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2024/3/27.
 */
public class GraffitiView extends View {

    public GraffitiView(Context context) {
        this(context, null);
    }

    public GraffitiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraffitiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}