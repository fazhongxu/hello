package com.xxl.core;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.multidex.MultiDex;

import com.xxl.core.utils.AppUtils;
import com.xxl.core.utils.ProcessUtils;
import com.xxl.core.utils.RouterUtils;

import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public abstract class BaseApplication extends DaggerApplication {

    //region: 页面生命周期

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this, new ActivityLifecycleImpl());
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
        if (isMainProcess()) {
            initPlugins();
        }
        if (isAgreePrivacyPolicy()) {
            initPluginsAfterAgreePrivacyPolicy();
        }
    }

    /**
     * 初始化插件
     */
    @CallSuper
    protected void initPlugins() {
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

    //region: 内部辅助方法

    /**
     * 判断是否是主进程
     *
     * @return
     */
    protected boolean isMainProcess() {
        return ProcessUtils.isMainProcess();
    }

    //endregion

}