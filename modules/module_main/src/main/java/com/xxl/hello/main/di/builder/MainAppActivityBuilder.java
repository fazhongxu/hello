package com.xxl.hello.main.di.builder;

import com.xxl.hello.main.ui.main.MainActivity;
import com.xxl.hello.main.ui.main.MainViewModule;
import com.xxl.hello.main.ui.splash.SplashActivity;
import com.xxl.hello.main.ui.splash.SplashViewModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public abstract class MainAppActivityBuilder {

    /**
     * 绑定启动页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = SplashViewModule.class)
    abstract SplashActivity bindSplashActivityBuilder();

    /**
     * 绑定首页
     *
     * @return
     */
    @ContributesAndroidInjector(modules = MainViewModule.class)
    abstract MainActivity bindMainActivityBuilder();
}