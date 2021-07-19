package com.xxl.hello.user.di.builder;

import com.xxl.hello.user.ui.LoginActivity;
import com.xxl.hello.user.ui.LoginActivityViewModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public abstract class UserActivityBuilder {

//    @ContributesAndroidInjector(modules = {LoginActivityProvider.class})
//    abstract LoginActivity bindLoginActivityBuilder();

    @ContributesAndroidInjector(modules = {LoginActivityViewModule.class})
    abstract LoginActivity bindLoginActivityBuilder();
}