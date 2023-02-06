package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * UserAgent标识
 *
 * @author xxl.
 * @date 2023/02/06.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForUserAgent {

}