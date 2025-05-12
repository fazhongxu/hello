package com.xxl.hello.widget.ui.view.recyclerview;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义让不同宽高item上下居中的LayoutManager
 */
public class CenterLayoutManager extends LinearLayoutManager {
    public CenterLayoutManager(Context context) {
        super(context, HORIZONTAL, false);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != null) {
                // 计算垂直居中的偏移量
                int parentHeight = getHeight();
                int itemHeight = child.getHeight();
                int top = (parentHeight - itemHeight) / 2;

                // 应用偏移
                child.offsetTopAndBottom(top - child.getTop());
            }
        }
    }
}