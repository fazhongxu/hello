package com.xxl.core;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.alipictures.statemanager.loader.StateRepository;
import com.evernote.android.state.StateSaver;
import com.scottyab.aescrypt.AESCrypt;
import com.xxl.core.manager.ExceptionServiceManager;
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
import com.xxl.core.utils.ShareUtils;

import dagger.android.DaggerApplication;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

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
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true);
        RouterUtils.init(this, isDebug());
        ShareUtils.preInit(this, getShareAppKey(), getChannel(), isDebug());
        RxJavaPlugins.setErrorHandler(ExceptionServiceManager::postCaughtException);
    }

    /**
     * 注册页面视图状态
     */
    private void registerState() {
        StateRepository.registerState(EmptyState.STATE, EmptyState.class);
        StateRepository.registerState(LoadingState.STATE, LoadingState.class);
        StateRepository.registerState(ExceptionState.STATE, ExceptionState.class);
        StateRepository.registerState(NetworkExceptionState.STATE, NetworkExceptionState.class);
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
        ShareUtils.init(this, getShareAppKey(), getShareSecret(), getChannel(), isDebug());
    }

    //endregion

    //region: 提供方法

    /**
     * 退出登录
     */
    public void logout() {

    }

    /**
     * 服务端的加密key
     */
    private String mRemoteEncryptKey;

    /**
     * 本地的加密key
     */
    private String mLocalEncryptKey;

    /**
     * 获取服务端的加密key
     *
     * @return
     */
    public String getRemoteEncryptKey() {
        synchronized (this) {
            if (mRemoteEncryptKey == null) {
                mRemoteEncryptKey = buildEncryptKey(getRemoteEncryptKeyString());
            }
            return mRemoteEncryptKey;
        }
    }

    /**
     * 获取本地的加密key
     *
     * @return
     */
    public String getLocalEncryptKey() {
        synchronized (this) {
            if (mLocalEncryptKey == null) {
                mLocalEncryptKey = buildEncryptKey(getLocalEncryptKeyString());
            }
            return mLocalEncryptKey;
        }
    }

    /**
     * 构建加密key
     *
     * @param encryptKey
     * @return
     */
    public String buildEncryptKey(@NonNull final String encryptKey) {
        try {
            return AESCrypt.decrypt(AppUtils.getAppSignaturesMD5String(), encryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取服务端的加密key
     *
     * @return
     */
    public abstract String getRemoteEncryptKeyString();

    /**
     * 获取本地加密的key
     *
     * @return
     */
    public abstract String getLocalEncryptKeyString();

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