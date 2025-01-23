package com.xxl.hello.widget.ui.im.message.list;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;

/**
 * 消息列表
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class MessageViewModel extends BaseViewModel<MessageNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public MessageViewModel(@NonNull Application application,
                            @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

    //region: 提供方法

    //endregion

}