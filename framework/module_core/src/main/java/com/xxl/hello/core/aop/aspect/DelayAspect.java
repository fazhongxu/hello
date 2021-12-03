package com.xxl.hello.core.aop.aspect;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.xxl.hello.core.aop.annotation.Delay;
import com.xxl.hello.core.rx.SchedulersProvider;
import com.xxl.hello.core.utils.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * <pre>
 *  example
 *
 *  @Delay(delay = 600)
 *  public void onTestClick() {
 *
 *  }
 * </pre>
 *
 * @author xxl.
 * @date 2021/12/02.
 */
@Aspect
public class DelayAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.hello.core.aop.annotation.Delay * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onDelayMethod() {

    }

    @Around("onDelayMethod() && @annotation(delay)")
    public Object doDelayMethod(final ProceedingJoinPoint joinPoint, Delay delay) throws Throwable {
        Object result = null;

        long delayMills = delay.delay();
        if (delayMills > 0) {

            final Disposable disposable = Observable.timer(delayMills, TimeUnit.MILLISECONDS)
                    .compose(SchedulersProvider.applySchedulers())
                    .subscribe(new Consumer<Long>() {
                        //Aspect 不支持lambda表达式
                        @Override
                        public void accept(Long aLong) throws Throwable {
                            final Object object = joinPoint.getThis();
                            if (object instanceof Activity) {
                                if (((Activity) object).isFinishing()) {
                                    return;
                                }
                            } else if (object instanceof Fragment) {
                                final Fragment fragment = (Fragment) object;
                                if (fragment.getActivity() == null || fragment.getActivity().isFinishing()) {
                                    return;
                                }
                            }
                            joinPoint.proceed();
                        }
                    }, throwable -> {
                        LogUtils.e(throwable);
                    });
        } else {
            result = joinPoint.proceed();
        }

        return result;
    }
}