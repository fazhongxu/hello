package com.xxl.hello;

import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.common.utils.CacheUtils;
import com.xxl.hello.common.utils.LogUtils;
import com.xxl.hello.di.component.DaggerAppComponent;
import com.xxl.hello.service.BaseApplication;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2020/8/20.
 */
public class HelloApplication extends BaseApplication {

    //region: 成员变量

    /**
     * Application 包装类
     */
    @Inject
    HelloApplicationWrapper mApplicationWrapper;

    //endregion

    //region: 页面生命周期

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    @Override
    public String getCurrentUserId() {
        return mApplicationWrapper.getCurrentUserId();
    }

    /**
     * 是否是debug模式
     *
     * @return
     */
    @Override
    public boolean isNetworkDebug() {
        return NetworkConfig.isNetworkDebug();
    }

    //endregion

    //region: 组件初始化操作

    /**
     * 初始化操作
     */
    private void init() {
        mApplicationWrapper.init(this);
        initPlugins();
    }

    /**
     * 初始化组件
     */
    private void initPlugins() {
        CacheUtils.init(this);
        LogUtils.init(isNetworkDebug());
    }

    //endregion
}
