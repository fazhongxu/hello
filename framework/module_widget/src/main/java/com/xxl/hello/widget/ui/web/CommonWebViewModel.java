package com.xxl.hello.widget.ui.web;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Web页视图模型
 *
 * @author xxl.
 * @date 2022/7/30.
 */
@HiltViewModel
public class CommonWebViewModel extends BaseViewModel<CommonWebNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    @Inject
    public CommonWebViewModel(@ForApplication final Application application,
                              @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}