package com.xxl.user.di.builder;

import com.xxl.user.ui.LoginActivity;
import com.xxl.user.ui.LoginActivityProvider;
import com.xxl.user.ui.LoginActivityViewModule;

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