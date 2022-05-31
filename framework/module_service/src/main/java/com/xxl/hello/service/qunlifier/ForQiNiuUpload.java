package com.xxl.hello.service.qunlifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 七牛上传
 *
 * @author xxl.
 * @date 2022/05/30.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForQiNiuUpload {

}
