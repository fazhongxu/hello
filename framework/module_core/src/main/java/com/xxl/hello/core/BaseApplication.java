package com.xxl.hello.core;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.xxl.hello.core.utils.AppUtils;
import com.xxl.hello.core.utils.LogUtils;
import com.xxl.hello.core.utils.RouterUtils;

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
        AppUtils.init(this, new ActivityLifecycleImpl());
        RouterUtils.init(this, isDebug());
        if (isAgreePrivacyPolicy()) {
            initPluginAfterAgreePrivacyPolicy();
        }
    }

    /**
     * 在用户统一"隐私政策"后初始化插件
     */
    public void initPluginAfterAgreePrivacyPolicy() {
        LogUtils.d("initPluginAfterAgreePrivacyPolicy");
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