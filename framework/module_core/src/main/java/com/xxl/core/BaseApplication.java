package com.xxl.core;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.multidex.MultiDex;

import com.alipictures.statemanager.loader.StateRepository;
import com.evernote.android.state.StateSaver;
import com.xxl.core.ui.state.EmptyState;
import com.xxl.core.ui.state.ExceptionState;
import com.xxl.core.ui.state.LoadingState;
import com.xxl.core.ui.state.NetworkExceptionState;
import com.xxl.core.ui.state.RequestErrorState;
import com.xxl.core.ui.state.UnKnowExceptionState;
import com.xxl.core.utils.CacheUtils;
import com.xxl.core.widget.swipebacklayout.SwipeBackActivityManager;
import com.xxl.kit.AppUtils;
import com.xxl.kit.ProcessUtils;
import com.xxl.kit.RouterUtils;
import com.xxl.kit.ShareUtils;

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
        CacheUtils.init(this, isDebug());
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
        SwipeBackActivityManager.init(this);
        registerState();
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this,true);
        RouterUtils.init(this, isDebug());
        ShareUtils.preInit(this, getShareAppKey(), getChannel(), isDebug());
    }

    /**
     * 注册页面视图状态
     */
    private void registerState() {
        StateRepository.registerState(EmptyState.STATE,EmptyState.class);
        StateRepository.registerState(LoadingState.STATE,LoadingState.class);
        StateRepository.registerState(ExceptionState.STATE,ExceptionState.class);
        StateRepository.registerState(NetworkExceptionState.STATE,NetworkExceptionState.class);
        StateRepository.registerState(UnKnowExceptionState.STATE, UnKnowExceptionState.class);
        StateRepository.registerState(RequestErrorState.STATE, RequestErrorState.class);
    }

    /**
     * 在用户统一"隐私政策"后初始化插件
     */
    public void initPluginsAfterAgreePrivacyPolicy() {
        setupShare();
    }

    /**
     * 设置分享相关
     */
    public void setupShare() {
        ShareUtils.init(this,getShareAppKey(),getShareSecret(),getChannel(),isDebug());
    }

    //endregion

    //region: 提供方法

    /**
     * 获取分享 appkey
     *
     * @return
     */
    public abstract String getShareAppKey();

    /**
     * 获取分享Secret
     *
     * @return
     */
    public abstract String getShareSecret();

    /**
     * 获取分享渠道
     *
     * @return
     */
    public abstract String getChannel();

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