package com.xxl.hello.service;

import android.app.Application;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;

/**
 * 服务提供组件
 * 主要处理 Service模块一些初始化功能
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public class ServiceWrapper extends ContextWrapper {

    //region: 成员变量

    /**
     * 上下文
     */
    private Application mApplication;

    /**
     * 数据服务接口集合
     */
    private final DataRepositoryKit mDataRepositoryKit;

    /**
     * 资源处理服务
     */
    private final ResourcesProcessService mResourcesProcessService;

    //endregion

    //region: 构造函数

    public ServiceWrapper(@NonNull final Application application,
                          @NonNull final DataRepositoryKit dataRepositoryKit,
                          @NonNull final ResourcesProcessService resourcesProcessService) {
        super(application);
        mApplication = application;
        mDataRepositoryKit = dataRepositoryKit;
        mResourcesProcessService = resourcesProcessService;
    }

    //endregion

    //region: 页面生命周期

    public void onCleared() {
        try {
            mResourcesProcessService.onCleared();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }

    /**
     * 用户退出
     */
    public void logout() {
        onCleared();
    }

    /**
     * 用户是否已经同意"隐私协议"
     *
     * @return
     */
    public boolean isAgreePrivacyPolicy() {
        UserRepositoryApi userRepositoryApi = mDataRepositoryKit.getUserRepositoryApi();
        return userRepositoryApi.isAgreePrivacyPolicy();
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

    //endregion

    //region: 内部辅助方法

    //endregion

}