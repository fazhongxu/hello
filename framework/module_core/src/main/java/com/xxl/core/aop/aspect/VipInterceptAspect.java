package com.xxl.core.aop.aspect;

import android.app.Activity;
import android.text.TextUtils;

import com.xxl.core.R;
import com.xxl.core.aop.annotation.VipIntercept;
import com.xxl.core.utils.AppExpandUtils;
import com.xxl.core.widget.window.VipInterceptPopupWindow;
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

    private static final String POINTCUT_METHOD = "execution(@com.xxl.core.aop.annotation.VipIntercept * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void onVipInterceptMethod() {

    }

    @Around("onVipInterceptMethod() && @annotation(vipIntercept)")
    public void doVipInterceptMethod(final ProceedingJoinPoint joinPoint, VipIntercept vipIntercept) throws Throwable {
        final String vipModel = vipIntercept.vipModel();
        final long functionId = vipIntercept.functionId();
        if (TextUtils.isEmpty(vipModel) || functionId <= 0) {
            joinPoint.proceed();
            return;
        }
        final boolean isVip = AppExpandUtils.isVip();
        LogUtils.d("点击了功能 functionId" + functionId + "--isVip " + isVip);
        if (isVip) {
            joinPoint.proceed();
            return;
        }
        final Activity topActivity = AppUtils.getTopActivity();
        if (topActivity == null) {
            return;
        }
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
    }
}