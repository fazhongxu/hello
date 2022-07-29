package com.xxl.hello.main.di.builder;

import com.xxl.hello.user.di.builder.UserActivityBuilder;
import com.xxl.hello.widget.di.builder.WidgetActivityBuilder;

import dagger.Module;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module(includes = {WidgetActivityBuilder.class,
        MainAppActivityBuilder.class,
        UserActivityBuilder.class})
public abstract class AppActivityBuilder {

}