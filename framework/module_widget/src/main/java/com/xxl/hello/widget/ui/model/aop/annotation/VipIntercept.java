package com.xxl.hello.widget.ui.model.aop.annotation;

import com.xxl.hello.service.data.model.enums.UserEnumsApi.VipModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VIP拦截
 *
 * @author xxl.
 * @date 2023/7/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VipIntercept {

    /**
     * VIP模块
     *
     * @return
     */
    String vipModel() default VipModel.USER;

    /**
     * 功能ID
     *
     * @return
     */
    long functionId() default 0;
}