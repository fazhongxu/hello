package com.xxl.hello.widget.ui.web;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public abstract class CommonWebFragmentProvider {

    /**
     * 绑定Web页
     *
     * @return
     */
    @ContributesAndroidInjector(modules = CommonWebFragmentModule.class)
    abstract CommonWebFragment bindCommonWebFragmentFactory();
}