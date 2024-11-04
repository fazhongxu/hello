package com.xxl.hello.main.di.builder;

import com.xxl.hello.im.di.builder.IMActivityBuilder;
import com.xxl.hello.user.di.builder.UserActivityBuilder;
import com.xxl.hello.widget.di.builder.WidgetActivityBuilder;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@InstallIn(SingletonComponent.class)
@Module(includes = {WidgetActivityBuilder.class,
        IMActivityBuilder.class,
        MainAppActivityBuilder.class,
        UserActivityBuilder.class})
@Deprecated
public abstract class AppActivityBuilder {

}