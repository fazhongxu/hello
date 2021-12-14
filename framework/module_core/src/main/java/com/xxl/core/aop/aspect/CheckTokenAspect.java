//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xxl.core.aop.aspect;

import android.text.TextUtils;
import android.util.Log;

import com.xxl.core.aop.annotation.CheckToken;
import com.xxl.core.listener.IApplication;
import com.xxl.core.utils.AppUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CheckTokenAspect {
    private static final String POINTCUT_METHOD = "execution(@com.xxl.core.aop.annotation.CheckToken * *(..))";

    @Pointcut("execution(@com.xxl.core.aop.annotation.CheckToken * *(..))")
    public void onCheckTokenMethod() {
    }

    @Around("onCheckTokenMethod() && @annotation(checkToken)")
    public void doCheckTokenMethod(ProceedingJoinPoint joinPoint, CheckToken checkToken) throws Throwable {
        if (AppUtils.getApplication() instanceof IApplication) {
            IApplication application = (IApplication) AppUtils.getApplication();
            if (TextUtils.isEmpty(application.getToken())) {
                Log.e("aaa", "doCheckTokenMethod: 没有token 去登录吧！");
                return;
            }
        }
        joinPoint.proceed();
    }
}
