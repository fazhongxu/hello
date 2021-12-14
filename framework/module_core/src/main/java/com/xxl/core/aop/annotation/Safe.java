package com.xxl.core.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 做try catch操作，保证程序稳定性
 *
 * <pre>
 *
 *  example
 *
 *  @Safe(callBack = "onErrorCallback")
 *  private void test() {
 *      int a = 1/0;
 *  }
 *
 *  private void onErrorCallback(@Nullable final Throwable throwable) {
 *      ToastUtils.show("on error call back"+throwable.getMessage());
 *  }
 *
 *  or
 *
 *  @Safe()
 *  private void test() {
 *      int a = 1/0;
 *  }
 *
 * </pre>
 *
 * @author xxl.
 * @date 2021/7/27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Safe {

    /**
     * 回调方法
     *
     * @return
     */
    String callBack() default "";
}