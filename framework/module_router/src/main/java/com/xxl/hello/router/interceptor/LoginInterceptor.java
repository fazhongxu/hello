package com.xxl.hello.router.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.xxl.kit.LogUtils;

/**
 * 登录拦截器
 *
 * @author xxl.
 * @date 2022/8/4.
 */
@Interceptor(priority = 1, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        LogUtils.d("LoginInterceptor process" + postcard.getDestination());
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        LogUtils.d("LoginInterceptor init");
    }
}