package com.xxl.core.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查是否登录
 *
 * @author xxl.
 * @date 2021/12/3.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckLogin {

    /**
     * 页面请求码,默认可以不传（如果页面有多处登录且登录后要做进一步的业务，
     * 则传对应请求码，以便在onActivityResult区分进一步做对应的业务）
     *
     * @return
     */
    int requestCode() default 0;
}

