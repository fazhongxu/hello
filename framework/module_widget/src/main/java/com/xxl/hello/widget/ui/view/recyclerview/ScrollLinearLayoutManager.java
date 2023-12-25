package com.xxl.hello.widget.ui.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Keep;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 控制滚动的LinearLayoutManager
 *
 * @author xxl.
 * @date 2023/12/25.
 */
public class ScrollLinearLayoutManager extends LinearLayoutManager {

    //region: 成员变量

    /**
     * 判断是否可以滚动
     */
    private boolean mScrollEnabled = true;

    //endregion

    //region: 构造函数

    public ScrollLinearLayoutManager(final Context context) {
        super(context);
    }

    public ScrollLinearLayoutManager(final Context context,
                                     final int orientation,
                                     final boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ScrollLinearLayoutManager(final Context context,
                                     final AttributeSet attrs,
                                     final int defStyleAttr,
                                     final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    //endregion

    //region: 页面生命周期

    @Override
    public boolean canScrollVertically() {
        return mScrollEnabled && super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return mScrollEnabled && super.canScrollHorizontally();
    }
    //endregion

    //region: 提供方法

    /**
     * 设置 RecyclerView 是否可以滑动
     *
     * @param isScrollEnabled
     */
    public void setScrollEnabled(final boolean isScrollEnabled) {
        mScrollEnabled = isScrollEnabled;
    }
    //endregion
}
