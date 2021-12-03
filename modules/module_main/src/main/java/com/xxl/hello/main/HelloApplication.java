package com.xxl.hello.main;

import android.content.Context;
import android.text.TextUtils;

import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.xxl.hello.core.BaseApplication;
import com.xxl.hello.core.config.NetworkConfig;
import com.xxl.hello.core.data.router.AppRouterApi;
import com.xxl.hello.core.image.selector.MediaSelector;
import com.xxl.hello.core.image.selector.MediaSelectorApp;
import com.xxl.hello.core.image.selector.PictureSelectorEngineImpl;
import com.xxl.hello.core.utils.CacheUtils;
import com.xxl.hello.core.utils.LogUtils;
import com.xxl.hello.core.widget.swipebacklayout.SwipeBackActivityManager;
import com.xxl.hello.main.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2020/8/20.
 */
public class HelloApplication extends BaseApplication implements MediaSelectorApp {

    //region: 成员变量

    /**
     * 网络环境是否是debug模式，上线必须改为false
     */
    private static final boolean sIsDebug = true;

    /**
     * Application 包装类
     */
    @Inject
    HelloApplicationWrapper mApplicationWrapper;

    //endregion

    //region: 页面生命周期

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    @Override
    public boolean isAgreePrivacyPolicy(){
        return mApplicationWrapper.isAgreePrivacyPolicy();
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    @Override
    public String getCurrentUserId() {
        return mApplicationWrapper.getCurrentUserId();
    }

    /**
     * 是否是debug模式
     *
     * @return
     */
    @Override
    public boolean isDebug() {
        return sIsDebug;
    }

    /**
     * 是否登录
     *
     * @return
     */
    @Override
    public boolean isLogin() {
        return !TextUtils.isEmpty(getCurrentUserId());
    }

    /**
     * 跳转到登录
     */
    @Override
    public void navigationToLogin() {
        AppRouterApi.navigationToLogin();
    }

    /**
     * 网络环境是否是debug模式
     *
     * @return
     */
    @Override
    public boolean isNetworkDebug() {
        return NetworkConfig.isNetworkDebug();
    }

    //endregion

    //region: 组件初始化操作

    /**
     * 初始化操作
     */
    private void init() {
        mApplicationWrapper.init(this);
    }

    /**
     * 初始化组件
     */
    @Override
    public void initPlugins() {
        super.initPlugins();
        CacheUtils.init(this);
        LogUtils.init(NetworkConfig.isDebug());
        MediaSelector.init(this);
        SwipeBackActivityManager.init(this);
    }

    /**
     * 在用户统一"隐私政策"后初始化插件
     */
    @Override
    public void initPluginsAfterAgreePrivacyPolicy() {
        LogUtils.d("initPluginAfterAgreePrivacyPolicy");
    }

    //endregion

    //region: MediaSelectorApp

    /**
     * Application
     *
     * @return
     */
    @Override
    public Context getAppContext() {
        return this;
    }

    /**
     * PictureSelectorEngine
     *
     * @return
     */
    @Override
    public PictureSelectorEngine getPictureSelectorEngine() {
        return new PictureSelectorEngineImpl();
    }

    //endregion
}
