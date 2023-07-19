package com.xxl.hello.widget.ui.model.aop.aspect;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xxl.core.R;
import com.xxl.core.utils.AppExpandUtils;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.ui.listener.OnVipInterceptListener;
import com.xxl.hello.widget.ui.model.aop.annotation.VipIntercept;
import com.xxl.hello.widget.ui.window.VipInterceptPopupWindow;
import com.xxl.kit.AppUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.ToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * VIP拦截
 *
 * @author xxl.
 * @date 2023/7/13.
 */
@Aspect
public class VipInterceptAspect {

    private static final String POINTCUT_METHOD = "execution(@com.xxl.hello.widget.ui.model.aop.annotation.VipIntercept * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onVipInterceptMethod() {

    }

    @Around("onVipInterceptMethod() && @annotation(vipIntercept)")
    public void doVipInterceptMethod(@NonNull final ProceedingJoinPoint joinPoint,
                                     @NonNull final VipIntercept vipIntercept) throws Throwable {
        final Activity topActivity = AppUtils.getTopActivity();
        final String vipModel = vipIntercept.vipModel();
        final long functionId = vipIntercept.functionId();
        if (TextUtils.isEmpty(vipModel) || functionId <= 0 || topActivity == null) {
            joinPoint.proceed();
            return;
        }
        final boolean isVip = AppExpandUtils.isVip();
        LogUtils.d("点击了功能 functionId" + functionId + "--isVip " + isVip);
        if (isVip) {
            joinPoint.proceed();
            return;
        }
        final Object target = joinPoint.getTarget();
        if (target instanceof OnVipInterceptListener) {
            final OnVipInterceptListener vipInterceptListener = (OnVipInterceptListener) target;
            final DataRepositoryKit dataRepositoryKit = vipInterceptListener.getDataRepositoryKit();
            if (dataRepositoryKit != null) {
                final VipInterceptPopupWindow.OnVipInterceptPopupWindowListener listener = new VipInterceptPopupWindow.OnVipInterceptPopupWindowListener() {
                    @Override
                    public void onOpenVipClick() {
                        ToastUtils.success(R.string.core_clicked_open_vip_text).show();
                    }

                    @Override
                    public void onVerifyComplete(boolean isSuccess) {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                };
                VipInterceptPopupWindow.from(topActivity, listener, vipModel, functionId)
                        .show();
                return;
            }
        }
        joinPoint.proceed();
    }
}