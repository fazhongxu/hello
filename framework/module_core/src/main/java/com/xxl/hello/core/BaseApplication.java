package com.xxl.hello.core;

import com.xxl.hello.core.utils.AppUtils;
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

    /**
     * 初始化
     */
    private void init() {
        AppUtils.init(this, new ActivityLifecycleImpl());
        RouterUtils.init(this, isDebug());
    }

    //endregion

    //region: 提供方法

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