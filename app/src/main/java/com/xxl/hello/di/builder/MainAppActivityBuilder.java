package com.xxl.hello.di.builder;

import com.xxl.hello.ui.MainActivity;
import com.xxl.hello.ui.MainViewModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public abstract class MainAppActivityBuilder {

    @ContributesAndroidInjector(modules = MainViewModule.class)
    abstract MainActivity bindMainActivityBuilder();
}