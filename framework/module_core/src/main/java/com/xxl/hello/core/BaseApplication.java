package com.xxl.hello.core;

import com.xxl.hello.core.utils.AppUtils;

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
    public abstract boolean isNetworkDebug();

    //endregion

}