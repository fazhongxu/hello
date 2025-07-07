package com.xxl.core.aop.aspect;

import com.xxl.kit.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 缩略图Hook
 */
@Aspect
public class ImageThumbHookAspect {

    @Around("call(* com.xxl.core.image.thumb.ImageThumb.getOriginalType(..))")
    public Object getOriginalType(ProceedingJoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                String url = (String) args[0];
                LogUtils.d("getOriginalType " + url);
                if (url.contains("ccc.com")) {
                    return 2;
                }
            }
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }
}