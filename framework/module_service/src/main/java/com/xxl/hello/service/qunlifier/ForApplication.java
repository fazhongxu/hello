package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 上下文标识
 *
 * @author xxl.
 * @date 2021/7/15.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApplication {

}
