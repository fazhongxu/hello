package com.xxl.hello.main.ui.splash;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 启动页面
 *
 * @author xxl
 * @date 2021/08/13.
 */
@Module
public abstract class SplashFragmentProvider {

    /**
     * 绑定启动页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = SplashFragmentModule.class)
    abstract SplashFragment bindSplashFragmentFactory();
}
