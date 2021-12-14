package com.xxl.core.aop.aspect;

import com.xxl.core.aop.annotation.SingleClick;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author xxl.
 * @date 2021/12/14.
 */
@Aspect
public class SingleClickAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.core.aop.annotation.SingleClick * *(..))";

    /**
     * 最近一次点击的时间戳（毫秒）
     */
    private long sLastClickTimeMillis = 0;

    @Pointcut(POINTCUT_METHOD)
    public void onSingleClickMethod() {

    }

    @Around("onSingleClickMethod() && @annotation(singleClick)")
    public void doSingleClickMethod(final ProceedingJoinPoint joinPoint, SingleClick singleClick) throws Throwable {
        final long currentTimeMillis = System.currentTimeMillis();
        final boolean isFastClick = currentTimeMillis - sLastClickTimeMillis < singleClick.clickMillis();
        sLastClickTimeMillis = currentTimeMillis;
        // 判断快速点击
        if (isFastClick) {
            return;
        }
        // 执行原方法
        joinPoint.proceed();
    }
}