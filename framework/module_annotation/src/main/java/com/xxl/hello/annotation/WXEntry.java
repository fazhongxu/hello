package com.xxl.hello.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生成微信回调页面的注解(解决微信回调必须写在项目，且建立微信的包名问题）
 * 任意找一个类，加上此注解，编译下，在应用的包名下面就会创建微信回调页
 *
 * @author xxl.
 * @date 2022/8/17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WXEntry {

    /**
     * 生成文件的包名
     *
     * @return
     */
    String packageName();

    /**
     * 自定义微信回调类名（全路径）
     * 生成的微信回调类回继承这个类，这个类必须是处理微信回调的Activity
     *
     * @return
     */
    String parentClassName();

}
