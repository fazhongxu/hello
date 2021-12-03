package com.xxl.hello.core.aop.aspect;

import android.annotation.SuppressLint;

import com.xxl.hello.core.R;
import com.xxl.hello.core.aop.annotation.CheckNetwork;
import com.xxl.hello.core.utils.NetworkUtils;
import com.xxl.hello.core.utils.StringUtils;
import com.xxl.hello.core.utils.ToastUtils;
import com.xxl.hello.core.utils.Utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <pre>
 *
 * @author xxl.
 * @date 2021/12/1.
 */
@Aspect
public class CheckNetworkAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.hello.core.aop.annotation.CheckNetwork * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onCheckNetworkMethod() {

    }

    @SuppressLint("MissingPermission")
    @Around("onCheckNetworkMethod() && @annotation(checkNetwork)")
    public Object doCheckNetworkMethod(final ProceedingJoinPoint joinPoint, CheckNetwork checkNetwork) throws Throwable {
        NetworkUtils.isAvailableAsync(new Utils.Consumer<Boolean>() {
            @Override
            public void accept(Boolean isAvailable) {
                if (isAvailable) {
                    try {
                        joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                } else {
                    ToastUtils.show(StringUtils.getString(R.string.core_network_error_tips));
                }
            }
        });
        return null;
    }
}