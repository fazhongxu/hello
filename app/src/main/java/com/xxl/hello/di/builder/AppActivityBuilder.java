package com.xxl.hello.di.builder;

import com.xxl.hello.user.di.builder.UserActivityBuilder;

import dagger.Module;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module(includes = UserActivityBuilder.class)
public abstract class AppActivityBuilder {

}