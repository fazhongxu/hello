package com.xxl.hello.service;

import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public abstract class BaseApplication extends DaggerApplication {
    
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    public String getCurrentUserId() {
        // TODO: 2021/7/20  
        return "";
    }
    
}