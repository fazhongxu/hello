package com.xxl.hello.service.ui;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.core.widget.swipebacklayout.SwipeBackActivity;
import com.xxl.kit.AppUtils;
import com.xxl.kit.OnAppStatusChangedListener;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class BaseActivity extends SwipeBackActivity
     implements HasAndroidInjector {

    //region: 成员变量

    @Inject
    DispatchingAndroidInjector<Object> mAndroidInjector;

    //endregion

    //region: 页面生命周期

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (enableInjection()) {
            AndroidInjection.inject(this);
        }
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView();
        afterSetContentView();
        afterCreateVieModel();
        setupData();
        setupLayout();
        requestData();
    }

    /**
     * 获取视图资源ID
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * 创建ViewModel数据模型后
     */
    protected void afterCreateVieModel() {

    }

    /**
     * 设置数据
     */
    protected void setupData() {

    }

    /**
     * 设置页面视图
     */
    protected void setupLayout() {

    }

    /**
     * 请求数据
     */
    protected void requestData() {

    }

    /**
     * 设置contentView之前
     */
    protected void beforeSetContentView() {

    }

    /**
     * 设置页面内容布局
     */
    protected void setContentView() {

    }

    /**
     * 设置contentView 之后
     */
    protected void afterSetContentView() {

    }

    /**
     * 是否需要依赖注入
     *
     * @return
     */
    protected boolean enableInjection() {
        return true;
    }

    /**
     * 注册前后台状态切换监听
     *
     * @param listener
     */
    protected void registerAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener) {
        AppUtils.addOnAppStatusChangedListener(listener);
    }

    /**
     * 取消注册前后台状态切换监听
     *
     * @param listener
     */
    protected void unregisterAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener) {
        AppUtils.removeOnAppStatusChangedListener(listener);
    }

    //endregion

    //region: HasAndroidInjector

    @Override
    public AndroidInjector<Object> androidInjector() {
        return mAndroidInjector;
    }

    //endregion
}