package com.xxl.core.widget.web;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.xxl.core.R;
import com.xxl.core.widget.swipebacklayout.SwipeBackActivity;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.RouterUtils;
import com.xxl.kit.StatusBarUtil;

/**
 * WebView 基础类
 *
 * @author xxl.
 * @date 2022/6/25.
 */
public abstract class BaseWebActivity extends SwipeBackActivity {

    //region: 成员变量

    /**
     * appbar
     */
    protected AppBarLayout mAppBar;

    /**
     * toobar
     */
    protected Toolbar mToolbar;

    /**
     * web url
     */
    protected String mUrl;

    //endregion

    //region: 页面生命周期

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_activity_web);
        if (enableInjectRouter()) {
            RouterUtils.inject(this);
        }
        setupData();
        mAppBar = findViewById(R.id.app_bar);
        mToolbar = findViewById(R.id.tool_bar);
        setupToolbarLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置数据
     */
    protected void setupData() {
        mUrl = getUrl();
    }

    /**
     * 是否允许注入路由
     *
     * @return
     */
    protected boolean enableInjectRouter() {
        return true;
    }

    /**
     * 获取加载的URL地址
     *
     * @return
     */
    protected abstract String getUrl();

    //endregion

    //region: 页面视图渲染

    /**
     * 设置标题栏布局
     */
    private void setupToolbarLayout() {
        StatusBarUtil.setDarkMode(this);
        final int statusBarHeight = StatusBarUtil.getStatusBarHeight();
        mAppBar.setPadding(DisplayUtils.dp2px(this, 10), statusBarHeight, 0, 0);
    }

    //endregion

    //region: OnWebCallBack



    //endregion

}