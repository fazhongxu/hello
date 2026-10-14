package com.xxl.hello.widget.ui.view.sticky;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 折叠吸顶Behavior（绑定在AppBarLayout上，替换其默认的AppBarLayout.Behavior）
 * <p>
 * AppBarLayout仅作为「带标记的垂直容器」使用，本Behavior接管其子View位移：
 * <ul>
 * <li>app:layout_scrollFlags="scroll"：折叠项，内容上滑时整体收起</li>
 * <li>不设置scrollFlags：吸顶项，上滑到顶部后固定</li>
 * </ul>
 * 支持折叠项与吸顶项交错排列。折叠模型（offset为当前折叠偏移量，范围[0, 所有折叠项高度总和]）：
 * <ul>
 * <li>折叠项：translationY = -min(offset, 其上方折叠项总高 + 自身高) -> 完全滑出</li>
 * <li>吸顶项：translationY = -min(offset, 其上方折叠项总高) -> 上移到顶部后钉住</li>
 * </ul>
 * 注意：吸顶项需要设置不透明背景，且建议设置android:elevation保证绘制在滑出的折叠项之上（API 21+）。
 * <p>
 * 滚动行为：上滑优先折叠头部、下拉优先展开头部（等价于scroll + enterAlways语义）；
 * 滚动结束后吸附到最近停靠点。
 *
 * @author xxl.
 * @date 2026/10/14.
 */
public class StickyAppBarBehavior extends CoordinatorLayout.Behavior<AppBarLayout> {

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
     * 头部子项几何信息
     */
    private final List<ChildHolder> mChildHolders = new ArrayList<>();

    /**
     * 折叠停靠点集合（含0，每个折叠项收起后的累计偏移量）
     */
    private final List<Integer> mCollapseStopOffsets = new ArrayList<>();

    /**
     * 抬手后头部折叠位移动画
     */
    private ValueAnimator mSnapAnimator;

    //endregion

    //region: 构造函数

    /**
     * CoordinatorLayout.parseBehavior通过反射实例化，必须提供(Context, AttributeSet)两参构造
     */
    public StickyAppBarBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    //endregion

    //region: 布局

    @Override
    public boolean onLayoutChild(@NonNull final CoordinatorLayout coordinatorLayout,
                                 @NonNull final AppBarLayout child,
                                 final int layoutDirection) {
        coordinatorLayout.onLayoutChild(child, layoutDirection);
        measureChildren(child);
        // 恢复当前折叠状态
        applyCollapseOffset(mCollapseOffset);
        return true;
    }

    /**
     * 统计头部子项几何信息（布局后调用）
     *
     * @param appBarLayout
     */
    private void measureChildren(@NonNull final AppBarLayout appBarLayout) {
        mChildHolders.clear();
        mCollapseStopOffsets.clear();
        mCollapseStopOffsets.add(0);
        int aboveCollapseHeight = 0;
        mMaxCollapseOffset = 0;
        for (int index = 0, count = appBarLayout.getChildCount(); index < count; index++) {
            final View childView = appBarLayout.getChildAt(index);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            final AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) childView.getLayoutParams();
            final boolean isCollapse = (layoutParams.getScrollFlags() & AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL) != 0;
            final int childHeight = childView.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            mChildHolders.add(new ChildHolder(childView, isCollapse, aboveCollapseHeight, childHeight));
            if (isCollapse) {
                aboveCollapseHeight += childHeight;
                mMaxCollapseOffset += childHeight;
                mCollapseStopOffsets.add(mMaxCollapseOffset);
            }
        }
    }

    //endregion

    //region: 折叠操作

    /**
     * 应用折叠偏移量到所有头部子View
     *
     * @param collapseOffset 折叠偏移量
     */
    private void applyCollapseOffset(final int collapseOffset) {
        for (int index = 0, count = mChildHolders.size(); index < count; index++) {
            final ChildHolder childHolder = mChildHolders.get(index);
            if (childHolder.mCollapse) {
                // 折叠项：完全滑出
                childHolder.mChild.setTranslationY(-Math.min(collapseOffset, childHolder.mAboveCollapseHeight + childHolder.mHeight));
            } else {
                // 吸顶项：上移其上方折叠项收起的高度后钉住
                childHolder.mChild.setTranslationY(-Math.min(collapseOffset, childHolder.mAboveCollapseHeight));
            }
        }
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
     * 设置折叠偏移量并联动内容View
     *
     * @param coordinatorLayout
     * @param appBarLayout
     * @param targetOffset    目标折叠偏移量
     * @return 实际改变的偏移量
     */
    private int setCollapseOffset(@NonNull final CoordinatorLayout coordinatorLayout,
                                  @NonNull final AppBarLayout appBarLayout,
                                  int targetOffset) {
        targetOffset = Math.max(0, Math.min(targetOffset, mMaxCollapseOffset));
        if (mCollapseOffset == targetOffset) {
            return 0;
        }
        final int changedOffset = targetOffset - mCollapseOffset;
        mCollapseOffset = targetOffset;
        applyCollapseOffset(mCollapseOffset);
        updateContentOffsets(coordinatorLayout, appBarLayout);
        return changedOffset;
    }

    //endregion

    //region: 嵌套滚动

    @Override
    public boolean onStartNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                       @NonNull final AppBarLayout child,
                                       @NonNull final View directTargetChild,
                                       @NonNull final View target,
                                       final int nestedScrollAxes,
                                       final int type) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && mMaxCollapseOffset > 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull final CoordinatorLayout coordinatorLayout,
                                       @NonNull final AppBarLayout child,
                                       @NonNull final View directTargetChild,
                                       @NonNull final View target,
                                       final int nestedScrollAxes,
                                       final int type) {
        // 新的滚动开始，取消未完成的吸附动画
        cancelSnapAnimation();
    }

    @Override
    public void onNestedPreScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                  @NonNull final AppBarLayout child,
                                  @NonNull final View target,
                                  final int dx,
                                  final int dy,
                                  @NonNull final int[] consumed,
                                  final int type) {
        if (dy > 0 && mCollapseOffset < mMaxCollapseOffset) {
            // 手指上滑时优先折叠头部（等价于AppBarLayout的scroll语义）
            consumed[1] += setCollapseOffset(coordinatorLayout, child, mCollapseOffset + dy);
        } else if (dy < 0 && mCollapseOffset > 0) {
            // 手指下拉时优先展开头部（等价于AppBarLayout的enterAlways语义）：
            // 头部展开跟手，全部展开后列表才开始回滚，避免"先滚列表再展头部"的割裂感
            consumed[1] += setCollapseOffset(coordinatorLayout, child, mCollapseOffset + dy);
        }
    }

    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                               @NonNull final AppBarLayout child,
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
                               @NonNull final AppBarLayout child,
                               @NonNull final View target,
                               final int dxConsumed,
                               final int dyConsumed,
                               final int dxUnconsumed,
                               final int dyUnconsumed,
                               final int type,
                               @NonNull final int[] consumed) {
        // 内容已滚动到顶部继续下拉时展开头部
        if (dyUnconsumed < 0 && mCollapseOffset > 0) {
            setCollapseOffset(coordinatorLayout, child, mCollapseOffset + dyUnconsumed);
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                   @NonNull final AppBarLayout child,
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
     * @param appBarLayout
     */
    private void startSnapAnimation(@NonNull final CoordinatorLayout coordinatorLayout,
                                    @NonNull final AppBarLayout appBarLayout) {
        final int currentOffset = mCollapseOffset;
        final int targetOffset = findNearestStopOffset(currentOffset);
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
            setCollapseOffset(coordinatorLayout, appBarLayout, (int) animation.getAnimatedValue());
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

    /**
     * 查找距离指定偏移量最近的折叠停靠点（0、只收起部分折叠项、全部收起）
     *
     * @param offset 指定偏移量
     * @return
     */
    private int findNearestStopOffset(final int offset) {
        int nearestOffset = 0;
        int nearestDistance = Integer.MAX_VALUE;
        for (int index = 0, count = mCollapseStopOffsets.size(); index < count; index++) {
            final int distance = Math.abs(mCollapseStopOffsets.get(index) - offset);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestOffset = mCollapseStopOffsets.get(index);
            }
        }
        return nearestOffset;
    }

    //endregion

    //region: 内容联动

    /**
     * 更新所有使用{@link StickyContentBehavior}的内容View位置
     *
     * @param coordinatorLayout
     * @param appBarLayout
     */
    private void updateContentOffsets(@NonNull final CoordinatorLayout coordinatorLayout,
                                      @NonNull final AppBarLayout appBarLayout) {
        for (int index = 0, count = coordinatorLayout.getChildCount(); index < count; index++) {
            final View childView = coordinatorLayout.getChildAt(index);
            final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) childView.getLayoutParams();
            final CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
            if (behavior instanceof StickyContentBehavior) {
                ((StickyContentBehavior) behavior).updateTop(childView, appBarLayout);
            }
        }
    }

    //endregion

    //region: 子项几何信息

    /**
     * 头部子项几何信息
     */
    private static class ChildHolder {

        final View mChild;

        /**
         * 是否是折叠项
         */
        final boolean mCollapse;

        /**
         * 该子项上方所有折叠项的高度总和
         */
        final int mAboveCollapseHeight;

        /**
         * 该子项自身高度（含上下外边距）
         */
        final int mHeight;

        ChildHolder(@NonNull final View child,
                    final boolean collapse,
                    final int aboveCollapseHeight,
                    final int height) {
            mChild = child;
            mCollapse = collapse;
            mAboveCollapseHeight = aboveCollapseHeight;
            mHeight = height;
        }
    }

    //endregion
}
