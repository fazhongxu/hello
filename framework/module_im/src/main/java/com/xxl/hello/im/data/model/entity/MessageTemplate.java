package com.xxl.hello.im.data.model.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息模板注解
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface MessageTemplate {

    /**
     * 消息模板类型
     *
     * @return
     */
    @MessageTemplateType
    String templateType();
}