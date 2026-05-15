package com.xxl.core.widget.toolbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.xxl.core.R;

/**
 * @author xxl.
 * @date 2023/2/27.
 */
public class ToolbarWrapper {

    //region: 成员变量

    /**
     * 上下文
     */
    private FragmentActivity mFragmentActivity;

    /**
     * 监听
     */
    private OnToolbarProvider mOnToolbarProvider;

    /**
     * appbar
     */
    private AppBarLayout mAppBarLayout;

    /**
     * toolbar
     */
    private CustomToolBar mCustomToolBar;

    //endregion

    //region: 构造函数

    public ToolbarWrapper(@NonNull final FragmentActivity fragmentActivity,
                          @NonNull final OnToolbarProvider onToolbarProvider) {
        mFragmentActivity = fragmentActivity;
        mOnToolbarProvider = onToolbarProvider;
    }

    public final static ToolbarWrapper create(@NonNull final FragmentActivity fragmentActivity,
                                              @NonNull final OnToolbarProvider onToolbarProvider) {
        return new ToolbarWrapper(fragmentActivity, onToolbarProvider);
    }

    //endregion

    //region: 提供方法

    /**
     * 获取appbar
     *
     * @return
     */
    public AppBarLayout getAppBarLayout() {
        return mAppBarLayout;
    }

    /**
     * 获取toolbar
     *
     * @return
     */
    public CustomToolBar getCustomToolBar() {
        return mCustomToolBar;
    }

    /**
     * 设置顶部的 view
     *
     * @param rootView
     */
    public void setupToolbar(View rootView) {
        mAppBarLayout = rootView.findViewById(R.id.app_bar);
        mCustomToolBar = rootView.findViewById(R.id.tool_bar);

        if (mOnToolbarProvider != null) {
            if (mOnToolbarProvider.getToolbarBackgroundColor() != 0) {
                mAppBarLayout.setBackgroundColor(mOnToolbarProvider.getToolbarBackgroundColor());
            }

            if (mOnToolbarProvider.isDisplayLeft()) {
                mCustomToolBar.setupToolbarLeftLayout(mOnToolbarProvider.getLeftText(), v -> mOnToolbarProvider.onToolbarLeftClick(v), v -> mOnToolbarProvider.onToolbarLeftLongClick(v));
            }
            if (mOnToolbarProvider.isDisplayRightText() || mOnToolbarProvider.isDisplayRightIcon()) {
                mCustomToolBar.setupToolbarRightLayout(mOnToolbarProvider.getRightText(), mOnToolbarProvider.getRightIcon(), v -> mOnToolbarProvider.onToolbarRightClick(v), v -> mOnToolbarProvider.onToolbarRightLongClick(v));
            } else if (mOnToolbarProvider.isDisplayRightCustom()) {
                View rightCustomLayout = mOnToolbarProvider.getRightCustomLayout();
                if (rightCustomLayout != null) {
                    mCustomToolBar.setupToolbarCustomRightLayout(rightCustomLayout);
                }
            }
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setToolbarTitle(CharSequence title) {
        if (mCustomToolBar != null) {
            mCustomToolBar.setToolbarTitle(title);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setToolbarTitle(@StringRes int resId) {
        if (mCustomToolBar != null) {
            mCustomToolBar.setToolbarTitle(resId);
        }
    }

    /**
     * 设置appbar padding
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setAppbarPadding(int left,
                                 int top,
                                 int right,
                                 int bottom) {
        if (mAppBarLayout != null) {
            mAppBarLayout.setPadding(left, top, right, bottom);
        }
    }

    /**
     * 设置左边图标可见性
     */
    public void setLeftIconVisible(int visibility) {
        if (mCustomToolBar != null) {
            mCustomToolBar.setLeftIconVisible(visibility);
        }
    }

    /**
     * 设置左边文字可见性
     */
    public void setLeftTextVisible(int visibility) {
        if (mCustomToolBar != null) {
            mCustomToolBar.setLeftTextVisible(visibility);
        }
    }

    /**
     * 设置左边文字
     */
    public void setLeftText(@NonNull CharSequence text) {
        if (mCustomToolBar != null) {
            mCustomToolBar.setLeftText(text);
        }
    }

    /**
     * 设置左边文字
     */
    public void setLeftText(@StringRes int resId) {
        if (mCustomToolBar != null) {
            mCustomToolBar.setLeftText(resId);
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}