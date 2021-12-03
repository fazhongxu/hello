package com.xxl.hello.core.aop.aspect;

import com.xxl.hello.core.BaseApplication;
import com.xxl.hello.core.aop.annotation.CheckLogin;
import com.xxl.hello.core.data.router.AppRouterApi;
import com.xxl.hello.core.utils.AppUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author xxl.
 * @date 2021/12/03.
 */
@Aspect
public class CheckLoginAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.hello.core.aop.annotation.CheckLogin * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onCheckLoginMethod() {

    }

    @Around("onCheckLoginMethod() && @annotation(checkLogin)")
    public Object doCheckLoginMethod(final ProceedingJoinPoint joinPoint, CheckLogin checkLogin) throws Throwable {
        if (AppUtils.getApplication() instanceof BaseApplication) {
            final BaseApplication application = (BaseApplication) AppUtils.getApplication();
            if (application.isLogin()) {
                return joinPoint.proceed();
            }
            AppRouterApi.navigationToLogin();
        }
        return joinPoint.proceed();
    }
}