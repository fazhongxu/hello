package com.xxl.hello.main.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.ui.BaseViewModel;

/**
 * 启动页数据模型
 *
 * @author xxl
 * @date 2021/08/13.
 */
public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    //region: 成员变量

    /**
     * 数据服务接口集合
     */
    private final DataRepositoryKit mDataRepositoryKit;

    //endregion

    //region: 构造函数

    public SplashViewModel(@NonNull final Application application,
                           @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
    }

    //endregion


}
