package com.xxl.hellonexus.di.builder;

import com.xxl.user.di.builder.UserActivityBuilder;

import dagger.Module;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Module(includes = UserActivityBuilder.class)
public abstract class AppActivityBuilder {

}