package com.xxl.hello.widget.ui.view.sticky;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * 折叠吸顶内容Behavior（绑定在内容View上，如ViewPager2）
 * <p>
 * 初始布局在{@link StickyHeaderLayout}下方；头部折叠时由
 * {@link StickyHeaderBehavior}联动上移补位（底部超出部分被裁剪）。
 *
 * @author xxl.
 * @date 2026/10/14.
 */
public class StickyHeaderContentBehavior extends CoordinatorLayout.Behavior<View> {

    //region: 构造函数

    /**
     * CoordinatorLayout.parseBehavior通过反射实例化，必须提供(Context, AttributeSet)两参构造
     */
    public StickyHeaderContentBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    //endregion

    //region: 布局与依赖

    @Override
    public boolean layoutDependsOn(@NonNull final CoordinatorLayout parent,
                                   @NonNull final View child,
                                   @NonNull final View dependency) {
        return dependency instanceof StickyHeaderLayout;
    }

    @Override
    public boolean onLayoutChild(@NonNull final CoordinatorLayout parent,
                                 @NonNull final View child,
                                 final int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        final StickyHeaderLayout header = findStickyHeader(parent);
        if (header != null) {
            updateTop(child, header);
        }
        return true;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull final CoordinatorLayout parent,
                                          @NonNull final View child,
                                          @NonNull final View dependency) {
        if (dependency instanceof StickyHeaderLayout) {
            updateTop(child, (StickyHeaderLayout) dependency);
        }
        return false;
    }

    @Nullable
    private StickyHeaderLayout findStickyHeader(@NonNull final CoordinatorLayout parent) {
        for (int index = 0, count = parent.getChildCount(); index < count; index++) {
            final View child = parent.getChildAt(index);
            if (child instanceof StickyHeaderLayout) {
                return (StickyHeaderLayout) child;
            }
        }
        return null;
    }

    //endregion

    //region: 位置更新

    /**
     * 内容View顶部对齐到头部当前可见高度的底部
     *
     * @param child  内容View
     * @param header 头部容器
     */
    void updateTop(@NonNull final View child,
                   @NonNull final StickyHeaderLayout header) {
        final int targetTop = header.getBottom() - header.getCurrentCollapseOffset();
        if (child.getTop() != targetTop) {
            child.offsetTopAndBottom(targetTop - child.getTop());
        }
    }

    //endregion
}
