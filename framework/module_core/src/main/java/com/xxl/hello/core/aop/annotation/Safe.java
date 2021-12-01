package com.xxl.hello.core.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xxl.
 * @date 2021/7/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Safe {

    /**
     * 回调方法
     *
     * @return
     */
    String callBack() default "";
}