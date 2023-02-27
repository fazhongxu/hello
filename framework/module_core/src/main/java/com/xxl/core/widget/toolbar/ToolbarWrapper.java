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
     * appbar
     */
    private AppBarLayout mAppBarLayout;

    /**
     * toolbar
     */
    private CustomToolBar mCustomToolBar;

    //endregion

    //region: 构造函数

    public ToolbarWrapper(@NonNull final FragmentActivity fragmentActivity) {
        mFragmentActivity = fragmentActivity;
    }

    public final static ToolbarWrapper create(@NonNull final FragmentActivity fragmentActivity) {
        return new ToolbarWrapper(fragmentActivity);
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
        // TODO: 2023/2/27 点击事件等
        mAppBarLayout = rootView.findViewById(R.id.app_bar);
        mCustomToolBar = rootView.findViewById(R.id.tool_bar);
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

    //endregion

    //region: 内部辅助方法

    //endregion

}