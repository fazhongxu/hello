package com.xxl.hello.service.data.repository.impl;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

/**
 * @author xxl.
 * @date 2021/7/23.
 */
public class BaseRepositoryIml {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public BaseRepositoryIml() {

    }

    //endregion

    //region: 提供方法

    /**
     * 发送EventBus通知事件
     *
     * @param object
     */
    public void postEventBus(@NonNull final Object object) {
        EventBus.getDefault().post(object);
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}