package com.xxl.hello.user.di.builder;

import com.xxl.hello.user.ui.login.LoginActivity;
import com.xxl.hello.user.ui.login.LoginFragmentProvider;
import com.xxl.hello.user.ui.setting.UserSettingActivity;
import com.xxl.hello.user.ui.setting.UserSettingFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public abstract class UserActivityBuilder {

    @ContributesAndroidInjector(modules = {LoginFragmentProvider.class})
    abstract LoginActivity bindLoginActivityBuilder();

    @ContributesAndroidInjector(modules = {UserSettingFragmentProvider.class})
    abstract UserSettingActivity bindUserSettingActivityBuilder();
}