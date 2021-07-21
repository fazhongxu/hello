package com.xxl.hello.service;

import com.xxl.hello.common.utils.AppUtils;

import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public abstract class BaseApplication extends DaggerApplication {
    
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
    }
    
    public String getCurrentUserId() {
        // TODO: 2021/7/20  
        return "";
    }
    
}