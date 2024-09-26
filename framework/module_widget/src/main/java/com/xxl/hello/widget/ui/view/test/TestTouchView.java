package com.xxl.hello.widget.ui.view.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2024/9/25.
 */
public class TestTouchView extends View {

    public TestTouchView(Context context) {
        super(context);
    }

    public TestTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("View", "onTouchEvent: " + event.getAction());
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("View", "dispatchTouchEvent: " + event.getAction());
        return super.dispatchTouchEvent(event);
    }
}