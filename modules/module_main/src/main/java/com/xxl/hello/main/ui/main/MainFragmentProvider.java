package com.xxl.hello.main.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2022/7/28.
 */
@Module
public abstract class MainFragmentProvider {

    /**
     * 绑定首页
     *
     * @return
     */
    @ContributesAndroidInjector(modules = MainFragmentModule.class)
    abstract MainFragment bindMainFragmentFactory();
}

 //TODO: 2022/7/28 Fragment dagger注入失败，待解决