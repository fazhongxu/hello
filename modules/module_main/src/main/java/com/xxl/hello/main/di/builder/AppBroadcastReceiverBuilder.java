package com.xxl.hello.main.di.builder;

import com.xxl.hello.main.ui.widget.HelloAppWidgetProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module
public abstract class AppBroadcastReceiverBuilder {

    /**
     * 绑定小组件
     *
     * @return
     */
    @ContributesAndroidInjector
    abstract HelloAppWidgetProvider bindHelloAppWidgetProviderBuilder();
}