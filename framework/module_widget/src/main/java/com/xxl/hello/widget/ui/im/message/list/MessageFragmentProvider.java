package com.xxl.hello.widget.ui.im.message.list;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 消息列表
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@Module
public abstract class MessageragmentProvider {

    /**
     * 绑定消息列表
     *
     * @return
     */
    @ContributesAndroidInjector(modules = MessageFragmentModule.class)
    abstract MessageFragment bindMessageFragmentFactory();
}