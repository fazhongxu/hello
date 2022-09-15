package com.xxl.hello.user.ui.login;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public abstract class LoginFragmentProvider {

    /**
     * 绑定首页
     *
     * @return
     */
    @ContributesAndroidInjector(modules = LoginFragmentModule.class)
    abstract LoginFragment bindLoginFragmentFactory();
}