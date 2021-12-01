package com.xxl.hello.core.aop.aspect;

import android.text.TextUtils;

import com.xxl.hello.core.aop.annotation.Safe;
import com.xxl.hello.core.utils.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author xxl.
 * @date 2021/12/1.
 */
@Aspect
public class SafeAspect {

    private static final String POINTCUT_METHOD = "execution(@com.test.core.aop.annotation.Safe * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onSafeMethod() {

    }

    @Around("onSafeMethod() && @annotation(safe)")
    public Object doSafeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Safe safe = method.getAnnotation(Safe.class);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            LogUtils.e(e);
            String callBack = safe.callBack();
            if (!TextUtils.isEmpty(callBack)) {
//                try {
//                    Reflect.on(joinPoint.getTarget()).call(callBack);
//                } catch (ReflectException exception) {
//                    exception.printStackTrace();
//                    L.e("no method "+callBack);
//                }
//                ReflectUtils.reflect()
            }

//            if (Preconditions.isNotBlank(callBack)) {
//
//                try {
//                    Reflect.on(joinPoint.getTarget()).call(callBack);
//                } catch (ReflectException exception) {
//                    exception.printStackTrace();
//                    L.e("no method " + callBack);
//                }
//            }
        }
        return result;
    }

}