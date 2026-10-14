package com.xxl.hello.widget.ui.view.sticky;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.xxl.hello.widget.R;

/**
 * 折叠吸顶头部容器（配合{@link StickyHeaderBehavior}使用）
 * <p>
 * 子View按声明顺序垂直排列，支持「折叠项」与「吸顶项」交错：
 * <ul>
 * <li>app:layout_collapse="true"：折叠项，内容上滑时整体收起</li>
 * <li>app:layout_sticky="true"：吸顶项，上滑到顶部后固定</li>
 * </ul>
 * 折叠模型（offset为当前折叠偏移量，范围[0, 所有折叠项高度总和]）：
 * <ul>
 * <li>折叠项：translationY = -min(offset, 其上方折叠项总高 + 自身高) -> 完全滑出</li>
 * <li>吸顶项：translationY = -min(offset, 其上方折叠项总高) -> 上移到顶部后钉住</li>
 * </ul>
 * 注意：吸顶项需要设置不透明背景，折叠项滑出时会从吸顶项下方穿过。
 *
 * @author xxl.
 * @date 2026/10/14.
 */
public class StickyHeaderLayout extends LinearLayout {

    //region: 成员变量

    /**
     * 当前折叠偏移量
     */
    private int mCollapseOffset;

    /**
     * 最大可折叠偏移量（所有折叠项高度总和）
     */
    private int mMaxCollapseOffset;

    /**
     * 自定义绘制顺序：折叠项 -> 吸顶项
     */
    private int[] mDrawingOrder = new int[0];

    //endregion

    //region: 构造函数

    public StickyHeaderLayout(final Context context) {
        this(context, null);
    }

    public StickyHeaderLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyHeaderLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 吸顶项需要绘制在折叠项之上（折叠项滑出时从吸顶项下方穿过）
        setChildrenDrawingOrderEnabled(true);
    }

    //endregion

    //region: 布局

    @Override
    public LayoutParams generateLayoutParams(final AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(final ViewGroup.LayoutParams params) {
        if (params instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) params);
        }
        if (params instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) params);
        }
        return new LayoutParams(params);
    }

    @Override
    protected boolean checkLayoutParams(final ViewGroup.LayoutParams params) {
        return params instanceof LayoutParams;
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int aboveCollapseHeight = 0;
        mMaxCollapseOffset = 0;
        // 记录每一个子项上方折叠项的总高度
        for (int index = 0, count = getChildCount(); index < count; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() == GONE) {
                continue;
            }
            final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            final int childHeight = child.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            layoutParams.mAboveCollapseHeight = aboveCollapseHeight;
            layoutParams.mHeight = childHeight;
            if (layoutParams.mCollapse) {
                aboveCollapseHeight += childHeight;
                mMaxCollapseOffset += childHeight;
            }
        }
        buildDrawingOrder();
        // 恢复当前折叠状态
        applyCollapseOffset(mCollapseOffset);
    }

    /**
     * 构建绘制顺序：折叠项 -> 吸顶项
     */
    private void buildDrawingOrder() {
        final int childCount = getChildCount();
        if (mDrawingOrder.length != childCount) {
            mDrawingOrder = new int[childCount];
        }
        int drawingIndex = 0;
        for (int index = 0; index < childCount; index++) {
            if (((LayoutParams) getChildAt(index).getLayoutParams()).mCollapse) {
                mDrawingOrder[drawingIndex++] = index;
            }
        }
        for (int index = 0; index < childCount; index++) {
            if (((LayoutParams) getChildAt(index).getLayoutParams()).mSticky) {
                mDrawingOrder[drawingIndex++] = index;
            }
        }
    }

    @Override
    protected int getChildDrawingOrder(final int childCount, final int drawingIndex) {
        if (drawingIndex < mDrawingOrder.length) {
            return mDrawingOrder[drawingIndex];
        }
        return drawingIndex;
    }

    //endregion

    //region: 折叠操作

    /**
     * 应用折叠偏移量到所有子View
     *
     * @param collapseOffset 折叠偏移量
     */
    private void applyCollapseOffset(final int collapseOffset) {
        for (int index = 0, count = getChildCount(); index < count; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() == GONE) {
                continue;
            }
            final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            if (layoutParams.mSticky) {
                // 吸顶项：上移其上方折叠项收起的高度后钉住
                child.setTranslationY(-Math.min(collapseOffset, layoutParams.mAboveCollapseHeight));
            } else if (layoutParams.mCollapse) {
                // 折叠项：完全滑出
                child.setTranslationY(-Math.min(collapseOffset, layoutParams.mAboveCollapseHeight + layoutParams.mHeight));
            }
        }
    }

    /**
     * 是否存在折叠项
     *
     * @return
     */
    public boolean hasCollapseChildren() {
        return mMaxCollapseOffset > 0;
    }

    /**
     * 获取当前折叠偏移量
     *
     * @return
     */
    public int getCurrentCollapseOffset() {
        return mCollapseOffset;
    }

    /**
     * 获取最大可折叠偏移量
     *
     * @return
     */
    public int getMaxCollapseOffset() {
        return mMaxCollapseOffset;
    }

    /**
     * 设置折叠偏移量
     *
     * @param targetOffset 目标折叠偏移量
     * @return 实际改变的偏移量
     */
    public int setCollapseOffset(int targetOffset) {
        targetOffset = Math.max(0, Math.min(targetOffset, mMaxCollapseOffset));
        if (mCollapseOffset == targetOffset) {
            return 0;
        }
        final int changedOffset = targetOffset - mCollapseOffset;
        mCollapseOffset = targetOffset;
        applyCollapseOffset(mCollapseOffset);
        return changedOffset;
    }

    //endregion

    //region: 子View布局参数

    public static class LayoutParams extends LinearLayout.LayoutParams {

        /**
         * 是否是折叠项
         */
        public boolean mCollapse;

        /**
         * 是否是吸顶项
         */
        public boolean mSticky;

        /**
         * 布局时记录：该子项上方所有折叠项的高度总和
         */
        int mAboveCollapseHeight;

        /**
         * 布局时记录：该子项自身高度（含上下外边距）
         */
        int mHeight;

        public LayoutParams(final Context context, final AttributeSet attrs) {
            super(context, attrs);
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickyHeaderLayout_Layout);
            mCollapse = typedArray.getBoolean(R.styleable.StickyHeaderLayout_Layout_layout_collapse, false);
            mSticky = typedArray.getBoolean(R.styleable.StickyHeaderLayout_Layout_layout_sticky, false);
            typedArray.recycle();
        }

        public LayoutParams(final int width, final int height) {
            super(width, height);
        }

        public LayoutParams(final MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(final ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    //endregion
}
