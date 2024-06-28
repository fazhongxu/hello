package com.xxl.hello.im.di.builder;

import com.xxl.hello.im.ui.message.session.privites.PrivateChatFragmentProvider;
import com.xxl.hello.im.ui.message.session.privites.PrivateChatSessionActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2022/7/19.
 */
@Module
public abstract class IMActivityBuilder {

    /**
     * 绑定单聊页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = PrivateChatFragmentProvider.class)
    abstract PrivateChatSessionActivity bindPrivateChatSesssionActivityBuilder();

}