package com.xxl.hello;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;

import com.xxl.hello.service.ServiceWrapper;

/**
 * Application 包装类
 *
 * @author xxl.
 * @date 2021/7/21.
 */
public class HelloApplicationWrapper extends ContextWrapper {

    //region: 成员变量

    /**
     * 上下文
     */
    private Application mApplication;

    /**
     * 服务提供组件
     */
    private ServiceWrapper mServiceWrapper;

    //endregion

    //region: 构造函数

    public HelloApplicationWrapper(@NonNull final Context context,
                                   @NonNull final ServiceWrapper serviceWrapper) {
        super(context);
        mServiceWrapper = serviceWrapper;
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
        mServiceWrapper.init(application);
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}