package com.xxl.hello.main.di.builder;

import com.xxl.hello.main.ui.jump.SchemeJumpActivity;
import com.xxl.hello.main.ui.jump.SchemeJumpFragmentProvider;
import com.xxl.hello.main.ui.main.MainActivity;
import com.xxl.hello.main.ui.main.MainFragmentProvider;
import com.xxl.hello.main.ui.splash.SplashActivity;
import com.xxl.hello.main.ui.splash.SplashFragmentProvider;

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
    @ContributesAndroidInjector(modules = SplashFragmentProvider.class)
    abstract SplashActivity bindSplashActivityBuilder();

    /**
     * 绑定首页
     *
     * @return
     */
    @ContributesAndroidInjector(modules = MainFragmentProvider.class)
    abstract MainActivity bindMainActivityBuilder();

    /**
     * 绑定scheme跳转处理页
     *
     * @return
     */
    @ContributesAndroidInjector(modules = SchemeJumpFragmentProvider.class)
    abstract SchemeJumpActivity bindSchemeJumpActivityBuilder();


}