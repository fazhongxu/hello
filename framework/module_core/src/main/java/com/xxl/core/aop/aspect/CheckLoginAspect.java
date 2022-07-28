package com.xxl.core.aop.aspect;

import com.xxl.core.aop.annotation.CheckLogin;
import com.xxl.core.listener.IApplication;
import com.xxl.kit.AppUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author xxl.
 * @date 2021/12/03.
 */
@Aspect
@DeclarePrecedence("com.xxl.core.aop.aspect.SafeAspect,*")
public class CheckLoginAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.core.aop.annotation.CheckLogin * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onCheckLoginMethod() {

    }

    @Around("onCheckLoginMethod() && @annotation(checkLogin)")
    public Object doCheckLoginMethod(final ProceedingJoinPoint joinPoint, CheckLogin checkLogin) throws Throwable {
        if (AppUtils.getApplication() instanceof IApplication) {
            final IApplication application = (IApplication) AppUtils.getApplication();
            if (application.isLogin()) {
                return joinPoint.proceed();
            }
            application.navigationToLogin(checkLogin.requestCode());
            return null;
        }
        return joinPoint.proceed();
    }
}