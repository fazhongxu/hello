package com.xxl.hello.widget.ui.browser;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * 文件浏览页面
 *
 * @author xxl.
 * @date 2023/07/21.
 */
@HiltViewModel
public class FileBrowserViewModel extends BaseViewModel<FileBrowserNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    @Inject
    public FileBrowserViewModel(@ForApplication final Application application,
                                @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

}