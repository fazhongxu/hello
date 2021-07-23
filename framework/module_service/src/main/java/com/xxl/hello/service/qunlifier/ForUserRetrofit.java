package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 用户模块Retrofit标识
 * 作用：每个模块都有单独的服务器主机地址，用户模块的主机地址标识，如果整个项目只有一个主机地址，则使用 {@link ForRetrofit}即可
 *
 * @author xxl.
 * @date 2021/7/15.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForUserRetrofit {

}
