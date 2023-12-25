package com.xxl.hello.widget.ui.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xxl.kit.DisplayUtils;

/**
 * 自定义最大高度的RecyclerView
 * 关掉recyclerview的滑动recyclerview#setNestedScrollingEnabled(false)
 *
 * @author xxl.
 * @date 2021/12/31.
 */
public class MaxHeightRecyclerView extends RecyclerView {

    /**
     * 最大高度
     */
    private int mMaxHeight;

    public MaxHeightRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightSpec) {
        int expendSpec;
        if (mMaxHeight != 0) {
            expendSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        } else {
            expendSpec = MeasureSpec.makeMeasureSpec(DisplayUtils.getScreenHeight(), MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, expendSpec);
    }

    /**
     * 设置最大高度
     *
     * @param maxHeight
     */
    public void setMaxHeight(final int maxHeight) {
        this.mMaxHeight = maxHeight;
    }
}