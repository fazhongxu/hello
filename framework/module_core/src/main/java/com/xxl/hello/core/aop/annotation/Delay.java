package com.xxl.hello.core.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xxl.
 * @date 2021/12/02.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Delay {

    /**
     * 延迟时间（毫秒）
     *
     * @return
     */
    long delay() default 0L;
}