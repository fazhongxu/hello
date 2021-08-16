package com.xxl.hello.main.di.builder;

import com.xxl.hello.user.di.builder.UserActivityBuilder;

import dagger.Module;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module(includes = {MainAppActivityBuilder.class, UserActivityBuilder.class})
public abstract class AppActivityBuilder {

}