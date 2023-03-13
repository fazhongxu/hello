package com.xxl.core.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.xxl.core.R;
import com.xxl.core.widget.toolbar.OnToolbarProvider;
import com.xxl.core.widget.toolbar.ToolbarWrapper;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.StatusBarUtil;

/**
 * 单个带Fragment通用tool bar Activity页面
 *
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class SingleFragmentBarActivity<F extends Fragment> extends SingleFragmentActivity<F>
        implements OnToolbarProvider {

    //region: 成员变量

    /**
     * Toolbar包装类
     */
    private ToolbarWrapper mToolbarWrapper;

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
     * 获取toolbar标题
     *
     * @return
     */
    @StringRes
    protected abstract int getToolbarTitle();

    /**
     * 设置toolbar
     */
    protected void setupToolbarLayout() {
        mToolbarWrapper = ToolbarWrapper.create(this, this);
        mToolbarWrapper.setupToolbar(findViewById(R.id.ll_root_container));

        if (getToolbarTitle() != 0) {
            mToolbarWrapper.setToolbarTitle(getToolbarTitle());
        }
        StatusBarUtil.setDarkMode(this);
        final int statusBarHeight = StatusBarUtil.getStatusBarHeight();
        mToolbarWrapper.setAppbarPadding(DisplayUtils.dp2px(this, 15), statusBarHeight - DisplayUtils.dp2px(this, 15), DisplayUtils.dp2px(this, 15),0);
    }

    @Override
    public boolean isDisplayLeft() {
        return true;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setTitle(@NonNull CharSequence title) {
        if (mToolbarWrapper != null) {
            mToolbarWrapper.setToolbarTitle(title);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    @Override
    public void setTitle(@StringRes int resId) {
        if (mToolbarWrapper != null) {
            mToolbarWrapper.setToolbarTitle(resId);
        }
    }

    @Override
    public void onToolbarLeftClick(View view) {
        finish();
    }

    //endregion
}