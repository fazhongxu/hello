package com.xxl.core.aop.aspect;

import android.text.TextUtils;

import com.xxl.core.aop.annotation.LogTag;
import com.xxl.kit.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * AOP打印log工具
 *
 * @author xxl.
 * @date 2022/5/13.
 */
@Aspect
public class LogTagAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.core.aop.annotation.LogTag * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onLogTagMethod() {

    }

    @Around("onLogTagMethod() && @annotation(logTag)")
    public Object doLogTagMethod(final ProceedingJoinPoint joinPoint, LogTag logTag) throws Throwable {
        Object result = joinPoint.proceed();
        if (logTag.level() == 0) {
            if (!TextUtils.isEmpty(logTag.tag())) {
                LogUtils.d(logTag.tag() + " %s", logTag.message());
            } else {
                LogUtils.d(logTag.message());
            }
        }
        return result;
    }
}