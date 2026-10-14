package com.xxl.hello.widget.ui.view.sticky;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

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

    //region: 成员变量

    /**
     * 抬手后头部折叠位移动画
     */
    private ValueAnimator mSnapAnimator;

    //endregion

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
    public void onNestedScrollAccepted(@NonNull final CoordinatorLayout coordinatorLayout,
                                       @NonNull final StickyHeaderLayout child,
                                       @NonNull final View directTargetChild,
                                       @NonNull final View target,
                                       final int nestedScrollAxes,
                                       final int type) {
        // 新的滚动开始，取消未完成的吸附动画
        cancelSnapAnimation();
    }

    @Override
    public void onNestedPreScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                  @NonNull final StickyHeaderLayout child,
                                  @NonNull final View target,
                                  final int dx,
                                  final int dy,
                                  @NonNull final int[] consumed,
                                  final int type) {
        if (dy > 0 && child.getCurrentCollapseOffset() < child.getMaxCollapseOffset()) {
            // 手指上滑时优先折叠头部
            final int consumedOffset = child.setCollapseOffset(child.getCurrentCollapseOffset() + dy);
            consumed[1] += consumedOffset;
            updateContentOffsets(coordinatorLayout, child);
        } else if (dy < 0 && child.getCurrentCollapseOffset() > 0
                && !ViewCompat.canScrollVertically(target, -1)) {
            // 列表已滚动到顶部时，下拉优先展开头部（保证展开跟手，不经过列表回滚阶段）
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

    @Override
    public void onStopNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                   @NonNull final StickyHeaderLayout child,
                                   @NonNull final View target,
                                   final int type) {
        // 滚动结束后将头部吸附到最近的停靠点，避免停在中间状态
        startSnapAnimation(coordinatorLayout, child);
    }

    //endregion

    //region: 吸附动画

    /**
     * 启动头部折叠吸附动画（吸附到最近的停靠点）
     *
     * @param coordinatorLayout
     * @param header
     */
    private void startSnapAnimation(@NonNull final CoordinatorLayout coordinatorLayout,
                                    @NonNull final StickyHeaderLayout header) {
        final int currentOffset = header.getCurrentCollapseOffset();
        final int targetOffset = header.findNearestStopOffset(currentOffset);
        if (currentOffset == targetOffset) {
            return;
        }
        cancelSnapAnimation();
        mSnapAnimator = ValueAnimator.ofInt(currentOffset, targetOffset);
        mSnapAnimator.setDuration(250L);
        mSnapAnimator.setInterpolator(new DecelerateInterpolator());
        mSnapAnimator.addUpdateListener(animation -> {
            if (!coordinatorLayout.isAttachedToWindow()) {
                animation.cancel();
                return;
            }
            header.setCollapseOffset((int) animation.getAnimatedValue());
            updateContentOffsets(coordinatorLayout, header);
        });
        mSnapAnimator.start();
    }

    /**
     * 取消吸附动画
     */
    private void cancelSnapAnimation() {
        if (mSnapAnimator != null) {
            mSnapAnimator.cancel();
            mSnapAnimator = null;
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
