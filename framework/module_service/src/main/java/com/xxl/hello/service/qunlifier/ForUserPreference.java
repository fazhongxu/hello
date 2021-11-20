package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @Author: xxl
 * @Date: 2021/11/21 12:09 AM
 **/
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForUserPreference {

}