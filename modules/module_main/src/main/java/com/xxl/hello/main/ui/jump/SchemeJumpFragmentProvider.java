package com.xxl.hello.main.ui.jump;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2022/8/11.
 */
@Module
public abstract class SchemeJumpFragmentProvider {

    /**
     * 绑定首页
     *
     * @return
     */
    @ContributesAndroidInjector(modules = SchemeJumpFragmentModule.class)
    abstract SchemeJumpFragment bindSchemeJumpFragmentFactory();
}