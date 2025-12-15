package com.xxl.hello.widget.ui.im.message.session.privites;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2024/6/28.
 */
@Module
public abstract class PrivateChatSessionFragmentProvider {

    /**
     * 绑定单聊页
     *
     * @return
     */
    @ContributesAndroidInjector(modules = PrivateChatSessionFragmentModule.class)
    abstract PrivateChatSessionFragment bindPrivateChatSessionFragmentFactory();
}