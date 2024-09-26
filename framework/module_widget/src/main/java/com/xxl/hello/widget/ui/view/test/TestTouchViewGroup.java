package com.xxl.hello.widget.ui.view.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2024/9/25.
 */
public class TestTouchViewGroup extends LinearLayout {

    public TestTouchViewGroup(Context context) {
        super(context);
    }

    public TestTouchViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTouchViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("ViewGroup", "onTouchEvent: " + event.getAction());
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("ViewGroup", "dispatchTouchEvent: " + event.getAction());
        return super.dispatchTouchEvent(event);
    }
}