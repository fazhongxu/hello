package com.xxl.hello.user.ui.setting;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public abstract class UserSettingFragmentProvider {

    /**
     * 用户设置页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = UserSettingFragmentModule.class)
    abstract UserSettingFragment bindUserSettingFragmentFactory();
}