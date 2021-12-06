package com.xxl.hello.core;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.multidex.MultiDex;

import com.xxl.hello.core.listener.IApplication;
import com.xxl.hello.core.utils.AppUtils;
import com.xxl.hello.core.utils.RouterUtils;

import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public abstract class BaseApplication extends DaggerApplication{

    //region: 页面生命周期

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化
     */
    private void init() {
        initPlugins();
        if (isAgreePrivacyPolicy()) {
            initPluginsAfterAgreePrivacyPolicy();
        }
    }

    /**
     * 初始化插件
     */
    @CallSuper
    protected void initPlugins() {
        AppUtils.init(this, new ActivityLifecycleImpl());
        RouterUtils.init(this, isDebug());
    }

    /**
     * 在用户统一"隐私政策"后初始化插件
     */
    public void initPluginsAfterAgreePrivacyPolicy() {

    }

    //endregion

    //region: 提供方法

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    public abstract boolean isAgreePrivacyPolicy();

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public abstract String getCurrentUserId();

    /**
     * 是否是debug模式
     *
     * @return
     */
    public abstract boolean isDebug();

    /**
     * 网络环境是否是debug模式
     *
     * @return
     */
    public abstract boolean isNetworkDebug();

    //endregion

}