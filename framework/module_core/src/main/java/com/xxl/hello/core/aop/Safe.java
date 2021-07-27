package com.xxl.hello.core.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * // TODO: 2021/7/27  通过注解标记方法 实现try catch 保证程序稳定性
 *
 * @author xxl.
 * @date 2021/7/27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Safe {

}