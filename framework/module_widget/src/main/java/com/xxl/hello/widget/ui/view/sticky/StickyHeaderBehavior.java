package com.xxl.hello.widget.ui.view.sticky;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * 折叠吸顶头部Behavior（绑定在{@link StickyHeaderLayout}上）
 * <p>
 * 通过CoordinatorLayout的嵌套滚动机制驱动{@link StickyHeaderLayout}折叠/展开，
 * 并同步联动使用{@link StickyHeaderContentBehavior}的内容View上移补位。
 * <p>
 * 滚动行为：内容上滑时优先折叠头部；内容滚动到顶部后继续下拉时展开头部。
 *
 * @author xxl.
 * @date 2026/10/14.
 */
public class StickyHeaderBehavior extends CoordinatorLayout.Behavior<StickyHeaderLayout> {

    //region: 构造函数

    /**
     * CoordinatorLayout.parseBehavior通过反射实例化，必须提供(Context, AttributeSet)两参构造
     */
    public StickyHeaderBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    //endregion

    //region: 嵌套滚动

    @Override
    public boolean onStartNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                       @NonNull final StickyHeaderLayout child,
                                       @NonNull final View directTargetChild,
                                       @NonNull final View target,
                                       final int nestedScrollAxes,
                                       final int type) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && child.hasCollapseChildren();
    }

    @Override
    public void onNestedPreScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                  @NonNull final StickyHeaderLayout child,
                                  @NonNull final View target,
                                  final int dx,
                                  final int dy,
                                  @NonNull final int[] consumed,
                                  final int type) {
        // 手指上滑时优先折叠头部
        if (dy > 0 && child.getCurrentCollapseOffset() < child.getMaxCollapseOffset()) {
            final int consumedOffset = child.setCollapseOffset(child.getCurrentCollapseOffset() + dy);
            consumed[1] += consumedOffset;
            updateContentOffsets(coordinatorLayout, child);
        }
    }

    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                               @NonNull final StickyHeaderLayout child,
                               @NonNull final View target,
                               final int dxConsumed,
                               final int dyConsumed,
                               final int dxUnconsumed,
                               final int dyUnconsumed,
                               final int type) {
        // 兼容NestedScrollingParent2的回调
        onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, new int[2]);
    }

    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                               @NonNull final StickyHeaderLayout child,
                               @NonNull final View target,
                               final int dxConsumed,
                               final int dyConsumed,
                               final int dxUnconsumed,
                               final int dyUnconsumed,
                               final int type,
                               @NonNull final int[] consumed) {
        // 内容已滚动到顶部继续下拉时展开头部
        if (dyUnconsumed < 0 && child.getCurrentCollapseOffset() > 0) {
            child.setCollapseOffset(child.getCurrentCollapseOffset() + dyUnconsumed);
            updateContentOffsets(coordinatorLayout, child);
        }
    }

    //endregion

    //region: 内容联动

    /**
     * 更新所有使用{@link StickyHeaderContentBehavior}的内容View位置
     *
     * @param coordinatorLayout
     * @param header
     */
    private void updateContentOffsets(@NonNull final CoordinatorLayout coordinatorLayout,
                                      @NonNull final StickyHeaderLayout header) {
        for (int index = 0, count = coordinatorLayout.getChildCount(); index < count; index++) {
            final View childView = coordinatorLayout.getChildAt(index);
            final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) childView.getLayoutParams();
            final CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
            if (behavior instanceof StickyHeaderContentBehavior) {
                ((StickyHeaderContentBehavior) behavior).updateTop(childView, header);
            }
        }
    }

    //endregion
}
