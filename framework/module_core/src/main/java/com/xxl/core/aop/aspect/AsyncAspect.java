package com.xxl.core.aop.aspect;

import com.xxl.core.aop.annotation.Async;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 作用在需要异步方法上，让此方法在异步线程执行
 *
 * @author xxl.
 * @date 2022/6/2.
 */
@Aspect
@DeclarePrecedence("com.xxl.core.aop.aspect.SafeAspect,*")
public class AsyncAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.core.aop.annotation.Async * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onAsyncMethod() {

    }

    @Around("onAsyncMethod() && @annotation(async)")
    public Object doAsyncMethod(final ProceedingJoinPoint joinPoint, Async async) throws Throwable {
        final Disposable disposable = Observable.just(new Object())
                .subscribeOn(Schedulers.io())
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object obj) throws Throwable {
                        joinPoint.proceed();
                        return obj;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {

                    }
                }, throwable -> {

                });

        return null;
    }
}