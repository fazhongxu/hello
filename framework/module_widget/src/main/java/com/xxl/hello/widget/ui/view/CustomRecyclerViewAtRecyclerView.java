package com.xxl.hello.widget.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义在RecyclerView 内部的RecyclerView （解决滑动冲突问题）
 *
 * @author xxl.
 * @date 2021/5/7.
 */
public class CustomRecyclerViewAtRecyclerView extends RecyclerView {

    //region: 构造函数

    public CustomRecyclerViewAtRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CustomRecyclerViewAtRecyclerView(@NonNull Context context,
                                            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerViewAtRecyclerView(@NonNull Context context,
                                            @Nullable AttributeSet attrs,
                                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    //endregion
}