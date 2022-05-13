package com.xxl.core.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xxl.
 * @date 2021/7/27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogTag {

    /**
     * log 的 tag标识
     *
     * @return
     */
    String tag() default "";

    /**
     * 打印的log等级 默认 0 ->log d
     *
     * @return
     */
    int level() default 0;

    /**
     * 要打印的消息
     *
     * @return
     */
    String message() default "";
}