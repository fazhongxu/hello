package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * hello上传
 *
 * @author xxl.
 * @date 2024/07/17.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForHelloUpload {

}
