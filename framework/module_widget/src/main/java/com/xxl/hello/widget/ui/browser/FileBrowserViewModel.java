package com.xxl.hello.widget.ui.browser;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;

/**
 * 文件浏览页面
 *
 * @author xxl.
 * @date 2023/07/21.
 */
public class FileBrowserViewModel extends BaseViewModel<FileBrowserNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public FileBrowserViewModel(@NonNull final Application application,
                                @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

}