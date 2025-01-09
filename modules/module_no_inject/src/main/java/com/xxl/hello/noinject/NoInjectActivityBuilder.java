package com.xxl.hello.noinject;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NoInjectActivityBuilder {

    @ContributesAndroidInjector
    abstract NoInjectFragment contributeNoInjectFragment();
}