package com.xxl.hello.user.ui;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.data.model.event.OnUserEventApi;
import com.xxl.hello.service.ui.BaseEventBusWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * 登录页面EventBus通知事件监听
 *
 * @author xxl.
 * @date 2021/7/23.
 */
public class LoginActivityEventBusWrapper extends BaseEventBusWrapper<LoginActivity> {

    //region: 构造函数

    @Inject
    public LoginActivityEventBusWrapper() {

    }

    //endregion

    //region: 用户信息更新通知事件

    /**
     * 在主线程监听用户信息更新的通知事件
     * <p>
     * 监听用户信息更新{@link LoginUserEntity} 通知事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThreed(@NonNull final OnUserEventApi.OnUpdateUserInfoEvent event) {
        final LoginActivity activity = getActivity();
        if (activity != null) {
            activity.refreshUserInfo(event.getTargetUserEntity());
        }
    }

    //endregion
}