package com.xxl.hello.widget.ui.view.sticky;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * 折叠吸顶内容Behavior（绑定在内容View上，如ViewPager2）
 * <p>
 * 初始布局在AppBarLayout下方；头部折叠时由{@link StickyAppBarBehavior}联动上移补位
 * （底部超出部分被裁剪）。
 * <p>
 * 注意：不可使用系统的appbar_scrolling_view_behavior（其依赖AppBarLayout整体位移，
 * 而本方案AppBarLayout整体不动、仅子View位移）。
 *
 * @author xxl.
 * @date 2026/10/14.
 */
public class StickyContentBehavior extends CoordinatorLayout.Behavior<View> {

    //region: 构造函数

    /**
     * CoordinatorLayout.parseBehavior通过反射实例化，必须提供(Context, AttributeSet)两参构造
     */
    public StickyContentBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    //endregion

    //region: 布局与依赖

    @Override
    public boolean layoutDependsOn(@NonNull final CoordinatorLayout parent,
                                   @NonNull final View child,
                                   @NonNull final View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onLayoutChild(@NonNull final CoordinatorLayout parent,
                                 @NonNull final View child,
                                 final int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        final AppBarLayout appBarLayout = findAppBarLayout(parent);
        if (appBarLayout != null) {
            updateTop(child, appBarLayout);
        }
        return true;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull final CoordinatorLayout parent,
                                          @NonNull final View child,
                                          @NonNull final View dependency) {
        if (dependency instanceof AppBarLayout) {
            updateTop(child, (AppBarLayout) dependency);
        }
        return false;
    }

    @Nullable
    private AppBarLayout findAppBarLayout(@NonNull final CoordinatorLayout parent) {
        for (int index = 0, count = parent.getChildCount(); index < count; index++) {
            final View child = parent.getChildAt(index);
            if (child instanceof AppBarLayout) {
                return (AppBarLayout) child;
            }
        }
        return null;
    }

    //endregion

    //region: 位置更新

    /**
     * 内容View顶部对齐到AppBarLayout当前可见高度的底部
     *
     * @param child       内容View
     * @param appBarLayout 头部容器
     */
    void updateTop(@NonNull final View child,
                   @NonNull final AppBarLayout appBarLayout) {
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        final CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        final int collapseOffset = behavior instanceof StickyAppBarBehavior
                ? ((StickyAppBarBehavior) behavior).getCurrentCollapseOffset() : 0;
        final int targetTop = appBarLayout.getBottom() - collapseOffset;
        if (child.getTop() != targetTop) {
            child.offsetTopAndBottom(targetTop - child.getTop());
        }
    }

    //endregion
}
