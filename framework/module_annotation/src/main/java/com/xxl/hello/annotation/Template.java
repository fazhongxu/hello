package com.xxl.hello.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义模板代码生成注解
 *
 * @author xxl.
 * @date 2022/8/29.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Template {

    /**
     * 作者
     *
     * @return
     */
    String author() default "";

    /**
     * 功能描述
     *
     * @return
     */
    String description() default "";

    /**
     * 包名
     *
     * @return
     */
    String packageName();

    /**
     * 生成的功能类名
     *
     * @return
     */
    String name();


}