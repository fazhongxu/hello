package com.xxl.hello.widget.ui.preview.item;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.ui.preview.MediaPreviewNavigator;

/**
 * 多媒体预览页面视图模型
 *
 * @author xxl.
 * @date 2023/04/06.
 */
public class MediaPreviewItemModel extends BaseViewModel<MediaPreviewItemNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public MediaPreviewItemModel(@NonNull final Application application,
                                 @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}