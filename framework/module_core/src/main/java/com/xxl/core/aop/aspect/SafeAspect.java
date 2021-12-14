package com.xxl.core.aop.aspect;

import android.text.TextUtils;

import com.xxl.core.aop.annotation.Safe;
import com.xxl.core.utils.LogUtils;
import com.xxl.core.utils.ReflectUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * @author xxl.
 * @date 2021/12/1.
 */
@Aspect
public class SafeAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.core.aop.annotation.Safe * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onSafeMethod() {

    }

    @Around("onSafeMethod() && @annotation(safe)")
    public Object doSafeMethod(final ProceedingJoinPoint joinPoint, Safe safe) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            LogUtils.e(e);
            String callBack = safe.callBack();
            if (!TextUtils.isEmpty(callBack)) {
                try {
                    ReflectUtils.reflect(joinPoint.getTarget()).method(callBack, e);
                } catch (Exception e1) {
                    LogUtils.e(e1);
                }
            }
        }
        return result;
    }
}