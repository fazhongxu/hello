package com.xxl.hello.widget.ui.im.message.session.privites;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.widget.ui.im.message.session.base.BaseChatSessionViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;

/**
 * 单聊会话
 *
 * @author xxl.
 * @date 2024/6/28.
 */
public class PrivateChatSessionViewModel extends BaseChatSessionViewModel<PrivateChatSessionNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public PrivateChatSessionViewModel(@NonNull Application application,
                                       @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

    //region: 提供方法

    //endregion

}