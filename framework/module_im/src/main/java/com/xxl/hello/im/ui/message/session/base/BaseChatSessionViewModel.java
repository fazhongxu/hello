package com.xxl.hello.im.ui.message.session.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.ui.BaseViewModel;

/**
 * 会话基础类
 *
 * @author xxl.
 * @date 2024/6/28.
 */
public class BaseChatSessionViewModel<N extends BaseChatSessionNavigator> extends BaseViewModel<N> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public BaseChatSessionViewModel(@NonNull Application application) {
        super(application);
    }

    //endregion

    //region: 提供方法

    //endregion

}