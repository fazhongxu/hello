package com.xxl.hello.widget.ui.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xxl.kit.DisplayUtils;
import com.xxl.kit.ViewUtils;

/**
 * 自定义最大高度的RecyclerView
 * <p>
 * 解决ScrollView,NestScrollView嵌套RecyclerView导致
 * recyclerview复用机制失效，滑动卡顿问题
 * <p>
 * 1.关掉recyclerview的滑动recyclerview#setNestedScrollingEnabled(false)
 *
 * 2.监听scrollview的滑动，自定义rv 的layoutManager 控制rv是否可以滑动
 * 比如某个控件可见的时候，rv设置为不可滑动，反之则可以滑动{@link ViewUtils#isVisibleLocal}
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