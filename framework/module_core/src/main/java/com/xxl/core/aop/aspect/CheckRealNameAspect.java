//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xxl.core.aop.aspect;

import android.text.TextUtils;
import android.util.Log;

import com.xxl.core.aop.annotation.CheckRealName;
import com.xxl.core.listener.IApplication;
import com.xxl.core.utils.AppUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@DeclarePrecedence("com.xxl.core.aop.aspect.CheckTokenAspect,*")
public class CheckRealNameAspect {
    private static final String POINTCUT_METHOD = "execution(@com.xxl.core.aop.annotation.CheckRealName * *(..))";

    @Pointcut("execution(@com.xxl.core.aop.annotation.CheckRealName * *(..))")
    public void onCheckRealNameMethod() {
    }

    @Around("onCheckRealNameMethod() && @annotation(checkRealName)")
    public void doCheckRealNameMethod(ProceedingJoinPoint joinPoint, CheckRealName checkRealName) throws Throwable {
        if (AppUtils.getApplication() instanceof IApplication) {
            IApplication application = (IApplication) AppUtils.getApplication();
            if (TextUtils.isEmpty(application.getRealName())) {
                Log.e("aaa", "doCheckTokenMethod: 未实名制度 去实名吧！");
                return;
            }
        }

        joinPoint.proceed();
    }
}
