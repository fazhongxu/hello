package com.xxl.hello.service.ui;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 * Event Bus 事件监听包装基础类
 *
 * @author xxl.
 * @date 2021/7/23.
 */
public abstract class BaseEventBusWrapper<T extends BaseActivity> {

    //region: 成员变量

    private WeakReference<T> mWeakReference;

    //endregion

    //region: 构造函数

    public BaseEventBusWrapper() {

    }

    //endregion

    //region: 提供方法

    /**
     * 注测EventBus事件监听
     *
     * @param object
     */
    public void register(T object) {
        mWeakReference = new WeakReference<>(object);
        EventBus.getDefault().register(this);
    }

    /**
     * 注销EventBus事件监听
     */
    public void unRegister() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    public T getActivity() {
        return mWeakReference.get();
    }

    //endregion
}