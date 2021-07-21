package com.xxl.hello.service;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;

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

    //endregion

    //region: 构造函数

    public ServiceWrapper(@NonNull final Context context) {
        super(context);
        // 构造入 DBClientKit,UploadService 等
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

    //endregion

    //region: 内部辅助方法

    //endregion

}