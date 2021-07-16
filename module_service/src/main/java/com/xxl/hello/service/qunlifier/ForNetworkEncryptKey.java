package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 网络请求加密key标识
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForNetworkEncryptKey {

}