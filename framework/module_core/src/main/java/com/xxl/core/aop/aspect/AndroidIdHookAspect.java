package com.xxl.core.aop.aspect;

import android.provider.Settings;
import android.util.Log;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;

import java.util.Arrays;

@Aspect
public class AndroidIdHookAspect {

    // 拦截 Settings.Secure.getString 方法调用
    @Before("call(* android.provider.Settings.Secure.getString(..))")
    public void beforeSecureGetString(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length >= 2 && args[1] != null && args[1].equals(Settings.Secure.ANDROID_ID)) {
            Log.d("AndroidIdHook", "获取 Android ID 的调用堆栈：" + Arrays.toString(Thread.currentThread().getStackTrace()));
            // 可以在这里进行拦截或修改返回值
        }
    }
}