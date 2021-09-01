package com.xxl.hello.service;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;

import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.xxl.hello.core.image.selector.MediaSelector;
import com.xxl.hello.core.image.selector.MediaSelectorApp;
import com.xxl.hello.core.image.selector.PictureSelectorEngineImpl;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;

/**
 * 服务提供组件
 * 主要处理 Service模块一些初始化功能
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public class ServiceWrapper extends ContextWrapper implements MediaSelectorApp {

    //region: 成员变量

    /**
     * 上下文
     */
    private Application mApplication;

    /**
     * 数据服务接口集合
     */
    private final DataRepositoryKit mDataRepositoryKit;

    //endregion

    //region: 构造函数

    public ServiceWrapper(@NonNull final Application application,
                          @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
        mApplication = application;
        mDataRepositoryKit = dataRepositoryKit;

    }

    //endregion

    //region: 提供方法

    /**
     * 初始化操作
     *
     * @param application
     */
    public void init(@NonNull final Application application) {
        mApplication = application;
        MediaSelector.init(this);
    }


    /**
     * 获取当前用户ID
     *
     * @return
     */
    public String getCurrentUserId() {
        final LoginUserEntity currentLoginUserEntity = getCurrentLoginUserEntity();
        if (currentLoginUserEntity != null) {
            return currentLoginUserEntity.getUserId();
        }
        return null;
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    public LoginUserEntity getCurrentLoginUserEntity() {
        final UserRepositoryApi userRepositoryApi = mDataRepositoryKit.getUserRepositoryApi();
        return userRepositoryApi.getCurrentLoginUserEntity();
    }

    /**
     * Application
     *
     * @return
     */
    @Override
    public Context getAppContext() {
        return mApplication;
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

    //region: 内部辅助方法

    //endregion

}