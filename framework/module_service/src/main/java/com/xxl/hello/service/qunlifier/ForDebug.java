package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 是否是debug模式标识
 *
 * @author xxl.
 * @date 2021/7/27.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForDebug {

}