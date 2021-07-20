package com.xxl.hello;

import com.xxl.hello.common.utils.CacheUtils;
import com.xxl.hello.common.utils.AppUtils;
import com.xxl.hello.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2020/8/20.
 */
public class HelloApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        initPlugins();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    /**
     * 初始化组件
     */
    private void initPlugins() {
        CacheUtils.initialize(this);
    }
}
