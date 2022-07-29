package com.xxl.hello.service.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.xxl.core.widget.CustomToolBar;
import com.xxl.hello.service.R;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.StatusBarUtil;

/**
 * 单个带r的Fragment带通用tool bar Activity页面
 *
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class SingleFragmentBarActivity<F extends Fragment> extends SingleFragmentActivity<F> {

    //region: 成员变量

    /**
     * app bar
     */
    private AppBarLayout mAppBarLayout;

    /**
     * tool bar
     */
    private CustomToolBar mCustomToolBar;

    //endregion

    //region: 页面生命周期


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbarLayout();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.core_activity_single_fragment_bar;
    }

    /**
     * 设置数据
     */
    @Override
    protected void setupData() {

    }

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {

    }

    /**
     * 设置toolbar
     */
    protected void setupToolbarLayout() {
        mAppBarLayout = findViewById(R.id.app_bar);
        mCustomToolBar = findViewById(R.id.tool_bar);
        StatusBarUtil.setDarkMode(this);
        final int statusBarHeight = StatusBarUtil.getStatusBarHeight();
        mAppBarLayout.setPadding(DisplayUtils.dp2px(this, 10), statusBarHeight, 0, 0);

        if (getToolbarTitle() != 0) {
            mCustomToolBar.setTitle(getToolbarTitle());
        }
    }

    /**
     * 获取toolbar标题
     *
     * @return
     */
    @StringRes
    protected abstract int getToolbarTitle();

    //endregion
}