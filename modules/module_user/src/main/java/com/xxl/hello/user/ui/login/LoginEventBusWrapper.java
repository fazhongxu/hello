package com.xxl.hello.user.ui.login;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.model.event.OnUserEventApi;
import com.xxl.core.ui.BaseEventBusWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * 登录页面EventBus通知事件监听
 *
 * @author xxl.
 * @date 2021/7/23.
 */
public class LoginEventBusWrapper extends BaseEventBusWrapper<LoginFragment> {

    //region: 构造函数

    @Inject
    public LoginEventBusWrapper() {

    }

    //endregion

    //region: 用户信息更新通知事件

    /**
     * 监听{@link OnUserEventApi.OnUpdateUserInfoEvent} 通知事件
     * 通知 {@link LoginUserEntity} 数据更新
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThreed(@NonNull final OnUserEventApi.OnUpdateUserInfoEvent event) {
        final LoginFragment fragment = getFragment();
        if (fragment != null) {
            fragment.refreshUserInfo(event.getTargetUserEntity());
        }
    }

    //endregion
}